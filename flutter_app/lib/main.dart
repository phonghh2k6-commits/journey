import 'package:flutter/material.dart';
import 'core/theme/journey_theme.dart';
import 'core/widgets/journey_components.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const JourneyAiApp());
}

class JourneyAiApp extends StatefulWidget {
  const JourneyAiApp({super.key});

  @override
  State<JourneyAiApp> createState() => _JourneyAiAppState();
}

class _JourneyAiAppState extends State<JourneyAiApp> {
  bool _isDarkMode = true;
  bool _isVietnamese = false;
  int _selectedTab = 0;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Journey AI',
      debugShowCheckedModeBanner: false,
      themeMode: _isDarkMode ? ThemeMode.dark : ThemeMode.light,
      theme: JourneyTheme.buildLightTheme(),
      darkTheme: JourneyTheme.buildDarkTheme(),
      home: Scaffold(
        body: IndexedStack(
          index: _selectedTab,
          children: [
            _HomeTab(
              isVietnamese: _isVietnamese,
              onOpenCamera: () => setState(() => _selectedTab = 2),
              onOpenMap: () => setState(() => _selectedTab = 1),
            ),
            _ExploreMapTab(isVietnamese: _isVietnamese),
            _AiCameraDiscoverTab(
              isVietnamese: _isVietnamese,
              onClose: () => setState(() => _selectedTab = 0),
            ),
            _JournalTab(isVietnamese: _isVietnamese),
            _ProfileTab(
              isDarkMode: _isDarkMode,
              isVietnamese: _isVietnamese,
              onToggleTheme: () => setState(() => _isDarkMode = !_isDarkMode),
              onToggleLanguage: () => setState(() => _isVietnamese = !_isVietnamese),
            ),
          ],
        ),
        bottomNavigationBar: _selectedTab == 2
            ? null
            : JourneyBottomNav(
                currentIndex: _selectedTab,
                isVietnamese: _isVietnamese,
                onTap: (idx) => setState(() => _selectedTab = idx),
              ),
      ),
    );
  }
}

class _HomeTab extends StatelessWidget {
  final bool isVietnamese;
  final VoidCallback onOpenCamera;
  final VoidCallback onOpenMap;

  const _HomeTab({
    required this.isVietnamese,
    required this.onOpenCamera,
    required this.onOpenMap,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    return SafeArea(
      child: ListView(
        padding: const EdgeInsets.all(JourneySpacing.md),
        children: [
          Text(
            'JOURNEY AI • HỘI AN, QUẢNG NAM',
            style: TextStyle(
              fontSize: 11,
              letterSpacing: 1.1,
              color: colors.accent,
              fontWeight: FontWeight.w600,
            ),
          ),
          const SizedBox(height: 4),
          Text(
            isVietnamese
                ? 'Khám phá Thế giới. Lắng nghe Câu chuyện.'
                : 'Explore the World. Discover the Stories.',
            style: TextStyle(
              fontSize: 26,
              fontWeight: FontWeight.w600,
              color: colors.primaryText,
            ),
          ),
          const SizedBox(height: 16),
          Container(
            height: 320,
            padding: const EdgeInsets.all(24),
            decoration: BoxDecoration(
              color: colors.surface,
              borderRadius: BorderRadius.circular(JourneyRadius.xl),
              border: Border.all(color: colors.border),
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Text(
                  '20.9101° N, 107.1839° E • 99% AI MATCH',
                  style: TextStyle(fontSize: 11, color: colors.accent),
                ),
                const SizedBox(height: 6),
                Text(
                  isVietnamese
                      ? 'Vịnh Hạ Long — Đảo Bồ Hòn'
                      : 'Vịnh Hạ Long — Sung Sot Grotto & Karsts',
                  style: const TextStyle(
                    fontSize: 28,
                    fontWeight: FontWeight.w600,
                    color: JourneyPalette.ivory,
                  ),
                ),
                const SizedBox(height: 14),
                Wrap(
                  spacing: 10,
                  runSpacing: 10,
                  children: [
                    JourneyButton(
                      text: isVietnamese ? 'Quét Di sản (AI Lens)' : 'Scan Monument (AI Lens)',
                      leadingIcon: Icons.camera_alt,
                      onPressed: onOpenCamera,
                    ),
                    JourneyButton(
                      text: isVietnamese ? 'Mở Bản đồ' : 'Explore Map',
                      variant: JourneyButtonVariant.secondary,
                      onPressed: onOpenMap,
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class _ExploreMapTab extends StatelessWidget {
  final bool isVietnamese;
  const _ExploreMapTab({required this.isVietnamese});

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    return SafeArea(
      child: Center(
        child: Text(
          isVietnamese ? 'Bản đồ Khám phá Di sản' : 'Interactive Heritage Explore Map',
          style: TextStyle(fontSize: 20, color: colors.primaryText),
        ),
      ),
    );
  }
}

class _AiCameraDiscoverTab extends StatelessWidget {
  final bool isVietnamese;
  final VoidCallback onClose;

  const _AiCameraDiscoverTab({
    required this.isVietnamese,
    required this.onClose,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: JourneyPalette.deepObsidian,
      body: SafeArea(
        child: Stack(
          children: [
            Align(
              alignment: Alignment.topLeft,
              child: IconButton(
                onPressed: onClose,
                icon: const Icon(Icons.close, color: JourneyPalette.ivory),
              ),
            ),
            Center(
              child: Text(
                isVietnamese ? 'Ống kính Nhận diện AI' : 'AI Visual Recognition Lens',
                style: const TextStyle(color: JourneyPalette.sandGold, fontSize: 18),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _JournalTab extends StatelessWidget {
  final bool isVietnamese;
  const _JournalTab({required this.isVietnamese});

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    return SafeArea(
      child: Center(
        child: Text(
          isVietnamese ? 'Nhật ký Hành trình & Ký ức' : 'Personal Travel Journal & Memories',
          style: TextStyle(fontSize: 20, color: colors.primaryText),
        ),
      ),
    );
  }
}

class _ProfileTab extends StatelessWidget {
  final bool isDarkMode;
  final bool isVietnamese;
  final VoidCallback onToggleTheme;
  final VoidCallback onToggleLanguage;

  const _ProfileTab({
    required this.isDarkMode,
    required this.isVietnamese,
    required this.onToggleTheme,
    required this.onToggleLanguage,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;
    return SafeArea(
      child: ListView(
        padding: const EdgeInsets.all(JourneySpacing.md),
        children: [
          SwitchListTile(
            title: Text(
              isVietnamese ? 'Chế độ Tối (Midnight)' : 'Dark Mode (Midnight)',
              style: TextStyle(color: colors.primaryText),
            ),
            value: isDarkMode,
            onChanged: (_) => onToggleTheme(),
          ),
          ListTile(
            title: Text(
              isVietnamese ? 'Ngôn ngữ: Tiếng Việt' : 'Language: English',
              style: TextStyle(color: colors.primaryText),
            ),
            trailing: JourneyButton(
              text: isVietnamese ? 'Switch EN' : 'Chuyển VI',
              variant: JourneyButtonVariant.secondary,
              onPressed: onToggleLanguage,
            ),
          ),
        ],
      ),
    );
  }
}
