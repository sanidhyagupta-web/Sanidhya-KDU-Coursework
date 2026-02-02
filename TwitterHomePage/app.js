/* =========================
   DOM
========================= */
const openComposerBtn = document.querySelector("#btn");
const composer = document.querySelector(".composer");
const closeBtn = document.querySelector(".close");
const feed = document.querySelector("#feed");

const galleryBtn = document.querySelector("#galleryBtn");
const galleryInput = document.querySelector("#galleryInput");
const postBtn = document.querySelector(".post-btn");
const postText = document.querySelector("#postText");
const mediaPreview = document.querySelector("#mediaPreview");

const mq = window.matchMedia("(max-width: 414px)");
const premium = document.querySelector(".prem");

const sidebar = document.querySelector(".container");
const overlay = document.querySelector(".overlay");
const menuBtn = document.querySelector(".menu-btn");
const plusBtn = document.querySelector(".plus-btn");

/* =========================
   State
========================= */
let selectedFiles = [];

/* =========================
   Sidebar (Mobile)
========================= */
function openSidebar() {
  sidebar?.classList.add("open");
  overlay?.classList.add("show");
}

function closeSidebar() {
  sidebar?.classList.remove("open");
  overlay?.classList.remove("show");
}

menuBtn?.addEventListener("click", () => {
  if (!mq.matches) return;
  if (sidebar?.classList.contains("open")) {
    closeSidebar();
  } else {
    openSidebar();
  }
});

// Close drawer when clicking overlay
overlay?.addEventListener("click", (e) => {
  e.stopPropagation();
  if (overlay.classList.contains("show")) {
    closeSidebar();
  }
});

// Close drawer with Escape key
document.addEventListener("keydown", (e) => {
  if (e.key === "Escape" && overlay?.classList.contains("show")) {
    closeSidebar();
  }
});

// Close sidebar when clicking any link in sidebar
sidebar?.addEventListener("click", (e) => {
  if (e.target.tagName === "A" || e.target.closest("a")) {
    closeSidebar();
  }
});

mq.addEventListener("change", (e) => {
  if (!e.matches) closeSidebar();
});

/* =========================
   Composer show/hide
========================= */
function updateFeedPadding() {
  if (!feed || !composer) return;
  if (composer.style.display === "block") {
    // Composer is open, add padding to feed so tweets appear below
    const composerHeight = composer.offsetHeight;
    feed.style.paddingTop = (composerHeight + 12) + "px";
  } else {
    // Composer is closed, reset padding
    feed.style.paddingTop = "12px";
  }
}

openComposerBtn?.addEventListener("click", () => {
  composer.style.display = "block";
  updateFeedPadding();
});

plusBtn?.addEventListener("click", () => {
  composer.style.display = "block";
  updateFeedPadding();
});

closeBtn?.addEventListener("click", () => {
  composer.style.display = "none";
  updateFeedPadding();
});

/* Premium toggle */
function handleMobileChange(e) {
  if (!premium) return;
  premium.style.display = e.matches ? "none" : "block";
}
mq.addEventListener("change", handleMobileChange);
handleMobileChange(mq);

/* =========================
   Media picker + preview
========================= */
galleryBtn?.addEventListener("click", () => galleryInput?.click());

galleryInput?.addEventListener("change", (e) => {
  selectedFiles = Array.from(e.target.files || []);
  renderPreview();
});

function renderPreview() {
  if (!mediaPreview) return;

  mediaPreview.innerHTML = "";

  if (selectedFiles.length === 0) {
    mediaPreview.style.display = "none";
    return;
  }

  mediaPreview.style.display = "grid";

  selectedFiles.forEach((file) => {
    const url = URL.createObjectURL(file);

    if (file.type.startsWith("image/")) {
      const img = document.createElement("img");
      img.src = url;
      img.alt = file.name;
      mediaPreview.appendChild(img);
    } else if (file.type.startsWith("video/")) {
      const video = document.createElement("video");
      video.src = url;
      video.controls = true;
      video.playsInline = true;
      mediaPreview.appendChild(video);
    }
  });
}

/* =========================
   Helpers
========================= */
function timeNow() {
  const d = new Date();
  return d.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
}

function escapeHtml(str) {
  return str
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}

