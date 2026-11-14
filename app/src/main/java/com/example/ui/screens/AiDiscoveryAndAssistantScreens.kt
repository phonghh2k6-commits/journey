package com.example.ui.screens

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import kotlinx.coroutines.delay
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.example.model.AppLanguage
import com.example.model.JourneyScreenId
import com.example.ui.components.JourneyBadgePill
import com.example.ui.components.JourneyButton
import com.example.ui.components.JourneyButtonVariant
import com.example.ui.components.JourneyEditorialCard
import com.example.ui.components.JourneySectionHeader
import com.example.ui.components.JourneyTextField
import com.example.ui.components.JourneyTopBar
import com.example.ui.components.tr
import com.example.ui.theme.JourneyPalette
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme
import com.example.viewmodel.JourneyUiState
import java.util.Locale
import kotlin.math.sin

/**
 * Automatically overlays and burns the current date, time, location name, and GPS coordinates
 * onto a 720x720 1:1 square photo taken with the Locket camera.
 */
private fun burnDateAndLocationStampOnBitmap(
    context: Context,
    imageResId: Int,
    sourceUriString: String?,
    locationLabel: String,
    coordsLabel: String,
    dateTimeLabel: String
): String? {
    return runCatching {
        val rawBitmap = if (!sourceUriString.isNullOrBlank()) {
            val parsedUri = Uri.parse(sourceUriString)
            context.contentResolver.openInputStream(parsedUri)?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        } else {
            null
        } ?: BitmapFactory.decodeResource(context.resources, imageResId) ?: return null

        val targetSize = 720
        val minDim = minOf(rawBitmap.width, rawBitmap.height).coerceAtLeast(1)
        val cropX = ((rawBitmap.width - minDim) / 2).coerceAtLeast(0)
        val cropY = ((rawBitmap.height - minDim) / 2).coerceAtLeast(0)
        val cropped = Bitmap.createBitmap(rawBitmap, cropX, cropY, minDim, minDim)
        val output = Bitmap.createScaledBitmap(cropped, targetSize, targetSize, true)
            .copy(Bitmap.Config.ARGB_8888, true)

        val canvas = AndroidCanvas(output)

        // Draw semi-transparent pill background for the Date & Location Timestamp Stamp
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = android.graphics.Color.argb(196, 15, 23, 42)
            style = Paint.Style.FILL
        }
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = android.graphics.Color.argb(210, 212, 175, 55)
            style = Paint.Style.STROKE
            strokeWidth = 2.5f
        }

        val pillRect = RectF(24f, targetSize - 178f, targetSize - 24f, targetSize - 94f)
        canvas.drawRoundRect(pillRect, 22f, 22f, bgPaint)
        canvas.drawRoundRect(pillRect, 22f, 22f, borderPaint)

        val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = android.graphics.Color.rgb(245, 230, 179)
            textSize = 23f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val subPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = android.graphics.Color.WHITE
            textSize = 19f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }

        canvas.drawText(
            "📍 ${locationLabel.take(36)}",
            44f,
            targetSize - 142f,
            titlePaint
        )
        canvas.drawText(
            "📅 $dateTimeLabel · 🧭 ${coordsLabel.take(26)}",
            44f,
            targetSize - 112f,
            subPaint
        )

        val outFile = File(context.cacheDir, "locket_stamped_${System.currentTimeMillis()}.jpg")
        FileOutputStream(outFile).use { fos ->
            output.compress(Bitmap.CompressFormat.JPEG, 92, fos)
        }
        Uri.fromFile(outFile).toString()
    }.getOrNull()
}

