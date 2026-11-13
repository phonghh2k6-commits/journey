import 'package:flutter/material.dart';
import '../theme/journey_theme.dart';

enum JourneyButtonVariant { primary, secondary, ghost, tonal }

class JourneyButton extends StatelessWidget {
  final String text;
  final VoidCallback onPressed;
  final JourneyButtonVariant variant;
  final IconData? leadingIcon;

  const JourneyButton({
    super.key,
    required this.text,
    required this.onPressed,
    this.variant = JourneyButtonVariant.primary,
    this.leadingIcon,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    switch (variant) {
      case JourneyButtonVariant.primary:
        return FilledButton.icon(
          onPressed: onPressed,
          style: FilledButton.styleFrom(
            backgroundColor: colors.accent,
            foregroundColor: colors.onAccent,
            padding: const EdgeInsets.symmetric(horizontal: 22, vertical: 14),
            shape: const StadiumBorder(),
          ),
          icon: leadingIcon != null
              ? Icon(leadingIcon, size: 18)
              : const SizedBox.shrink(),
          label: Text(
            text,
            style: const TextStyle(fontWeight: FontWeight.w600, letterSpacing: 0.6),
          ),
        );
      case JourneyButtonVariant.secondary:
        return OutlinedButton.icon(
          onPressed: onPressed,
          style: OutlinedButton.styleFrom(
            foregroundColor: colors.primaryText,
            side: BorderSide(color: colors.accent.withOpacity(0.75)),
            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 13),
            shape: const StadiumBorder(),
          ),
          icon: leadingIcon != null
              ? Icon(leadingIcon, size: 18, color: colors.accent)
              : const SizedBox.shrink(),
          label: Text(text),
        );
      case JourneyButtonVariant.tonal:
        return FilledButton.tonalIcon(
          onPressed: onPressed,
          style: FilledButton.styleFrom(
            backgroundColor: colors.elevatedSurface,
            foregroundColor: colors.primaryText,
            shape: const StadiumBorder(),
          ),
          icon: leadingIcon != null
              ? Icon(leadingIcon, size: 18, color: colors.accent)
              : const SizedBox.shrink(),
          label: Text(text),
        );
      case JourneyButtonVariant.ghost:
        return TextButton.icon(
          onPressed: onPressed,
          icon: leadingIcon != null
              ? Icon(leadingIcon, size: 16, color: colors.accent)
              : const SizedBox.shrink(),
          label: Text(text, style: TextStyle(color: colors.accent)),
        );
    }
  }
}

class JourneyBottomNav extends StatelessWidget {
  final int currentIndex;
  final ValueChanged<int> onTap;
  final bool isVietnamese;

  const JourneyBottomNav({
    super.key,
    required this.currentIndex,
    required this.onTap,
    this.isVietnamese = false,
  });

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).extension<JourneySemanticColors>() ??
        JourneySemanticColors.dark;

    final items = [
      (Icons.home_outlined, Icons.home, isVietnamese ? 'Trang chủ' : 'Home'),
      (Icons.explore_outlined, Icons.explore, isVietnamese ? 'Khám phá' : 'Explore'),
      (Icons.camera_alt, Icons.camera_alt, isVietnamese ? 'Ống kính AI' : 'Discover'),
      (Icons.menu_book_outlined, Icons.menu_book, isVietnamese ? 'Nhật ký' : 'Journal'),
      (Icons.person_outline, Icons.person, isVietnamese ? 'Hồ sơ' : 'Profile'),
    ];

    return Container(
      decoration: BoxDecoration(
        color: colors.secondaryBackground,
        border: Border(top: BorderSide(color: colors.border)),
      ),
      child: SafeArea(
        top: false,
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 8),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: List.generate(items.length, (index) {
              final item = items[index];
              final selected = currentIndex == index;
              if (index == 2) {
                return GestureDetector(
                  onTap: () => onTap(index),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Container(
                        width: 54,
                        height: 54,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          gradient: const LinearGradient(
                            colors: [JourneyPalette.sandGold, Color(0xFFD5BA91)],
                          ),
                          border: Border.all(color: colors.primaryBackground, width: 2),
                        ),
                        child: const Icon(
                          Icons.camera_alt,
                          color: JourneyPalette.midnight,
                          size: 24,
                        ),
                      ),
                      const SizedBox(height: 3),
                      Text(
                        item.$3,
                        style: TextStyle(
                          fontSize: 10,
                          fontWeight: FontWeight.w600,
                          color: colors.accent,
                        ),
                      ),
                    ],
                  ),
                );
              }
              return InkWell(
                borderRadius: BorderRadius.circular(JourneyRadius.md),
                onTap: () => onTap(index),
                child: Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 6),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Icon(
                        selected ? item.$2 : item.$1,
                        color: selected ? colors.accent : colors.secondaryText,
                        size: 22,
                      ),
                      const SizedBox(height: 4),
                      Text(
                        item.$3,
                        style: TextStyle(
                          fontSize: 10,
                          fontWeight: selected ? FontWeight.w600 : FontWeight.normal,
                          color: selected ? colors.primaryText : colors.secondaryText,
                        ),
                      ),
                    ],
                  ),
                ),
              );
            }),
          ),
        ),
      ),
    );
  }
}