/* =========================
   Post tweet
========================= */
postBtn?.addEventListener("click", () => {
  const text = (postText?.value || "").trim();
  if (!text && selectedFiles.length === 0) return;

  const tweet = document.createElement("div");
  tweet.className = "tweet";

  tweet.innerHTML = `
    <img class="avatar" src="./icons/profile pic.png" alt="avatar" />

    <div class="tweet-body">
      <div class="tweet-header">
        <span class="tweet-name">Nitish Gupta</span>
        <span class="tweet-handle">@nit_hck</span>
        <span class="tweet-time">· ${timeNow()}</span>
      </div>

      ${text ? `<div class="tweet-text">${escapeHtml(text)}</div>` : ""}

      <div class="tweet-media"></div>

      <div class="tweet-actions">
        <button type="button" class="action-btn" data-action="comment">
          <img src="./icons/comment.svg" alt="">
          <span class="count comment-cnt">0</span>
        </button>

        <button type="button" class="action-btn" data-action="retweet">
          <img src="./icons/retweet.svg" alt="">
          <span class="count retweet-cnt">0</span>
        </button>

        <button type="button" class="action-btn" data-action="like">
          <img src="./icons/like.svg" alt="">
          <span class="count like-cnt">0</span>
        </button>

        <button type="button" class="action-btn" data-action="share">
          <img src="./icons/share.svg" alt="">
          <span class="count share-cnt">0</span>
        </button>

        <button type="button" class="action-btn" data-action="stats">
          <img src="./icons/stats.svg" alt="">
          <span class="count stats-cnt">0</span>
        </button>

        <button type="button" class="action-btn" data-action="bookmark">
          <img src="./icons/bookmark-grey.svg" alt="">
        </button>
      </div>
    </div>
  `;

  const mediaBox = tweet.querySelector(".tweet-media");

  if (selectedFiles.length === 0) {
    mediaBox?.remove();
  } else {
    selectedFiles.forEach((file) => {
      const url = URL.createObjectURL(file);

      if (file.type.startsWith("image/")) {
        const img = document.createElement("img");
        img.src = url;
        img.alt = file.name;
        mediaBox?.appendChild(img);
      } else if (file.type.startsWith("video/")) {
        const video = document.createElement("video");
        video.src = url;
        video.controls = true;
        video.playsInline = true;
        mediaBox?.appendChild(video);
      }
    });
  }

  feed?.prepend(tweet);

  // reset
  if (postText) postText.value = "";
  selectedFiles = [];
  if (galleryInput) galleryInput.value = "";
  renderPreview();
  composer.style.display = "none";
  
  // Scroll feed to top on mobile to show new tweet
  feed?.scrollTo(0, 0);
});

/* =========================
   Actions (event delegation)
========================= */
feed?.addEventListener("click", (e) => {
  const btn = e.target.closest(".action-btn");
  if (!btn) return;

  const tweet = btn.closest(".tweet");
  const action = btn.dataset.action;

  const countEl = btn.querySelector(".count");
  const current = Number(countEl.textContent || 0);

  if (action === "like") {
    const img = btn.querySelector("img");
    const active = btn.classList.toggle("active");

    countEl.textContent = active ? current + 1 : Math.max(0, current - 1);
    img.src = active ? "./icons/like-pink.svg" : "./icons/like.svg";
  }

  if (action === "retweet") {
    const img = btn.querySelector("img");
    const active = btn.classList.toggle("active");

    countEl.textContent = active ? current + 1 : Math.max(0, current - 1);
    img.src = active ? "./icons/retweet-blue.svg" : "./icons/retweet.svg";
  }

  if (action === "comment") {
    let box = tweet.querySelector(".comment-box");

    if (box) {
      box.remove();
      return;
    }

    box = document.createElement("div");
    box.className = "comment-box";
    box.innerHTML = `
      <div class="comment-row">
        <img class="profile" src="./icons/profile pic.png" alt="">
        <textarea class="comment-input" placeholder="Post your reply" rows="2"></textarea>
        <button class="comment-send" type="button">Reply</button>
      </div>
    `;

    tweet.querySelector(".tweet-body").appendChild(box);

    const sendBtn = box.querySelector(".comment-send");
    const input = box.querySelector(".comment-input");

    sendBtn.addEventListener("click", () => {
      const val = input.value.trim();
      if (!val) return;

      const reply = document.createElement("div");
      reply.className = "comment-item";
      reply.textContent = val;

      box.appendChild(reply);
      input.value = "";

      countEl.textContent = Number(countEl.textContent || 0) + 1;
    });
  }

  if (action === "share") {
    const img = btn.querySelector("img");
    const active = btn.classList.toggle("active");

    countEl.textContent = active ? current + 1 : Math.max(0, current - 1);
  }

  if(action === "stats") {
    const active = btn.classList.toggle("active");

    countEl.textContent = active ? current + 1 : Math.max(0, current - 1);
  }

  if (action === "bookmark") {
    const img = btn.querySelector("img");
    const active = btn.classList.toggle("active");

    img.src = active ? "./icons/bookmark-icon.svg" : "./icons/bookmark-grey.svg";
  }

});
