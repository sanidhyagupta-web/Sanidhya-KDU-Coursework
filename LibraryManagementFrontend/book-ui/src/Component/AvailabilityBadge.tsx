export function AvailabilityBadge({ available }: { available: boolean }) {
  return (
    <span className={`badge ${available ? "badge--ok" : "badge--no"}`}>
      {available ? "Available" : "Unavailable"}
    </span>
  );
}