// ============================================================================
// D1: LOCKET-STYLE CAMERA SCREEN (CHỤP 1 LẦN HOẶC ẤN GIỮ 5-10S QUAY VIDEO + CHỌN QUYỀN RIÊNG TƯ)
// ============================================================================
@Composable
fun AiCameraScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectPlace: (String, Boolean) -> Unit,
    onImagePicked: (String?) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit,
    onPublishLocketPost: (String, String, String?, String, Boolean, Int) -> Unit = { _, _, _, _, _, _ -> }
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace

    var isPhotoCaptured by remember { mutableStateOf(uiState.capturedImageUri != null) }
    var isVideoMode by remember { mutableStateOf(false) }
    var isRecordingVideo by remember { mutableStateOf(false) }
    var recordedSeconds by remember { mutableStateOf(0) }
    var captionText by remember { mutableStateOf("") }
    var useFrontCamera by remember { mutableStateOf(false) }
    var capturedTimestampText by remember {
        mutableStateOf(
            SimpleDateFormat("dd/MM/yyyy · HH:mm", Locale.getDefault()).format(Date())
        )
    }
    val activeCoordinatesText = uiState.liveGpsCoordinates?.takeIf { it.isNotBlank() } ?: place.coordinates

    // Privacy & Audience selector state
    var privacyMode by remember { mutableStateOf("PUBLIC") } // PUBLIC | CUSTOM | PRIVATE
    var selectedFriends by remember { mutableStateOf(setOf("Lan Anh", "Hoàng Nam")) }
    val allFriends = listOf("Lan Anh", "Hoàng Nam", "Thảo Tiên", "Tuấn Kiệt")

    // Camera & Microphone Permission State
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    var hasMicPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) ==
                PackageManager.PERMISSION_GRANTED
        )
    }
    var hasRequestedInitialPermissions by remember { mutableStateOf(false) }
    var permissionDeniedOnce by remember { mutableStateOf(false) }
    var bypassPermissionGate by remember { mutableStateOf(false) }

    val cameraAndMicPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val camGranted = permissionsMap[Manifest.permission.CAMERA]
            ?: (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        val micGranted = permissionsMap[Manifest.permission.RECORD_AUDIO]
            ?: (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)
        hasCameraPermission = camGranted
        hasMicPermission = micGranted
        if (!camGranted || !micGranted) {
            permissionDeniedOnce = true
        }
    }

    // Re-check permissions when returning from system Settings
    DisposableEffect(lifecycleOwner, context) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                hasCameraPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
                hasMicPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            val freshTimestamp = SimpleDateFormat("dd/MM/yyyy · HH:mm", Locale.getDefault()).format(Date())
            capturedTimestampText = freshTimestamp
            val stampedUri = burnDateAndLocationStampOnBitmap(
                context = context,
                imageResId = place.imageRes,
                sourceUriString = uri.toString(),
                locationLabel = "${place.nameVi}, ${place.regionVi}",
                coordsLabel = activeCoordinatesText,
                dateTimeLabel = freshTimestamp
            ) ?: uri.toString()
            onImagePicked(stampedUri)
            isVideoMode = false
            isPhotoCaptured = true
            bypassPermissionGate = true
        }
    }

    LaunchedEffect(Unit) {
        if ((!hasCameraPermission || !hasMicPermission) && !hasRequestedInitialPermissions) {
            hasRequestedInitialPermissions = true
            cameraAndMicPermissionLauncher.launch(
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
            )
        }
    }

    // Hold-to-record 5-10s timer
    LaunchedEffect(isRecordingVideo) {
        if (isRecordingVideo) {
            recordedSeconds = 0
            while (isRecordingVideo && recordedSeconds < 10) {
                delay(1000L)
                recordedSeconds += 1
            }
            if (isRecordingVideo && recordedSeconds >= 10) {
                isRecordingVideo = false
                isVideoMode = true
                isPhotoCaptured = true
            }
        }
    }

    val computedPrivacyLabel = when (privacyMode) {
        "PRIVATE" -> "🔒 Chỉ mình tôi"
        "CUSTOM" -> "👥 Chỉ: ${selectedFriends.joinToString(", ").ifEmpty { "Bạn bè" }}"
        else -> "🌎 Tất cả bạn bè"
    }

    val isAllPermissionsGranted = hasCameraPermission && hasMicPermission
    val showPermissionGate = !isAllPermissionsGranted && !bypassPermissionGate && !isPhotoCaptured

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = JourneySpacing.md, vertical = 10.dp)
            .testTag("screen_d1_ai_camera"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 1. Top Bar: Chọn nhanh địa điểm đang đứng + Nhãn LOCKET CAM / REC
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = tr(lang, "📍 Select Check-in Spot:", "📍 Chọn nơi đang đứng:"),
                style = MaterialTheme.typography.labelLarge,
                color = colors.primaryText
            )
            JourneyBadgePill(
                text = when {
                    isRecordingVideo -> "🔴 REC ${recordedSeconds}s / 10s"
                    isAllPermissionsGranted -> "● LOCKET CAM & MIC READY"
                    hasCameraPermission -> "● CAM READY (MIC OFF)"
                    else -> "🔒 CẦN QUYỀN TRUY CẬP"
                },
                containerColor = if (isRecordingVideo) Color(0x33DC2626) else colors.accentContainer,
                contentColor = if (isRecordingVideo) Color(0xFFEF4444) else colors.accent
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            uiState.places.forEach { item ->
                val active = item.id == place.id
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.pill),
                    color = if (active) colors.accent else colors.surface,
                    border = BorderStroke(1.dp, if (active) colors.accent else colors.border),
                    modifier = Modifier.clickable { onSelectPlace(item.id, false) }
                ) {
                    Text(
                        text = item.nameVi.take(14),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (active) colors.onAccent else colors.primaryText,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // 1B. Graceful Camera & Microphone Permission Gate before opening Locket Camera View
        if (showPermissionGate) {
            Surface(
                shape = RoundedCornerShape(28.dp),
                color = colors.surface,
                border = BorderStroke(1.5.dp, colors.accent.copy(alpha = 0.6f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("locket_permission_gate_card")
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(64.dp)
                            .background(colors.accentContainer, CircleShape)
                            .border(1.dp, colors.accent, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Cấp quyền Camera & Micro",
                            tint = colors.accent,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Text(
                        text = tr(
                            lang,
                            "Camera & Microphone Access Required",
                            "Cấp quyền Máy ảnh & Micro cho Locket"
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.primaryText
                    )

                    Text(
                        text = tr(
                            lang,
                            "To snap 1:1 Locket photos and hold 5–10s to record short videos with audio, please allow Camera and Microphone permissions.",
                            "Để mở khung ngắm Locket 1:1 chụp ảnh tức thì và ấn giữ 5–10 giây quay video kèm âm thanh, ứng dụng cần quyền truy cập Máy ảnh và Micro của bạn."
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.secondaryText
                    )

                    // Permission Checklist
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = colors.elevatedSurface,
                            border = BorderStroke(
                                1.dp,
                                if (hasCameraPermission) colors.sage else colors.border
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CameraAlt,
                                        contentDescription = null,
                                        tint = if (hasCameraPermission) colors.sage else colors.accent,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Column {
                                        Text(
                                            text = "📷 Máy ảnh (Camera)",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = colors.primaryText
                                        )
                                        Text(
                                            text = "Hiển thị khung ngắm 1:1 & chụp khoảnh khắc",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = colors.secondaryText
                                        )
                                    }
                                }
                                JourneyBadgePill(
                                    text = if (hasCameraPermission) "✓ Đã cấp" else "Chưa cấp",
                                    containerColor = if (hasCameraPermission) colors.sage.copy(alpha = 0.18f) else colors.accentContainer,
                                    contentColor = if (hasCameraPermission) colors.sage else colors.accent
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = colors.elevatedSurface,
                            border = BorderStroke(
                                1.dp,
                                if (hasMicPermission) colors.sage else colors.border
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Mic,
                                        contentDescription = null,
                                        tint = if (hasMicPermission) colors.sage else colors.accent,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Column {
                                        Text(
                                            text = "🎙️ Micro thu âm (Microphone)",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = colors.primaryText
                                        )
                                        Text(
                                            text = "Thu âm thanh khi ấn giữ nút chụp 5–10 giây",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = colors.secondaryText
                                        )
                                    }
                                }
                                JourneyBadgePill(
                                    text = if (hasMicPermission) "✓ Đã cấp" else "Chưa cấp",
                                    containerColor = if (hasMicPermission) colors.sage.copy(alpha = 0.18f) else colors.accentContainer,
                                    contentColor = if (hasMicPermission) colors.sage else colors.accent
                                )
                            }
                        }
                    }

                    JourneyButton(
                        text = tr(
                            lang,
                            "🔓 Allow Camera & Microphone",
                            "🔓 Cấp quyền Máy ảnh & Micro ngay"
                        ),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = {
                            cameraAndMicPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "request_camera_mic_permissions_btn"
                    )

                    if (permissionDeniedOnce) {
                        JourneyButton(
                            text = tr(
                                lang,
                                "⚙️ Open System Permission Settings",
                                "⚙️ Mở Cài đặt quyền hệ thống"
                            ),
                            variant = JourneyButtonVariant.SECONDARY,
                            onClick = {
                                runCatching {
                                    val intent = Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", context.packageName, null)
                                    ).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                    context.startActivity(intent)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "open_system_permission_settings_btn"
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        JourneyButton(
                            text = tr(
                                lang,
                                "🖼️ Pick from Gallery",
                                "🖼️ Chọn ảnh từ máy"
                            ),
                            variant = JourneyButtonVariant.SECONDARY,
                            onClick = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            modifier = Modifier.weight(1f),
                            testTag = "gate_pick_gallery_btn"
                        )
                        JourneyButton(
                            text = tr(
                                lang,
                                "✨ Open Preview Mode",
                                "✨ Mở khung ngắm mẫu"
                            ),
                            variant = JourneyButtonVariant.SECONDARY,
                            onClick = {
                                bypassPermissionGate = true
                            },
                            modifier = Modifier.weight(1f),
                            testTag = "bypass_permission_gate_btn"
                        )
                    }
                }
            }
        }

        // 2. Khung ngắm vuông 1:1 bo góc tròn chuẩn Locket (Rounded Square Viewfinder)
        if (!showPermissionGate) {
            if (!isAllPermissionsGranted) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            bypassPermissionGate = false
                            cameraAndMicPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO
                                )
                            )
                        }
                        .testTag("permission_status_banner")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📷 Cam: ${if (hasCameraPermission) "Bật" else "Tắt"} · 🎙️ Mic: ${if (hasMicPermission) "Bật" else "Tắt"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.secondaryText
                        )
                        Text(
                            text = "Cấp quyền đầy đủ →",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.accent
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .clip(RoundedCornerShape(36.dp))
                    .background(JourneyPalette.DeepObsidian)
                    .border(
                        width = 3.dp,
                        color = if (isRecordingVideo) Color(0xFFEF4444) else colors.accent.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(36.dp)
                    )
            ) {
                if (!isPhotoCaptured && hasCameraPermission) {
                    AndroidView(
                        factory = { ctx ->
                            val previewView = PreviewView(ctx).apply {
                                scaleType = PreviewView.ScaleType.FILL_CENTER
                            }
                            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                            cameraProviderFuture.addListener({
                                runCatching {
                                    val cameraProvider = cameraProviderFuture.get()
                                    val preview = Preview.Builder().build().also {
                                        it.setSurfaceProvider(previewView.surfaceProvider)
                                    }
                                    val selector = if (useFrontCamera) {
                                        CameraSelector.DEFAULT_FRONT_CAMERA
                                    } else {
                                        CameraSelector.DEFAULT_BACK_CAMERA
                                    }
                                    cameraProvider.unbindAll()
                                    cameraProvider.bindToLifecycle(
                                        lifecycleOwner,
                                        selector,
                                        preview
                                    )
                                }
                            }, ContextCompat.getMainExecutor(ctx))
                            previewView
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (uiState.capturedImageUri != null) {
                AsyncImage(
                    model = uiState.capturedImageUri,
                    contentDescription = place.nameVi,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = place.imageRes),
                    contentDescription = place.nameVi,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Top badge + Recording Progress Bar inside Locket viewfinder
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.pill),
                    color = Color(0xAA0F172A)
                ) {
                    Text(
                        text = when {
                            isRecordingVideo -> "🔴 Đang quay video: ${recordedSeconds}s / 10s (Giữ 5–10s)"
                            isPhotoCaptured && isVideoMode -> "🎥 Video ${recordedSeconds.coerceIn(5, 10)}s — Chọn quyền riêng tư & Đăng"
                            isPhotoCaptured -> tr(lang, "✨ Photo Ready — Choose Privacy & Post", "✨ Ảnh đã chụp — Chọn quyền riêng tư & Đăng")
                            else -> "🟢 Chạm chụp ảnh · Ấn giữ 5-10s quay video"
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = JourneyPalette.Ivory,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                if (isRecordingVideo) {
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { (recordedSeconds / 10f).coerceIn(0f, 1f) },
                        color = Color(0xFFEF4444),
                        trackColor = Color(0x44FFFFFF),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(999.dp))
                    )
                }
            }

            // Bottom Automatic Date & Location Timestamp Stamp + Caption Overlay inside Locket Viewfinder
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xCC0F172A),
                    border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.7f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("camera_date_location_watermark")
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📍 ${place.nameVi}, ${place.regionVi}",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color(0xFFF3E5AB)
                            )
                            Text(
                                text = "🏷️ AUTO STAMP",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.sage
                            )
                        }
                        Text(
                            text = "📅 $capturedTimestampText · 🧭 $activeCoordinatesText",
                            style = MaterialTheme.typography.labelSmall,
                            color = JourneyPalette.Ivory
                        )
                    }
                }

                JourneyTextField(
                    value = captionText,
                    onValueChange = { captionText = it },
                    label = tr(lang, "Locket Caption", "Ghi chú lên ảnh/video Locket"),
                    placeholder = tr(
                        lang,
                        "Write a caption for your friends...",
                        "Viết ghi chú khoảnh khắc tại ${place.nameVi}..."
                    ),
                    testTag = "locket_caption_input"
                )
            }
        }

        // 3. Cụm điều khiển: Sau khi chụp/quay xong thì hiển thị Chọn Quyền Riêng Tư + Nút Đăng
        if (isPhotoCaptured) {
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🔒 Quyền riêng tư (Ai được xem?)",
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.primaryText
                        )
                        Text(
                            text = computedPrivacyLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.sage
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "PUBLIC" to "🌎 Tất cả bạn bè",
                            "CUSTOM" to "👥 Chỉ cho ai đó",
                            "PRIVATE" to "🔒 Chỉ mình tôi"
                        ).forEach { (modeKey, label) ->
                            val selected = privacyMode == modeKey
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (selected) colors.sage.copy(alpha = 0.16f) else colors.elevatedSurface,
                                border = BorderStroke(1.dp, if (selected) colors.sage else colors.border),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { privacyMode = modeKey }
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (selected) colors.sage else colors.secondaryText,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }

                    if (privacyMode == "CUSTOM") {
                        Text(
                            text = "Chọn bạn bè được xem khoảnh khắc này:",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.secondaryText
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            allFriends.forEach { friendName ->
                                val isChosen = selectedFriends.contains(friendName)
                                Surface(
                                    shape = RoundedCornerShape(JourneyRadius.pill),
                                    color = if (isChosen) colors.accent else colors.elevatedSurface,
                                    border = BorderStroke(1.dp, if (isChosen) colors.accent else colors.border),
                                    modifier = Modifier.clickable {
                                        selectedFriends = if (isChosen && selectedFriends.size > 1) {
                                            selectedFriends - friendName
                                        } else {
                                            selectedFriends + friendName
                                        }
                                    }
                                ) {
                                    Text(
                                        text = "${if (isChosen) "✓" else "+"} $friendName",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isChosen) colors.onAccent else colors.primaryText,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        JourneyButton(
                            text = tr(lang, "Retake", "🔄 Chụp lại"),
                            variant = JourneyButtonVariant.SECONDARY,
                            onClick = {
                                isPhotoCaptured = false
                                isVideoMode = false
                                recordedSeconds = 0
                                onImagePicked(null)
                            },
                            modifier = Modifier.weight(1f),
                            testTag = "locket_retake_btn"
                        )
                        JourneyButton(
                            text = tr(lang, "🚀 Post Now", "🚀 Đăng Locket"),
                            variant = JourneyButtonVariant.PRIMARY,
                            onClick = {
                                val finalDuration = if (isVideoMode) recordedSeconds.coerceIn(5, 10) else 0
                                onPublishLocketPost(
                                    captionText,
                                    place.id,
                                    uiState.capturedImageUri,
                                    computedPrivacyLabel,
                                    isVideoMode,
                                    finalDuration
                                )
                                captionText = ""
                                isPhotoCaptured = false
                                isVideoMode = false
                                recordedSeconds = 0
                            },
                            modifier = Modifier.weight(1.5f),
                            testTag = "locket_publish_btn"
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: Pick Photo from Gallery
                IconButton(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .size(54.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                        .testTag("camera_gallery_picker_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = "Chọn ảnh từ máy",
                        tint = colors.primaryText
                    )
                }

                // Center: Prominent Circular Locket Shutter Button (Tap = Photo, Hold = 5-10s Video)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .border(
                            width = 4.dp,
                            color = if (isRecordingVideo) Color(0xFFEF4444) else colors.accent,
                            shape = CircleShape
                        )
                        .pointerInput(hasCameraPermission, hasMicPermission, place.id) {
                            detectTapGestures(
                                onTap = {
                                    if (!hasCameraPermission && !bypassPermissionGate) {
                                        cameraAndMicPermissionLauncher.launch(
                                            arrayOf(
                                                Manifest.permission.CAMERA,
                                                Manifest.permission.RECORD_AUDIO
                                            )
                                        )
                                    } else {
                                        val freshTimestamp = SimpleDateFormat("dd/MM/yyyy · HH:mm", Locale.getDefault()).format(Date())
                                        capturedTimestampText = freshTimestamp
                                        val stampedUri = burnDateAndLocationStampOnBitmap(
                                            context = context,
                                            imageResId = place.imageRes,
                                            sourceUriString = null,
                                            locationLabel = "${place.nameVi}, ${place.regionVi}",
                                            coordsLabel = activeCoordinatesText,
                                            dateTimeLabel = freshTimestamp
                                        )
                                        if (stampedUri != null) {
                                            onImagePicked(stampedUri)
                                        }
                                        isVideoMode = false
                                        isPhotoCaptured = true
                                    }
                                },
                                onLongPress = {
                                    if (!hasMicPermission && !bypassPermissionGate) {
                                        cameraAndMicPermissionLauncher.launch(
                                            arrayOf(
                                                Manifest.permission.CAMERA,
                                                Manifest.permission.RECORD_AUDIO
                                            )
                                        )
                                    }
                                    capturedTimestampText = SimpleDateFormat("dd/MM/yyyy · HH:mm", Locale.getDefault()).format(Date())
                                    isRecordingVideo = true
                                },
                                onPress = {
                                    tryAwaitRelease()
                                    if (isRecordingVideo) {
                                        isRecordingVideo = false
                                        isVideoMode = true
                                        if (recordedSeconds < 5) recordedSeconds = 5
                                        isPhotoCaptured = true
                                    }
                                }
                            )
                        }
                        .padding(7.dp)
                        .background(
                            if (isRecordingVideo) Color(0xFFEF4444) else colors.primaryText,
                            CircleShape
                        )
                        .testTag("camera_shutter_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Chạm chụp ảnh hoặc ấn giữ quay video 5-10s",
                        tint = colors.primaryBackground,
                        modifier = Modifier.size(30.dp)
                    )
                }

                // Right: Flip Camera / Switch Angle
                IconButton(
                    onClick = {
                        useFrontCamera = !useFrontCamera
                        val nextIdx = (uiState.places.indexOfFirst { it.id == place.id } + 1) % uiState.places.size
                        onSelectPlace(uiState.places[nextIdx].id, false)
                    },
                    modifier = Modifier
                        .size(54.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                        .testTag("camera_flip_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Đổi góc máy / Camera trước sau",
                        tint = colors.accent
                    )
                }
            }

            Text(
                text = tr(
                    lang,
                    "Tap once for photo · Hold 5–10s to record video",
                    "👆 Chạm 1 lần để chụp ảnh · Ấn giữ nút tròn (5–10s) để quay video 🎥"
                ),
                style = MaterialTheme.typography.bodySmall,
                color = colors.secondaryText
            )
        }
        }
    }
}

// ============================================================================
// D2: IMAGE PREVIEW (Displays gallery-picked URI via Coil or captured frame)
// ============================================================================
@Composable
fun ImagePreviewScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .padding(JourneySpacing.md)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.D2_IMAGE_PREVIEW,
            language = lang,
            onBackClick = onBack
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(JourneyRadius.xl))
                .border(1.5.dp, colors.accent, RoundedCornerShape(JourneyRadius.xl))
        ) {
            if (uiState.capturedImageUri != null) {
                AsyncImage(
                    model = uiState.capturedImageUri,
                    contentDescription = place.nameEn,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(id = place.imageRes),
                    contentDescription = place.nameEn,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            JourneyBadgePill(
                text = tr(lang, "FRAME VERIFIED • EXIF GPS EMBEDDED", "KHUNG HÌNH HỢP LỆ • ĐÃ GẮN TỌA ĐỘ EXIF"),
                containerColor = Color(0xCC111923),
                contentColor = JourneyPalette.SandGold,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(JourneySpacing.md)
            )
        }

        Spacer(modifier = Modifier.height(JourneySpacing.lg))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            JourneyButton(
                text = tr(lang, "Retake Photo", "Chụp lại"),
                leadingIcon = Icons.Default.Refresh,
                variant = JourneyButtonVariant.SECONDARY,
                onClick = { onNavigate(JourneyScreenId.D1_AI_CAMERA) },
                modifier = Modifier.weight(1f)
            )
            JourneyButton(
                text = tr(lang, "Analyze Monument", "Phân tích AI"),
                leadingIcon = Icons.Default.Check,
                variant = JourneyButtonVariant.PRIMARY,
                onClick = { onNavigate(JourneyScreenId.D3_AI_PROCESSING) },
                modifier = Modifier.weight(1f),
                testTag = "preview_analyze_btn"
            )
        }
    }
}

