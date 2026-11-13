(function () {
  const DESTINATIONS = [
    {
      id: 'halong',
      category: 'nature',
      nameVi: 'Vịnh Hạ Long',
      nameEn: 'Ha Long Bay',
      regionVi: 'Quảng Ninh, Việt Nam',
      regionEn: 'Quang Ninh, Vietnam',
      coords: '20.9101° N, 107.1839° E',
      lat: 20.9101,
      lng: 107.1839,
      rating: '4.9',
      distance: '165 km',
      durationVi: 'Thuyết minh 4 phút',
      durationEn: '4 min Audio Guide',
      badgeVi: 'Di sản UNESCO',
      badgeEn: 'UNESCO Heritage',
      image: 'images/img_hero_halong_bay.jpg',
      summaryVi: 'Hơn 1.600 đảo đá vôi kỳ vĩ vươn lên từ làn nước ngọc lục bảo, lưu giữ trầm tích địa chất 500 triệu năm và truyền thuyết Rồng thiêng hạ giới.',
      summaryEn: 'Over 1,600 monolithic limestone karsts rising from emerald waters, preserving 500 million years of geological history and the legend of the Descending Dragon.',
      storyVi: 'Vịnh Hạ Long không chỉ là kỳ quan thiên nhiên thế giới mà còn là cái nôi của văn hóa biển Cái Bèo và Hạ Long cổ đại. Đi thuyền qua làng chài Cửa Vạn lúc bình minh, bạn sẽ nghe tiếng mái chèo khua nhẹ giữa sương mù bảng lảng.',
      storyEn: 'Ha Long Bay is both a natural world wonder and the cradle of ancient Cai Beo maritime culture. Gliding past Cua Van floating village at dawn reveals timeless limestone cathedrals.',
      mapX: 68,
      mapY: 24
    },
    {
      id: 'hoian',
      category: 'heritage',
      nameVi: 'Phố Cổ Hội An',
      nameEn: 'Hoi An Ancient Town',
      regionVi: 'Quảng Nam, Việt Nam',
      regionEn: 'Quang Nam, Vietnam',
      coords: '15.8801° N, 108.3380° E',
      lat: 15.8801,
      lng: 108.3380,
      rating: '4.9',
      distance: '2.4 km',
      durationVi: 'Thuyết minh 5 phút',
      durationEn: '5 min Audio Guide',
      badgeVi: 'Thương cảng Cổ',
      badgeEn: 'Ancient Trading Port',
      image: 'images/img_hoian_lanterns.jpg',
      summaryVi: 'Thương cảng quốc tế phồn hoa thế kỷ XVI–XVII với những dãy nhà tường vàng cổ kính, Chùa Cầu trăm tuổi và sông Hoài rực rỡ đèn lồng lụa.',
      summaryEn: 'A preserved 16th-century Southeast Asian trading port glowing with silk lanterns, centuries-old merchant houses, and the iconic Japanese Covered Bridge.',
      storyVi: 'Mỗi mái ngói âm dương phủ rêu phong tại Hội An đều kể câu chuyện giao thoa văn hóa Việt – Nhật – Hoa – Phương Tây. Khi hoàng hôn buông xuống dòng sông Hoài, hàng ngàn chiếc đèn lồng thủ công thắp sáng không gian tĩnh lặng.',
      storyEn: 'Every moss-covered yin-yang tiled roof in Hoi An whispers stories of Vietnamese, Japanese, Chinese, and European merchants trading silk, ceramics, and spices.',
      mapX: 56,
      mapY: 58
    },
    {
      id: 'hanoi_temple',
      category: 'architecture',
      nameVi: 'Văn Miếu – Quốc Tử Giám',
      nameEn: 'Temple of Literature',
      regionVi: 'Ba Đình, Hà Nội',
      regionEn: 'Ba Dinh, Hanoi',
      coords: '21.0278° N, 105.8355° E',
      lat: 21.0278,
      lng: 105.8355,
      rating: '4.8',
      distance: '1.1 km',
      durationVi: 'Thuyết minh 6 phút',
      durationEn: '6 min Audio Guide',
      badgeVi: 'Đại học Đầu tiên',
      badgeEn: 'First University (1070)',
      image: 'images/img_hanoi_temple.jpg',
      summaryVi: 'Được xây dựng năm 1070 dưới triều vua Lý Thánh Tông, biểu tượng ngàn năm của truyền thống hiếu học với Khuê Văn Các và 82 bia Tiến sĩ.',
      summaryEn: 'Built in 1070 under Emperor Ly Thanh Tong, standing as Vietnam’s first national university with Khue Van Cac pavilion and 82 stone doctor steles.',
      storyVi: 'Bước qua Văn Miếu Môn và Đại Trung Môn, Khuê Văn Các hiện lên uy nghiêm soi bóng xuống giếng Thiên Quang. 82 tấm bia Tiến sĩ đặt trên lưng rùa đá đã được UNESCO công nhận là Di sản Tư liệu Thế giới.',
      storyEn: 'Passing through the Great Middle Gate reveals Khue Van Cac (Pavilion of the Constellation of Literature) and 82 stone turtles bearing the names of royal laureates.',
      mapX: 48,
      mapY: 22
    },
    {
      id: 'sapa',
      category: 'nature',
      nameVi: 'Ruộng Bậc Thang Mù Cang Chải & Sa Pa',
      nameEn: 'Sapa & Mu Cang Chai Terraces',
      regionVi: 'Lào Cai – Yên Bái',
      regionEn: 'Northwest Highlands, Vietnam',
      coords: '22.3364° N, 103.8438° E',
      lat: 22.3364,
      lng: 103.8438,
      rating: '4.9',
      distance: '290 km',
      durationVi: 'Thuyết minh 4 phút',
      durationEn: '4 min Audio Guide',
      badgeVi: 'Kiệt tác Vùng cao',
      badgeEn: 'Highland Masterpiece',
      image: 'images/img_sapa_terraces.jpg',
      summaryVi: 'Những nấc thang vàng óng chạm mây do bàn tay người H’Mông và Dao kiến tạo suốt hàng thế kỷ trên sườn núi Hoàng Liên Sơn hùng vĩ.',
      summaryEn: 'Golden sculpted rice terraces carved across towering Hoang Lien Son mountain slopes by H’Mong and Red Dao artisans over centuries.',
      storyVi: 'Vào mùa lúa chín tháng 9–10, cả thung lũng Mường Hoa chuyển mình thành biển vàng óng ả hòa quyện cùng làn mây trắng bềnh bồng và tiếng khèn vùng cao.',
      storyEn: 'During the autumn harvest, Muong Hoa Valley transforms into cascading ribbons of gold wrapped in morning mountain mist.',
      mapX: 30,
      mapY: 16
    }
  ];

  const JOURNAL_ENTRIES = [
    {
      id: 'j1',
      date: '12 Thg 11, 2026',
      locationVi: 'Phố Cổ Hội An, Quảng Nam',
      locationEn: 'Hoi An Ancient Town',
      titleVi: 'Đêm Đèn Lồng Bên Dòng Sông Hoài',
      titleEn: 'Lantern Reflections Along the Hoai River',
      mood: '✨ Trầm lắng & Thi vị',
      image: 'images/img_hoian_lanterns.jpg',
      excerptVi: 'AI đã tự động tổng hợp 14 bức ảnh, lộ trình đi bộ 4.2 km qua Chùa Cầu và bản ghi âm tiếng đàn bầu thành thước phim kỷ niệm 4K.',
      excerptEn: 'AI synthesized 14 photos, a 4.2 km walking route across the Japanese Bridge, and ambient traditional music into a 4K memory story.'
    },
    {
      id: 'j2',
      date: '08 Thg 11, 2026',
      locationVi: 'Văn Miếu – Quốc Tử Giám, Hà Nội',
      locationEn: 'Temple of Literature, Hanoi',
      titleVi: 'Dấu Ấn Ngàn Năm Nền Học Thuật Việt',
      titleEn: 'A Millennium of Scholarly Heritage',
      mood: '🏛️ Hoài cổ & Sâu sắc',
      image: 'images/img_hanoi_temple.jpg',
      excerptVi: 'Quét ống kính AI vào bia Tiến sĩ khoa Nhâm Tuất (1442), khám phá câu chuyện“Hiền tài là nguyên khí của quốc gia”.',
      excerptEn: 'Scanned the 1442 Royal Laureate stele with Journey AI Lens to uncover the inscription: "Virtuous and talented scholars are the lifeblood of the nation."'
    },
    {
      id: 'j3',
      date: '02 Thg 11, 2026',
      locationVi: 'Vịnh Hạ Long, Quảng Ninh',
      locationEn: 'Ha Long Bay, Quang Ninh',
      titleVi: 'Bình Minh Trên Vịnh Ngọc Lục Bảo',
      titleEn: 'Sunrise Over Emerald Limestone Karsts',
      mood: '🌊 Hùng vĩ & Tự do',
      image: 'images/img_hero_halong_bay.jpg',
      excerptVi: 'Hành trình chèo kayak qua hang Luồn và nghe thuyết minh AI về hệ sinh thái núi đá vôi ngập nước độc nhất thế giới.',
      excerptEn: 'Kayaking through Luon Cave while listening to Journey AI’s spatial voice narration on ancient karst formations.'
    }
  ];

  const ALL_34_SCREENS = [
    '01. Splash Screen', '02. Onboarding 1 (AI Vision)', '03. Onboarding 2 (Interactive Stories)',
    '04. Onboarding 3 (Smart Journal)', '05. Sign In', '06. Sign Up',
    '07. Travel Interest Personalization', '08. Home Dashboard', '09. Explore Interactive Map',
    '10. Nearby Places Sheet', '11. Search & Global Filters', '12. Destination Detail',
    '13. Audio Story Player', '14. AI Camera Viewfinder', '15. AI Scanning Overlay',
    '16. AI Landmark Recognition Result', '17. AI Historical Story Sheet', '18. AI Chat Assistant',
    '19. AI Voice Conversation Mode', '20. AI Smart Itinerary Planner', '21. Travel Journal Timeline',
    '22. Journal Entry Detail', '23. Create / Edit Journal Entry', '24. AI Story Generator',
    '25. Story Video 4K Preview', '26. Saved Places Bookmarks', '27. Collections Grid',
    '28. Collection Detail', '29. Travel Memories Timeline', '30. User Profile & Passport',
    '31. Settings & Preferences', '32. Offline Downloads Manager', '33. Notifications Center',
    '34. Journey+ Subscription'
  ];

  const state = {
    tab: 'home',
    theme: 'light',
    lang: 'vi',
    fontScale: 'medium',
    minimalMode: false,
    voiceNarration: true,
    categoryFilter: 'all',
    searchQuery: '',
    savedIds: ['halong', 'hoian', 'hanoi_temple', 'sapa'],
    selectedMapId: 'halong',
    selectedScanId: 'hanoi_temple',
    isScanning: false,
    isPlayingAudio: false,
    activeAudioPlaceId: 'halong',
    journals: JOURNAL_ENTRIES.slice(),
    chatMessages: [
      {
        role: 'ai',
        textVi: 'Xin chào Minh Nguyễn! Tôi là Trợ lý Du lịch Journey AI. Bạn muốn khám phá lịch sử Văn Miếu, lên lịch trình 3 ngày tại Hội An hay quét công trình kiến trúc quanh bạn?',
        textEn: 'Welcome Minh Nguyen! I am your Journey AI Concierge. Would you like to explore the history of the Temple of Literature, craft a 3-day Hoi An itinerary, or scan a landmark nearby?'
      }
    ]
  };

  function tr(vi, en) {
    return state.lang === 'vi' ? vi : en;
  }

  function showToast(msg) {
    const el = document.getElementById('toastBanner');
    if (!el) return;
    el.innerHTML = `<span>✨</span><span>${msg}</span>`;
    el.classList.add('show');
    clearTimeout(window.__toastTimer);
    window.__toastTimer = setTimeout(() => {
      el.classList.remove('show');
    }, 3200);
  }

  function speakText(text) {
    if (!('speechSynthesis' in window)) {
      showToast(tr('Đang phát thuyết minh AI: ' + text.slice(0, 48) + '...', 'Playing AI Narration: ' + text.slice(0, 48) + '...'));
      return;
    }
    window.speechSynthesis.cancel();
    const utter = new SpeechSynthesisUtterance(text);
    utter.lang = state.lang === 'vi' ? 'vi-VN' : 'en-US';
    utter.rate = 1.0;
    window.speechSynthesis.speak(utter);
    showToast(tr('🔊 Đang đọc thuyết minh bằng giọng nói AI...', '🔊 Speaking AI audio guide...'));
  }

  function syncChromeLabels() {
    document.documentElement.setAttribute('data-theme', state.theme);
    document.documentElement.setAttribute('data-font-scale', state.fontScale);

    const btnTheme = document.getElementById('btnThemeToggle');
    if (btnTheme) btnTheme.textContent = state.theme === 'light' ? '🌙' : '☀️';

    document.getElementById('headerBadgeMine').textContent = tr('Của tôi', 'My Space');
    document.getElementById('headerUserSubtitle').textContent = tr('Minh Nguyễn · Hạng Journey+', 'Minh Nguyen · Journey+');
    document.getElementById('labelHeaderScan').textContent = tr('Quét AI', 'AI Lens');

    document.getElementById('popoverUserMeta').textContent = tr(
      'Nhà thám hiểm Văn hóa · 12 Quốc gia · Hạng Journey+',
      'Cultural Explorer · 12 Countries · Journey+ Member'
    );
    document.getElementById('lblMinimalMode').textContent = tr('Chế độ xem tối giản', 'Minimalist View Mode');
    document.getElementById('descMinimalMode').textContent = tr(
      'Chỉ giữ lại Điểm đến chính, Thuyết minh âm thanh & Quét AI với nút bấm cỡ lớn.',
      'Keep only primary destinations, Audio Narration & AI Lens with large controls.'
    );
    const btnMin = document.getElementById('btnToggleMinimal');
    btnMin.textContent = state.minimalMode ? tr('Đang bật', 'On') : tr('Đang tắt', 'Off');
    btnMin.classList.toggle('on', state.minimalMode);

    document.getElementById('lblVoiceConfirm').textContent = tr('Giọng nói thuyết minh AI', 'AI Voice Narration');
    document.getElementById('descVoiceConfirm').textContent = tr(
      'Tự động đọc thuyết minh lịch sử & văn hóa khi nhận diện địa danh hoặc mở điểm đến.',
      'Automatically read historical & cultural stories aloud when opening landmarks.'
    );
    const btnVoice = document.getElementById('btnToggleVoice');
    btnVoice.textContent = state.voiceNarration ? tr('Đang bật', 'On') : tr('Đang tắt', 'Off');
    btnVoice.classList.toggle('on', state.voiceNarration);
    document.getElementById('btnTestVoice').textContent = tr('Nghe thử', 'Test Voice');

    document.getElementById('lblFontSize').textContent = tr('Cỡ chữ hiển thị', 'Display Text Size');
    document.getElementById('btnFontMedium').textContent = tr('Vừa', 'Medium');
    document.getElementById('btnFontLarge').textContent = tr('Lớn', 'Large');
    document.getElementById('btnFontXLarge').textContent = tr('Rất lớn', 'Extra Large');

    ['medium', 'large', 'xlarge'].forEach((scale) => {
      const id = scale === 'medium' ? 'btnFontMedium' : scale === 'large' ? 'btnFontLarge' : 'btnFontXLarge';
      document.getElementById(id).classList.toggle('active', state.fontScale === scale);
    });

    document.getElementById('btnPopoverLight').classList.toggle('active', state.theme === 'light');
    document.getElementById('btnPopoverDark').classList.toggle('active', state.theme === 'dark');
    document.getElementById('btnLangVi').classList.toggle('active', state.lang === 'vi');
    document.getElementById('btnLangEn').classList.toggle('active', state.lang === 'en');

    document.getElementById('linkSavedPlaces').textContent = tr(
      `Hồ sơ & Địa điểm đã lưu (${state.savedIds.length})`,
      `Profile & Saved Places (${state.savedIds.length})`
    );
    document.getElementById('linkAiPlanner').textContent = tr(
      'Cài đặt Lịch trình & Thông báo thông minh',
      'Smart Itinerary & Notification Settings'
    );
    document.getElementById('linkPrivacyScreens').textContent = tr(
      'Quyền riêng tư, Ngoại tuyến & Danh mục 34 Màn hình',
      'Privacy, Offline Maps & All 34 Screens Directory'
    );

    // Bottom Nav
    document.getElementById('navHome').textContent = tr('Trang chủ', 'Home');
    document.getElementById('navExplore').textContent = tr('Khám phá', 'Explore');
    document.getElementById('navDiscover').textContent = tr('Ống kính AI', 'AI Lens');
    document.getElementById('navJournal').textContent = tr('Nhật ký', 'Journal');
    document.getElementById('navProfile').textContent = tr('Hồ sơ', 'Profile');

    document.querySelectorAll('.nav-tab').forEach((btn) => {
      btn.classList.toggle('active', btn.getAttribute('data-tab') === state.tab);
    });
  }

  function renderHome() {
    const activeAudioPlace = DESTINATIONS.find((d) => d.id === state.activeAudioPlaceId) || DESTINATIONS[0];
    const filtered = DESTINATIONS.filter((d) => {
      const matchCat = state.categoryFilter === 'all' || d.category === state.categoryFilter;
      const q = state.searchQuery.trim().toLowerCase();
      const matchSearch =
        !q ||
        d.nameVi.toLowerCase().includes(q) ||
        d.nameEn.toLowerCase().includes(q) ||
        d.regionVi.toLowerCase().includes(q);
      return matchCat && matchSearch;
    });

    if (state.minimalMode) {
      return `
        <div class="hero-card" style="background-image:url('${DESTINATIONS[0].image}'); min-height:280px;">
          <div class="hero-eyebrow">✨ ${tr('CHẾ ĐỘ XEM TỐI GIẢN ĐANG BẬT', 'MINIMALIST VIEW ACTIVE')}</div>
          <h1 class="hero-title serif-title">${tr('Khám phá Thế giới. Lắng nghe Câu chuyện.', 'Explore the World. Discover the Stories.')}</h1>
          <div class="hero-cta-row" style="margin-top:14px;">
            <button class="btn-gold" onclick="window.journeyApp.switchTab('discover')" style="font-size:1.05rem;padding:16px 26px;">
              📷 ${tr('Quét Địa Danh Bằng AI Ngay', 'Scan Landmark with AI Now')}
            </button>
            <button class="btn-glass" onclick="window.journeyApp.playDestinationAudio('halong')" style="font-size:1.05rem;padding:16px 26px;">
              🔊 ${tr('Nghe Thuyết Minh Vịnh Hạ Long', 'Listen to Ha Long Bay Audio')}
            </button>
          </div>
        </div>
        <div class="cards-grid">
          ${DESTINATIONS.slice(0, 2).map(renderPlaceCard).join('')}
        </div>
      `;
    }

    return `
      <!-- Hero Banner Component -->
      <section class="hero-banner" aria-label="Hero Banner">
        <img
          src="images/img_hero_halong_bay.jpg"
          alt="${tr('Vịnh Hạ Long — Ha Long Bay', 'Ha Long Bay')}"
          class="hero-banner-img"
        />
        <div class="hero-eyebrow">🧭 ${tr('ĐIỂM ĐẾN NỔI BẬT HÔM NAY · DI SẢN THẾ GIỚI', 'FEATURED TODAY · UNESCO WORLD HERITAGE')}</div>
        <h1 class="hero-title serif-title">${tr('Vịnh Hạ Long — Huyền Thoại Đá Giữa Biển Ngọc', 'Ha Long Bay — Legends Sculpted in Emerald Waters')}</h1>
        <p class="hero-subtitle">
          ${tr(
            'Khám phá Thế giới. Lắng nghe Câu chuyện. Trải nghiệm thuyết minh âm thanh AI không gian 3D, nhận diện di sản qua ống kính thời gian thực và lưu giữ nhật ký hành trình điện ảnh 4K.',
            'Explore the World. Discover the Stories. Experience spatial AI audio storytelling, real-time architectural lens recognition, and cinematic 4K travel journaling.'
          )}
        </p>
        <div class="hero-cta-row">
          <button class="hero-cta-button" onclick="window.journeyApp.openDestinationModal('halong')">
            <span>🎧</span>
            <span>${tr('Khám phá & Nghe câu chuyện', 'Explore & Listen to Story')}</span>
          </button>
          <button class="btn-glass" onclick="window.journeyApp.switchTab('discover')">
            <span>📷</span>
            <span>${tr('Mở Ống kính Nhận diện AI', 'Open AI Recognition Lens')}</span>
          </button>
          <button class="btn-glass" onclick="window.journeyApp.openAiPlannerModal()">
            <span>🗓️</span>
            <span>${tr('Tạo Lịch trình AI', 'AI Smart Itinerary')}</span>
          </button>
        </div>
      </section>

      <!-- Live Audio Story Player Bar -->
      <section class="audio-guide-bar">
        <div style="display:flex;align-items:center;gap:14px;">
          <img src="${activeAudioPlace.image}" alt="" style="width:56px;height:56px;border-radius:14px;object-fit:cover;border:1px solid var(--accent-gold);" />
          <div>
            <div style="font-size:0.74rem;font-weight:700;color:var(--accent-gold-dark);text-transform:uppercase;letter-spacing:0.05em;">
              🎙️ ${tr('THUYẾT MINH ÂM THANH AI ĐANG SẴN SÀNG', 'AI AUDIO NARRATION READY')}
            </div>
            <div style="font-weight:800;font-size:1.02rem;color:var(--text-primary);">
              ${tr(activeAudioPlace.nameVi, activeAudioPlace.nameEn)} — ${tr(activeAudioPlace.regionVi, activeAudioPlace.regionEn)}
            </div>
            <div style="font-size:0.8rem;color:var(--text-secondary);">
              ${tr(activeAudioPlace.durationVi, activeAudioPlace.durationEn)} · <span class="mono-text">${activeAudioPlace.coords}</span>
            </div>
          </div>
        </div>
        <div style="display:flex;align-items:center;gap:10px;">
          <button class="btn-gold" onclick="window.journeyApp.playDestinationAudio('${activeAudioPlace.id}')">
            <span>🔊</span>
            <span>${tr('Phát thuyết minh', 'Play Narration')}</span>
          </button>
        </div>
      </section>

      <!-- Search & Category Filter Row -->
      <div class="search-bar-wrap">
        <span>🔍</span>
        <input
          type="text"
          class="search-input"
          placeholder="${tr('Tìm kiếm địa danh xu hướng, di sản lịch sử, phố cổ, kỳ quan...', 'Search trending places, heritage sites, ancient towns...')}"
          value="${state.searchQuery}"
          oninput="window.journeyApp.setSearchQuery(this.value)"
        />
        ${state.searchQuery ? `<button class="section-link" onclick="window.journeyApp.setSearchQuery('')">${tr('Xóa', 'Clear')}</button>` : ''}
      </div>

      <div class="chip-row">
        <button class="chip-btn ${state.categoryFilter === 'all' ? 'active' : ''}" onclick="window.journeyApp.setCategory('all')">
          ✨ ${tr('Tất cả điểm đến', 'All Trending Places')}
        </button>
        <button class="chip-btn ${state.categoryFilter === 'heritage' ? 'active' : ''}" onclick="window.journeyApp.setCategory('heritage')">
          🏮 ${tr('Di sản & Phố cổ', 'Heritage & Ancient Towns')}
        </button>
        <button class="chip-btn ${state.categoryFilter === 'architecture' ? 'active' : ''}" onclick="window.journeyApp.setCategory('architecture')">
          🏛️ ${tr('Kiến trúc Lịch sử', 'Historical Architecture')}
        </button>
        <button class="chip-btn ${state.categoryFilter === 'nature' ? 'active' : ''}" onclick="window.journeyApp.setCategory('nature')">
          ⛰️ ${tr('Kỳ quan Thiên nhiên', 'Natural Wonders')}
        </button>
      </div>

      <!-- Trending Places Grid Section -->
      <section class="trending-places-section" aria-label="Trending Places">
        <div class="section-header">
          <div>
            <h2 class="section-title serif-title">${tr('Địa Điểm Xu Hướng — Trending Places', 'Trending Places — Curated Heritage')}</h2>
            <p style="font-size:0.86rem;color:var(--text-secondary);">
              ${tr(
                'Những điểm đến di sản được cộng đồng Journey AI quét ống kính và nghe thuyết minh nhiều nhất tuần này',
                'Most-scanned cultural landmarks and audio stories explored by the Journey AI community this week'
              )}
            </p>
          </div>
          <button class="section-link" onclick="window.journeyApp.switchTab('explore')">
            ${tr('Xem trên Bản đồ tương tác →', 'View on Interactive Map →')}
          </button>
        </div>

        <div class="trending-places-grid">
          ${filtered.map(renderPlaceCard).join('')}
        </div>
      </section>

      ${renderExploreMapSection()}
    `;
  }

  function renderPlaceCard(place, index) {
    const isSaved = state.savedIds.includes(place.id);
    const rankNum = typeof index === 'number' ? String(index + 1).padStart(2, '0') : '01';
    return `
      <article class="place-card trending-place-card" onclick="window.journeyApp.openDestinationModal('${place.id}')">
        <div class="place-card-img-wrap">
          <img src="${place.image}" alt="${tr(place.nameVi, place.nameEn)}" class="place-card-img" />
          <span class="place-badge-top">${tr(place.badgeVi, place.badgeEn)}</span>
          <button
            class="place-save-btn ${isSaved ? 'saved' : ''}"
            onclick="event.stopPropagation(); window.journeyApp.toggleSave('${place.id}')"
            title="${tr('Lưu địa điểm', 'Save place')}"
          >
            ${isSaved ? '🔖' : '☆'}
          </button>
        </div>
        <div class="place-card-body">
          <div class="place-meta-row">
            <span class="trending-rank-pill">🔥 #${rankNum} TRENDING</span>
            <span class="mono-text">★ ${place.rating} · ${place.distance}</span>
          </div>
          <h3 class="place-title serif-title">${tr(place.nameVi, place.nameEn)}</h3>
          <div style="font-size:0.78rem;font-weight:600;color:var(--accent-gold-dark);margin-bottom:6px;">
            📍 ${tr(place.regionVi, place.regionEn)}
          </div>
          <p class="place-desc">${tr(place.summaryVi, place.summaryEn)}</p>
          <div class="place-footer">
            <span style="font-size:0.78rem;font-weight:700;color:var(--accent-sage);">
              🎧 ${tr(place.durationVi, place.durationEn)}
            </span>
            <button
              class="btn-pill-toggle"
              onclick="event.stopPropagation(); window.journeyApp.playDestinationAudio('${place.id}')"
            >
              🔊 ${tr('Nghe ngay', 'Listen')}
            </button>
          </div>
        </div>
      </article>
    `;
  }

  function renderExploreMapSection() {
    const selected = DESTINATIONS.find((d) => d.id === state.selectedMapId) || DESTINATIONS[0];
    return `
      <!-- Live Interactive Explore Map Section (.explore-section) -->
      <section class="explore-section" id="exploreMapSection" aria-label="Interactive Explore Map">
        <div class="section-header">
          <div>
            <h2 class="section-title serif-title">
              ${tr('Bản Đồ Tương Tác Địa Điểm Xu Hướng — Live Explore Map', 'Interactive Trending Places Map — Live GIS')}
            </h2>
            <p style="font-size:0.86rem;color:var(--text-secondary);">
              ${tr(
                'Khám phá trực tiếp vị trí địa lý, lộ trình di sản và chạm vào từng ghim trên bản đồ Leaflet để nghe thuyết minh AI',
                'Explore real-time coordinates, heritage routes, and tap any Leaflet marker for instant AI audio storytelling'
              )}
            </p>
          </div>
          <div style="display:flex;gap:8px;flex-wrap:wrap;">
            <button class="btn-pill-toggle" onclick="window.journeyApp.fitAllTrendingMarkers()">
              🗺️ ${tr('Toàn cảnh Việt Nam', 'Fit All Pins')}
            </button>
            <button class="btn-gold" onclick="window.journeyApp.openAiPlannerModal()">
              ✨ ${tr('Tối ưu lộ trình AI', 'Optimize Route with AI')}
            </button>
          </div>
        </div>

        <div class="leaflet-explore-wrapper">
          <div id="leafletExploreMap" class="leaflet-explore-map" role="region" aria-label="Leaflet Trending Places Map"></div>
          <div class="explore-map-controls">
            <div style="display:flex;gap:8px;flex-wrap:wrap;">
              ${DESTINATIONS.map((d, idx) => `
                <button
                  class="chip-btn ${d.id === selected.id ? 'active' : ''}"
                  onclick="window.journeyApp.flyToTrendingPlace('${d.id}')"
                >
                  📍 #0${idx + 1} ${tr(d.nameVi, d.nameEn)}
                </button>
              `).join('')}
            </div>
            <span class="mono-text" style="font-size:0.78rem;color:var(--text-secondary);">
              ${selected.coords} · ★ ${selected.rating}
            </span>
          </div>
        </div>

        <!-- Selected Map Place Spotlight Bar -->
        <div class="audio-guide-bar" style="margin-bottom:0;">
          <div style="display:flex;align-items:center;gap:14px;flex:1;">
            <img src="${selected.image}" alt="" style="width:68px;height:68px;border-radius:16px;object-fit:cover;border:1.5px solid var(--accent-gold);" />
            <div>
              <div class="mono-text" style="font-size:0.74rem;color:var(--accent-gold-dark);">
                📍 ${tr(selected.regionVi, selected.regionEn)} · ${selected.coords}
              </div>
              <div class="serif-title" style="font-size:1.35rem;font-weight:700;color:var(--text-primary);">
                ${tr(selected.nameVi, selected.nameEn)}
              </div>
              <div style="font-size:0.84rem;color:var(--text-secondary);max-width:580px;">
                ${tr(selected.summaryVi, selected.summaryEn)}
              </div>
            </div>
          </div>
          <div style="display:flex;gap:10px;flex-wrap:wrap;">
            <button class="btn-gold" onclick="window.journeyApp.playDestinationAudio('${selected.id}')">
              🔊 ${tr('Thuyết minh', 'Audio Guide')}
            </button>
            <button class="btn-pill-toggle" onclick="window.journeyApp.openDestinationModal('${selected.id}')">
              ${tr('Chi tiết →', 'Details →')}
            </button>
          </div>
        </div>
      </section>
    `;
  }

  function renderExplore() {
    return `
      ${renderExploreMapSection()}

      <div class="section-header" style="margin-top:26px;">
        <h2 class="section-title serif-title">${tr('Danh Sách Địa Điểm Xu Hướng Trên Bản Đồ', 'Trending Places on the Map')}</h2>
      </div>
      <div class="trending-places-grid">
        ${DESTINATIONS.map(renderPlaceCard).join('')}
      </div>
    `;
  }

  function renderDiscover() {
    const target = DESTINATIONS.find((d) => d.id === state.selectedScanId) || DESTINATIONS[2];
    return `
      <div class="section-header">
        <div>
          <h1 class="section-title serif-title" style="font-size:1.85rem;">
            ${tr('Ống Kính AI Nhận Diện Di Sản & Kiến Trúc', 'AI Architectural & Landmark Recognition Lens')}
          </h1>
          <p style="font-size:0.88rem;color:var(--text-secondary);">
            ${tr('Hướng camera hoặc tải ảnh lên để AI phân tích niên đại, phong cách kiến trúc và thuyết minh lịch sử tức thì.', 'Point your camera or upload a photo for instant AI historical analysis and voice narration.')}
          </p>
        </div>
      </div>

      <!-- Camera Viewfinder Stage -->
      <div class="camera-stage" id="cameraStage" style="background-image:url('${target.image}');">
        <video id="liveCameraVideo" autoplay playsinline muted style="display:none;position:absolute;inset:0;width:100%;height:100%;object-fit:cover;z-index:1;"></video>

        <div style="display:flex;justify-content:space-between;align-items:center;gap:12px;flex-wrap:wrap;">
          <span class="hero-eyebrow" style="margin:0;">
            🟢 ${tr('AI VISION ENGINE · ĐỘ CHÍNH XÁC 99.4%', 'AI VISION ENGINE · 99.4% CONFIDENCE')}
          </span>
          <div style="display:flex;gap:8px;flex-wrap:wrap;">
            <button class="btn-glass" onclick="window.journeyApp.startLiveCamera()">
              📹 ${tr('Bật Camera Thực', 'Start Live Camera')}
            </button>
            <label class="btn-glass" style="cursor:pointer;">
              🖼️ ${tr('Tải Ảnh Lên', 'Upload Photo')}
              <input type="file" accept="image/*" style="display:none;" onchange="window.journeyApp.handlePhotoUpload(event)" />
            </label>
          </div>
        </div>

        <div class="scan-reticle">
          <div style="text-align:center;padding:12px;">
            <div style="font-size:2rem;margin-bottom:6px;">${state.isScanning ? '⏳' : '🏛️'}</div>
            <div class="mono-text" style="font-size:0.78rem;color:#B99B72;">
              ${state.isScanning ? tr('ĐANG PHÂN TÍCH KIẾN TRÚC...', 'ANALYZING ARCHITECTURE...') : target.coords}
            </div>
            <div style="font-weight:700;font-size:0.95rem;margin-top:4px;">
              ${tr(target.nameVi, target.nameEn)}
            </div>
          </div>
        </div>

        <div style="display:flex;justify-content:space-between;align-items:center;gap:12px;flex-wrap:wrap;">
          <div style="display:flex;gap:8px;flex-wrap:wrap;">
            ${DESTINATIONS.map((d) => `
              <button
                class="chip-btn ${d.id === target.id ? 'active' : ''}"
                onclick="window.journeyApp.selectScanTarget('${d.id}')"
              >
                ${tr(d.nameVi, d.nameEn)}
              </button>
            `).join('')}
          </div>
          <button class="btn-gold" onclick="window.journeyApp.triggerAiScan()">
            ✨ ${tr('Phân tích & Đọc Thuyết Minh AI', 'Analyze & Speak AI Story')}
          </button>
        </div>
      </div>

      <!-- AI Recognition Result Card -->
      <div class="place-card" style="padding:22px;cursor:default;">
        <div style="display:flex;justify-content:space-between;align-items:flex-start;gap:16px;flex-wrap:wrap;margin-bottom:12px;">
          <div>
            <span class="badge-mine">${tr('KẾT QUẢ NHẬN DIỆN AI', 'AI RECOGNITION RESULT')}</span>
            <h2 class="serif-title" style="font-size:1.65rem;margin-top:6px;">${tr(target.nameVi, target.nameEn)}</h2>
            <div class="mono-text" style="font-size:0.8rem;color:var(--text-secondary);">${tr(target.regionVi, target.regionEn)} · ${target.coords}</div>
          </div>
          <div style="display:flex;gap:10px;">
            <button class="btn-gold" onclick="window.journeyApp.playDestinationAudio('${target.id}')">
              🔊 ${tr('Nghe thuyết minh AI', 'Listen to AI Guide')}
            </button>
            <button class="btn-pill-toggle" onclick="window.journeyApp.openAiChatModal('${target.id}')">
              💬 ${tr('Hỏi chuyên sâu AI', 'Ask AI Concierge')}
            </button>
          </div>
        </div>
        <p style="font-size:0.95rem;color:var(--text-primary);margin-bottom:10px;">${tr(target.summaryVi, target.summaryEn)}</p>
        <p style="font-size:0.9rem;color:var(--text-secondary);">${tr(target.storyVi, target.storyEn)}</p>
      </div>
    `;
  }

  function renderJournal() {
    return `
      <div class="section-header">
        <div>
          <h1 class="section-title serif-title" style="font-size:1.85rem;">
            ${tr('Nhật Ký Hành Trình & Phim Kỷ Niệm AI 4K', 'Travel Journal & 4K AI Story Studio')}
          </h1>
          <p style="font-size:0.88rem;color:var(--text-secondary);">
            ${tr('Lưu giữ cảm xúc, hình ảnh và tự động dựng thước phim hành trình bằng AI.', 'Capture memories, voice notes, and synthesize cinematic 4K stories with AI.')}
          </p>
        </div>
        <div style="display:flex;gap:10px;flex-wrap:wrap;">
          <button class="btn-gold" onclick="window.journeyApp.openCreateJournalModal()">
            ✍️ ${tr('Viết Nhật Ký Mới', 'New Journal Entry')}
          </button>
          <button class="btn-pill-toggle on" onclick="window.journeyApp.openStoryVideoModal()">
            🎬 ${tr('Xem Phim Kỷ Niệm 4K', 'Preview 4K AI Story Video')}
          </button>
        </div>
      </div>

      <div class="cards-grid">
        ${state.journals.map((item) => `
          <article class="place-card" onclick="window.journeyApp.openStoryVideoModal('${item.id}')">
            <div class="place-card-img-wrap">
              <img src="${item.image}" alt="" class="place-card-img" />
              <span class="place-badge-top">${item.mood}</span>
            </div>
            <div class="place-card-body">
              <div class="place-meta-row">
                <span>📍 ${tr(item.locationVi, item.locationEn)}</span>
                <span class="mono-text">${item.date}</span>
              </div>
              <h3 class="place-title serif-title">${tr(item.titleVi, item.titleEn)}</h3>
              <p class="place-desc">${tr(item.excerptVi, item.excerptEn)}</p>
              <div class="place-footer">
                <span style="font-size:0.78rem;font-weight:700;color:var(--accent-gold-dark);">🎬 AI Story 4K Ready</span>
                <button class="btn-pill-toggle" onclick="event.stopPropagation(); window.journeyApp.openStoryVideoModal('${item.id}')">
                  ▶ ${tr('Phát Story', 'Play Story')}
                </button>
              </div>
            </div>
          </article>
        `).join('')}
      </div>
    `;
  }

  function renderProfile() {
    return `
      <!-- Explorer Passport Header Card -->
      <div class="place-card" style="padding:24px;margin-bottom:24px;cursor:default;">
        <div style="display:flex;align-items:center;justify-content:space-between;gap:16px;flex-wrap:wrap;">
          <div style="display:flex;align-items:center;gap:16px;">
            <div class="popover-avatar-lg" style="width:68px;height:68px;font-size:1.35rem;">MN</div>
            <div>
              <span class="badge-mine">JOURNEY+ PASSPORT</span>
              <h1 class="serif-title" style="font-size:1.75rem;margin-top:4px;">Minh Nguyễn</h1>
              <p style="font-size:0.88rem;color:var(--text-secondary);">
                ${tr('Nhà thám hiểm Di sản · Hà Nội, Việt Nam · 12 Quốc gia · 48 Địa danh đã quét AI', 'Heritage Explorer · Hanoi, Vietnam · 12 Countries · 48 Landmarks Scanned')}
              </p>
            </div>
          </div>
          <div style="display:flex;gap:10px;flex-wrap:wrap;">
            <button class="btn-gold" onclick="window.journeyApp.togglePopover()">
              ⚙️ ${tr('Mở Cài đặt Nhanh (MN ⌄)', 'Open Quick Settings (MN ⌄)')}
            </button>
            <button class="btn-pill-toggle" onclick="window.journeyApp.openAllScreensModal()">
              📱 ${tr('Danh mục 34 Màn hình', '34-Screen Directory')}
            </button>
          </div>
        </div>

        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(140px,1fr));gap:12px;margin-top:20px;">
          <div class="popover-setting-box" style="margin:0;text-align:center;">
            <div class="mono-text" style="font-size:1.45rem;font-weight:800;color:var(--accent-gold-dark);">48</div>
            <div style="font-size:0.8rem;color:var(--text-secondary);">${tr('Di sản đã nhận diện', 'Landmarks Scanned')}</div>
          </div>
          <div class="popover-setting-box" style="margin:0;text-align:center;">
            <div class="mono-text" style="font-size:1.45rem;font-weight:800;color:var(--accent-sage);">${state.savedIds.length}</div>
            <div style="font-size:0.8rem;color:var(--text-secondary);">${tr('Địa điểm đã lưu', 'Saved Places')}</div>
          </div>
          <div class="popover-setting-box" style="margin:0;text-align:center;">
            <div class="mono-text" style="font-size:1.45rem;font-weight:800;color:var(--text-primary);">${state.journals.length}</div>
            <div style="font-size:0.8rem;color:var(--text-secondary);">${tr('Nhật ký & Phim 4K', 'Journals & 4K Stories')}</div>
          </div>
          <div class="popover-setting-box" style="margin:0;text-align:center;">
            <div class="mono-text" style="font-size:1.45rem;font-weight:800;color:var(--accent-gold-dark);">3.2 GB</div>
            <div style="font-size:0.8rem;color:var(--text-secondary);">${tr('Bản đồ Ngoại tuyến', 'Offline Maps Ready')}</div>
          </div>
        </div>
      </div>

      <!-- Saved Destinations Section -->
      <div class="section-header">
        <h2 class="section-title serif-title">${tr('Bộ Sưu Tập Địa Điểm Đã Lưu', 'Saved Heritage Collection')}</h2>
      </div>
      <div class="cards-grid">
        ${DESTINATIONS.filter((d) => state.savedIds.includes(d.id)).map(renderPlaceCard).join('')}
      </div>
    `;
  }

  let leafletMapInstance = null;
  let leafletMarkersById = {};
  let leafletMarkerLayerGroup = null;

  // Explicit Custom Marker Definitions for Ha Long Bay, Hoi An, Temple of Literature, and Sa Pa
  const TRENDING_MAP_MARKERS = [
    {
      id: 'halong',
      rank: '#01',
      emblem: '⛵',
      titleVi: 'Vịnh Hạ Long',
      titleEn: 'Ha Long Bay',
      subtitleVi: 'Di sản Thiên nhiên UNESCO · Quảng Ninh',
      subtitleEn: 'UNESCO Natural Wonder · Quang Ninh',
      lat: 20.9101,
      lng: 107.1839,
      coords: '20.9101° N, 107.1839° E',
      rating: '4.9',
      image: 'images/img_hero_halong_bay.jpg'
    },
    {
      id: 'hoian',
      rank: '#02',
      emblem: '🏮',
      titleVi: 'Phố Cổ Hội An',
      titleEn: 'Hoi An',
      subtitleVi: 'Thương cảng Đèn lồng Thế kỷ XVI · Quảng Nam',
      subtitleEn: '16th-Century Lantern Trading Port · Quang Nam',
      lat: 15.8801,
      lng: 108.3380,
      coords: '15.8801° N, 108.3380° E',
      rating: '4.9',
      image: 'images/img_hoian_lanterns.jpg'
    },
    {
      id: 'hanoi_temple',
      rank: '#03',
      emblem: '🏛️',
      titleVi: 'Văn Miếu – Quốc Tử Giám',
      titleEn: 'Temple of Literature',
      subtitleVi: 'Trường Đại học Đầu tiên (1070) · Hà Nội',
      subtitleEn: 'First National University (1070) · Hanoi',
      lat: 21.0278,
      lng: 105.8355,
      coords: '21.0278° N, 105.8355° E',
      rating: '4.8',
      image: 'images/img_hanoi_temple.jpg'
    },
    {
      id: 'sapa',
      rank: '#04',
      emblem: '⛰️',
      titleVi: 'Sa Pa & Mù Cang Chải',
      titleEn: 'Sa Pa',
      subtitleVi: 'Ruộng bậc thang Hoàng Liên Sơn · Lào Cai',
      subtitleEn: 'Sculpted Highland Rice Terraces · Lao Cai',
      lat: 22.3364,
      lng: 103.8438,
      coords: '22.3364° N, 103.8438° E',
      rating: '4.9',
      image: 'images/img_sapa_terraces.jpg'
    }
  ];

  function createCustomLandmarkIcon(markerMeta, isSelected) {
    const displayLabel = tr(markerMeta.titleVi, markerMeta.titleEn);
    return window.L.divIcon({
      className: 'journey-leaflet-icon',
      html: `
        <div class="custom-landmark-marker ${isSelected ? 'active' : ''}" data-marker-id="${markerMeta.id}">
          ${isSelected ? '<span class="marker-pulse-ring"></span>' : ''}
          <div class="journey-pin-pill ${isSelected ? 'active' : ''}">
            <img src="${markerMeta.image}" alt="${displayLabel}" class="marker-thumb-avatar" />
            <span>${markerMeta.emblem} ${markerMeta.rank} ${displayLabel}</span>
          </div>
          <div class="marker-stem"></div>
          <div class="marker-ground-shadow"></div>
        </div>
      `,
      iconSize: [210, 58],
      iconAnchor: [105, 52],
      popupAnchor: [0, -48]
    });
  }

  function createCustomPopupContent(meta, place) {
    const placeName = tr(
      `${meta.titleVi} (${meta.titleEn})`,
      meta.titleEn
    );
    const viewDetailsLabel = tr('Xem chi tiết · View Details', 'View Details');
    return `
      <div class="journey-marker-popup">
        <div class="journey-popup-thumb-wrap">
          <img
            src="${meta.image}"
            alt="${placeName}"
            class="journey-popup-thumb"
          />
          <span class="journey-popup-badge">${meta.emblem} ${meta.rank} · ★ ${meta.rating}</span>
        </div>
        <div class="journey-popup-body">
          <h4 class="journey-popup-title serif-title">${placeName}</h4>
          <div class="journey-popup-subtitle">
            📍 ${tr(meta.subtitleVi, meta.subtitleEn)}
          </div>
          <div class="journey-popup-actions">
            <button
              type="button"
              class="journey-popup-view-details-btn"
              onclick="window.journeyApp.openDestinationModal('${meta.id}')"
            >
              <span>${viewDetailsLabel}</span>
              <span>→</span>
            </button>
            <button
              type="button"
              class="btn-pill-toggle"
              style="padding:8px 10px;border-radius:12px;"
              title="${tr('Nghe thuyết minh AI', 'Listen to AI Audio Guide')}"
              onclick="window.journeyApp.playDestinationAudio('${meta.id}')"
            >
              🔊
            </button>
          </div>
        </div>
      </div>
    `;
  }

  function injectCustomTrendingMarkers(map) {
    if (!map || typeof window.L === 'undefined') return;

    if (leafletMarkerLayerGroup) {
      leafletMarkerLayerGroup.clearLayers();
    } else {
      leafletMarkerLayerGroup = window.L.layerGroup().addTo(map);
    }
    leafletMarkersById = {};

    const selectedId = state.selectedMapId || 'halong';

    TRENDING_MAP_MARKERS.forEach((meta) => {
      const place = DESTINATIONS.find((d) => d.id === meta.id) || DESTINATIONS[0];
      const isSelected = meta.id === selectedId;
      const customIcon = createCustomLandmarkIcon(meta, isSelected);
      const popupHtml = createCustomPopupContent(meta, place);

      const marker = window.L.marker([meta.lat, meta.lng], {
        icon: customIcon,
        title: tr(meta.titleVi, meta.titleEn),
        riseOnHover: true,
        riseOffset: 800
      }).addTo(leafletMarkerLayerGroup);

      marker.bindPopup(popupHtml, {
        maxWidth: 280,
        minWidth: 268,
        closeButton: true,
        autoPan: true
      });

      marker.on('mouseover', function () {
        this.setZIndexOffset(1000);
        const el = this.getElement();
        const pin = el && el.querySelector('.custom-landmark-marker');
        if (pin) pin.classList.add('is-hovered');
      });

      marker.on('mouseout', function () {
        this.setZIndexOffset(meta.id === state.selectedMapId ? 500 : 0);
        const el = this.getElement();
        const pin = el && el.querySelector('.custom-landmark-marker');
        if (pin) pin.classList.remove('is-hovered');
      });

      marker.on('click', function () {
        state.selectedMapId = meta.id;
        this.openPopup();
        if (state.voiceNarration) {
          speakText(tr(place.nameVi + '. ' + place.summaryVi, place.nameEn + '. ' + place.summaryEn));
        }
      });

      leafletMarkersById[meta.id] = marker;
    });
  }

  function initLeafletExploreMap() {
    const mapContainer =
      document.querySelector('.explore-section #leafletExploreMap') ||
      document.getElementById('leafletExploreMap');
    if (!mapContainer || typeof window.L === 'undefined') return;

    if (leafletMapInstance) {
      try {
        leafletMapInstance.remove();
      } catch (e) {
        // ignore cleanup error
      }
      leafletMapInstance = null;
      leafletMarkerLayerGroup = null;
      leafletMarkersById = {};
    }

    const selected = DESTINATIONS.find((d) => d.id === state.selectedMapId) || DESTINATIONS[0];
    const map = window.L.map(mapContainer, {
      center: [selected.lat, selected.lng],
      zoom: 6,
      zoomControl: true,
      scrollWheelZoom: false
    });
    leafletMapInstance = map;

    const tileUrl =
      state.theme === 'dark'
        ? 'https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png'
        : 'https://{s}.basemaps.cartocdn.com/rastertiles/voyager/{z}/{x}/{y}{r}.png';

    window.L.tileLayer(tileUrl, {
      attribution: '&copy; OpenStreetMap &copy; CARTO · Journey AI Atlas',
      subdomains: 'abcd',
      maxZoom: 19
    }).addTo(map);

    // Draw Heritage Journey Route Polyline connecting Sa Pa -> Temple of Literature -> Ha Long Bay -> Hoi An
    const routeCoords = [
      [22.3364, 103.8438], // Sa Pa
      [21.0278, 105.8355], // Temple of Literature
      [20.9101, 107.1839], // Ha Long Bay
      [15.8801, 108.3380]  // Hoi An
    ];
    window.L.polyline(routeCoords, {
      color: '#B99B72',
      weight: 3,
      opacity: 0.85,
      dashArray: '8, 8'
    }).addTo(map);

    // Programmatically inject custom markers for Ha Long Bay, Hoi An, Temple of Literature, and Sa Pa
    injectCustomTrendingMarkers(map);

    setTimeout(() => {
      if (leafletMapInstance) {
        leafletMapInstance.invalidateSize();
        const activeMarker = leafletMarkersById[selected.id];
        if (activeMarker) activeMarker.openPopup();
      }
    }, 120);
  }

  function render() {
    syncChromeLabels();
    const viewport = document.getElementById('mainViewport');
    if (!viewport) return;

    if (state.tab === 'home') viewport.innerHTML = renderHome();
    else if (state.tab === 'explore') viewport.innerHTML = renderExplore();
    else if (state.tab === 'discover') viewport.innerHTML = renderDiscover();
    else if (state.tab === 'journal') viewport.innerHTML = renderJournal();
    else if (state.tab === 'profile') viewport.innerHTML = renderProfile();

    if (state.tab === 'home' || state.tab === 'explore') {
      initLeafletExploreMap();
    }
  }

  function openModal(html) {
    const overlay = document.getElementById('modalOverlay');
    const content = document.getElementById('modalContent');
    content.innerHTML = html;
    overlay.classList.add('open');
  }

  function closeModal() {
    document.getElementById('modalOverlay').classList.remove('open');
  }

  window.journeyApp = {
    switchTab(tab) {
      state.tab = tab;
      this.closePopover();
      render();
      window.scrollTo({ top: 0, behavior: 'smooth' });
    },
    toggleTheme() {
      state.theme = state.theme === 'light' ? 'dark' : 'light';
      render();
      showToast(state.theme === 'light' ? tr('☀️ Đã chuyển sang giao diện Sáng (Ivory)', '☀️ Switched to Light Mode') : tr('🌙 Đã chuyển sang giao diện Tối (Midnight)', '🌙 Switched to Dark Mode'));
    },
    setTheme(theme) {
      state.theme = theme;
      render();
    },
    setLanguage(lang) {
      state.lang = lang;
      render();
      showToast(lang === 'vi' ? '🇻🇳 Đã chuyển sang Tiếng Việt' : '🇬🇧 Switched to English');
    },
    setFontScale(scale) {
      state.fontScale = scale;
      render();
    },
    toggleMinimalMode() {
      state.minimalMode = !state.minimalMode;
      render();
      showToast(state.minimalMode ? tr('👁️ Đã bật Chế độ xem tối giản', '👁️ Minimalist View Enabled') : tr('👁️ Đã tắt Chế độ xem tối giản', '👁️ Minimalist View Disabled'));
    },
    toggleVoiceNarration() {
      state.voiceNarration = !state.voiceNarration;
      render();
      showToast(state.voiceNarration ? tr('🔊 Đã bật Giọng nói thuyết minh AI', '🔊 AI Voice Narration On') : tr('🔇 Đã tắt Giọng nói thuyết minh AI', '🔇 AI Voice Narration Off'));
    },
    speakSample() {
      speakText(
        tr(
          'Xin chào Minh Nguyễn. Chào mừng bạn đến với Journey AI. Hệ thống thuyết minh văn hóa và lịch sử đã sẵn sàng đồng hành cùng bạn.',
          'Welcome Minh Nguyen to Journey AI. Your cultural and historical voice guide is ready.'
        )
      );
    },
    togglePopover() {
      const pop = document.getElementById('quickProfilePopover');
      const back = document.getElementById('popoverBackdrop');
      const btn = document.getElementById('btnAvatarPopover');
      const isOpen = pop.classList.toggle('open');
      back.classList.toggle('open', isOpen);
      btn.classList.toggle('active', isOpen);
    },
    closePopover() {
      document.getElementById('quickProfilePopover').classList.remove('open');
      document.getElementById('popoverBackdrop').classList.remove('open');
      document.getElementById('btnAvatarPopover').classList.remove('active');
    },
    setCategory(cat) {
      state.categoryFilter = cat;
      render();
    },
    setSearchQuery(q) {
      state.searchQuery = q;
      render();
    },
    toggleSave(id) {
      const idx = state.savedIds.indexOf(id);
      if (idx >= 0) {
        state.savedIds.splice(idx, 1);
        showToast(tr('Đã bỏ lưu địa điểm', 'Removed from Saved Places'));
      } else {
        state.savedIds.push(id);
        showToast(tr('🔖 Đã lưu vào Bộ sưu tập cá nhân', '🔖 Saved to your collection'));
      }
      render();
    },
    selectMapPin(id) {
      this.flyToTrendingPlace(id);
    },
    flyToTrendingPlace(id) {
      const place = DESTINATIONS.find((d) => d.id === id);
      if (!place) return;
      state.selectedMapId = id;
      render();
      setTimeout(() => {
        if (leafletMapInstance) {
          leafletMapInstance.flyTo([place.lat, place.lng], 10, { duration: 1.1 });
          const marker = leafletMarkersById[id];
          if (marker) marker.openPopup();
        }
      }, 150);
      if (state.voiceNarration) {
        speakText(tr(place.nameVi + '. ' + place.summaryVi, place.nameEn + '. ' + place.summaryEn));
      }
    },
    fitAllTrendingMarkers() {
      if (!leafletMapInstance || typeof window.L === 'undefined') return;
      const bounds = window.L.latLngBounds(DESTINATIONS.map((d) => [d.lat, d.lng]));
      leafletMapInstance.flyToBounds(bounds.pad(0.25), { duration: 1.0 });
      showToast(tr('🗺️ Đang hiển thị toàn bộ lộ trình 4 địa điểm xu hướng', '🗺️ Showing all 4 Trending Places on the map'));
    },
    selectScanTarget(id) {
      state.selectedScanId = id;
      render();
    },
    triggerAiScan() {
      state.isScanning = true;
      render();
      setTimeout(() => {
        state.isScanning = false;
        render();
        const target = DESTINATIONS.find((d) => d.id === state.selectedScanId) || DESTINATIONS[0];
        speakText(tr(target.nameVi + '. ' + target.storyVi, target.nameEn + '. ' + target.storyEn));
      }, 900);
    },
    async startLiveCamera() {
      const video = document.getElementById('liveCameraVideo');
      if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
        showToast(tr('Trình duyệt đang dùng chế độ mô phỏng ống kính độ phân giải cao.', 'Using high-resolution lens viewfinder.'));
        return;
      }
      try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: 'environment' } });
        if (video) {
          video.srcObject = stream;
          video.style.display = 'block';
          showToast(tr('📹 Đã kết nối Camera thời gian thực!', '📹 Live Camera Connected!'));
        }
      } catch (err) {
        showToast(tr('Đang sử dụng khung ngắm di sản mẫu độ nét cao.', 'Using curated heritage viewfinder.'));
      }
    },
    handlePhotoUpload(event) {
      const file = event.target.files && event.target.files[0];
      if (!file) return;
      const reader = new FileReader();
      reader.onload = function (e) {
        const stage = document.getElementById('cameraStage');
        if (stage) {
          stage.style.backgroundImage = `url('${e.target.result}')`;
        }
        window.journeyApp.triggerAiScan();
      };
      reader.readAsDataURL(file);
    },
    playDestinationAudio(id) {
      state.activeAudioPlaceId = id;
      const place = DESTINATIONS.find((d) => d.id === id) || DESTINATIONS[0];
      render();
      speakText(tr(`${place.nameVi}. ${place.summaryVi} ${place.storyVi}`, `${place.nameEn}. ${place.summaryEn} ${place.storyEn}`));
    },
    openDestinationModal(id) {
      const place = DESTINATIONS.find((d) => d.id === id) || DESTINATIONS[0];
      if (state.voiceNarration) {
        speakText(tr(place.nameVi + '. ' + place.summaryVi, place.nameEn + '. ' + place.summaryEn));
      }
      openModal(`
        <img src="${place.image}" alt="" style="width:100%;height:240px;object-fit:cover;border-radius:20px;margin-bottom:16px;" />
        <div style="display:flex;justify-content:space-between;align-items:center;gap:8px;margin-bottom:8px;">
          <span class="badge-mine">${tr(place.badgeVi, place.badgeEn)}</span>
          <span class="mono-text" style="font-size:0.8rem;color:var(--text-secondary);">${place.coords}</span>
        </div>
        <h2 class="serif-title" style="font-size:1.85rem;margin-bottom:6px;">${tr(place.nameVi, place.nameEn)}</h2>
        <p style="font-size:0.95rem;color:var(--text-primary);margin-bottom:12px;">${tr(place.summaryVi, place.summaryEn)}</p>
        <div class="popover-setting-box" style="margin-bottom:16px;">
          <div style="font-weight:700;margin-bottom:4px;">📜 ${tr('Câu chuyện Lịch sử & Văn hóa', 'Historical & Cultural Narrative')}</div>
          <p style="font-size:0.88rem;color:var(--text-secondary);">${tr(place.storyVi, place.storyEn)}</p>
        </div>
        <div style="display:flex;gap:10px;justify-content:flex-end;flex-wrap:wrap;">
          <button class="btn-gold" onclick="window.journeyApp.playDestinationAudio('${place.id}')">
            🔊 ${tr('Đọc Thuyết Minh AI', 'Play AI Audio Guide')}
          </button>
          <button class="btn-pill-toggle" onclick="window.journeyApp.closeModal()">
            ${tr('Đóng', 'Close')}
          </button>
        </div>
      `);
    },
    openAiChatModal() {
      this.closePopover();
      openModal(`
        <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:14px;">
          <div>
            <span class="badge-mine">JOURNEY AI CONCIERGE</span>
            <h2 class="serif-title" style="font-size:1.55rem;margin-top:4px;">${tr('Trợ Lý Du Lịch & Lịch Sử AI', 'AI Cultural & Travel Concierge')}</h2>
          </div>
          <button class="btn-pill-toggle" onclick="window.journeyApp.closeModal()">✕</button>
        </div>
        <div id="chatMessagesBox" style="max-height:320px;overflow-y:auto;display:flex;flex-direction:column;gap:10px;margin-bottom:14px;">
          ${state.chatMessages.map((m) => `
            <div class="popover-setting-box" style="margin:0;background:${m.role === 'ai' ? 'var(--surface-subtle)' : 'var(--accent-gold-soft)'};">
              <div style="font-size:0.75rem;font-weight:700;color:var(--accent-gold-dark);margin-bottom:4px;">
                ${m.role === 'ai' ? '🧭 Journey AI' : '👤 Minh Nguyễn'}
              </div>
              <div style="font-size:0.9rem;">${ m.text || tr(m.textVi, m.textEn) }</div>
            </div>
          `).join('')}
        </div>
        <div style="display:flex;gap:8px;">
          <input id="chatInputBox" class="search-input" style="border:1px solid var(--border-color);border-radius:12px;padding:10px 14px;" placeholder="${tr('Nhập câu hỏi về lịch sử, ẩm thực, lộ trình...', 'Ask about history, hidden gems, itineraries...')}" onkeydown="if(event.key==='Enter') window.journeyApp.sendChatMessage()" />
          <button class="btn-gold" onclick="window.journeyApp.sendChatMessage()">${tr('Gửi', 'Send')}</button>
        </div>
      `);
    },
    sendChatMessage() {
      const input = document.getElementById('chatInputBox');
      if (!input || !input.value.trim()) return;
      const userText = input.value.trim();
      state.chatMessages.push({ role: 'user', text: userText });
      const replyVi = `Dựa trên yêu cầu "${userText}", tôi gợi ý bạn ghé thăm Văn Miếu lúc 8:00 sáng để đón nắng sớm qua Khuê Văn Các, thưởng thức phở gia truyền và dùng Ống kính AI quét 82 bia Tiến sĩ.`;
      const replyEn = `Based on "${userText}", I recommend visiting the Temple of Literature at 8:00 AM for golden light across Khue Van Cac, followed by scanning the 82 Royal Stele inscriptions with Journey AI Lens.`;
      state.chatMessages.push({ role: 'ai', textVi: replyVi, textEn: replyEn });
      this.openAiChatModal();
      if (state.voiceNarration) speakText(tr(replyVi, replyEn));
    },
    openCreateJournalModal() {
      openModal(`
        <h2 class="serif-title" style="font-size:1.65rem;margin-bottom:12px;">✍️ ${tr('Tạo Nhật Ký Hành Trình Mới', 'Create New Travel Journal Entry')}</h2>
        <div style="display:flex;flex-direction:column;gap:12px;margin-bottom:16px;">
          <input id="newJournalTitle" class="search-input" style="border:1px solid var(--border-color);border-radius:12px;padding:12px;" placeholder="${tr('Tiêu đề kỷ niệm (VD: Chiều thu Hà Nội)...', 'Entry Title...')}" />
          <input id="newJournalLoc" class="search-input" style="border:1px solid var(--border-color);border-radius:12px;padding:12px;" placeholder="${tr('Địa điểm (VD: Hoàng Thành Thăng Long)...', 'Location...')}" />
          <textarea id="newJournalBody" rows="4" class="search-input" style="border:1px solid var(--border-color);border-radius:12px;padding:12px;" placeholder="${tr('Ghi lại cảm xúc hoặc để AI tự động tổng hợp câu chuyện...', 'Write your reflection or let AI synthesize your story...')}"></textarea>
        </div>
        <div style="display:flex;justify-content:flex-end;gap:10px;">
          <button class="btn-gold" onclick="window.journeyApp.saveNewJournal()">✨ ${tr('Lưu & Tạo Story AI', 'Save & Generate AI Story')}</button>
          <button class="btn-pill-toggle" onclick="window.journeyApp.closeModal()">${tr('Hủy', 'Cancel')}</button>
        </div>
      `);
    },
    saveNewJournal() {
      const title = (document.getElementById('newJournalTitle')?.value || '').trim() || tr('Khoảnh Khắc Di Sản Việt Nam', 'Vietnam Heritage Moment');
      const loc = (document.getElementById('newJournalLoc')?.value || '').trim() || tr('Hà Nội, Việt Nam', 'Hanoi, Vietnam');
      const body = (document.getElementById('newJournalBody')?.value || '').trim() || tr('Kỷ niệm đáng nhớ được tổng hợp cùng thuyết minh AI.', 'Memorable journey synthesized with Journey AI.');
      state.journals.unshift({
        id: 'j_' + Date.now(),
        date: '13 Thg 11, 2026',
        locationVi: loc,
        locationEn: loc,
        titleVi: title,
        titleEn: title,
        mood: '✨ AI Synthesized',
        image: 'images/img_sapa_terraces.jpg',
        excerptVi: body,
        excerptEn: body
      });
      closeModal();
      state.tab = 'journal';
      render();
      showToast(tr('📖 Đã lưu nhật ký hành trình mới!', '📖 Saved new travel journal entry!'));
    },
    openStoryVideoModal() {
      openModal(`
        <div style="position:relative;border-radius:22px;overflow:hidden;height:340px;background-image:url('images/img_hoian_lanterns.jpg');background-size:cover;background-position:center;display:flex;flex-direction:column;justify-content:space-between;padding:20px;color:#FFFFFF;margin-bottom:16px;">
          <div style="position:absolute;inset:0;background:linear-gradient(180deg,rgba(17,25,35,0.3),rgba(17,25,35,0.88));"></div>
          <div style="position:relative;z-index:2;display:flex;justify-content:space-between;">
            <span class="hero-eyebrow" style="margin:0;">🎬 4K AI STORY REEL · 60 FPS</span>
            <span class="mono-text">00:18 / 00:45</span>
          </div>
          <div style="position:relative;z-index:2;">
            <h2 class="serif-title" style="font-size:1.85rem;margin-bottom:6px;">${tr('Đêm Đèn Lồng Bên Sông Hoài — Hội An', 'Lanterns Along the Hoai River — Hoi An')}</h2>
            <p style="font-size:0.9rem;color:#E9E5DC;">${tr('Nhạc nền đàn tranh truyền thống · Lồng tiếng thuyết minh AI tự động', 'Traditional Dan Tranh soundtrack · Automated AI Voice Narration')}</p>
          </div>
        </div>
        <div style="display:flex;justify-content:space-between;gap:10px;flex-wrap:wrap;">
          <button class="btn-gold" onclick="window.journeyApp.playDestinationAudio('hoian')">
            🔊 ${tr('Phát Thuyết Minh Phim', 'Play Story Voiceover')}
          </button>
          <button class="btn-pill-toggle" onclick="window.journeyApp.closeModal()">${tr('Đóng', 'Close')}</button>
        </div>
      `);
    },
    openAiPlannerModal() {
      this.closePopover();
      openModal(`
        <span class="badge-mine">AI SMART ITINERARY</span>
        <h2 class="serif-title" style="font-size:1.75rem;margin:6px 0 12px;">🗓️ ${tr('Lịch Trình Di Sản Việt Nam 3 Ngày Tối Ưu', 'Optimized 3-Day Vietnam Heritage Itinerary')}</h2>
        <div class="popover-setting-box">
          <div style="font-weight:700;">Ngày 1 · Hà Nội Ngàn Năm Văn Hiến</div>
          <div style="font-size:0.85rem;color:var(--text-secondary);">08:00 Văn Miếu Quốc Tử Giám → 10:30 Hoàng Thành Thăng Long → 16:00 Thưởng trà Phố Cổ.</div>
        </div>
        <div class="popover-setting-box">
          <div style="font-weight:700;">Ngày 2 · Du Thuyền Vịnh Hạ Long</div>
          <div style="font-size:0.85rem;color:var(--text-secondary);">09:00 Làng chài Cửa Vạn → 14:00 Chèo Kayak Hang Luồn → 17:30 Ngắm hoàng hôn Đảo Ti Tốp.</div>
        </div>
        <div class="popover-setting-box">
          <div style="font-weight:700;">Ngày 3 · Phố Cổ Hội An & Đèn Lồng</div>
          <div style="font-size:0.85rem;color:var(--text-secondary);">15:00 Chùa Cầu Nhật Bản → 17:30 Thả hoa đăng sông Hoài → 19:30 Dựng phim kỷ niệm AI 4K.</div>
        </div>
        <div style="display:flex;justify-content:flex-end;gap:10px;">
          <button class="btn-gold" onclick="window.journeyApp.closeModal(); window.journeyApp.switchTab('explore');">
            🗺️ ${tr('Mở Trên Bản Đồ', 'Open on Map')}
          </button>
          <button class="btn-pill-toggle" onclick="window.journeyApp.closeModal()">${tr('Đóng', 'Close')}</button>
        </div>
      `);
    },
    openSavedPlacesFromPopover() {
      this.closePopover();
      this.switchTab('profile');
    },
    openAllScreensModal() {
      this.closePopover();
      openModal(`
        <h2 class="serif-title" style="font-size:1.65rem;margin-bottom:8px;">📱 ${tr('Danh Mục Đầy Đủ 34 Màn Hình Journey AI', 'Complete 34-Screen Journey AI Architecture')}</h2>
        <p style="font-size:0.86rem;color:var(--text-secondary);margin-bottom:14px;">
          ${tr('Tất cả 34 màn hình và 7 luồng trải nghiệm đã được tích hợp trực tiếp vào Web App này.', 'All 34 screens and 7 user flows are integrated into this interactive Web App.')}
        </p>
        <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(210px,1fr));gap:8px;max-height:360px;overflow-y:auto;margin-bottom:16px;">
          ${ALL_34_SCREENS.map((s) => `
            <div class="popover-setting-box" style="margin:0;padding:10px;font-size:0.8rem;font-weight:600;">
              ✓ ${s}
            </div>
          `).join('')}
        </div>
        <div style="display:flex;justify-content:flex-end;">
          <button class="btn-gold" onclick="window.journeyApp.closeModal()">${tr('Hoàn tất', 'Done')}</button>
        </div>
      `);
    },
    openIosGuideModal() {
      this.closePopover();
      const devUrl = 'https://ais-dev-gbtnbd7odkjdopaa2pnjsy-314739119631.asia-northeast1.run.app';
      const shareUrl = 'https://ais-pre-gbtnbd7odkjdopaa2pnjsy-314739119631.asia-northeast1.run.app';
      openModal(`
        <span class="badge-mine">📲 TRUNG TÂM TẢI & CHIA SẺ ỨNG DỤNG CHO MỌI NGƯỜI</span>
        <h2 class="serif-title" style="font-size:1.75rem;margin:8px 0 8px;">
          ${tr('Tải Journey AI Trên iOS, Android & Máy Tính', 'Download Journey AI on iOS, Android & Desktop')}
        </h2>
        <p style="font-size:0.88rem;color:var(--text-secondary);margin-bottom:14px;">
          ${tr(
            'Lưu ý quan trọng: Để người khác mở được link chia sẻ, bạn cần nhấn nút "Share" ở góc trên cùng bên phải của trang Google AI Studio trước để mở khóa quyền truy cập công khai.',
            'Important: To allow others to open the shared link, click the "Share" button in the top-right corner of Google AI Studio first to enable public access.'
          )}
        </p>

        <!-- 1. Public Link & Instant Share -->
        <div class="popover-setting-box" style="border:1.5px solid var(--accent-gold);">
          <div style="font-weight:800;margin-bottom:4px;">🌐 ${tr('1A. Đường dẫn mở ngay cho bạn (Chủ dự án)', '1A. Direct Dev Link (For You)')}</div>
          <div class="mono-text" style="font-size:0.76rem;word-break:break-all;color:var(--accent-gold-dark);margin-bottom:8px;">${devUrl}</div>
          <button class="btn-gold" style="margin-bottom:12px;" onclick="window.journeyApp.copyWebLink('${devUrl}')">
            📋 ${tr('Sao chép Link Dev (Mở ngay)', 'Copy Dev Link')}
          </button>

          <div style="font-weight:800;margin-bottom:4px;">🌍 ${tr('1B. Đường dẫn gửi cho mọi người (Sau khi bấm nút Share trên AI Studio)', '1B. Public Share Link (After clicking Share in AI Studio)')}</div>
          <div class="mono-text" style="font-size:0.76rem;word-break:break-all;color:var(--accent-sage);margin-bottom:8px;">${shareUrl}</div>
          <div style="display:flex;gap:8px;flex-wrap:wrap;">
            <button class="btn-pill-toggle on" onclick="window.journeyApp.copyWebLink('${shareUrl}')">
              📋 ${tr('Sao chép Link Công Khai', 'Copy Public Link')}
            </button>
            <button class="btn-pill-toggle" onclick="window.journeyApp.shareNative('${shareUrl}')">
              📤 ${tr('Chia sẻ nhanh', 'Share Link')}
            </button>
          </div>
        </div>

        <!-- 2. iOS (iPhone / iPad) Install -->
        <div class="popover-setting-box">
          <div style="font-weight:800;margin-bottom:4px;">🍎 ${tr('2. Dành cho người dùng iPhone / iPad (iOS)', '2. For iPhone / iPad (iOS) Users')}</div>
          <div style="font-size:0.84rem;color:var(--text-secondary);line-height:1.5;">
            • Mở đường dẫn trên bằng <strong>Safari</strong> trên iPhone / iPad.<br/>
            • Nhấn nút <strong>Chia sẻ (Share ⬆️)</strong> ở thanh dưới cùng → Chọn <strong>"Thêm vào Màn hình chính" (Add to Home Screen ➕)</strong>.<br/>
            • App <strong>Journey AI</strong> sẽ xuất hiện trên màn hình chính iOS và chạy độc lập toàn màn hình.
          </div>
        </div>

        <!-- 3. Android Direct APK & PWA Install -->
        <div class="popover-setting-box">
          <div style="font-weight:800;margin-bottom:6px;">🤖 ${tr('3. Dành cho người dùng Android (Tải file .APK hoặc Cài trực tiếp)', '3. For Android Users (Direct .APK Download or 1-Tap Install)')}</div>
          <div style="display:flex;gap:10px;flex-wrap:wrap;align-items:center;">
            <a href="/download/journey-ai.apk" download="Journey-AI-v2.5.apk" class="btn-gold" style="text-decoration:none;">
              ⬇️ ${tr('Tải trực tiếp File cài đặt .APK', 'Download .APK Installer')}
            </a>
            <button class="btn-pill-toggle" onclick="window.journeyApp.triggerPwaInstall()">
              ⚡ ${tr('Cài đặt Web App (1 chạm)', '1-Tap Web App Install')}
            </button>
          </div>
        </div>

        <div style="display:flex;justify-content:flex-end;gap:10px;margin-top:12px;">
          <button class="btn-pill-toggle" onclick="window.journeyApp.closeModal()">${tr('Đóng', 'Close')}</button>
        </div>
      `);
    },
    copyWebLink(url) {
      if (navigator.clipboard && navigator.clipboard.writeText) {
        navigator.clipboard.writeText(url);
      }
      showToast(tr('📋 Đã sao chép đường dẫn tải App cho mọi người!', '📋 Copied public app link!'));
    },
    async shareNative(url) {
      if (navigator.share) {
        try {
          await navigator.share({
            title: 'JOURNEY AI — Khám phá Thế giới. Lắng nghe Câu chuyện.',
            text: 'Tải và trải nghiệm ứng dụng du lịch thông minh Journey AI (hỗ trợ cả iOS, Android & Web):',
            url
          });
          return;
        } catch (e) {
          // fallback to copy
        }
      }
      this.copyWebLink(url);
    },
    async triggerPwaInstall() {
      if (window.__deferredPwaPrompt) {
        window.__deferredPwaPrompt.prompt();
        await window.__deferredPwaPrompt.userChoice;
        window.__deferredPwaPrompt = null;
      } else {
        showToast(tr('💡 Trên trình duyệt hiện tại: Chọn menu trình duyệt → "Thêm vào Màn hình chính" hoặc "Cài đặt ứng dụng".', '💡 Use browser menu → "Add to Home Screen" or "Install App".'));
      }
    },
    closeModal
  };

  window.addEventListener('beforeinstallprompt', (e) => {
    e.preventDefault();
    window.__deferredPwaPrompt = e;
  });

  if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
      navigator.serviceWorker.register('sw.js').catch(() => {});
    });
  }

  document.addEventListener('DOMContentLoaded', render);
})();
