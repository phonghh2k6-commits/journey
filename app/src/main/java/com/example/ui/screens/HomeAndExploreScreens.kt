package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.JourneyScreenId
import com.example.ui.components.JourneyBadgePill
import com.example.ui.components.JourneyButton
import com.example.ui.components.JourneyButtonVariant
import com.example.ui.components.JourneyCompactLandmarkRowCard
import com.example.ui.components.JourneyEditorialCard
import com.example.ui.components.JourneyFilterChip
import com.example.ui.components.JourneyGhostButton
import com.example.ui.components.JourneyPrimaryButton
import com.example.ui.components.JourneySearchInputBar
import com.example.ui.components.JourneySecondaryButton
import com.example.ui.components.JourneySectionHeader
import com.example.ui.components.JourneyStatPillCard
import com.example.ui.components.JourneyTopBar
import com.example.ui.components.tr
import com.example.ui.theme.JourneyElevation
import com.example.ui.theme.JourneyPalette
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme
import com.example.viewmodel.JourneyUiState

// ============================================================================
// PHASE 2 — SCREEN B1: HOME DASHBOARD
// Editorial Hero + Active Expedition Strip + Category Filter + Spatial Landmarks + Collections
// ============================================================================
@Composable
fun HomeDashboardScreen(
    uiState: JourneyUiState,
    onNavigate: (JourneyScreenId) -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onSelectCollection: (String) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var selectedRegionFilter by remember { mutableStateOf("ALL") }

    val featuredPlace = uiState.places.firstOrNull { it.id == "place_halong" } ?: uiState.places.first()
    val activeTrip = uiState.selectedTrip

    val filteredPlaces = uiState.places.filter { place ->
        when (selectedRegionFilter) {
            "NORTH" -> place.id == "place_halong" || place.id == "place_hanoi_temple" || place.id == "place_sapa"
            "CENTRAL" -> place.id == "place_hoian"
            "SAVED" -> place.isSaved
            else -> true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_b1_home_dashboard")
    ) {
        // 1. Editorial Masthead Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md, vertical = JourneySpacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "JOURNEY AI",
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "• HỘI AN, QUẢNG NAM • 25°C",
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.sage
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = tr(lang, "Explore the World. Discover the Stories.", "Khám phá Thế giới. Lắng nghe Câu chuyện."),
                    style = MaterialTheme.typography.headlineMedium,
                    color = colors.primaryText
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = { onNavigate(JourneyScreenId.B2_GLOBAL_SEARCH) },
                    modifier = Modifier
                        .size(44.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                        .testTag("home_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Global Search",
                        tint = colors.primaryText
                    )
                }
                IconButton(
                    onClick = { onNavigate(JourneyScreenId.B3_NOTIFICATIONS) },
                    modifier = Modifier
                        .size(44.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                        .testTag("home_notifications_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = colors.accent
                    )
                }
            }
        }

        // 2. Full-Bleed Editorial Hero Showcase
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md)
                .height(390.dp)
                .clip(RoundedCornerShape(JourneyRadius.xl))
                .border(1.dp, colors.border, RoundedCornerShape(JourneyRadius.xl))
                .clickable { onSelectPlace(featuredPlace.id, true) }
                .testTag("home_hero_card")
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_hero_halong_bay),
                contentDescription = featuredPlace.nameEn,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0x190B1017),
                                Color(0x660B1017),
                                Color(0xF20B1017)
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(JourneySpacing.lg),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                JourneyBadgePill(
                    text = "FEATURED STORY • ${featuredPlace.coordinates}",
                    containerColor = Color(0xBB111923),
                    contentColor = JourneyPalette.SandGold
                )
                JourneyBadgePill(
                    text = "${(featuredPlace.confidenceScore * 100).toInt()}% AI MATCH",
                    containerColor = Color(0xBB111923),
                    contentColor = JourneyPalette.Ivory
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(JourneySpacing.xl)
            ) {
                Text(
                    text = tr(lang, featuredPlace.categoryEn, featuredPlace.categoryVi).uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = JourneyPalette.SandGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = tr(lang, featuredPlace.nameEn, featuredPlace.nameVi),
                    style = MaterialTheme.typography.displayMedium,
                    color = JourneyPalette.Ivory
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tr(lang, featuredPlace.summaryEn, featuredPlace.summaryVi),
                    style = MaterialTheme.typography.bodyMedium,
                    color = JourneyPalette.Ivory.copy(alpha = 0.85f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(JourneySpacing.md))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    JourneyPrimaryButton(
                        text = tr(lang, "Scan Monument (AI Lens)", "Quét Di sản (Ống kính AI)"),
                        leadingIcon = Icons.Default.CameraAlt,
                        onClick = { onNavigate(JourneyScreenId.D1_AI_CAMERA) },
                        testTag = "hero_scan_camera_btn"
                    )
                    JourneySecondaryButton(
                        text = tr(lang, "Voice Guide", "Thuyết minh"),
                        leadingIcon = Icons.Default.GraphicEq,
                        onClick = { onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT) },
                        testTag = "hero_voice_guide_btn"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(JourneySpacing.lg))

        // 3. Active Expedition & Live Audio Guide Strip (Connects Home -> Journal & Voice Guide)
        Surface(
            shape = RoundedCornerShape(JourneyRadius.lg),
            color = colors.surface,
            border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.45f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md)
                .clickable { onNavigate(JourneyScreenId.F3_TRIP_DETAIL) }
        ) {
            Row(
                modifier = Modifier.padding(JourneySpacing.md),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = tr(lang, "ACTIVE EXPEDITION JOURNAL • DAY 8 OF 10", "NHẬT KÝ ĐANG DIỄN RA • NGÀY 8 / 10"),
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tr(lang, activeTrip.titleEn, activeTrip.titleVi),
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.primaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${tr(lang, activeTrip.locationEn, activeTrip.locationVi)} • ${activeTrip.moments.size} moments logged",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.secondaryText
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                JourneyGhostButton(
                    text = tr(lang, "Open Folio", "Mở Nhật ký"),
                    onClick = { onNavigate(JourneyScreenId.F3_TRIP_DETAIL) }
                )
            }
        }

        if (!uiState.isMinimalViewEnabled) {
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // 4. Quick Atelier Capabilities Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = JourneySpacing.md),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf(
                    Triple(tr(lang, "AI Visual Lens", "Ống kính AI"), Icons.Default.CameraAlt, JourneyScreenId.D1_AI_CAMERA),
                    Triple(tr(lang, "AI Cultural Chat", "Trò chuyện AI"), Icons.Default.AutoAwesome, JourneyScreenId.E1_AI_CHAT),
                    Triple(tr(lang, "Voice Travel Guide", "Thuyết minh Giọng nói"), Icons.Default.Mic, JourneyScreenId.E2_VOICE_ASSISTANT),
                    Triple(tr(lang, "Cinematic Reel", "Dựng Phim 4K"), Icons.Default.MovieCreation, JourneyScreenId.H2_VIDEO_CREATOR),
                    Triple(tr(lang, "Global Search", "Tìm kiếm"), Icons.Default.Search, JourneyScreenId.B2_GLOBAL_SEARCH)
                ).forEach { (label, icon, targetScreen) ->
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.md),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border),
                        modifier = Modifier.clickable { onNavigate(targetScreen) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                tint = colors.accent,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = label,
                                style = MaterialTheme.typography.titleSmall,
                                color = colors.primaryText
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xxl))

            // 5. Spatial Heritage Filter & Landmarks List
            Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
                JourneySectionHeader(
                    eyebrow = tr(lang, "SPATIAL HERITAGE", "DI SẢN QUANH BẠN"),
                    title = tr(lang, "Stories Around Your Horizon", "Câu Chuyện Quanh Chân Trời"),
                    actionLabel = tr(lang, "Explore Map", "Mở Bản đồ"),
                    onActionClick = { onNavigate(JourneyScreenId.C1_EXPLORE_MAP) }
                )
                Spacer(modifier = Modifier.height(JourneySpacing.sm))

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        "ALL" to tr(lang, "All Vietnam (4)", "Toàn Việt Nam (4)"),
                        "NORTH" to tr(lang, "Northern Tonkin (3)", "Miền Bắc (3)"),
                        "CENTRAL" to tr(lang, "Central Heritage (1)", "Miền Trung (1)"),
                        "SAVED" to tr(lang, "Saved Atlas", "Đã lưu")
                    ).forEach { (key, label) ->
                        JourneyFilterChip(
                            label = label,
                            selected = selectedRegionFilter == key,
                            onClick = { selectedRegionFilter = key }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(JourneySpacing.md))

                filteredPlaces.forEach { place ->
                    JourneyEditorialCard(
                        imageRes = place.imageRes,
                        tagText = tr(lang, place.categoryEn, place.categoryVi),
                        title = tr(lang, place.nameEn, place.nameVi),
                        subtitle = tr(lang, place.summaryEn, place.summaryVi),
                        metaText = "${place.coordinates} • ${place.distanceKm} • AUDIO ${place.audioDuration}",
                        isSaved = place.isSaved,
                        onCardClick = { onSelectPlace(place.id, true) },
                        onSaveClick = { onToggleSavePlace(place.id) },
                        modifier = Modifier.padding(bottom = JourneySpacing.md),
                        heightDp = 245,
                        testTag = "home_place_card_${place.id}"
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // 6. Discovery Collections Carousel
            Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
                JourneySectionHeader(
                    eyebrow = tr(lang, "CURATED ANTHOLOGIES", "BỘ SƯU TẬP TUYỂN CHỌN"),
                    title = tr(lang, "Discovery Collections", "Bộ Sưu Tập Khám Phá"),
                    actionLabel = tr(lang, "See All", "Xem tất cả"),
                    onActionClick = { onNavigate(JourneyScreenId.G1_DISCOVERY_COLLECTION) }
                )
                Spacer(modifier = Modifier.height(JourneySpacing.md))

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    uiState.collections.forEach { coll ->
                        JourneyEditorialCard(
                            imageRes = coll.coverRes,
                            tagText = tr(lang, coll.badgeTagEn, coll.badgeTagVi),
                            title = tr(lang, coll.titleEn, coll.titleVi),
                            subtitle = tr(lang, coll.subtitleEn, coll.subtitleVi),
                            metaText = "${coll.placeIds.size} CURATED MONUMENTS",
                            onCardClick = { onSelectCollection(coll.id) },
                            modifier = Modifier.width(290.dp),
                            heightDp = 210
                        )
                    }
                }
            }
        } else {
            // Minimalist View Mode: Large focused buttons for AI Scan, Voice Guide & Today's Journal
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(JourneySpacing.md),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                JourneyPrimaryButton(
                    text = tr(lang, "Open AI Camera Lens (Large View)", "Mở Ống Kính Quét Di Sản AI (Cỡ lớn)"),
                    leadingIcon = Icons.Default.CameraAlt,
                    onClick = { onNavigate(JourneyScreenId.D1_AI_CAMERA) },
                    modifier = Modifier.fillMaxWidth()
                )
                JourneySecondaryButton(
                    text = tr(lang, "Listen to Today's Voice Travel Guide", "Nghe Thuyết Minh Giọng Nói Hôm Nay"),
                    leadingIcon = Icons.Default.GraphicEq,
                    onClick = { onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT) },
                    modifier = Modifier.fillMaxWidth()
                )
                JourneySecondaryButton(
                    text = tr(lang, "Log Daily Moment in Travel Journal", "Ghi Khoảnh Khắc Vào Nhật Ký Hôm Nay"),
                    onClick = { onNavigate(JourneyScreenId.F5_CREATE_DAILY_MOMENT) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN B2: GLOBAL SEARCH
// ============================================================================
@Composable
fun GlobalSearchScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onQueryChange: (String) -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val query = uiState.searchQuery

    val filteredPlaces = uiState.places.filter {
        query.isBlank() ||
            it.nameEn.contains(query, ignoreCase = true) ||
            it.nameVi.contains(query, ignoreCase = true) ||
            it.regionEn.contains(query, ignoreCase = true) ||
            it.eraEn.contains(query, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.B2_GLOBAL_SEARCH,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySearchInputBar(
                value = query,
                onValueChange = onQueryChange,
                placeholder = tr(lang, "Search 'Hội An', 'Văn Miếu', '1593', 'UNESCO'...", "Tìm 'Hội An', 'Văn Miếu', 'Hạ Long', '1593'..."),
                trailingAction = if (query.isNotEmpty()) {
                    {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear search",
                                tint = colors.secondaryText
                            )
                        }
                    }
                } else null,
                testTag = "global_search_input"
            )

            Spacer(modifier = Modifier.height(JourneySpacing.md))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Hội An", "Hạ Long", "Văn Miếu", "Mù Cang Chải", "UNESCO", "1593").forEach { tag ->
                    JourneyFilterChip(
                        label = tag,
                        selected = query.equals(tag, ignoreCase = true),
                        onClick = {
                            onQueryChange(if (query.equals(tag, ignoreCase = true)) "" else tag)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xl))

            Text(
                text = tr(lang, "MATCHING HERITAGE DESTINATIONS (${filteredPlaces.size})", "ĐỊA DANH PHÙ HỢP (${filteredPlaces.size})"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(JourneySpacing.sm))

            filteredPlaces.forEach { place ->
                JourneyCompactLandmarkRowCard(
                    imageRes = place.imageRes,
                    name = tr(lang, place.nameEn, place.nameVi),
                    region = tr(lang, place.regionEn, place.regionVi),
                    era = tr(lang, place.eraEn, place.eraVi),
                    coordinates = place.coordinates,
                    distance = place.distanceKm,
                    audioDuration = place.audioDuration,
                    isSaved = place.isSaved,
                    onClick = { onSelectPlace(place.id, true) },
                    onSaveClick = {},
                    onAudioClick = {
                        onSelectPlace(place.id, false)
                        onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT)
                    },
                    modifier = Modifier.padding(bottom = JourneySpacing.sm)
                )
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN B3: NOTIFICATIONS
// ============================================================================
@Composable
fun NotificationsScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    val notifications = listOf(
        Triple(
            tr(lang, "AI Story & 4K Reel Ready", "Câu chuyện AI & Phim 4K Đã Sẵn Sàng"),
            tr(lang, "Your 10-day 'Mists of Tonkin & Lanterns of Faifo' anthology has been composed with ambient đàn tranh score.", "Tuyển tập 'Sương mờ Bắc Bộ & Đêm đèn lồng Hội An' đã hoàn tất dựng phim cùng nhạc nền đàn tranh."),
            JourneyScreenId.H1_AI_STORY_PREVIEW
        ),
        Triple(
            tr(lang, "Proximity Heritage Alert • 180m", "Phát Hiện Di Sản Gần Bạn • 180m"),
            tr(lang, "You are approaching Chùa Cầu (1593). Launch the AI Voice Guide for acoustic history.", "Bạn đang ở gần Chùa Cầu Hội An (1593). Mở Thuyết minh Giọng nói để nghe lịch sử kiến trúc."),
            JourneyScreenId.E2_VOICE_ASSISTANT
        ),
        Triple(
            tr(lang, "Offline Heritage Pack Updated", "Đã Cập Nhật Gói Di Sản Ngoại Tuyến"),
            tr(lang, "High-resolution architectural diagrams for Văn Miếu & Hoàng Liên Terraces cached for offline use.", "Bản vẽ kiến trúc Văn Miếu & Ruộng bậc thang Hoàng Liên Sơn đã được lưu ngoại tuyến."),
            JourneyScreenId.C4_SAVED_PLACES
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.B3_NOTIFICATIONS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            notifications.forEach { (title, desc, target) ->
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onNavigate(target) }
                ) {
                    Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                        JourneyBadgePill(text = "DISCOVERY DISPATCH")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = title, style = MaterialTheme.typography.titleLarge, color = colors.primaryText)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = desc, style = MaterialTheme.typography.bodyMedium, color = colors.secondaryText)
                    }
                }
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN C1: INTERACTIVE EXPLORE MAP
// Custom Topographic Contour Map + Search/Filter Overlay + Spatial Pins + Place Sheet
// ============================================================================
@Composable
fun ExploreMapScreen(
    uiState: JourneyUiState,
    onSelectPlace: (String, Boolean) -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val activePlace = uiState.selectedPlace
    var mapLayerStyle by remember { mutableStateOf("TOPO_DARK") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("screen_c1_explore_map")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.C1_EXPLORE_MAP,
            language = lang,
            trailingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    JourneySecondaryButton(
                        text = tr(lang, "Feed", "Bảng tin"),
                        onClick = { onNavigate(JourneyScreenId.C2_DISCOVERY_FEED) }
                    )
                    JourneyButton(
                        text = tr(lang, "Saved", "Đã lưu"),
                        variant = JourneyButtonVariant.SURFACE_TONAL,
                        onClick = { onNavigate(JourneyScreenId.C4_SAVED_PLACES) }
                    )
                }
            }
        )

        // Interactive Topographic Map Canvas + Spatial Pins
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md)
                .clip(RoundedCornerShape(JourneyRadius.xl))
                .background(if (mapLayerStyle == "TOPO_DARK") JourneyPalette.DeepObsidian else Color(0xFF192730))
                .border(1.dp, colors.border, RoundedCornerShape(JourneyRadius.xl))
        ) {
            // Custom Topographic Contour Lines, Route Path & Radar Pulse
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val gridColor = Color(0x26879A91)
                val goldLine = Color(0x55B99B72)

                for (i in 1..6) {
                    val x = w * (i / 7f)
                    drawLine(gridColor, Offset(x, 0f), Offset(x, h), strokeWidth = 1f)
                }
                for (j in 1..6) {
                    val y = h * (j / 7f)
                    drawLine(gridColor, Offset(0f, y), Offset(w, y), strokeWidth = 1f)
                }

                // Stylized topographic elevation rings centered on active place
                val cx = w * activePlace.mapXPercent
                val cy = h * activePlace.mapYPercent
                for (r in 1..4) {
                    drawCircle(
                        color = goldLine,
                        radius = (r * 65f),
                        center = Offset(cx, cy),
                        style = Stroke(
                            width = 1.5f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
                        )
                    )
                }

                // Expedition route line connecting Vietnam heritage points
                val routePoints = uiState.places.map { Offset(w * it.mapXPercent, h * it.mapYPercent) }
                for (k in 0 until routePoints.size - 1) {
                    drawLine(
                        color = Color(0x88B99B72),
                        start = routePoints[k],
                        end = routePoints[k + 1],
                        strokeWidth = 3f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f), 0f)
                    )
                }
            }

            // Top Map Layer & Telemetry Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(JourneySpacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                JourneyBadgePill(
                    text = tr(lang, "VIETNAM HERITAGE ATLAS • POSTGIS 4326", "BẢN ĐỒ DI SẢN VIỆT NAM • POSTGIS 4326"),
                    containerColor = Color(0xCC111923),
                    contentColor = JourneyPalette.SandGold
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = Color(0xCC1C2934),
                        border = BorderStroke(1.dp, JourneyPalette.SandGold.copy(alpha = 0.5f)),
                        modifier = Modifier.clickable {
                            mapLayerStyle = if (mapLayerStyle == "TOPO_DARK") "SATELLITE_CONTOUR" else "TOPO_DARK"
                        }
                    ) {
                        Text(
                            text = if (mapLayerStyle == "TOPO_DARK") "Topo Dark" else "Bathymetry",
                            style = MaterialTheme.typography.labelSmall,
                            color = JourneyPalette.Ivory,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Interactive Landmark Pins list on Map
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 56.dp, start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                uiState.places.forEach { place ->
                    val isSelected = place.id == activePlace.id
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = if (isSelected) JourneyPalette.SandGold else Color(0xCC1C2934),
                        border = BorderStroke(
                            1.2.dp,
                            if (isSelected) JourneyPalette.Ivory else JourneyPalette.SandGold.copy(alpha = 0.6f)
                        ),
                        shadowElevation = JourneyElevation.card,
                        modifier = Modifier
                            .clickable { onSelectPlace(place.id, false) }
                            .testTag("map_pin_${place.id}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.MyLocation else Icons.Default.LocationOn,
                                contentDescription = place.nameEn,
                                tint = if (isSelected) JourneyPalette.Midnight else JourneyPalette.SandGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = tr(lang, place.nameEn, place.nameVi),
                                style = MaterialTheme.typography.titleSmall,
                                color = if (isSelected) JourneyPalette.Midnight else JourneyPalette.Ivory
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = place.distanceKm,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) JourneyPalette.Midnight.copy(alpha = 0.75f) else JourneyPalette.SandGold
                            )
                        }
                    }
                }
            }

            // Bottom Selected Place Preview Sheet inside Map (FLOW 4: Select Place -> Place Detail -> Ask AI -> Save Place)
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface.copy(alpha = 0.96f),
                border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.55f)),
                shadowElevation = JourneyElevation.floating,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(JourneySpacing.md)
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.md)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = activePlace.imageRes),
                            contentDescription = activePlace.nameEn,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(78.dp)
                                .clip(RoundedCornerShape(JourneyRadius.md))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${activePlace.coordinates} • ${activePlace.distanceKm}",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.accent
                            )
                            Text(
                                text = tr(lang, activePlace.nameEn, activePlace.nameVi),
                                style = MaterialTheme.typography.titleLarge,
                                color = colors.primaryText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = tr(lang, activePlace.regionEn, activePlace.regionVi),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }
                        IconButton(onClick = { onToggleSavePlace(activePlace.id) }) {
                            Icon(
                                imageVector = if (activePlace.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Save Place",
                                tint = colors.accent
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        JourneyPrimaryButton(
                            text = tr(lang, "Explore Place Details", "Xem Chi tiết Điểm đến"),
                            onClick = { onSelectPlace(activePlace.id, true) },
                            modifier = Modifier.weight(1f),
                            testTag = "map_open_place_detail_btn"
                        )
                        JourneySecondaryButton(
                            text = tr(lang, "Ask AI", "Hỏi AI"),
                            leadingIcon = Icons.Default.AutoAwesome,
                            onClick = { onNavigate(JourneyScreenId.E1_AI_CHAT) },
                            testTag = "map_ask_ai_btn"
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

// ============================================================================
// PHASE 2 — SCREEN C2: DISCOVERY FEED
// ============================================================================
@Composable
fun DiscoveryFeedScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val lang = uiState.language
    var selectedEraFilter by remember { mutableStateOf("ALL") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.C2_DISCOVERY_FEED,
            language = lang,
            onBackClick = onBack
        )
        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "EDITORIAL DISCOVERY STREAM", "BẢNG TIN KHÁM PHÁ CHUYÊN SÂU"),
                title = tr(lang, "Architectural & Natural Wonders", "Kỳ Quan Kiến Trúc & Thiên Nhiên")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.sm))

            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "ALL" to tr(lang, "All Curations", "Tất cả"),
                    "UNESCO" to tr(lang, "UNESCO Heritage", "Di sản UNESCO"),
                    "IMPERIAL" to tr(lang, "Dynastic Architecture", "Kiến trúc Cổ")
                ).forEach { (id, label) ->
                    JourneyFilterChip(
                        label = label,
                        selected = selectedEraFilter == id,
                        onClick = { selectedEraFilter = id }
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))
            uiState.places.forEach { place ->
                JourneyEditorialCard(
                    imageRes = place.imageRes,
                    tagText = tr(lang, place.eraEn, place.eraVi),
                    title = tr(lang, place.nameEn, place.nameVi),
                    subtitle = tr(lang, place.culturalStoryEn, place.culturalStoryVi),
                    metaText = "${place.coordinates} • AUDIO GUIDE ${place.audioDuration}",
                    isSaved = place.isSaved,
                    onCardClick = { onSelectPlace(place.id, true) },
                    onSaveClick = { onToggleSavePlace(place.id) },
                    modifier = Modifier.padding(bottom = JourneySpacing.md),
                    heightDp = 265
                )
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN C3: PLACE DETAILS (With Tabbed Dossier + Mini Audio Player + FLOW 4 Actions)
// ============================================================================
@Composable
fun PlaceDetailsScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace
    var activeTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_c3_place_details")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.C3_PLACE_DETAILS,
            language = lang,
            onBackClick = onBack,
            trailingContent = {
                IconButton(
                    onClick = { onToggleSavePlace(place.id) },
                    modifier = Modifier
                        .size(42.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                        .testTag("place_detail_save_btn")
                ) {
                    Icon(
                        imageVector = if (place.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save Place",
                        tint = colors.accent
                    )
                }
            }
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneyEditorialCard(
                imageRes = place.imageRes,
                tagText = tr(lang, place.categoryEn, place.categoryVi),
                title = tr(lang, place.nameEn, place.nameVi),
                subtitle = tr(lang, place.regionEn, place.regionVi),
                metaText = "${place.coordinates} • ${tr(lang, place.eraEn, place.eraVi)}",
                isSaved = place.isSaved,
                onCardClick = {},
                onSaveClick = { onToggleSavePlace(place.id) },
                heightDp = 300
            )

            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // Embedded Binaural Voice Guide Mini-Player Bar
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.accentContainer,
                border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.5f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT) }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(38.dp)
                                .background(colors.accent, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play Audio Guide",
                                tint = colors.onAccent
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = tr(lang, "BINAURAL VOICE GUIDE • ${place.audioDuration}", "THUYẾT MINH GIỌNG NÓI AI • ${place.audioDuration}"),
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.accent
                            )
                            Text(
                                text = tr(lang, "Listen to scholar narration while walking", "Nghe thuyết minh lịch sử & kiến trúc khi dạo bước"),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.primaryText
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = colors.accent
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // Primary Action Row: Ask AI Assistant, Deep Monograph
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyPrimaryButton(
                    text = tr(lang, "Ask AI Scholar", "Hỏi Chuyên gia AI"),
                    leadingIcon = Icons.Default.AutoAwesome,
                    onClick = { onNavigate(JourneyScreenId.E1_AI_CHAT) },
                    modifier = Modifier.weight(1f),
                    testTag = "place_detail_ask_ai_btn"
                )
                JourneySecondaryButton(
                    text = tr(lang, "Deep History", "Hồ sơ Chuyên khảo"),
                    onClick = { onNavigate(JourneyScreenId.D5_DISCOVERY_DETAILS) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // Dossier Section Tabs (Historical Overview / Architecture / Folklore)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    0 to tr(lang, "Overview", "Tổng quan"),
                    1 to tr(lang, "Architecture", "Kiến trúc"),
                    2 to tr(lang, "Oral Lore", "Truyền thuyết")
                ).forEach { (idx, tabTitle) ->
                    JourneyFilterChip(
                        label = tabTitle,
                        selected = activeTabIndex == idx,
                        onClick = { activeTabIndex = idx }
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // Historical Overview Card
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    Text(
                        text = tr(lang, "HISTORICAL SIGNIFICANCE", "GIÁ TRỊ LỊCH SỬ & VĂN HÓA"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tr(lang, place.summaryEn, place.summaryVi),
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.primaryText
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = colors.subtleBorder)
                    Text(
                        text = tr(lang, "ARCHITECTURAL & GEOLOGICAL NOTES", "ĐẶC ĐIỂM KIẾN TRÚC & ĐỊA CHẤT"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.architecturalNotesEn, place.architecturalNotesVi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = colors.subtleBorder)
                    Text(
                        text = tr(lang, "ORAL TRADITION & STORY", "CÂU CHUYỆN DI SẢN TRUYỀN ĐỜI"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.culturalStoryEn, place.culturalStoryVi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyButton(
                    text = tr(lang, "Saved Places", "Địa điểm Đã lưu"),
                    variant = JourneyButtonVariant.SURFACE_TONAL,
                    onClick = { onNavigate(JourneyScreenId.C4_SAVED_PLACES) },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "Save to Collection", "Lưu vào Bộ sưu tập"),
                    variant = JourneyButtonVariant.SURFACE_TONAL,
                    onClick = { onNavigate(JourneyScreenId.G1_DISCOVERY_COLLECTION) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// ============================================================================
// PHASE 2 — SCREEN C4: SAVED PLACES & OFFLINE PACKS
// ============================================================================
@Composable
fun SavedPlacesScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val savedPlaces = uiState.places.filter { it.isSaved }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.C4_SAVED_PLACES,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "PERSONAL ATLAS • ${savedPlaces.size} BOOKMARKS", "BẢN ĐỒ CÁ NHÂN • ${savedPlaces.size} ĐỊA ĐIỂM ĐÃ LƯU"),
                title = tr(lang, "Saved Places & Offline Guides", "Địa Điểm Đã Lưu & Dữ Liệu Ngoại Tuyến")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            // Offline Storage Telemetry Card
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = JourneySpacing.md)
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = tr(lang, "OFFLINE HERITAGE STORAGE", "DUNG LƯỢNG GÓI DI SẢN NGOẠI TUYẾN"),
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.accent
                        )
                        Text(
                            text = "148 MB / 500 MB",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.secondaryText
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { 0.3f },
                        color = colors.accent,
                        trackColor = colors.subtleBorder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }

            savedPlaces.forEach { place ->
                JourneyEditorialCard(
                    imageRes = place.imageRes,
                    tagText = tr(lang, "OFFLINE PACK READY • ${place.audioDuration} AUDIO", "ĐÃ TẢI GÓI NGOẠI TUYẾN • ${place.audioDuration}"),
                    title = tr(lang, place.nameEn, place.nameVi),
                    subtitle = tr(lang, place.summaryEn, place.summaryVi),
                    metaText = place.coordinates,
                    isSaved = true,
                    onCardClick = { onSelectPlace(place.id, true) },
                    onSaveClick = { onToggleSavePlace(place.id) },
                    modifier = Modifier.padding(bottom = JourneySpacing.md),
                    heightDp = 225
                )
            }
        }
    }
}