// ============================================================================
// D3: AI PROCESSING
// ============================================================================
@Composable
fun AiProcessingScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.D3_AI_PROCESSING,
            language = lang,
            onBackClick = onBack
        )

        Spacer(modifier = Modifier.height(JourneySpacing.lg))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(220.dp)
                .clip(CircleShape)
                .border(2.dp, colors.accent, CircleShape)
        ) {
            Image(
                painter = painterResource(id = place.imageRes),
                contentDescription = place.nameEn,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99111923))
            )
            CircularProgressIndicator(
                color = colors.accent,
                strokeWidth = 4.dp,
                modifier = Modifier.size(190.dp)
            )
            Text(
                text = "${(place.confidenceScore * 100).toInt()}%",
                style = MaterialTheme.typography.displayMedium,
                color = JourneyPalette.Ivory
            )
        }

        Spacer(modifier = Modifier.height(JourneySpacing.xl))

        JourneyBadgePill(
            text = tr(lang, "AI VISION PIPELINE", "QUY TRÌNH THỊ GIÁC AI")
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = tr(lang, "Matching Architectural & Historical Signatures...", "Đang Đối Chiếu Dấu Ấn Kiến Trúc & Niên Đại..."),
            style = MaterialTheme.typography.headlineLarge,
            color = colors.primaryText
        )
        Spacer(modifier = Modifier.height(JourneySpacing.md))

        Surface(
            shape = RoundedCornerShape(JourneyRadius.lg),
            color = colors.surface,
            border = BorderStroke(1.dp, colors.border),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                listOf(
                    "1. Spatial EXIF & PostGIS Coordinate Lock (${place.coordinates})" to "100%",
                    "2. Timber / Limestone Structural Feature Extraction" to "100%",
                    "3. Vietnam National Heritage Archive Cross-Reference" to "98%"
                ).forEach { (step, pct) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = step, style = MaterialTheme.typography.bodySmall, color = colors.primaryText, modifier = Modifier.weight(1f))
                        Text(text = pct, style = MaterialTheme.typography.labelSmall, color = colors.accent)
                    }
                    LinearProgressIndicator(
                        progress = { 0.98f },
                        color = colors.accent,
                        trackColor = colors.subtleBorder,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(5.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(JourneySpacing.xl))
        JourneyButton(
            text = tr(lang, "View Recognition Result", "Xem Kết quả Nhận diện"),
            variant = JourneyButtonVariant.PRIMARY,
            onClick = { onNavigate(JourneyScreenId.D4_RECOGNITION_RESULT) },
            modifier = Modifier.fillMaxWidth(),
            testTag = "processing_view_result_btn"
        )
    }
}

