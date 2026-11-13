/**
 * JOURNEY AI — Mobile-First Social Travel, Locket Camera & Footprint Map
 * 5 Clear Tabs:
 *  1. Trang chủ (home): Xem Tin (Stories) & Bài đăng của bạn bè hoặc của mình
 *  2. Khám phá (explore): Bản đồ những nơi đã đi / đã đến (Leaflet custom markers & popups)
 *  3. Ống kính (discover): Chuyên chụp ảnh khoảnh khắc kiểu Locket (1:1 viewfinder, shutter, flip, đăng ngay)
 *  4. Nhật ký (journal): Những bức ảnh & địa điểm mình đã check-in
 *  5. Hồ sơ (profile): Thay đổi Avatar, Tên hiển thị & Thông tin cá nhân
 */

const DESTINATIONS = {
  halong: {
    id: 'halong',
    rank: '#01 TRENDING',
    nameVi: 'Vịnh Hạ Long',
    nameEn: 'Ha Long Bay',
    province: 'Quảng Ninh',
    categoryVi: 'Di sản Thiên nhiên UNESCO',
    rating: '4.9',
    visitedDate: '12/11/2026',
    coords: '20.9101° N, 107.1839° E',
    lat: 20.9101,
    lng: 107.1839,
    pinIcon: '⛵',
    image: 'images/img_hero_halong_bay.jpg',
    shortDescVi:
      'Hơn 1.600 đảo đá vôi kỳ vĩ vươn lên từ làn nước ngọc lục bảo, nơi lưu giữ dấu ấn địa chất 500 triệu năm.',
    storyVi:
      'Vịnh Hạ Long là kiệt tác kiến tạo địa chất suốt hơn 500 triệu năm. Theo truyền thuyết, đàn Rồng thiêng đã đáp xuống vùng biển Đông Bắc, phun châu nhả ngọc tạo thành bức tường thành đá vôi kỳ vĩ.'
  },
  hoian: {
    id: 'hoian',
    rank: '#02 TRENDING',
    nameVi: 'Phố Cổ Hội An',
    nameEn: 'Hoi An',
    province: 'Quảng Nam',
    categoryVi: 'Thương cảng Di sản Cổ',
    rating: '4.9',
    visitedDate: '05/11/2026',
    coords: '15.8801° N, 108.3380° E',
    lat: 15.8801,
    lng: 108.338,
    pinIcon: '🏮',
    image: 'images/img_hoian_lanterns.jpg',
    shortDescVi:
      'Thương cảng quốc tế thế kỷ XVI–XVII với những dãy nhà tường vàng cổ kính và sông Hoài rực rỡ đèn lồng lụa.',
    storyVi:
      'Từ thế kỷ XVI đến XVII, Hội An từng là một trong những thương cảng sầm uất bậc nhất Đông Nam Á. Khi màn đêm buông xuống, hàng ngàn chiếc đèn lồng thủ công thắp sáng dòng sông Hoài.'
  },
  hanoi_temple: {
    id: 'hanoi_temple',
    rank: '#03 TRENDING',
    nameVi: 'Văn Miếu – Quốc Tử Giám',
    nameEn: 'Temple of Literature',
    province: 'Hà Nội',
    categoryVi: 'Trường Đại học Đầu tiên',
    rating: '4.8',
    visitedDate: '28/10/2026',
    coords: '21.0278° N, 105.8355° E',
    lat: 21.0278,
    lng: 105.8355,
    pinIcon: '⛩️',
    image: 'images/img_hanoi_temple.jpg',
    shortDescVi:
      'Được xây dựng năm 1070, biểu tượng ngàn năm của truyền thống hiếu học với Khuê Văn Các và 82 bia Tiến sĩ.',
    storyVi:
      'Được khởi dựng vào năm 1070 dưới triều vua Lý Thánh Tông, Văn Miếu – Quốc Tử Giám là trường đại học quốc gia đầu tiên của Việt Nam với 82 tấm bia Tiến sĩ đặt trên lưng rùa đá.'
  },
  sapa: {
    id: 'sapa',
    rank: '#04 TRENDING',
    nameVi: 'Ruộng Bậc Thang Sa Pa',
    nameEn: 'Sa Pa',
    province: 'Lào Cai',
    categoryVi: 'Kiệt tác Nông nghiệp Vùng cao',
    rating: '4.9',
    visitedDate: '19/10/2026',
    coords: '22.3364° N, 103.8438° E',
    lat: 22.3364,
    lng: 103.8438,
    pinIcon: '🌾',
    image: 'images/img_sapa_terraces.jpg',
    shortDescVi:
      'Những nấc thang vàng óng chạm mây do bàn tay người H’Mông và Dao kiến tạo trên sườn núi Hoàng Liên Sơn.',
    storyVi:
      'Trên những sườn núi dốc đứng ở độ cao hơn 1.500 mét, đồng bào H’Mông và Dao Đỏ đã biến địa hình hiểm trở thành những đường vân sóng lúa uốn lượn tuyệt mỹ.'
  }
};

const FRIEND_STORIES = {
  lan_anh: {
    id: 'lan_anh',
    author: 'Lan Anh',
    avatarText: 'LA',
    time: '18 phút trước',
    placeName: 'Phố Cổ Hội An',
    placeId: 'hoian',
    image: 'images/img_hoian_lanterns.jpg',
    caption: 'Đêm phố cổ lung linh đèn lồng lụa, thả hoa đăng trên sông Hoài 🏮✨'
  },
  hoang_nam: {
    id: 'hoang_nam',
    author: 'Hoàng Nam',
    avatarText: 'HN',
    time: '1 giờ trước',
    placeName: 'Vịnh Hạ Long',
    placeId: 'halong',
    image: 'images/img_hero_halong_bay.jpg',
    caption: 'Bình minh trên boong tàu giữa kỳ quan thiên nhiên thế giới ⛵🌊'
  },
  thao_tien: {
    id: 'thao_tien',
    author: 'Thảo Tiên',
    avatarText: 'TT',
    time: '3 giờ trước',
    placeName: 'Ruộng Bậc Thang Sa Pa',
    placeId: 'sapa',
    image: 'images/img_sapa_terraces.jpg',
    caption: 'Săn mây buổi sớm trên thung lũng Mường Hoa - Sa Pa 🌾☁️'
  },
  tuan_kiet: {
    id: 'tuan_kiet',
    author: 'Tuấn Kiệt',
    avatarText: 'TK',
    time: '5 giờ trước',
    placeName: 'Văn Miếu – Quốc Tử Giám',
    placeId: 'hanoi_temple',
    image: 'images/img_hanoi_temple.jpg',
    caption: 'Chiều thu Hà Nội ghé thăm Khuê Văn Các cổ kính ⛩️🍂'
  }
};

