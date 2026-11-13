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
import com.example.ui.components.JourneyTextField
import com.example.ui.components.JourneyTopBar
import com.example.ui.components.tr
import com.example.ui.theme.JourneyElevation
import com.example.ui.theme.JourneyPalette
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme
import com.example.viewmodel.JourneyUiState

// ============================================================================
// PHASE 2 — SCREEN B1: HOME DASHBOARD (TRANG CHỦ: TIN BẠN BÈ & BÀI ĐĂNG LOCKET)
// ============================================================================
@Composable
fun HomeDashboardScreen(
    uiState: JourneyUiState,
    onNavigate: (JourneyScreenId) -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onToggleSavePlace: (String) -> Unit,
    onSelectCollection: (String) -> Unit,
    onSetFeedFilter: (String) -> Unit = {},
    onToggleLikePost: (String) -> Unit = {},
    onAddComment: (String, String) -> Unit = { _, _ -> }
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var activeStoryId by remember { mutableStateOf<String?>(null) }
    var commentingPostId by remember { mutableStateOf<String?>(null) }
    var commentDraft by remember { mutableStateOf("") }

    val filteredPosts = uiState.socialPosts.filter { post ->
        when (uiState.socialFeedFilter) {
            "FRIENDS" -> !post.isMine
            "MINE" -> post.isMine
            else -> true
        }
    }

    val activeStory = uiState.friendStories.find { it.id == activeStoryId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
            .testTag("screen_b1_home_dashboard")
    ) {
        // 1. Thanh Tin 24h của Bạn bè & Của mình (Stories Strip)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = JourneySpacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tr(lang, "Friends' Stories", "Tin Khoảnh Khắc Bạn Bè"),
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.primaryText
                )
                Text(
                    text = tr(lang, "+ Post Locket", "+ Đăng Locket"),
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.accent,
                    modifier = Modifier.clickable { onNavigate(JourneyScreenId.D1_AI_CAMERA) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = JourneySpacing.md),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Add My Locket Story item
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(68.dp)
                        .clickable { onNavigate(JourneyScreenId.D1_AI_CAMERA) }
                        .testTag("story_add_locket_btn")
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(62.dp)
                            .clip(CircleShape)
                            .background(colors.surface)
                            .border(2.dp, colors.accent, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Chụp mới",
                            tint = colors.accent,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = tr(lang, "Snap New", "Chụp mới"),
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.primaryText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Friends' Stories
                uiState.friendStories.forEach { story ->
                    val isSelectedStory = story.id == activeStoryId
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(68.dp)
                            .clickable {
                                activeStoryId = if (isSelectedStory) null else story.id
                            }
                            .testTag("story_item_${story.id}")
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(62.dp)
                                .clip(CircleShape)
                                .border(
                                    width = if (isSelectedStory) 3.dp else 2.dp,
                                    color = if (isSelectedStory) colors.sage else colors.accent,
                                    shape = CircleShape
                                )
                                .padding(3.dp)
                                .clip(CircleShape)
                        ) {
                            Image(
                                painter = painterResource(id = story.photoRes),
                                contentDescription = story.authorName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = story.authorName,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.primaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        // Story Viewer Card (when a friend's story is tapped)
        if (activeStory != null) {
            Surface(
                shape = RoundedCornerShape(JourneyRadius.xl),
                color = colors.surface,
                border = BorderStroke(1.5.dp, colors.accent),
                shadowElevation = JourneyElevation.card,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = JourneySpacing.md, vertical = 8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(colors.accentContainer, CircleShape)
                            ) {
                                Text(
                                    text = activeStory.avatarInitials,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = colors.accent
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = activeStory.authorName,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = colors.primaryText
                                )
                                Text(
                                    text = "📍 ${activeStory.placeName} · ${activeStory.timeAgo}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colors.secondaryText
                                )
                            }
                        }
                        IconButton(
                            onClick = { activeStoryId = null },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Đóng tin",
                                tint = colors.secondaryText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(18.dp))
                    ) {
                        Image(
                            painter = painterResource(id = activeStory.photoRes),
                            contentDescription = activeStory.placeName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Surface(
                            shape = RoundedCornerShape(JourneyRadius.pill),
                            color = Color(0xCC0F172A),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(12.dp)
                        ) {
                            Text(
                                text = activeStory.caption,
                                style = MaterialTheme.typography.bodySmall,
                                color = JourneyPalette.Ivory,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Bộ lọc Bảng tin (Tất cả / Bạn bè / Của tôi)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = JourneySpacing.md, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "ALL" to tr(lang, "All Posts (${uiState.socialPosts.size})", "🌐 Tất cả (${uiState.socialPosts.size})"),
                "FRIENDS" to tr(
                    lang,
                    "Friends (${uiState.socialPosts.count { !it.isMine }})",
                    "👥 Bạn bè (${uiState.socialPosts.count { !it.isMine }})"
                ),
                "MINE" to tr(
                    lang,
                    "My Posts (${uiState.socialPosts.count { it.isMine }})",
                    "👤 Của tôi (${uiState.socialPosts.count { it.isMine }})"
                )
            ).forEach { (key, label) ->
                JourneyFilterChip(
                    label = label,
                    selected = uiState.socialFeedFilter == key,
                    onClick = { onSetFeedFilter(key) }
                )
            }
        }

        // 3. Danh sách Bài đăng Bảng tin (Locket-style Square Photo Feed)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            filteredPosts.forEach { post ->
                val displayAuthor = if (post.isMine) uiState.userName else post.authorName
                val displayInitials = if (post.isMine) uiState.userInitials else post.avatarInitials

                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    shadowElevation = JourneyElevation.card,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("feed_post_card_${post.id}")
                ) {
                    Column {
                        // Post Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(colors.accentContainer, CircleShape)
                                        .border(1.dp, colors.accent.copy(alpha = 0.5f), CircleShape)
                                ) {
                                    if (post.isMine && uiState.userAvatarUri != null) {
                                        coil.compose.AsyncImage(
                                            model = uiState.userAvatarUri,
                                            contentDescription = displayAuthor,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    } else {
                                        Text(
                                            text = displayInitials,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = colors.accent
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = displayAuthor,
                                            style = MaterialTheme.typography.titleSmall,
                                            color = colors.primaryText
                                        )
                                        if (post.isMine) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(
                                                shape = RoundedCornerShape(JourneyRadius.pill),
                                                color = colors.accentContainer
                                            ) {
                                                Text(
                                                    text = tr(lang, "You", "Bạn"),
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = colors.accent,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                                                )
                                            }
                                        }
                                    }
                                    Text(
                                        text = "📍 ${post.placeName} · ${post.timeAgo}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = colors.secondaryText,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.clickable {
                                            onSelectPlace(post.placeId, false)
                                            onNavigate(JourneyScreenId.C1_EXPLORE_MAP)
                                        }
                                    )
                                }
                            }
                        }

                        // Square Locket Photo with Overlaid Caption Pill
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(310.dp)
                                .background(JourneyPalette.DeepObsidian)
                                .clickable { onToggleLikePost(post.id) }
                        ) {
                            if (post.photoUri != null) {
                                coil.compose.AsyncImage(
                                    model = post.photoUri,
                                    contentDescription = post.caption,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = post.photoRes),
                                    contentDescription = post.caption,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            // Locket Overlaid Caption Pill
                            Surface(
                                shape = RoundedCornerShape(18.dp),
                                color = Color(0xBF0F172A),
                                border = BorderStroke(1.dp, Color(0x33FFFFFF)),
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(horizontal = 16.dp, vertical = 14.dp)
                            ) {
                                Text(
                                    text = post.caption,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = JourneyPalette.Ivory,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }

                        // Post Actions Bar (Like, Comment, View on Map)
                        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Surface(
                                        shape = RoundedCornerShape(JourneyRadius.pill),
                                        color = if (post.isLiked) Color(0x22DC2626) else colors.elevatedSurface,
                                        border = BorderStroke(
                                            1.dp,
                                            if (post.isLiked) Color(0x66DC2626) else colors.border
                                        ),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(JourneyRadius.pill))
                                            .clickable { onToggleLikePost(post.id) }
                                            .testTag("like_btn_${post.id}")
                                    ) {
                                        Text(
                                            text = "${if (post.isLiked) "❤️" else "🤍"} ${post.likesCount}",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = colors.primaryText,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(JourneyRadius.pill),
                                        color = colors.elevatedSurface,
                                        border = BorderStroke(1.dp, colors.border),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(JourneyRadius.pill))
                                            .clickable {
                                                commentingPostId = if (commentingPostId == post.id) null else post.id
                                            }
                                            .testTag("comment_btn_${post.id}")
                                    ) {
                                        Text(
                                            text = "💬 ${post.comments.size} bình luận",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = colors.primaryText,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }

                                Surface(
                                    shape = RoundedCornerShape(JourneyRadius.pill),
                                    color = colors.elevatedSurface,
                                    border = BorderStroke(1.dp, colors.border),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(JourneyRadius.pill))
                                        .clickable {
                                            onSelectPlace(post.placeId, false)
                                            onNavigate(JourneyScreenId.C1_EXPLORE_MAP)
                                        }
                                ) {
                                    Text(
                                        text = tr(lang, "🗺️ Map", "🗺️ Bản đồ"),
                                        style = MaterialTheme.typography.labelLarge,
                                        color = colors.accent,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            // Comments Preview
                            if (post.comments.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                post.comments.takeLast(3).forEach { c ->
                                    Text(
                                        text = "${c.authorName}: ${c.text}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = colors.secondaryText,
                                        modifier = Modifier.padding(vertical = 2.dp)
                                    )
                                }
                            }

                            // Inline Comment Input Box
                            if (commentingPostId == post.id) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(modifier = Modifier.weight(1f)) {
                                        JourneyTextField(
                                            value = commentDraft,
                                            onValueChange = { commentDraft = it },
                                            label = tr(lang, "Add comment", "Viết bình luận"),
                                            placeholder = tr(lang, "Write something...", "Nhập bình luận..."),
                                            testTag = "comment_input_${post.id}"
                                        )
                                    }
                                    JourneyButton(
                                        text = tr(lang, "Send", "Gửi"),
                                        onClick = {
                                            if (commentDraft.isNotBlank()) {
                                                onAddComment(post.id, commentDraft)
                                                commentDraft = ""
                                                commentingPostId = null
                                            }
                                        },
                                        variant = JourneyButtonVariant.PRIMARY
                                    )
                                }
                            }
                        }
                    }
                }
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
// PHASE 2 — SCREEN C1: VISITED PLACES MAP (KHÁM PHÁ: BẢN ĐỒ NHỮNG NƠI ĐÃ ĐI / ĐÃ ĐẾN)
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
    val myCheckinsCount = uiState.socialPosts.count { it.isMine }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
            .testTag("screen_c1_explore_map")
    ) {
        // 1. Thống kê Dấu chân Hành trình (Visited Stats Row)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            JourneyStatPillCard(
                value = "${uiState.places.size}",
                label = tr(lang, "Visited", "Nơi đã đến"),
                sublabel = tr(lang, "Landmarks", "Cột mốc"),
                modifier = Modifier.weight(1f)
            )
            JourneyStatPillCard(
                value = "4",
                label = tr(lang, "Provinces", "Tỉnh / Thành"),
                sublabel = tr(lang, "Vietnam", "Việt Nam"),
                modifier = Modifier.weight(1f)
            )
            JourneyStatPillCard(
                value = "$myCheckinsCount",
                label = tr(lang, "Check-ins", "Ảnh Check-in"),
                sublabel = tr(lang, "Memories", "Kỷ niệm"),
                modifier = Modifier.weight(1f)
            )
        }

        // 2. Bản đồ những nơi đã đi / đã đến (Interactive Footprint Map)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(370.dp)
                .padding(horizontal = JourneySpacing.md)
                .clip(RoundedCornerShape(JourneyRadius.xl))
                .background(JourneyPalette.DeepObsidian)
                .border(1.dp, colors.border, RoundedCornerShape(JourneyRadius.xl))
        ) {
            // Topographic Contour Lines & Visited Route Path
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

                val cx = w * activePlace.mapXPercent
                val cy = h * activePlace.mapYPercent
                for (r in 1..3) {
                    drawCircle(
                        color = goldLine,
                        radius = (r * 55f),
                        center = Offset(cx, cy),
                        style = Stroke(
                            width = 1.5f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f), 0f)
                        )
                    )
                }

                val routePoints = uiState.places.map { Offset(w * it.mapXPercent, h * it.mapYPercent) }
                for (k in 0 until routePoints.size - 1) {
                    drawLine(
                        color = Color(0x99B99B72),
                        start = routePoints[k],
                        end = routePoints[k + 1],
                        strokeWidth = 3.5f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 6f), 0f)
                    )
                }
            }

            // Map Header Badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                JourneyBadgePill(
                    text = tr(lang, "🗺️ VISITED FOOTPRINT MAP", "🗺️ BẢN ĐỒ NHỮNG NƠI ĐÃ ĐI"),
                    containerColor = Color(0xCC111923),
                    contentColor = JourneyPalette.SandGold
                )
            }

            // Interactive Visited Pins on Map
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 48.dp, start = 12.dp, end = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (isSelected) Icons.Default.MyLocation else Icons.Default.LocationOn,
                                contentDescription = place.nameVi,
                                tint = if (isSelected) JourneyPalette.Midnight else JourneyPalette.SandGold,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = tr(lang, place.nameEn, place.nameVi),
                                style = MaterialTheme.typography.labelLarge,
                                color = if (isSelected) JourneyPalette.Midnight else JourneyPalette.Ivory
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "✓ Đã đến",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) JourneyPalette.Midnight.copy(alpha = 0.8f) else JourneyPalette.SandGold
                            )
                        }
                    }
                }
            }

            // Bottom Selected Visited Place Card inside Map
            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface.copy(alpha = 0.96f),
                border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.55f)),
                shadowElevation = JourneyElevation.floating,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = activePlace.imageRes),
                        contentDescription = activePlace.nameVi,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "✓ ĐÃ ĐẶT CHÂN TỚI • ${activePlace.regionVi}",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.sage
                        )
                        Text(
                            text = tr(lang, activePlace.nameEn, activePlace.nameVi),
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.primaryText,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = activePlace.coordinates,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.secondaryText
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    JourneyPrimaryButton(
                        text = tr(lang, "Details", "Chi tiết"),
                        onClick = { onSelectPlace(activePlace.id, true) },
                        testTag = "map_open_place_detail_btn"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Danh sách Cột mốc Đã đặt chân tới
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = JourneySpacing.md),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tr(lang, "Visited Milestones (${uiState.places.size})", "Cột Mốc Đã Đặt Chân Tới (${uiState.places.size})"),
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.primaryText
                )
                Text(
                    text = tr(lang, "View Check-ins →", "Xem ảnh Check-in →"),
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.accent,
                    modifier = Modifier.clickable { onNavigate(JourneyScreenId.F1_JOURNAL_HOME) }
                )
            }

            uiState.places.forEach { place ->
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = colors.surface,
                    border = BorderStroke(
                        1.dp,
                        if (place.id == activePlace.id) colors.accent else colors.border
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectPlace(place.id, false) }
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = place.imageRes),
                            contentDescription = place.nameVi,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(lang, place.nameEn, place.nameVi),
                                style = MaterialTheme.typography.titleSmall,
                                color = colors.primaryText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "📍 ${place.regionVi} · ${place.coordinates}",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(JourneyRadius.pill),
                            color = colors.accentContainer,
                            modifier = Modifier.clickable { onSelectPlace(place.id, true) }
                        ) {
                            Text(
                                text = tr(lang, "View", "Xem"),
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.accent,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
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