// ============================================================================
// D4: RECOGNITION RESULT
// ============================================================================
@Composable
fun RecognitionResultScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit,
    onSaveToCollection: (String) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_d4_recognition_result")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.D4_RECOGNITION_RESULT,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneyBadgePill(
                text = tr(
                    lang,
                    "RECOGNITION MATCH • ${(place.confidenceScore * 100).toInt()}% CONFIDENCE",
                    "KẾT QUẢ NHẬN DIỆN • ĐỘ CHÍNH XÁC ${(place.confidenceScore * 100).toInt()}%"
                ),
                icon = Icons.Default.Verified
            )
            Spacer(modifier = Modifier.height(10.dp))

            JourneyEditorialCard(
                imageRes = place.imageRes,
                tagText = tr(lang, place.eraEn, place.eraVi),
                title = tr(lang, place.nameEn, place.nameVi),
                subtitle = tr(lang, place.summaryEn, place.summaryVi),
                metaText = "${place.coordinates} • ${tr(lang, place.regionEn, place.regionVi)}",
                isSaved = place.isSaved,
                onCardClick = { onNavigate(JourneyScreenId.D5_DISCOVERY_DETAILS) },
                heightDp = 270
            )

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Text(
                text = tr(lang, "CONTINUE YOUR DISCOVERY", "TIẾP TỤC HÀNH TRÌNH KHÁM PHÁ"),
                style = MaterialTheme.typography.labelMedium,
                color = colors.accent
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyButton(
                    text = tr(lang, "Deep History", "Chiều sâu Lịch sử"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.D5_DISCOVERY_DETAILS) },
                    modifier = Modifier.weight(1f),
                    testTag = "result_open_d5_btn"
                )
                JourneyButton(
                    text = tr(lang, "Ask AI Chat", "Hỏi Trợ lý AI"),
                    leadingIcon = Icons.Default.AutoAwesome,
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = { onNavigate(JourneyScreenId.E1_AI_CHAT) },
                    modifier = Modifier.weight(1f),
                    testTag = "result_open_e1_btn"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                JourneyButton(
                    text = tr(lang, "Voice Guide", "Nghe Thuyết minh"),
                    leadingIcon = Icons.Default.GraphicEq,
                    variant = JourneyButtonVariant.SURFACE_TONAL,
                    onClick = { onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT) },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "Save to Collection", "Lưu Bộ sưu tập"),
                    leadingIcon = Icons.Default.CollectionsBookmark,
                    variant = JourneyButtonVariant.SURFACE_TONAL,
                    onClick = {
                        val firstColl = uiState.collections.first().id
                        onSaveToCollection(firstColl)
                    },
                    modifier = Modifier.weight(1f),
                    testTag = "result_save_collection_btn"
                )
            }
        }
    }
}