const DEFAULT_POSTS = [
  {
    id: 'post-1',
    isMine: true,
    authorName: 'Minh Nguyễn',
    authorHandle: '@minh.journey',
    avatarText: 'MN',
    avatarImg: '',
    placeId: 'halong',
    placeName: 'Vịnh Hạ Long, Quảng Ninh',
    time: 'Vừa xong',
    dateStr: '14/11/2026',
    image: 'images/img_hero_halong_bay.jpg',
    caption: 'Chèo kayak xuyên hang Luồn, nước trong xanh như ngọc bích! ⛵✨',
    likes: 28,
    liked: true,
    comments: [
      { user: 'Lan Anh', text: 'Ảnh nét quá Minh ơi! Góc chụp đỉnh thật 😍' },
      { user: 'Hoàng Nam', text: 'Cuối tuần này mình cũng ra Hạ Long nè!' }
    ]
  },
  {
    id: 'post-2',
    isMine: false,
    authorName: 'Lan Anh',
    authorHandle: '@lananh.wander',
    avatarText: 'LA',
    avatarImg: '',
    placeId: 'hoian',
    placeName: 'Phố Cổ Hội An, Quảng Nam',
    time: '42 phút trước',
    dateStr: '14/11/2026',
    image: 'images/img_hoian_lanterns.jpg',
    caption: 'Một góc phố đèn lồng rực rỡ bên bờ sông Hoài đêm rằm 🏮💛',
    likes: 64,
    liked: false,
    comments: [
      { user: 'Thảo Tiên', text: 'Nhớ món Cao lầu Hội An quá đi!' }
    ]
  },
  {
    id: 'post-3',
    isMine: true,
    authorName: 'Minh Nguyễn',
    authorHandle: '@minh.journey',
    avatarText: 'MN',
    avatarImg: '',
    placeId: 'sapa',
    placeName: 'Bản Cát Cát — Sa Pa, Lào Cai',
    time: 'Hôm qua',
    dateStr: '13/11/2026',
    image: 'images/img_sapa_terraces.jpg',
    caption: 'Mùa vàng trên rẻo cao Tây Bắc, không khí se lạnh 14°C cực thích 🌾🏔️',
    likes: 45,
    liked: true,
    comments: []
  },
  {
    id: 'post-4',
    isMine: false,
    authorName: 'Hoàng Nam',
    authorHandle: '@nam.explorer',
    avatarText: 'HN',
    avatarImg: '',
    placeId: 'hanoi_temple',
    placeName: 'Văn Miếu – Quốc Tử Giám, Hà Nội',
    time: '2 ngày trước',
    dateStr: '12/11/2026',
    image: 'images/img_hanoi_temple.jpg',
    caption: 'Nét kiến trúc cổ kính gần 1.000 năm tuổi giữa lòng thủ đô ⛩️📖',
    likes: 51,
    liked: false,
    comments: []
  }
];

const DEFAULT_CHECKINS = [
  {
    id: 'chk-1',
    placeId: 'halong',
    placeName: 'Vịnh Hạ Long',
    province: 'Quảng Ninh',
    date: '14/11/2026',
    image: 'images/img_hero_halong_bay.jpg',
    caption: 'Chèo kayak xuyên hang Luồn, nước trong xanh như ngọc bích! ⛵✨'
  },
  {
    id: 'chk-2',
    placeId: 'sapa',
    placeName: 'Ruộng Bậc Thang Sa Pa',
    province: 'Lào Cai',
    date: '13/11/2026',
    image: 'images/img_sapa_terraces.jpg',
    caption: 'Mùa vàng trên rẻo cao Tây Bắc, không khí se lạnh 14°C 🌾🏔️'
  },
  {
    id: 'chk-3',
    placeId: 'hoian',
    placeName: 'Phố Cổ Hội An',
    province: 'Quảng Nam',
    date: '05/11/2026',
    image: 'images/img_hoian_lanterns.jpg',
    caption: 'Thả đèn hoa đăng trên dòng sông Hoài 🏮'
  },
  {
    id: 'chk-4',
    placeId: 'hanoi_temple',
    placeName: 'Văn Miếu – Quốc Tử Giám',
    province: 'Hà Nội',
    date: '28/10/2026',
    image: 'images/img_hanoi_temple.jpg',
    caption: 'Ghé thăm Khuê Văn Các ngày thu Hà Nội ⛩️'
  }
];

class JourneyAppController {
  constructor() {
    this.activeTab = 'home';
    this.feedFilter = 'all'; // 'all' | 'friends' | 'mine'
    this.theme = localStorage.getItem('journey_theme') || 'light';

    // Load User Profile from localStorage
    const savedProfile = this.loadJson('journey_user_profile', null);
    this.profile = savedProfile || {
      name: 'Minh Nguyễn',
      username: '@minh.journey',
      bio: 'Yêu dịch chuyển, thích chụp ảnh khoảnh khắc Locket và lưu dấu bản đồ Việt Nam 🇻🇳✨',
      city: 'Hà Nội, Việt Nam',
      email: 'minh.nguyen@journey.ai',
      avatarImg: '',
      avatarText: 'MN'
    };

    // Load Posts & Check-ins
    this.posts = this.loadJson('journey_feed_posts', DEFAULT_POSTS);
    this.checkins = this.loadJson('journey_user_checkins', DEFAULT_CHECKINS);
    this.customPlaces = this.loadJson('journey_custom_places', []);

    // Locket Camera State
    this.cameraStream = null;
    this.cameraFacingMode = 'environment';
    this.capturedDataUrl = null;
    this.selectedPlaceId = 'halong';
    this.fallbackPreviewIndex = 0;

    // Leaflet Map Instance
    this.leafletMap = null;
    this.leafletMarkers = {};

    this.init();
  }

  loadJson(key, fallback) {
    try {
      const raw = localStorage.getItem(key);
      return raw ? JSON.parse(raw) : fallback;
    } catch (_e) {
      return fallback;
    }
  }

  saveJson(key, value) {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch (_e) {
      // Ignore quota errors
    }
  }

  init() {
    this.applyTheme(this.theme);
    this.syncHeaderProfile();
    this.renderHomeTab();
  }

  applyTheme(theme) {
    this.theme = theme === 'dark' ? 'dark' : 'light';
    document.documentElement.setAttribute('data-theme', this.theme);
    localStorage.setItem('journey_theme', this.theme);
    const btnTheme = document.getElementById('btnThemeToggle');
    if (btnTheme) {
      btnTheme.textContent = this.theme === 'dark' ? '☀️' : '🌙';
    }
  }

  toggleTheme() {
    this.applyTheme(this.theme === 'dark' ? 'light' : 'dark');
    this.showToast(this.theme === 'dark' ? '🌙 Đã bật giao diện Tối' : '☀️ Đã bật giao diện Sáng');
  }

  getInitials(name) {
    if (!name) return 'MN';
    const parts = name.trim().split(/\s+/);
    if (parts.length === 1) return parts[0].slice(0, 2).toUpperCase();
    return (parts[0][0] + parts[parts.length - 1][0]).toUpperCase();
  }

  syncHeaderProfile() {
    this.profile.avatarText = this.getInitials(this.profile.name);
    const sub = document.getElementById('headerUserSubtitle');
    if (sub) {
      sub.textContent = `${this.profile.name} · ${this.profile.username}`;
    }
    const avatarCircle = document.getElementById('headerAvatarCircle');
    if (avatarCircle) {
      if (this.profile.avatarImg) {
        avatarCircle.innerHTML = `<img src="${this.profile.avatarImg}" alt="${this.profile.name}" />`;
      } else {
        avatarCircle.textContent = this.profile.avatarText;
      }
    }
  }

  switchTab(tabId) {
    if (this.activeTab === 'discover' && tabId !== 'discover') {
      this.stopCameraStream();
    }
    this.activeTab = tabId;

    document.querySelectorAll('.nav-tab').forEach((btn) => {
      btn.classList.toggle('active', btn.getAttribute('data-tab') === tabId);
    });

    const viewport = document.getElementById('mainViewport');
    if (viewport) viewport.scrollTop = 0;

    if (tabId === 'home') {
      this.renderHomeTab();
    } else if (tabId === 'explore') {
      this.renderExploreTab();
    } else if (tabId === 'discover') {
      this.renderLocketCameraTab();
    } else if (tabId === 'journal') {
      this.renderJournalTab();
    } else if (tabId === 'profile') {
      this.renderProfileTab();
    }
  }

  /* =========================================================================
     TAB 1: TRANG CHỦ — XEM TIN (STORIES) & BÀI ĐĂNG BẠN BÈ / CỦA MÌNH
     ========================================================================= */
  renderHomeTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    const filteredPosts = this.posts.filter((p) => {
      if (this.feedFilter === 'mine') return p.isMine;
      if (this.feedFilter === 'friends') return !p.isMine;
      return true;
    });

