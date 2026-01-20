package com.RailwayBooking.BackendHomework8.Brokers;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TopicBroker {

    private final Map<String, CopyOnWriteArrayList<AckConsumer<Envelope<Object>>>> subscribers = new ConcurrentHashMap<>();
    private final Map<String, BlockingQueue<Envelope<Object>>> queues = new ConcurrentHashMap<>();
    private final Map<String, InFlight> inFlight = new ConcurrentHashMap<>();

    // 🔥 Add this (for DLQ / debug inspection)
    private final Map<String, BlockingQueue<Object>> storedQueues = new ConcurrentHashMap<>();

    private final ExecutorService executor;
    private final ScheduledExecutorService watchdog = Executors.newSingleThreadScheduledExecutor();

    private final Duration ackTimeout;

    public TopicBroker(int workers, Duration ackTimeout) {
        this.ackTimeout = ackTimeout;

        this.executor = Executors.newFixedThreadPool(workers, new ThreadFactory() {
            private final AtomicInteger n = new AtomicInteger(1);
            @Override public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("broker-worker-" + n.getAndIncrement());
                t.setDaemon(true);
                return t;
            }
        });

        watchdog.scheduleAtFixedRate(this::requeueExpiredInFlight, 200, 200, TimeUnit.MILLISECONDS);

        // Ensure DLQ exists for inspection
        storedQueues.putIfAbsent("booking-error-queue", new LinkedBlockingQueue<>());
    }

    public void subscribe(String topic, AckConsumer<Envelope<Object>> handler) {
        subscribers.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>()).add(handler);
        queues.computeIfAbsent(topic, k -> new LinkedBlockingQueue<>());
        startDispatcher(topic);
    }

    public void publish(String topic, String messageId, Object payload) {
        queues.computeIfAbsent(topic, k -> new LinkedBlockingQueue<>());
        queues.get(topic).offer(new Envelope<>(messageId, payload, Instant.now()));

        // Store if this topic is “inspectable” (DLQ etc.)
        BlockingQueue<Object> store = storedQueues.get(topic);
        if (store != null) store.offer(payload);
    }

    public void publish(String topic, Object payload) {
        publish(topic, UUID.randomUUID().toString(), payload);
    }

    // Optional: enable storage for any topic
    public void enableStorageForTopic(String topic) {
        storedQueues.putIfAbsent(topic, new LinkedBlockingQueue<>());
    }

    private final ConcurrentHashMap<String, Boolean> started = new ConcurrentHashMap<>();

    private void startDispatcher(String topic) {
        if (started.putIfAbsent(topic, true) != null) return;

        executor.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Envelope<Object> env = queues.get(topic).take();

                    for (var sub : subscribers.getOrDefault(topic, new CopyOnWriteArrayList<>())) {
                        deliver(topic, env, sub);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // ✅ restore interrupt
                    return; // ✅ exit cleanly
                }
            }
        });
    }

    private void deliver(String topic, Envelope<Object> env, AckConsumer<Envelope<Object>> sub) {
        String deliveryKey = topic + "|" + System.identityHashCode(sub) + "|" + env.getMessageId();
        inFlight.put(deliveryKey, new InFlight(topic, env, sub, Instant.now()));

        AckToken ack = new AckToken(() -> inFlight.remove(deliveryKey));

        executor.submit(() -> {
            try {
                sub.onMessage(env, ack);
                // consumer must call ack.ack()
            } catch (Exception e) {
                System.out.printf("[BROKER] Consumer threw exception for messageId=%s topic=%s : %s%n",
                        env.getMessageId(), topic, e.getMessage());
            }
        });
    }

    private void requeueExpiredInFlight() {
        Instant now = Instant.now();

        for (var entry : inFlight.entrySet()) {
            InFlight inf = entry.getValue();

            if (Duration.between(inf.deliveredAt, now).compareTo(ackTimeout) >= 0) {
                System.out.printf("[BROKER] Ack timeout. Redelivering messageId=%s topic=%s%n",
                        inf.env.getMessageId(), inf.topic);

                inFlight.remove(entry.getKey());
                queues.get(inf.topic).offer(inf.env);
            }
        }
    }

    // ✅ inspect DLQ / stored topics
    public List<Object> drain(String topic) {
        BlockingQueue<Object> q = storedQueues.get(topic);
        if (q == null) return List.of();

        List<Object> out = new ArrayList<>();
        q.drainTo(out);
        return out;
    }

    private static class InFlight {
        final String topic;
        final Envelope<Object> env;
        final AckConsumer<Envelope<Object>> sub;
        final Instant deliveredAt;

        InFlight(String topic, Envelope<Object> env, AckConsumer<Envelope<Object>> sub, Instant deliveredAt) {
            this.topic = topic;
            this.env = env;
            this.sub = sub;
            this.deliveredAt = deliveredAt;
        }
    }
}