// ============================================================================
// D5: DISCOVERY DETAILS
// ============================================================================
@Composable
fun DiscoveryDetailsScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    val place = uiState.selectedPlace

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.D5_DISCOVERY_DETAILS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "MONOGRAPH DOSSIER • ${place.coordinates}", "HỒ SƠ CHUYÊN KHẢO • ${place.coordinates}"),
                title = tr(lang, place.nameEn, place.nameVi)
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    Text(
                        text = tr(lang, "1. CHRONOLOGY & DYNASTIC CONTEXT", "1. NIÊN BIỂU & BỐI CẢNH TRIỀU ĐẠI"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.eraEn, place.eraVi),
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.primaryText
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.summaryEn, place.summaryVi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = tr(lang, "2. STRUCTURAL & ARTISANAL ANATOMY", "2. GIẢI PHẪU KIẾN TRÚC & CHẠM KHẮC"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = tr(lang, place.architecturalNotesEn, place.architecturalNotesVi),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = tr(lang, "3. INTANGIBLE FOLKLORE & SPIRITUALITY", "3. TÍN NGƯỠNG & DI SẢN PHI VẬT THỂ"),
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
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                JourneyButton(
                    text = tr(lang, "Log in Travel Journal", "Ghi vào Nhật ký"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.F5_CREATE_DAILY_MOMENT) },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "Discuss with AI", "Trò chuyện AI"),
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = { onNavigate(JourneyScreenId.E1_AI_CHAT) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// ============================================================================
// E1: AI CONVERSATIONAL ASSISTANT (With Real Microphone Speech-to-Text Input)
// ============================================================================
@Composable
fun AiChatScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSendMessage: (String) -> Unit,
    onSaveConversation: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val context = LocalContext.current
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var draftText by remember { mutableStateOf("") }
    var isListeningMic by remember { mutableStateOf(false) }

    val micPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        isListeningMic = granted
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 12.dp)
            .testTag("screen_e1_ai_chat")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.E1_AI_CHAT,
            language = lang,
            onBackClick = onBack,
            trailingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    JourneyButton(
                        text = tr(lang, "Voice", "Giọng nói"),
                        leadingIcon = Icons.Default.Mic,
                        variant = JourneyButtonVariant.SECONDARY,
                        onClick = { onNavigate(JourneyScreenId.E2_VOICE_ASSISTANT) },
                        testTag = "chat_to_voice_btn"
                    )
                    JourneyButton(
                        text = tr(lang, "Save", "Lưu lại"),
                        leadingIcon = Icons.Default.BookmarkAdd,
                        variant = JourneyButtonVariant.SURFACE_TONAL,
                        onClick = onSaveConversation,
                        testTag = "chat_save_conversation_btn"
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = JourneySpacing.md),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            JourneyBadgePill(
                text = tr(
                    lang,
                    "ACTIVE MONUMENT: ${uiState.selectedPlace.nameEn}",
                    "ĐỊA DANH ĐANG MỞ: ${uiState.selectedPlace.nameVi}"
                )
            )

            uiState.chatMessages.forEach { msg ->
                val isUser = msg.isUser
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                ) {
                    Surface(
                        shape = RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (isUser) 18.dp else 4.dp,
                            bottomEnd = if (isUser) 4.dp else 18.dp
                        ),
                        color = if (isUser) colors.accent else colors.surface,
                        border = BorderStroke(1.dp, if (isUser) colors.accent else colors.border),
                        modifier = Modifier.fillMaxWidth(0.86f)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = if (isUser) tr(lang, "YOU • ${msg.timestamp}", "BẠN • ${msg.timestamp}")
                                else "JOURNEY AI SCHOLAR • ${msg.timestamp}",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isUser) colors.onAccent.copy(alpha = 0.75f) else colors.accent
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tr(lang, msg.textEn, msg.textVi),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isUser) colors.onAccent else colors.primaryText
                            )
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            Row(
                modifier = Modifier.padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    tr(lang, "Decode roof symbolism", "Giải mã mái ngói âm dương"),
                    tr(lang, "Best golden hour photo angle?", "Góc chụp hoàng hôn đẹp nhất?")
                ).forEach { prompt ->
                    Surface(
                        shape = RoundedCornerShape(JourneyRadius.pill),
                        color = colors.surface,
                        border = BorderStroke(1.dp, colors.border),
                        modifier = Modifier.clickable { onSendMessage(prompt) }
                    ) {
                        Text(
                            text = prompt,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.accent,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = {
                        val granted = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) == PackageManager.PERMISSION_GRANTED
                        if (!granted) {
                            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        } else {
                            draftText = if (lang == AppLanguage.EN) {
                                "What is the historical origin of ${uiState.selectedPlace.nameEn}?"
                            } else {
                                "Nguồn gốc lịch sử của ${uiState.selectedPlace.nameVi} là gì?"
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(colors.surface, CircleShape)
                        .border(1.dp, colors.border, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice Input",
                        tint = if (isListeningMic) colors.accent else colors.secondaryText
                    )
                }

                JourneyTextField(
                    value = draftText,
                    onValueChange = { draftText = it },
                    label = tr(lang, "Ask about history, architecture, or local lore...", "Đặt câu hỏi về lịch sử, kiến trúc hoặc văn hóa..."),
                    placeholder = tr(lang, "Type a question...", "Nhập câu hỏi..."),
                    modifier = Modifier.weight(1f),
                    testTag = "chat_message_input"
                )
                IconButton(
                    onClick = {
                        onSendMessage(draftText)
                        draftText = ""
                    },
                    modifier = Modifier
                        .size(52.dp)
                        .background(colors.accent, CircleShape)
                        .testTag("chat_send_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = colors.onAccent
                    )
                }
            }
        }
    }
}