    const storiesHtml = Object.values(FRIEND_STORIES)
      .map(
        (st) => `
        <div class="story-item" onclick="window.journeyApp.openStoryModal('${st.id}')">
          <div class="story-ring">
            <div class="story-avatar">
              <img src="${st.image}" alt="${st.author}" />
            </div>
          </div>
          <span class="story-name">${st.author}</span>
        </div>
      `
      )
      .join('');

    const postsHtml = filteredPosts
      .map((post) => {
        const displayAuthor = post.isMine ? this.profile.name : post.authorName;
        const displayAvatarImg = post.isMine ? this.profile.avatarImg : post.avatarImg;
        const displayAvatarText = post.isMine ? this.profile.avatarText : post.avatarText;

        const avatarHtml = displayAvatarImg
          ? `<img src="${displayAvatarImg}" alt="${displayAuthor}" />`
          : displayAvatarText;

        const commentsPreview =
          post.comments && post.comments.length > 0
            ? `<div style="margin-top:6px;padding-top:6px;border-top:1px dashed var(--border-subtle);display:flex;flex-direction:column;gap:4px;">
                ${post.comments
                  .slice(-2)
                  .map(
                    (c) =>
                      `<div style="font-size:0.78rem;color:var(--text-secondary);"><strong style="color:var(--text-primary);">${c.user}:</strong> ${c.text}</div>`
                  )
                  .join('')}
              </div>`
            : '';

        return `
        <article class="feed-post-card">
          <div class="feed-post-header">
            <div class="feed-author-row">
              <div class="feed-author-avatar">${avatarHtml}</div>
              <div class="feed-author-info">
                <div class="feed-author-name">
                  ${displayAuthor}
                  ${post.isMine ? '<span style="font-size:0.66rem;padding:2px 6px;border-radius:999px;background:var(--accent-gold-soft);color:var(--accent-gold);margin-left:4px;">Bạn</span>' : ''}
                </div>
                <div class="feed-post-meta" onclick="window.journeyApp.openDestinationModal('${post.placeId || 'halong'}')" style="cursor:pointer;">
                  <span>📍 ${post.placeName}</span>
                  <span>· ${post.time}</span>
                </div>
              </div>
            </div>
            <button class="feed-action-btn" onclick="window.journeyApp.playDestinationAudio('${post.placeId || 'halong'}')" title="Nghe thuyết minh địa điểm">
              🔊
            </button>
          </div>

          <div class="feed-photo-wrap" ondblclick="window.journeyApp.toggleLikePost('${post.id}')">
            <img src="${post.image}" alt="${post.placeName}" class="feed-photo-img" loading="lazy" />
            <div class="feed-locket-caption-pill">${post.caption}</div>
          </div>

          <div class="feed-post-footer">
            <div class="feed-actions-bar">
              <div class="feed-actions-left">
                <button class="feed-action-btn ${post.liked ? 'liked' : ''}" onclick="window.journeyApp.toggleLikePost('${post.id}')">
                  <span>${post.liked ? '❤️' : '🤍'}</span>
                  <span>${post.likes}</span>
                </button>
                <button class="feed-action-btn" onclick="window.journeyApp.openCommentModal('${post.id}')">
                  <span>💬</span>
                  <span>${post.comments ? post.comments.length : 0} bình luận</span>
                </button>
              </div>
              <button class="feed-action-btn" onclick="window.journeyApp.viewPlaceOnMap('${post.placeId || 'halong'}')">
                <span>🗺️</span>
                <span>Xem bản đồ</span>
              </button>
            </div>
            ${commentsPreview}
          </div>
        </article>
      `;
      })
      .join('');

    const trendingCardsHtml = Object.values(DESTINATIONS)
      .map(
        (d) => `
        <article class="trending-place-card" onclick="window.journeyApp.openDestinationModal('${d.id}')">
          <div class="place-card-img-wrap">
            <img src="${d.image}" alt="${d.nameVi}" class="place-card-img" loading="lazy" />
            <span class="place-badge-top">${d.categoryVi}</span>
          </div>
          <div class="place-card-body">
            <div class="place-meta-row">
              <span class="trending-rank-pill">🔥 ${d.rank}</span>
              <span class="mono-text">★ ${d.rating} · ${d.province}</span>
            </div>
            <h3 class="place-title serif-title">${d.nameVi}</h3>
            <p class="place-desc">${d.shortDescVi}</p>
          </div>
        </article>
      `
      )
      .join('');