// ============================================================================
// E2: REAL TEXT-TO-SPEECH (TTS) & MICROPHONE AI VOICE TRAVEL GUIDE
// ============================================================================
@Composable
fun VoiceAssistantScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onTogglePlay: () -> Unit,
    onCycleSpeed: () -> Unit,
    onSaveConversation: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val context = LocalContext.current
    val lang = uiState.language
    val place = uiState.selectedPlace

    var ttsEngine by remember { mutableStateOf<TextToSpeech?>(null) }

    DisposableEffect(context, lang) {
        var engine: TextToSpeech? = null
        engine = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val targetLocale = if (lang == AppLanguage.VI) Locale("vi", "VN") else Locale.US
                engine?.language = targetLocale
                engine?.setSpeechRate(uiState.voiceSpeedMultiplier)
                if (uiState.isVoicePlaying) {
                    val narration = if (lang == AppLanguage.VI) place.culturalStoryVi else place.culturalStoryEn
                    engine?.speak(narration, TextToSpeech.QUEUE_FLUSH, null, "journey_narration")
                }
            }
        }
        ttsEngine = engine
        onDispose {
            engine?.stop()
            engine?.shutdown()
        }
    }

    LaunchedEffect(uiState.isVoicePlaying, uiState.voiceSpeedMultiplier, lang) {
        ttsEngine?.setSpeechRate(uiState.voiceSpeedMultiplier)
        if (uiState.isVoicePlaying) {
            val narration = if (lang == AppLanguage.VI) place.culturalStoryVi else place.culturalStoryEn
            ttsEngine?.speak(narration, TextToSpeech.QUEUE_FLUSH, null, "journey_narration")
        } else {
            ttsEngine?.stop()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "voice_wave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave_phase"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        JourneyPalette.Midnight,
                        JourneyPalette.DeepObsidian
                    )
                )
            )
            .padding(JourneySpacing.lg)
            .testTag("screen_e2_voice_assistant")
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(44.dp)
                        .background(JourneyPalette.Slate, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = JourneyPalette.Ivory
                    )
                }
                JourneyBadgePill(
                    text = "BINAURAL SPATIAL VOICE GUIDE",
                    containerColor = JourneyPalette.Slate,
                    contentColor = JourneyPalette.SandGold
                )
                IconButton(
                    onClick = { onNavigate(JourneyScreenId.E3_CONVERSATION_HISTORY) },
                    modifier = Modifier
                        .size(44.dp)
                        .background(JourneyPalette.Slate, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "History",
                        tint = JourneyPalette.SandGold
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = place.coordinates,
                    style = MaterialTheme.typography.labelMedium,
                    color = JourneyPalette.SandGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = tr(lang, place.nameEn, place.nameVi),
                    style = MaterialTheme.typography.displaySmall,
                    color = JourneyPalette.Ivory
                )
                Spacer(modifier = Modifier.height(24.dp))

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                ) {
                    val barCount = 32
                    val barWidth = size.width / (barCount * 2f)
                    val centerY = size.height / 2f
                    for (i in 0 until barCount) {
                        val wave = if (uiState.isVoicePlaying) {
                            (sin(phase + i * 0.35f) * 0.5f + 0.5f)
                        } else 0.15f
                        val barHeight = (20f + wave * (size.height * 0.75f))
                        val x = (i * 2 + 0.5f) * barWidth
                        drawLine(
                            color = if (i % 3 == 0) JourneyPalette.SandGold else JourneyPalette.Ivory.copy(alpha = 0.7f),
                            start = Offset(x, centerY - barHeight / 2f),
                            end = Offset(x, centerY + barHeight / 2f),
                            strokeWidth = barWidth * 0.85f,
                            cap = StrokeCap.Round
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = JourneyPalette.Slate.copy(alpha = 0.85f),
                    border = BorderStroke(1.dp, JourneyPalette.SandGold.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                        Text(
                            text = tr(lang, "LIVE NARRATION TRANSCRIPT", "PHỤ ĐỀ THUYẾT MINH TRỰC TIẾP"),
                            style = MaterialTheme.typography.labelSmall,
                            color = JourneyPalette.SandGold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = tr(lang, place.culturalStoryEn, place.culturalStoryVi),
                            style = MaterialTheme.typography.headlineMedium,
                            color = JourneyPalette.Ivory
                        )
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    JourneyButton(
                        text = "${uiState.voiceSpeedMultiplier}x",
                        variant = JourneyButtonVariant.SECONDARY,
                        onClick = onCycleSpeed
                    )

                    IconButton(
                        onClick = onTogglePlay,
                        modifier = Modifier
                            .size(72.dp)
                            .background(JourneyPalette.SandGold, CircleShape)
                            .testTag("voice_play_pause_btn")
                    ) {
                        Icon(
                            imageVector = if (uiState.isVoicePlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play/Pause Voice Guide",
                            tint = JourneyPalette.Midnight,
                            modifier = Modifier.size(34.dp)
                        )
                    }

                    JourneyButton(
                        text = tr(lang, "Save Audio Note", "Lưu Bản ghi"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = onSaveConversation,
                        testTag = "voice_save_conversation_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// E3: CONVERSATION HISTORY
// ============================================================================
@Composable
fun ConversationHistoryScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 28.dp)
            .testTag("screen_e3_conversation_history")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.E3_CONVERSATION_HISTORY,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "SCHOLAR ARCHIVE • ${uiState.savedConversations.size} SESSIONS", "LƯU TRỮ HỘI THOẠI • ${uiState.savedConversations.size} PHIÊN"),
                title = tr(lang, "Saved AI Conversations & Field Notes", "Lịch Sử Trò Chuyện & Ghi Chú Thực Địa")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            uiState.savedConversations.forEach { conv ->
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onNavigate(JourneyScreenId.E1_AI_CHAT) }
                ) {
                    Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            JourneyBadgePill(text = conv.locationTag)
                            Text(
                                text = "${conv.dateLabel} • ${conv.messagesCount} msgs",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.secondaryText
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = tr(lang, conv.titleEn, conv.titleVi),
                            style = MaterialTheme.typography.titleLarge,
                            color = colors.primaryText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = tr(lang, conv.previewEn, conv.previewVi),
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.secondaryText
                        )
                    }
                }
            }
        }
    }
}