    main.innerHTML = `
      <!-- 1. Tin 24h của Bạn bè & Của mình (Stories Strip) -->
      <section aria-label="Tin của bạn bè">
        <div class="section-header" style="margin-bottom:6px;">
          <h2 class="section-title serif-title">Tin Khoảnh Khắc Bạn Bè</h2>
          <button class="section-link" onclick="window.journeyApp.switchTab('discover')">+ Đăng Locket</button>
        </div>
        <div class="stories-strip">
          <div class="story-item" onclick="window.journeyApp.switchTab('discover')">
            <div class="story-ring add-story">
              <div class="story-avatar">📷</div>
            </div>
            <span class="story-name">Chụp mới</span>
          </div>
          ${storiesHtml}
        </div>
      </section>

      <!-- 2. Bộ lọc Bảng tin -->
      <div style="display:flex;align-items:center;justify-content:space-between;gap:8px;flex-wrap:wrap;">
        <div class="explore-map-controls" style="flex:1;">
          <button class="chip-btn ${this.feedFilter === 'all' ? 'active' : ''}" onclick="window.journeyApp.setFeedFilter('all')">
            🌐 Tất cả (${this.posts.length})
          </button>
          <button class="chip-btn ${this.feedFilter === 'friends' ? 'active' : ''}" onclick="window.journeyApp.setFeedFilter('friends')">
            👥 Bạn bè (${this.posts.filter((p) => !p.isMine).length})
          </button>
          <button class="chip-btn ${this.feedFilter === 'mine' ? 'active' : ''}" onclick="window.journeyApp.setFeedFilter('mine')">
            👤 Của tôi (${this.posts.filter((p) => p.isMine).length})
          </button>
        </div>
      </div>

      <!-- 3. Danh sách Bài đăng Bảng tin (Locket Feed Cards) -->
      <section aria-label="Bảng tin bài đăng" style="display:flex;flex-direction:column;gap:14px;">
        ${postsHtml || '<div style="text-align:center;padding:24px;color:var(--text-secondary);">Chưa có bài đăng nào trong mục này. Hãy bấm Ống kính 📷 để chụp ngay!</div>'}
      </section>

      <!-- 4. Hero Banner & Trending Places (Preserved for Journey AI Discovery) -->
      <section class="hero-banner" aria-label="Hero Banner">
        <img
          src="images/img_hero_halong_bay.jpg"
          alt="Vịnh Hạ Long — Ha Long Bay"
          class="hero-banner-img"
        />
        <div class="hero-eyebrow">🧭 GỢI Ý HÀNH TRÌNH HÔM NAY</div>
        <h1 class="hero-title serif-title">Vịnh Hạ Long — Huyền Thoại Đá Giữa Biển Ngọc</h1>
        <p class="hero-subtitle">
          Chạm để nghe thuyết minh AI hoặc mở Ống kính Locket chụp ảnh check-in cùng bạn bè.
        </p>
        <div class="hero-cta-row">
          <button class="hero-cta-button" onclick="window.journeyApp.openDestinationModal('halong')">
            <span>🎧</span>
            <span>Khám phá &amp; Nghe câu chuyện</span>
          </button>
          <button class="btn-glass" onclick="window.journeyApp.switchTab('discover')">
            <span>📷</span>
            <span>Chụp Locket ngay</span>
          </button>
        </div>
      </section>

      <section class="trending-places-section" aria-label="Trending Places">
        <div class="section-header">
          <h2 class="section-title serif-title">Địa Điểm Xu Hướng — Trending Places</h2>
          <button class="section-link" onclick="window.journeyApp.switchTab('explore')">
            Bản đồ nơi đã đi →
          </button>
        </div>
        <div class="trending-places-grid">
          ${trendingCardsHtml}
        </div>
      </section>
    `;
  }

  setFeedFilter(filter) {
    this.feedFilter = filter;
    this.renderHomeTab();
  }

  toggleLikePost(postId) {
    const post = this.posts.find((p) => p.id === postId);
    if (!post) return;
    post.liked = !post.liked;
    post.likes += post.liked ? 1 : -1;
    this.saveJson('journey_feed_posts', this.posts);
    if (this.activeTab === 'home') {
      this.renderHomeTab();
    }
  }

  openCommentModal(postId) {
    const post = this.posts.find((p) => p.id === postId);
    if (!post) return;

    const commentsList =
      post.comments && post.comments.length > 0
        ? post.comments
            .map(
              (c) => `
            <div style="padding:8px 10px;border-radius:12px;background:var(--bg-elevated);border:1px solid var(--border-subtle);font-size:0.84rem;">
              <strong style="color:var(--text-primary);">${c.user}:</strong>
              <span style="color:var(--text-secondary);margin-left:4px;">${c.text}</span>
            </div>
          `
            )
            .join('')
        : '<div style="font-size:0.84rem;color:var(--text-muted);text-align:center;padding:12px;">Chưa có bình luận nào. Hãy là người đầu tiên!</div>';

    this.openModal(`
      <div class="modal-header-row">
        <h3 class="serif-title" style="font-size:1.15rem;">💬 Bình luận bài đăng của ${post.isMine ? this.profile.name : post.authorName}</h3>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <div style="display:flex;flex-direction:column;gap:8px;max-height:240px;overflow-y:auto;margin-bottom:12px;">
        ${commentsList}
      </div>
      <div style="display:flex;gap:8px;">
        <input id="commentInputBox" class="form-input" type="text" placeholder="Viết bình luận của bạn..." style="flex:1;" />
        <button class="btn-save-profile" style="width:auto;padding:10px 16px;" onclick="window.journeyApp.submitComment('${post.id}')">Gửi</button>
      </div>
    `);
  }

  submitComment(postId) {
    const input = document.getElementById('commentInputBox');
    if (!input || !input.value.trim()) return;
    const post = this.posts.find((p) => p.id === postId);
    if (!post) return;
    if (!post.comments) post.comments = [];
    post.comments.push({
      user: this.profile.name,
      text: input.value.trim()
    });
    this.saveJson('journey_feed_posts', this.posts);
    this.openCommentModal(postId);
    if (this.activeTab === 'home') {
      this.renderHomeTab();
    }
  }

  openStoryModal(storyId) {
    const st = FRIEND_STORIES[storyId];
    if (!st) return;
    this.openModal(`
      <div class="modal-header-row">
        <div style="display:flex;align-items:center;gap:8px;">
          <div class="feed-author-avatar">${st.avatarText}</div>
          <div>
            <div style="font-weight:800;font-size:0.92rem;">${st.author}</div>
            <div style="font-size:0.74rem;color:var(--text-secondary);">📍 ${st.placeName} · ${st.time}</div>
          </div>
        </div>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <div class="feed-photo-wrap" style="border-radius:20px;overflow:hidden;margin-bottom:12px;">
        <img src="${st.image}" alt="${st.placeName}" class="feed-photo-img" />
        <div class="feed-locket-caption-pill">${st.caption}</div>
      </div>
      <div style="display:flex;justify-content:space-between;align-items:center;gap:8px;">
        <div style="display:flex;gap:8px;">
          <button class="chip-btn" onclick="window.journeyApp.showToast('❤️ Đã thả tim tin của ${st.author}!')">❤️ Thả tim</button>
          <button class="chip-btn" onclick="window.journeyApp.showToast('🔥 Đã gửi phản hồi cho ${st.author}!')">🔥 Tuyệt vời</button>
        </div>
        <button class="chip-btn active" onclick="window.journeyApp.closeModal(); window.journeyApp.viewPlaceOnMap('${st.placeId}')">
          🗺️ Xem nơi này
        </button>
      </div>
    `);
  }

  /* =========================================================================
     TAB 2: KHÁM PHÁ — BẢN ĐỒ NHỮNG NƠI ĐÃ ĐI / ĐÃ ĐẾN (LEAFLET FOOTPRINT MAP)
     ========================================================================= */
  getAllVisitedPlaces() {
    const baseList = Object.values(DESTINATIONS);
    return [...baseList, ...this.customPlaces];
  }

  renderExploreTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    const allPlaces = this.getAllVisitedPlaces();
    const provincesCount = new Set(allPlaces.map((p) => p.province)).size;

    const chipsHtml = allPlaces
      .map(
        (p, idx) => `
        <button class="chip-btn ${idx === 0 ? 'active' : ''}" data-place-chip="${p.id}" onclick="window.journeyApp.flyToTrendingPlace('${p.id}')">
          ${p.pinIcon || '📍'} ${p.nameVi}
        </button>
      `
      )
      .join('');

    const visitedCardsHtml = allPlaces
      .map(
        (p) => `
        <div style="display:flex;align-items:center;gap:12px;padding:10px;border-radius:16px;background:var(--bg-surface);border:1px solid var(--border-subtle);box-shadow:var(--shadow-card);cursor:pointer;" onclick="window.journeyApp.flyToTrendingPlace('${p.id}')">
          <img src="${p.image}" alt="${p.nameVi}" style="width:62px;height:62px;border-radius:12px;object-fit:cover;flex-shrink:0;" />
          <div style="flex:1;min-width:0;">
            <div style="display:flex;align-items:center;gap:6px;">
              <span style="font-size:0.68rem;font-weight:800;padding:2px 7px;border-radius:999px;background:var(--accent-sage-soft);color:var(--accent-sage);">✓ Đã đến</span>
              <span style="font-size:0.72rem;color:var(--text-muted);">${p.visitedDate || 'Tháng 11/2026'}</span>
            </div>
            <div class="serif-title" style="font-size:1rem;font-weight:700;color:var(--text-primary);white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${p.nameVi}</div>
            <div style="font-size:0.76rem;color:var(--text-secondary);">📍 ${p.province} · ${p.coords}</div>
          </div>
          <button class="chip-btn" onclick="event.stopPropagation(); window.journeyApp.openDestinationModal('${p.id}')">Chi tiết</button>
        </div>
      `
      )
      .join('');

    main.innerHTML = `
      <!-- Thống kê Dấu chân Hành trình -->
      <div class="visited-stats-row">
        <div class="visited-stat-box">
          <div class="visited-stat-num">${allPlaces.length}</div>
          <div class="visited-stat-label">Nơi đã đến</div>
        </div>
        <div class="visited-stat-box">
          <div class="visited-stat-num">${provincesCount}</div>
          <div class="visited-stat-label">Tỉnh / Thành</div>
        </div>
        <div class="visited-stat-box">
          <div class="visited-stat-num">${this.checkins.length}</div>
          <div class="visited-stat-label">Ảnh Check-in</div>
        </div>
      </div>

      <!-- Bản đồ Leaflet những nơi đã đi (.explore-section) -->
      <section class="explore-section" id="exploreMapSection" aria-label="Interactive Explore Map">
        <div class="section-header" style="margin-bottom:8px;">
          <div>
            <h2 class="section-title serif-title">Bản Đồ Những Nơi Đã Đi</h2>
            <p style="font-size:0.76rem;color:var(--text-secondary);">Chạm vào từng cột mốc để xem ảnh kỷ niệm &amp; thông tin chi tiết</p>
          </div>
          <div style="display:flex;gap:6px;">
            <button class="chip-btn" onclick="window.journeyApp.openAddPlaceModal()">+ Thêm nơi đã đến</button>
            <button class="chip-btn active" onclick="window.journeyApp.fitAllTrendingMarkers()">Toàn bộ VN</button>
          </div>
        </div>

        <div class="leaflet-explore-wrapper">
          <div id="leafletExploreMap" class="leaflet-explore-map" role="region" aria-label="Leaflet Trending Places Map"></div>
          <div class="explore-map-controls" style="margin-top:8px;">
            ${chipsHtml}
          </div>
        </div>
      </section>

      <!-- Danh sách Cột mốc Đã đặt chân tới -->
      <section style="display:flex;flex-direction:column;gap:8px;">
        <div class="section-header">
          <h2 class="section-title serif-title">Cột Mốc Đã Đặt Chân Tới (${allPlaces.length})</h2>
          <button class="section-link" onclick="window.journeyApp.switchTab('journal')">Xem ảnh Check-in →</button>
        </div>
        ${visitedCardsHtml}
      </section>
    `;

    setTimeout(() => {
      this.initExploreLeafletMap();
    }, 60);
  }

  initExploreLeafletMap() {
    const mapContainer = document.getElementById('leafletExploreMap');
    if (!mapContainer || typeof window.L === 'undefined') return;

    if (this.leafletMap) {
      this.leafletMap.remove();
      this.leafletMap = null;
    }

    this.leafletMap = window.L.map('leafletExploreMap', {
      center: [18.8, 106.2],
      zoom: 6,
      zoomControl: true,
      scrollWheelZoom: true
    });

    window.L.tileLayer('https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png', {
      attribution: '&copy; OpenStreetMap &amp; CARTO',
      subdomains: 'abcd',
      maxZoom: 19
    }).addTo(this.leafletMap);

    this.injectCustomTrendingMarkers(this.leafletMap);

    setTimeout(() => {
      if (this.leafletMap) this.leafletMap.invalidateSize();
    }, 180);
  }

  /**
   * Programmatically injects custom markers for Ha Long Bay, Hoi An,
   * Temple of Literature, and Sa Pa (plus any user-added visited places)
   * with hover scale + shadow and custom popup with 'View Details' button.
   */
  injectCustomTrendingMarkers(map) {
    if (!map || typeof window.L === 'undefined') return;

    this.leafletMarkers = {};
    const allPlaces = this.getAllVisitedPlaces();
    const routeCoords = [];

    allPlaces.forEach((place) => {
      routeCoords.push([place.lat, place.lng]);

      const customIcon = window.L.divIcon({
        className: 'custom-landmark-marker',
        html: `
          <div class="marker-hover-container" title="${place.nameEn} (${place.nameVi})">
            <div class="marker-pin-pill">
              <span class="marker-pin-emoji">${place.pinIcon || '📍'}</span>
              <span>${place.nameEn || place.nameVi}</span>
            </div>
            <div class="marker-pin-stem"></div>
            <div class="marker-ground-shadow"></div>
          </div>
        `,
        iconSize: [145, 50],
        iconAnchor: [72, 44],
        popupAnchor: [0, -40]
      });

      const popupHtml = `
        <div class="journey-marker-popup">
          <div class="journey-popup-thumb-wrap">
            <img src="${place.image}" alt="${place.nameEn || place.nameVi}" class="journey-popup-thumb" />
            <span class="journey-popup-rank-badge">✓ Đã đến · ${place.province}</span>
          </div>
          <div class="journey-popup-body">
            <h4 class="journey-popup-title">${place.nameEn || place.nameVi}</h4>
            <p class="journey-popup-subtitle">${place.nameVi} · ★ ${place.rating || '5.0'}</p>
            <button
              type="button"
              class="journey-popup-view-details-btn"
              onclick="window.journeyApp.openDestinationModal('${place.id}')"
            >
              View Details
            </button>
          </div>
        </div>
      `;

      const marker = window.L.marker([place.lat, place.lng], {
        icon: customIcon,
        title: `${place.nameEn || place.nameVi} — ${place.nameVi}`
      }).addTo(map);

      marker.bindPopup(popupHtml, {
        maxWidth: 240,
        minWidth: 205,
        closeButton: true
      });

      this.leafletMarkers[place.id] = marker;
    });

    if (routeCoords.length > 1) {
      window.L.polyline(routeCoords, {
        color: '#C89D54',
        weight: 2.5,
        dashArray: '6, 8',
        opacity: 0.85
      }).addTo(map);
    }
  }

  flyToTrendingPlace(placeId) {
    const allPlaces = this.getAllVisitedPlaces();
    const place = allPlaces.find((p) => p.id === placeId) || DESTINATIONS[placeId];
    if (!place) return;

    document.querySelectorAll('[data-place-chip]').forEach((btn) => {
      btn.classList.toggle('active', btn.getAttribute('data-place-chip') === placeId);
    });

    if (this.leafletMap && this.leafletMarkers[placeId]) {
      this.leafletMap.flyTo([place.lat, place.lng], 10, { duration: 0.9 });
      this.leafletMarkers[placeId].openPopup();
    }
  }

  fitAllTrendingMarkers() {
    if (!this.leafletMap || typeof window.L === 'undefined') return;
    const allPlaces = this.getAllVisitedPlaces();
    const bounds = window.L.latLngBounds(allPlaces.map((p) => [p.lat, p.lng]));
    this.leafletMap.flyToBounds(bounds.pad(0.25), { duration: 0.9 });
  }

  viewPlaceOnMap(placeId) {
    this.switchTab('explore');
    setTimeout(() => {
      this.flyToTrendingPlace(placeId);
    }, 280);
  }

  openAddPlaceModal() {
    this.openModal(`
      <div class="modal-header-row">
        <h3 class="serif-title" style="font-size:1.15rem;">📍 Đánh dấu nơi mới đã đến</h3>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <div class="profile-form" style="margin-top:8px;">
        <div class="form-group">
          <label class="form-label">Tên địa danh / Thành phố</label>
          <input id="newPlaceName" class="form-input" type="text" placeholder="VD: Đảo Phú Quốc, Đà Lạt, Ninh Bình..." />
        </div>
        <div class="form-group">
          <label class="form-label">Tỉnh / Thành</label>
          <input id="newPlaceProvince" class="form-input" type="text" placeholder="VD: Kiên Giang, Lâm Đồng..." />
        </div>
        <div style="display:grid;grid-template-columns:1fr 1fr;gap:8px;">
          <div class="form-group">
            <label class="form-label">Vĩ độ (Latitude)</label>
            <input id="newPlaceLat" class="form-input" type="number" step="0.0001" value="10.2899" />
          </div>
          <div class="form-group">
            <label class="form-label">Kinh độ (Longitude)</label>
            <input id="newPlaceLng" class="form-input" type="number" step="0.0001" value="103.9840" />
          </div>
        </div>
        <button class="btn-save-profile" onclick="window.journeyApp.saveNewVisitedPlace()">
          🗺️ Lưu vào Bản đồ nơi đã đi
        </button>
      </div>
    `);
  }

  saveNewVisitedPlace() {
    const nameEl = document.getElementById('newPlaceName');
    const provEl = document.getElementById('newPlaceProvince');
    const latEl = document.getElementById('newPlaceLat');
    const lngEl = document.getElementById('newPlaceLng');
    if (!nameEl || !nameEl.value.trim()) {
      this.showToast('⚠️ Vui lòng nhập tên địa điểm!');
      return;
    }
    const nameVi = nameEl.value.trim();
    const province = provEl && provEl.value.trim() ? provEl.value.trim() : 'Việt Nam';
    const lat = parseFloat(latEl ? latEl.value : '16.0471') || 16.0471;
    const lng = parseFloat(lngEl ? lngEl.value : '108.2068') || 108.2068;

    const newPlace = {
      id: 'custom_' + Date.now(),
      rank: 'ĐÃ ĐẾN',
      nameVi,
      nameEn: nameVi,
      province,
      categoryVi: 'Điểm check-in của tôi',
      rating: '5.0',
      visitedDate: new Date().toLocaleDateString('vi-VN'),
      coords: `${lat.toFixed(4)}° N, ${lng.toFixed(4)}° E`,
      lat,
      lng,
      pinIcon: '📌',
      image: 'images/img_hero_halong_bay.jpg',
      shortDescVi: `Cột mốc hành trình tại ${nameVi}, ${province}.`,
      storyVi: `Bạn đã đánh dấu đặt chân đến ${nameVi} (${province}) trong bản đồ cá nhân.`
    };

    this.customPlaces.push(newPlace);
    this.saveJson('journey_custom_places', this.customPlaces);
    this.closeModal();
    this.showToast(`🎉 Đã thêm "${nameVi}" vào Bản đồ những nơi đã đi!`);
    if (this.activeTab === 'explore') {
      this.renderExploreTab();
    }
  }

  /* =========================================================================
     TAB 3: ỐNG KÍNH — CHỈ CHUYÊN CHỤP ẢNH NHƯ CAMERA CỦA LOCKET
     ========================================================================= */
  renderLocketCameraTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    const allPlaces = this.getAllVisitedPlaces();
    const currentPlace =
      allPlaces.find((p) => p.id === this.selectedPlaceId) || DESTINATIONS.halong;

    const placeOptionsHtml = allPlaces
      .map(
        (p) =>
          `<option value="${p.id}" ${p.id === currentPlace.id ? 'selected' : ''}>📍 ${p.nameVi} (${p.province})</option>`
      )
      .join('');

    main.innerHTML = `
      <div class="locket-camera-screen">
        <!-- Top Bar: Chọn địa điểm đang đứng -->
        <div class="locket-top-pill">
          <div style="display:flex;align-items:center;gap:6px;flex:1;min-width:0;">
            <select
              id="locketPlaceSelect"
              onchange="window.journeyApp.onChangeLocketPlace(this.value)"
              style="background:var(--bg-surface);color:var(--text-primary);border:1px solid var(--border-subtle);padding:6px 10px;border-radius:999px;font-size:0.78rem;font-weight:700;max-width:220px;outline:none;"
            >
              ${placeOptionsHtml}
            </select>
          </div>
          <span class="locket-badge">● LOCKET CAM</span>
        </div>

        <!-- Khung ngắm 1:1 Bo tròn kiểu Locket (.locket-viewfinder) -->
        <div class="locket-viewfinder" id="locketViewfinder">
          <video id="locketLiveVideo" class="locket-video" autoplay playsinline muted></video>
          <img
            id="locketFallbackImg"
            src="${this.capturedDataUrl || currentPlace.image}"
            alt="Locket Viewfinder"
            class="locket-preview-img"
            style="${this.capturedDataUrl ? 'display:block;' : 'display:none;'}"
          />
          <canvas id="locketCaptureCanvas" style="display:none;"></canvas>

          <div class="locket-viewfinder-overlay">
            <div style="display:flex;justify-content:space-between;align-items:center;">
              <span id="locketCameraStatus" style="background:rgba(15,23,42,0.65);backdrop-filter:blur(8px);color:#fff;font-size:0.7rem;font-weight:700;padding:4px 10px;border-radius:999px;">
                ${this.capturedDataUrl ? '✨ Ảnh đã chụp — Sẵn sàng đăng' : '📷 Ống kính trực tiếp'}
              </span>
              <span style="background:rgba(15,23,42,0.65);backdrop-filter:blur(8px);color:#F3E5AB;font-size:0.7rem;font-weight:700;padding:4px 10px;border-radius:999px;">
                ${new Date().toLocaleTimeString('vi-VN', { hour: '2-digit', minute: '2-digit' })}
              </span>
            </div>

            <!-- Ô nhập chú thích ngay trong khung ảnh giống Locket -->
            <input
              id="locketCaptionInput"
              type="text"
              class="locket-caption-input"
              placeholder="Thêm ghi chú khoảnh khắc lên ảnh..."
              maxlength="100"
            />
          </div>
        </div>

        <!-- Cụm nút điều khiển máy ảnh Locket -->
        ${
          this.capturedDataUrl
            ? `
          <div style="display:flex;gap:10px;width:100%;max-width:380px;justify-content:center;margin-top:4px;">
            <button class="chip-btn" style="padding:12px 18px;font-size:0.86rem;" onclick="window.journeyApp.retakeLocketPhoto()">
              🔄 Chụp lại
            </button>
            <button class="btn-save-profile" style="flex:1;padding:12px 18px;font-size:0.9rem;" onclick="window.journeyApp.publishLocketPhoto()">
              🚀 Đăng lên Bảng tin &amp; Check-in
            </button>
          </div>
        `
            : `
          <div class="locket-controls-bar">
            <label class="locket-side-btn" title="Chọn ảnh từ điện thoại" style="cursor:pointer;">
              🖼️
              <input type="file" accept="image/*" style="display:none;" onchange="window.journeyApp.handlePhotoFileUpload(event)" />
            </label>

            <button class="locket-shutter-outer" onclick="window.journeyApp.captureLocketShutter()" aria-label="Chụp ảnh Locket" title="Bấm để chụp">
              <div class="locket-shutter-inner"></div>
            </button>

            <button class="locket-side-btn" onclick="window.journeyApp.flipLocketCamera()" title="Đổi camera Trước / Sau">
              🔄
            </button>
          </div>
          <p style="font-size:0.76rem;color:var(--text-secondary);text-align:center;">
            Bấm nút tròn ở giữa để chụp khoảnh khắc hoặc chọn ảnh từ thư viện 🖼️
          </p>
        `
        }
      </div>
    `;

    if (!this.capturedDataUrl) {
      this.startCameraStream();
    }
  }

  async startCameraStream() {
    const videoEl = document.getElementById('locketLiveVideo');
    const fallbackImg = document.getElementById('locketFallbackImg');
    const statusEl = document.getElementById('locketCameraStatus');
    if (!videoEl) return;

    this.stopCameraStream();

    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({
          video: { facingMode: this.cameraFacingMode, width: { ideal: 720 }, height: { ideal: 720 } },
          audio: false
        });
        this.cameraStream = stream;
        videoEl.srcObject = stream;
        videoEl.style.display = 'block';
        if (fallbackImg) fallbackImg.style.display = 'none';
        if (statusEl) statusEl.textContent = '🟢 Camera trực tiếp sẵn sàng';
        return;
      } catch (_err) {
        // Fallback to high-res preview image when camera permission is denied or unavailable
      }
    }

    videoEl.style.display = 'none';
    if (fallbackImg) {
      const allPlaces = this.getAllVisitedPlaces();
      const place =
        allPlaces.find((p) => p.id === this.selectedPlaceId) || DESTINATIONS.halong;
      fallbackImg.src = place.image;
      fallbackImg.style.display = 'block';
    }
    if (statusEl) statusEl.textContent = '📷 Chế độ Khung ngắm Cảnh đẹp';
  }

  stopCameraStream() {
    if (this.cameraStream) {
      this.cameraStream.getTracks().forEach((track) => track.stop());
      this.cameraStream = null;
    }
  }

  onChangeLocketPlace(placeId) {
    this.selectedPlaceId = placeId;
    const fallbackImg = document.getElementById('locketFallbackImg');
    const allPlaces = this.getAllVisitedPlaces();
    const place = allPlaces.find((p) => p.id === placeId) || DESTINATIONS.halong;
    if (fallbackImg && !this.cameraStream && !this.capturedDataUrl) {
      fallbackImg.src = place.image;
    }
  }

  flipLocketCamera() {
    this.cameraFacingMode =
      this.cameraFacingMode === 'environment' ? 'user' : 'environment';

    if (this.cameraStream) {
      this.startCameraStream();
      this.showToast(
        this.cameraFacingMode === 'user'
          ? '🤳 Đã chuyển sang Camera trước'
          : '📷 Đã chuyển sang Camera sau'
      );
    } else {
      const allPlaces = this.getAllVisitedPlaces();
      this.fallbackPreviewIndex = (this.fallbackPreviewIndex + 1) % allPlaces.length;
      const nextPlace = allPlaces[this.fallbackPreviewIndex];
      this.selectedPlaceId = nextPlace.id;
      this.renderLocketCameraTab();
      this.showToast(`🔄 Đã chuyển góc ngắm: ${nextPlace.nameVi}`);
    }
  }

  captureLocketShutter() {
    const videoEl = document.getElementById('locketLiveVideo');
    const fallbackImg = document.getElementById('locketFallbackImg');
    const canvas = document.getElementById('locketCaptureCanvas');

    if (this.cameraStream && videoEl && videoEl.videoWidth > 0 && canvas) {
      const size = Math.min(videoEl.videoWidth, videoEl.videoHeight);
      canvas.width = 640;
      canvas.height = 640;
      const ctx = canvas.getContext('2d');
      const sx = (videoEl.videoWidth - size) / 2;
      const sy = (videoEl.videoHeight - size) / 2;
      ctx.drawImage(videoEl, sx, sy, size, size, 0, 0, 640, 640);
      this.capturedDataUrl = canvas.toDataURL('image/jpeg', 0.88);
    } else if (fallbackImg) {
      this.capturedDataUrl = fallbackImg.getAttribute('src');
    }

    const savedCaption = document.getElementById('locketCaptionInput')
      ? document.getElementById('locketCaptionInput').value
      : '';

    this.stopCameraStream();
    this.renderLocketCameraTab();

    const newCaptionInput = document.getElementById('locketCaptionInput');
    if (newCaptionInput && savedCaption) {
      newCaptionInput.value = savedCaption;
    }
    this.showToast('📸 Đã chụp khoảnh khắc! Nhập ghi chú rồi bấm Đăng nhé.');
  }

  handlePhotoFileUpload(event) {
    const file = event.target.files && event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = (e) => {
      this.capturedDataUrl = e.target.result;
      this.stopCameraStream();
      this.renderLocketCameraTab();
      this.showToast('🖼️ Đã tải ảnh lên khung Locket!');
    };
    reader.readAsDataURL(file);
  }

  retakeLocketPhoto() {
    this.capturedDataUrl = null;
    this.renderLocketCameraTab();
  }

  publishLocketPhoto() {
    const allPlaces = this.getAllVisitedPlaces();
    const place =
      allPlaces.find((p) => p.id === this.selectedPlaceId) || DESTINATIONS.halong;
    const captionEl = document.getElementById('locketCaptionInput');
    const caption =
      captionEl && captionEl.value.trim()
        ? captionEl.value.trim()
        : `Check-in khoảnh khắc tuyệt đẹp tại ${place.nameVi} ✨`;

    const imageSrc = this.capturedDataUrl || place.image;
    const todayStr = new Date().toLocaleDateString('vi-VN');

    // 1. Add to Home Social Feed
    const newPost = {
      id: 'post-' + Date.now(),
      isMine: true,
      authorName: this.profile.name,
      authorHandle: this.profile.username,
      avatarText: this.profile.avatarText,
      avatarImg: this.profile.avatarImg,
      placeId: place.id,
      placeName: `${place.nameVi}, ${place.province}`,
      time: 'Vừa xong',
      dateStr: todayStr,
      image: imageSrc,
      caption,
      likes: 1,
      liked: true,
      comments: []
    };
    this.posts.unshift(newPost);
    this.saveJson('journey_feed_posts', this.posts);

    // 2. Add to Check-in Journal
    const newCheckin = {
      id: 'chk-' + Date.now(),
      placeId: place.id,
      placeName: place.nameVi,
      province: place.province,
      date: todayStr,
      image: imageSrc,
      caption
    };
    this.checkins.unshift(newCheckin);
    this.saveJson('journey_user_checkins', this.checkins);

    this.capturedDataUrl = null;
    this.showToast('🚀 Đã đăng ảnh lên Bảng tin & lưu vào Nhật ký Check-in!');
    this.switchTab('home');
  }

  /* =========================================================================
     TAB 4: NHẬT KÝ — NHỮNG BỨC ẢNH & NƠI MÌNH ĐÃ CHECK-IN
     ========================================================================= */
  renderJournalTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    const cardsHtml = this.checkins
      .map(
        (chk) => `
        <article class="checkin-card" onclick="window.journeyApp.openCheckinDetailModal('${chk.id}')">
          <div class="checkin-img-wrap">
            <img src="${chk.image}" alt="${chk.placeName}" loading="lazy" />
            <span class="checkin-date-badge">${chk.date}</span>
          </div>
          <div class="checkin-body">
            <div class="checkin-place">📍 ${chk.placeName}</div>
            <div class="checkin-note">${chk.caption}</div>
          </div>
        </article>
      `
      )
      .join('');

    main.innerHTML = `
      <section style="display:flex;flex-direction:column;gap:12px;">
        <div class="section-header">
          <div>
            <h2 class="section-title serif-title">Nhật Ký Ảnh Đã Check-in (${this.checkins.length})</h2>
            <p style="font-size:0.78rem;color:var(--text-secondary);">
              Tất cả những bức ảnh và địa điểm bạn đã đặt chân tới
            </p>
          </div>
          <button class="chip-btn active" onclick="window.journeyApp.switchTab('discover')">
            + Chụp Check-in
          </button>
        </div>

        <div class="checkin-grid">
          ${cardsHtml}
        </div>
      </section>
    `;
  }

  openCheckinDetailModal(checkinId) {
    const chk = this.checkins.find((c) => c.id === checkinId);
    if (!chk) return;

    this.openModal(`
      <div class="modal-header-row">
        <div>
          <h3 class="serif-title" style="font-size:1.15rem;">📍 ${chk.placeName}</h3>
          <span style="font-size:0.75rem;color:var(--text-secondary);">Check-in ngày ${chk.date} · ${chk.province || 'Việt Nam'}</span>
        </div>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <div class="feed-photo-wrap" style="border-radius:18px;overflow:hidden;margin-bottom:12px;">
        <img src="${chk.image}" alt="${chk.placeName}" class="feed-photo-img" />
        <div class="feed-locket-caption-pill">${chk.caption}</div>
      </div>
      <div style="display:flex;justify-content:space-between;gap:8px;">
        <button class="chip-btn" style="color:var(--danger);" onclick="window.journeyApp.deleteCheckin('${chk.id}')">
          🗑️ Xóa ảnh này
        </button>
        <button class="chip-btn active" onclick="window.journeyApp.closeModal(); window.journeyApp.viewPlaceOnMap('${chk.placeId || 'halong'}')">
          🗺️ Xem trên Bản đồ
        </button>
      </div>
    `);
  }

  deleteCheckin(checkinId) {
    this.checkins = this.checkins.filter((c) => c.id !== checkinId);
    this.saveJson('journey_user_checkins', this.checkins);
    this.closeModal();
    this.showToast('🗑️ Đã xóa ảnh khỏi Nhật ký Check-in.');
    if (this.activeTab === 'journal') {
      this.renderJournalTab();
    }
  }

  /* =========================================================================
     TAB 5: HỒ SƠ — CHỈ GHI THAY AVATAR, TÊN & THÔNG TIN CÁ NHÂN
     ========================================================================= */
  renderProfileTab() {
    const main = document.getElementById('mainViewport');
    if (!main) return;

    const avatarContent = this.profile.avatarImg
      ? `<img src="${this.profile.avatarImg}" alt="${this.profile.name}" />`
      : this.profile.avatarText;

    main.innerHTML = `
      <section class="profile-editor-card" aria-label="Hồ sơ cá nhân">
        <div class="profile-avatar-upload">
          <label class="profile-avatar-Editable" title="Chạm để đổi ảnh đại diện">
            ${avatarContent}
            <span class="profile-avatar-badge">📷</span>
            <input type="file" accept="image/*" style="display:none;" onchange="window.journeyApp.handleAvatarUpload(event)" />
          </label>
          <div style="text-align:center;">
            <h2 class="serif-title" style="font-size:1.25rem;font-weight:700;">${this.profile.name}</h2>
            <p style="font-size:0.78rem;color:var(--text-secondary);">${this.profile.username} · ${this.profile.city}</p>
          </div>
          <div style="display:flex;gap:6px;flex-wrap:wrap;justify-content:center;">
            <label class="chip-btn" style="cursor:pointer;">
              🖼️ Tải ảnh từ máy
              <input type="file" accept="image/*" style="display:none;" onchange="window.journeyApp.handleAvatarUpload(event)" />
            </label>
            ${
              this.profile.avatarImg
                ? `<button class="chip-btn" onclick="window.journeyApp.resetAvatarToInitials()">Chữ viết tắt (${this.profile.avatarText})</button>`
                : ''
            }
          </div>
        </div>

        <div class="profile-form">
          <div class="form-group">
            <label class="form-label" for="profName">Họ và tên hiển thị</label>
            <input id="profName" class="form-input" type="text" value="${this.profile.name}" placeholder="Nhập tên của bạn" />
          </div>

          <div class="form-group">
            <label class="form-label" for="profUsername">ID / Username</label>
            <input id="profUsername" class="form-input" type="text" value="${this.profile.username}" placeholder="@username" />
          </div>

          <div class="form-group">
            <label class="form-label" for="profBio">Giới thiệu bản thân (Bio)</label>
            <textarea id="profBio" class="form-input" rows="2" placeholder="Viết đôi dòng giới thiệu...">${this.profile.bio}</textarea>
          </div>

          <div class="form-group">
            <label class="form-label" for="profCity">Thành phố / Quê quán</label>
            <input id="profCity" class="form-input" type="text" value="${this.profile.city}" placeholder="Hà Nội, TP.HCM..." />
          </div>

          <div class="form-group">
            <label class="form-label" for="profEmail">Email / Liên hệ</label>
            <input id="profEmail" class="form-input" type="email" value="${this.profile.email}" placeholder="email@example.com" />
          </div>

          <div style="display:flex;align-items:center;justify-content:space-between;padding:10px 12px;border-radius:12px;background:var(--bg-elevated);border:1px solid var(--border-subtle);">
            <span style="font-size:0.84rem;font-weight:700;">Giao diện hiển thị</span>
            <button type="button" class="chip-btn" onclick="window.journeyApp.toggleTheme()">
              ${this.theme === 'dark' ? '🌙 Chế độ Tối' : '☀️ Chế độ Sáng'}
            </button>
          </div>

          <button type="button" class="btn-save-profile" onclick="window.journeyApp.saveProfileChanges()">
            💾 Lưu thông tin cá nhân
          </button>
        </div>
      </section>
    `;
  }

  handleAvatarUpload(event) {
    const file = event.target.files && event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = (e) => {
      this.profile.avatarImg = e.target.result;
      this.saveJson('journey_user_profile', this.profile);
      this.syncHeaderProfile();
      this.renderProfileTab();
      this.showToast('📷 Đã cập nhật ảnh đại diện mới!');
    };
    reader.readAsDataURL(file);
  }

  resetAvatarToInitials() {
    this.profile.avatarImg = '';
    this.saveJson('journey_user_profile', this.profile);
    this.syncHeaderProfile();
    this.renderProfileTab();
    this.showToast('✓ Đã chuyển về avatar chữ viết tắt.');
  }

  saveProfileChanges() {
    const nameEl = document.getElementById('profName');
    const userEl = document.getElementById('profUsername');
    const bioEl = document.getElementById('profBio');
    const cityEl = document.getElementById('profCity');
    const emailEl = document.getElementById('profEmail');

    if (nameEl && nameEl.value.trim()) {
      this.profile.name = nameEl.value.trim();
    }
    if (userEl && userEl.value.trim()) {
      const val = userEl.value.trim();
      this.profile.username = val.startsWith('@') ? val : '@' + val;
    }
    if (bioEl) this.profile.bio = bioEl.value.trim();
    if (cityEl) this.profile.city = cityEl.value.trim();
    if (emailEl) this.profile.email = emailEl.value.trim();

    this.profile.avatarText = this.getInitials(this.profile.name);
    this.saveJson('journey_user_profile', this.profile);
    this.syncHeaderProfile();
    this.renderProfileTab();
    this.showToast('✅ Đã lưu thông tin hồ sơ cá nhân!');
  }

  /* =========================================================================
     MODALS, AUDIO STORY & TOAST UTILITIES
     ========================================================================= */
  openDestinationModal(destId) {
    const allPlaces = this.getAllVisitedPlaces();
    const d = allPlaces.find((p) => p.id === destId) || DESTINATIONS[destId] || DESTINATIONS.halong;

    this.openModal(`
      <div class="modal-header-row">
        <div>
          <span style="font-size:0.7rem;font-weight:800;color:var(--accent-gold);">${d.categoryVi}</span>
          <h3 class="serif-title" style="font-size:1.25rem;">${d.nameVi} (${d.nameEn})</h3>
        </div>
        <button class="btn-close-modal" onclick="window.journeyApp.closeModal()">✕</button>
      </div>
      <img src="${d.image}" alt="${d.nameVi}" style="width:100%;height:200px;object-fit:cover;border-radius:16px;margin-bottom:12px;" />
      <p style="font-size:0.86rem;line-height:1.55;color:var(--text-secondary);margin-bottom:14px;">
        ${d.storyVi || d.shortDescVi}
      </p>
      <div style="display:flex;gap:8px;flex-wrap:wrap;">
        <button class="chip-btn active" onclick="window.journeyApp.playDestinationAudio('${d.id}')">
          🔊 Nghe thuyết minh
        </button>
        <button class="chip-btn" onclick="window.journeyApp.closeModal(); window.journeyApp.selectedPlaceId='${d.id}'; window.journeyApp.switchTab('discover')">
          📷 Chụp Locket tại đây
        </button>
        <button class="chip-btn" onclick="window.journeyApp.closeModal(); window.journeyApp.viewPlaceOnMap('${d.id}')">
          🗺️ Xem trên Bản đồ
        </button>
      </div>
    `);
  }

  playDestinationAudio(destId) {
    const allPlaces = this.getAllVisitedPlaces();
    const d = allPlaces.find((p) => p.id === destId) || DESTINATIONS[destId] || DESTINATIONS.halong;
    this.showToast(`🔊 Đang đọc thuyết minh: ${d.nameVi}`);

    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      const utter = new SpeechSynthesisUtterance(`${d.nameVi}. ${d.storyVi || d.shortDescVi}`);
      utter.lang = 'vi-VN';
      utter.rate = 1.0;
      window.speechSynthesis.speak(utter);
    }
  }

  openModal(html) {
    const overlay = document.getElementById('modalOverlay');
    const content = document.getElementById('modalContent');
    if (!overlay || !content) return;
    content.innerHTML = html;
    overlay.classList.add('open');
  }

  closeModal() {
    const overlay = document.getElementById('modalOverlay');
    if (overlay) overlay.classList.remove('open');
  }

  showToast(message) {
    const toast = document.getElementById('toastBanner');
    if (!toast) return;
    toast.textContent = message;
    toast.classList.add('show');
    clearTimeout(this._toastTimer);
    this._toastTimer = setTimeout(() => {
      toast.classList.remove('show');
    }, 2600);
  }
}

window.addEventListener('DOMContentLoaded', () => {
  window.journeyApp = new JourneyAppController();
});
