package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.model.JourneyScreenId
import com.example.ui.components.JourneyBadgePill
import com.example.ui.components.JourneyButton
import com.example.ui.components.JourneyButtonVariant
import com.example.ui.components.JourneySectionHeader
import com.example.ui.components.JourneyTextField
import com.example.ui.components.JourneyTopBar
import com.example.ui.components.tr
import com.example.ui.theme.JourneyPalette
import com.example.ui.theme.JourneyRadius
import com.example.ui.theme.JourneySpacing
import com.example.ui.theme.JourneyTheme
import com.example.viewmodel.JourneyUiState

// ============================================================================
// A1: SPLASH SCREEN (No Bottom Bar per Section 5, FLOW 1 Entry)
// ============================================================================
@Composable
fun SplashScreen(
    uiState: JourneyUiState,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val lang = uiState.language

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(JourneyPalette.Midnight)
            .testTag("screen_a1_splash")
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_hero_halong_bay),
            contentDescription = "Journey AI Splash",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0x99111923),
                            Color(0xCC111923),
                            Color(0xF5111923)
                        )
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(JourneySpacing.xxl)
        ) {
            JourneyBadgePill(
                text = "A1-SPLASH • VIETNAMESE & GLOBAL EDITION",
                containerColor = Color(0xAA1C2934),
                contentColor = JourneyPalette.SandGold
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .border(2.dp, JourneyPalette.SandGold, CircleShape)
                        .background(JourneyPalette.Midnight)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_launcher_compass),
                        contentDescription = "Journey AI Emblem",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(72.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "JOURNEY AI",
                    style = MaterialTheme.typography.displayLarge,
                    color = JourneyPalette.Ivory
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tr(lang, "Explore the World. Discover the Stories.", "Khám phá Thế giới. Lắng nghe Câu chuyện."),
                    style = MaterialTheme.typography.titleMedium,
                    color = JourneyPalette.SandGold,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                JourneyButton(
                    text = tr(lang, "Begin Journey (A2 Welcome)", "Bắt đầu Hành trình (A2 Chào mừng)"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.A2_WELCOME) },
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "splash_continue_btn"
                )
                Spacer(modifier = Modifier.height(8.dp))
                JourneyButton(
                    text = tr(lang, "Skip to Home Dashboard (B1)", "Vào thẳng Trang chủ (B1)"),
                    variant = JourneyButtonVariant.GHOST,
                    onClick = { onNavigate(JourneyScreenId.B1_HOME_DASHBOARD) }
                )
            }
        }
    }
}

// ============================================================================
// A2: WELCOME SCREEN (Language Selection VI/EN & Editorial Invitation)
// ============================================================================
@Composable
fun WelcomeScreen(
    uiState: JourneyUiState,
    onToggleLanguage: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.xl)
            .testTag("screen_a2_welcome"),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            JourneyBadgePill(text = "A2-WELCOME • BILINGUAL EDITION")
            JourneyButton(
                text = if (lang == com.example.model.AppLanguage.EN) "Tiếng Việt (VI)" else "English (EN)",
                leadingIcon = Icons.Default.Language,
                variant = JourneyButtonVariant.SECONDARY,
                onClick = onToggleLanguage
            )
        }

        Column(modifier = Modifier.padding(vertical = JourneySpacing.xl)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .clip(RoundedCornerShape(JourneyRadius.xl))
                    .border(1.dp, colors.border, RoundedCornerShape(JourneyRadius.xl))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_hoian_lanterns),
                    contentDescription = "Hoi An Lanterns",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(JourneySpacing.xl))
            Text(
                text = tr(lang, "Every Monument Holds a Living Memory.", "Mỗi Di Sản Đều Ẩn Chứa Một Ký Ức Sống Động."),
                style = MaterialTheme.typography.displayMedium,
                color = colors.primaryText
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = tr(
                    lang,
                    "Point your lens at centuries-old pagodas, karst cathedrals, and imperial stelae. Let Journey AI narrate their stories and compose your personal travel anthology.",
                    "Hướng ống kính về những ngôi chùa cổ, vịnh đá vôi kỳ vĩ hay bia đá ngàn năm để lắng nghe câu chuyện lịch sử và lưu giữ nhật ký điện ảnh."
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = colors.secondaryText
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            JourneyButton(
                text = tr(lang, "Explore Capabilities (A3 Onboarding)", "Khám phá Tính năng (A3)"),
                variant = JourneyButtonVariant.PRIMARY,
                onClick = { onNavigate(JourneyScreenId.A3_ONBOARDING) },
                modifier = Modifier.fillMaxWidth(),
                testTag = "welcome_to_onboarding_btn"
            )
            JourneyButton(
                text = tr(lang, "Sign In to Existing Account (A5)", "Đăng nhập Tài khoản (A5)"),
                variant = JourneyButtonVariant.SECONDARY,
                onClick = { onNavigate(JourneyScreenId.A5_LOGIN) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ============================================================================
// A3: ONBOARDING SLIDES (3 Interactive Editorial Capabilities Slides)
// ============================================================================
@Composable
fun OnboardingSlidesScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var slideIndex by remember { mutableIntStateOf(0) }

    val slides = listOf(
        Triple(
            R.drawable.img_hanoi_temple,
            tr(lang, "01 / AI Visual Monument Recognition", "01 / Nhận Diện Kiến Trúc & Cổ Vật Bằng AI"),
            tr(
                lang,
                "Instant architectural and historical decoding for temples, stelae, and UNESCO landscapes across Vietnam and beyond.",
                "Giải mã tức thì niên đại, điển tích và kiến trúc chùa tháp, văn bia cùng các di sản UNESCO."
            )
        ),
        Triple(
            R.drawable.img_hero_halong_bay,
            tr(lang, "02 / Binaural AI Voice Travel Guide", "02 / Thuyết Minh Giọng Nói AI Đa Ngữ"),
            tr(
                lang,
                "Walk hands-free while an editorial scholar voice narrates local folklore, geology, and dynastic history.",
                "Tự do dạo bước và lắng nghe giọng đọc truyền cảm kể lại truyền thuyết địa phương và lịch sử các triều đại."
            )
        ),
        Triple(
            R.drawable.img_sapa_terraces,
            tr(lang, "03 / Travel Journal & 4K Story Reels", "03 / Nhật Ký Hành Trình & Dựng Phim 4K"),
            tr(
                lang,
                "Transform daily moments and GPS footprints into illustrated essays and 24fps cinematic travel films.",
                "Biến những khoảnh khắc mỗi ngày thành trang viết nghệ thuật và thước phim hành trình chuẩn điện ảnh."
            )
        )
    )

    val current = slides[slideIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.lg)
            .testTag("screen_a3_onboarding"),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A3_ONBOARDING,
            language = lang,
            onBackClick = onBack
        )

        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(JourneyRadius.xl))
            ) {
                Image(
                    painter = painterResource(id = current.first),
                    contentDescription = current.second,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            // Slide indicators
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                slides.indices.forEach { idx ->
                    Box(
                        modifier = Modifier
                            .width(if (idx == slideIndex) 32.dp else 10.dp)
                            .height(6.dp)
                            .background(
                                if (idx == slideIndex) colors.accent else colors.border,
                                RoundedCornerShape(4.dp)
                            )
                            .clickable { slideIndex = idx }
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.md))
            Text(
                text = current.second,
                style = MaterialTheme.typography.headlineLarge,
                color = colors.primaryText
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = current.third,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.secondaryText
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (slideIndex < slides.lastIndex) {
                JourneyButton(
                    text = tr(lang, "Next Slide", "Tiếp theo"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { slideIndex++ },
                    modifier = Modifier.weight(1f),
                    testTag = "onboarding_next_slide_btn"
                )
            } else {
                JourneyButton(
                    text = tr(lang, "Configure Permissions (A4)", "Thiết lập Quyền truy cập (A4)"),
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = { onNavigate(JourneyScreenId.A4_PERMISSIONS) },
                    modifier = Modifier.weight(1f),
                    testTag = "onboarding_to_permissions_btn"
                )
            }
            JourneyButton(
                text = tr(lang, "Login (A5)", "Đăng nhập (A5)"),
                variant = JourneyButtonVariant.SECONDARY,
                onClick = { onNavigate(JourneyScreenId.A5_LOGIN) }
            )
        }
    }
}

// ============================================================================
// A4: PERMISSION INTRODUCTION (Camera, Location, Microphone Toggles)
// ============================================================================
@Composable
fun PermissionIntroductionScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onTogglePermission: (String) -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
            .testTag("screen_a4_permissions")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A4_PERMISSIONS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "SENSOR & PRIVACY TRANSPARENCY", "MINH BẠCH QUYỀN TRUY CẬP THIẾT BỊ"),
                title = tr(lang, "Enable Spatial & Visual Discovery", "Cấp Quyền Cho Ống Kính & Bản Đồ")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            val perms = listOf(
                Triple(
                    "CAMERA",
                    tr(lang, "Camera Access (AI Lens)", "Quyền Máy ảnh (Ống kính AI)"),
                    uiState.cameraPermissionGranted to Icons.Default.CameraAlt
                ),
                Triple(
                    "LOCATION",
                    tr(lang, "Precise Location (Explore Map & EXIF)", "Vị trí Chính xác (Bản đồ Di sản)"),
                    uiState.locationPermissionGranted to Icons.Default.LocationOn
                ),
                Triple(
                    "MIC",
                    tr(lang, "Microphone (Hands-Free Voice Guide)", "Micro (Hỏi đáp Giọng nói)"),
                    uiState.micPermissionGranted to Icons.Default.Mic
                )
            )

            perms.forEach { (key, title, statePair) ->
                val (granted, icon) = statePair
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = colors.surface,
                    border = BorderStroke(1.dp, if (granted) colors.accent else colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(JourneySpacing.lg),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = title,
                                tint = colors.accent
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primaryText
                            )
                        }
                        Switch(
                            checked = granted,
                            onCheckedChange = { onTogglePermission(key) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colors.onAccent,
                                checkedTrackColor = colors.accent
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.xl))
            JourneyButton(
                text = tr(lang, "Continue to Authentication (A5)", "Tiếp tục Đăng nhập (A5)"),
                variant = JourneyButtonVariant.PRIMARY,
                onClick = { onNavigate(JourneyScreenId.A5_LOGIN) },
                modifier = Modifier.fillMaxWidth(),
                testTag = "permissions_to_login_btn"
            )
        }
    }
}

// ============================================================================
// A5: LOGIN (Supabase Auth Handoff Ready)
// ============================================================================
@Composable
fun LoginScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var email by remember { mutableStateOf(uiState.userEmail) }
    var password by remember { mutableStateOf("••••••••••••") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
            .testTag("screen_a5_login")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A5_LOGIN,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = "SUPABASE AUTH • JWT SESSION",
                title = tr(lang, "Welcome Back to Journey AI", "Chào Mừng Trở Lại Journey AI")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(JourneySpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    JourneyTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        placeholder = "explorer@journey.ai",
                        leadingIcon = Icons.Default.Email,
                        testTag = "login_email_input"
                    )
                    JourneyTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = tr(lang, "Password", "Mật khẩu"),
                        placeholder = "••••••••",
                        leadingIcon = Icons.Default.Lock
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        JourneyButton(
                            text = tr(lang, "Forgot Password? (A7)", "Quên mật khẩu? (A7)"),
                            variant = JourneyButtonVariant.GHOST,
                            onClick = { onNavigate(JourneyScreenId.A7_FORGOT_PASSWORD) }
                        )
                        JourneyButton(
                            text = tr(lang, "Create Account (A6)", "Đăng ký mới (A6)"),
                            variant = JourneyButtonVariant.GHOST,
                            onClick = { onNavigate(JourneyScreenId.A6_REGISTER) }
                        )
                    }

                    JourneyButton(
                        text = tr(lang, "Sign In & Enter Home (B1)", "Đăng Nhập & Vào Trang Chủ (B1)"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onNavigate(JourneyScreenId.B1_HOME_DASHBOARD) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "login_submit_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// A6: REGISTER
// ============================================================================
@Composable
fun RegisterScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var fullName by remember { mutableStateOf(uiState.userName) }
    var email by remember { mutableStateOf(uiState.userEmail) }
    var password by remember { mutableStateOf("••••••••••••") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A6_REGISTER,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "CREATE PERSONAL ARCHIVE", "TẠO HỒ SƠ KHÁM PHÁ"),
                title = tr(lang, "Join the Journey AI Atelier", "Đăng Ký Tài Khoản Journey AI")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(JourneySpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    JourneyTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = tr(lang, "Explorer Name", "Họ và Tên"),
                        placeholder = "Minh Nguyễn",
                        leadingIcon = Icons.Default.Person
                    )
                    JourneyTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        placeholder = "minh.nguyen@journey.ai",
                        leadingIcon = Icons.Default.Email
                    )
                    JourneyTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = tr(lang, "Password", "Mật khẩu"),
                        placeholder = "••••••••••••",
                        leadingIcon = Icons.Default.Lock
                    )

                    JourneyButton(
                        text = tr(lang, "Register & Verify Email (A8)", "Đăng ký & Xác thực Email (A8)"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onNavigate(JourneyScreenId.A8_EMAIL_VERIFY) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "register_submit_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// A7: FORGOT PASSWORD
// ============================================================================
@Composable
fun ForgotPasswordScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var email by remember { mutableStateOf(uiState.userEmail) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A7_FORGOT_PASSWORD,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "CREDENTIAL RECOVERY", "KHÔI PHỤC MẬT KHẨU"),
                title = tr(lang, "Send Recovery Code", "Gửi Mã Khôi Phục Qua Email")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(JourneySpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    JourneyTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Registered Email",
                        placeholder = "minh.nguyen@journey.ai",
                        leadingIcon = Icons.Default.Email
                    )
                    JourneyButton(
                        text = tr(lang, "Send OTP & Verify (A8)", "Gửi Mã Xác Thực (A8)"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onNavigate(JourneyScreenId.A8_EMAIL_VERIFY) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// ============================================================================
// A8: EMAIL VERIFICATION
// ============================================================================
@Composable
fun EmailVerificationScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language
    var otpCode by remember { mutableStateOf("8 4 2 9 1 6") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(JourneySpacing.md)
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.A8_EMAIL_VERIFY,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.sm)) {
            JourneySectionHeader(
                eyebrow = "SUPABASE OTP VERIFICATION",
                title = tr(lang, "Verify Your Explorer Identity", "Xác Thực Mã Bảo Mật 6 Số")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(JourneySpacing.lg),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = tr(
                            lang,
                            "We sent a 6-digit verification code to ${uiState.userEmail}.",
                            "Mã xác thực 6 chữ số đã được gửi tới ${uiState.userEmail}."
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )
                    JourneyTextField(
                        value = otpCode,
                        onValueChange = { otpCode = it },
                        label = "6-Digit OTP",
                        placeholder = "8 4 2 9 1 6"
                    )
                    JourneyButton(
                        text = tr(lang, "Verify & Enter Home Dashboard (B1)", "Xác Thực & Vào Trang Chủ (B1)"),
                        variant = JourneyButtonVariant.PRIMARY,
                        onClick = { onNavigate(JourneyScreenId.B1_HOME_DASHBOARD) },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "verify_email_submit_btn"
                    )
                }
            }
        }
    }
}

// ============================================================================
// I1: PROFILE (FLOW 7: Profile -> Subscription -> Plan Comparison -> Settings & Privacy)
// ============================================================================
@Composable
fun ProfileScreen(
    uiState: JourneyUiState,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_i1_profile")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.I1_PROFILE,
            language = lang,
            trailingContent = {
                JourneyButton(
                    text = tr(lang, "Settings", "Cài đặt"),
                    leadingIcon = Icons.Default.Settings,
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = { onNavigate(JourneyScreenId.I2_SETTINGS) },
                    testTag = "profile_to_settings_btn"
                )
            }
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            // Explorer Identity Card
            Surface(
                shape = RoundedCornerShape(JourneyRadius.xl),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.accent.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.xl)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(68.dp)
                                .clip(CircleShape)
                                .border(2.dp, colors.accent, CircleShape)
                                .background(colors.secondaryBackground)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_launcher_compass),
                                contentDescription = uiState.userName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            JourneyBadgePill(text = "JOURNEY+ PATRON • ${uiState.selectedPlanTier}")
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = uiState.userName,
                                style = MaterialTheme.typography.headlineLarge,
                                color = colors.primaryText
                            )
                            Text(
                                text = uiState.userEmail,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(JourneySpacing.lg))

                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf(
                            "28" to tr(lang, "AI Scans", "Lượt quét AI"),
                            "${uiState.trips.size}" to tr(lang, "Journals", "Nhật ký"),
                            "${uiState.collections.size}" to tr(lang, "Collections", "Bộ sưu tập"),
                            "4" to tr(lang, "4K Reels", "Phim 4K")
                        ).forEach { (num, label) ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = num, style = MaterialTheme.typography.headlineMedium, color = colors.accent)
                                Text(text = label, style = MaterialTheme.typography.labelSmall, color = colors.secondaryText)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            val menuRows = listOf(
                Triple(
                    tr(lang, "Journey+ Membership & Plans", "Gói Hội Viên Journey+"),
                    tr(lang, "Unlimited AI lens scans, offline voice packs & 4K cinema reel exports", "Quét AI không giới hạn, thuyết minh ngoại tuyến & xuất phim 4K"),
                    JourneyScreenId.I3_SUBSCRIPTION
                ),
                Triple(
                    tr(lang, "Application Settings & Language", "Cài Đặt Ứng Dụng & Ngôn Ngữ"),
                    tr(lang, "Dark/Light appearance, Vietnamese/English, voice speed & offline cache", "Giao diện Sáng/Tối, Tiếng Việt/Anh, tốc độ giọng đọc & bộ nhớ đệm"),
                    JourneyScreenId.I2_SETTINGS
                ),
                Triple(
                    tr(lang, "Privacy, Permissions & Data", "Quyền Riêng Tư & Quản Lý Dữ Liệu"),
                    tr(lang, "Hardware permissions, personal archive export & cloud security", "Quyền thiết bị, xuất dữ liệu cá nhân & bảo mật đám mây"),
                    JourneyScreenId.I4_PRIVACY_AND_DATA
                ),
                Triple(
                    tr(lang, "Saved AI Conversations", "Lịch Sử Trò Chuyện AI"),
                    tr(lang, "Review your saved cultural Q&A and audio guide transcripts", "Xem lại các cuộc trò chuyện văn hóa và bản ghi thuyết minh"),
                    JourneyScreenId.E3_CONVERSATION_HISTORY
                ),
                Triple(
                    tr(lang, "Sign Out / Switch Account", "Đăng Xuất / Đổi Tài Khoản"),
                    tr(lang, "Return to Welcome & Sign In screen", "Quay về màn hình Chào mừng & Đăng nhập"),
                    JourneyScreenId.A1_SPLASH
                )
            )

            menuRows.forEach { (title, subtitle, targetScreen) ->
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.lg),
                    color = colors.surface,
                    border = BorderStroke(1.dp, colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable { onNavigate(targetScreen) }
                        .testTag("profile_menu_${targetScreen.code.lowercase()}")
                ) {
                    Row(
                        modifier = Modifier.padding(JourneySpacing.lg),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = title, style = MaterialTheme.typography.titleMedium, color = colors.primaryText)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = colors.secondaryText)
                        }
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = title,
                            tint = colors.accent
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// I2: SETTINGS
// ============================================================================
@Composable
fun SettingsScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    onCycleVoiceSpeed: () -> Unit,
    onNavigate: (JourneyScreenId) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_i2_settings")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.I2_SETTINGS,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "PREFERENCES & LOCALIZATION", "TÙY CHỈNH HỆ THỐNG"),
                title = tr(lang, "Application Settings", "Cài Đặt Ứng Dụng")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            Surface(
                shape = RoundedCornerShape(JourneyRadius.lg),
                color = colors.surface,
                border = BorderStroke(1.dp, colors.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(JourneySpacing.lg)) {
                    // Dark / Light Mode
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(lang, "Dark Mode (Midnight #111923)", "Chế độ Tối (Midnight #111923)"),
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primaryText
                            )
                            Text(
                                text = tr(lang, "Switch between Midnight Dark and Warm Ivory Light theme", "Chuyển đổi giữa giao diện Tối điện ảnh và Sáng màu ngà"),
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }
                        Switch(
                            checked = uiState.isDarkTheme,
                            onCheckedChange = { onToggleTheme() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = colors.onAccent,
                                checkedTrackColor = colors.accent
                            )
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = colors.subtleBorder)

                    // Language EN / VI
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(lang, "Display & Voice Language", "Ngôn ngữ Hiển thị & Thuyết minh"),
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primaryText
                            )
                            Text(
                                text = "Current: ${uiState.language.label}",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }
                        JourneyButton(
                            text = if (lang == com.example.model.AppLanguage.EN) "Switch to Tiếng Việt" else "Chuyển sang English",
                            variant = JourneyButtonVariant.SECONDARY,
                            onClick = onToggleLanguage
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = colors.subtleBorder)

                    // Voice Narration Speed
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tr(lang, "AI Voice Guide Cadence", "Tốc độ Thuyết minh Giọng nói"),
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.primaryText
                            )
                            Text(
                                text = "${uiState.voiceSpeedMultiplier}x speed",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.secondaryText
                            )
                        }
                        JourneyButton(
                            text = "${uiState.voiceSpeedMultiplier}x",
                            variant = JourneyButtonVariant.SURFACE_TONAL,
                            onClick = onCycleVoiceSpeed
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))
            JourneyButton(
                text = tr(lang, "Privacy & Data Controls (I4)", "Quyền riêng tư & Dữ liệu (I4)"),
                variant = JourneyButtonVariant.SECONDARY,
                onClick = { onNavigate(JourneyScreenId.I4_PRIVACY_AND_DATA) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// ============================================================================
// I3: SUBSCRIPTION & PLAN COMPARISON (FLOW 7: Profile -> Subscription -> Plan Comparison)
// ============================================================================
@Composable
fun SubscriptionScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onSelectPlan: (String) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    val plans = listOf(
        Triple(
            "EXPLORER_FREE",
            tr(lang, "Explorer Tier • Free", "Gói Khám Phá • Miễn Phí"),
            listOf(
                tr(lang, "5 AI Monument Recognition scans / day", "5 lượt quét nhận diện di sản AI / ngày"),
                tr(lang, "Standard 1080p travel journal stories", "Nhật ký hành trình & câu chuyện AI tiêu chuẩn"),
                tr(lang, "Online map & community discovery feed", "Bản đồ trực tuyến & bảng tin khám phá")
            )
        ),
        Triple(
            "JOURNEY_PRO_MONTHLY",
            tr(lang, "Journey+ Atelier • 199.000₫ / mo ($7.99)", "Hội Viên Journey+ • 199.000₫ / tháng"),
            listOf(
                tr(lang, "Unlimited AI Architectural & Artifact Recognition", "Nhận diện Kiến trúc & Cổ vật AI không giới hạn"),
                tr(lang, "Full Binaural Voice Travel Guide (VI & EN)", "Thuyết minh giọng nói chuyên sâu song ngữ Việt - Anh"),
                tr(lang, "Offline Vietnam & UNESCO Heritage Maps", "Tải bản đồ & dữ liệu di sản ngoại tuyến")
            )
        ),
        Triple(
            "JOURNEY_PRO_ANNUAL",
            tr(lang, "Journey+ Patron Annual • 1.490.000₫ / yr ($59.99)", "Journey+ Năm (Tiết kiệm 38%) • 1.490.000₫ / năm"),
            listOf(
                tr(lang, "Everything in Journey+ Monthly included", "Bao gồm toàn bộ đặc quyền gói Journey+ Tháng"),
                tr(lang, "4K 60fps Cinematic Travel Film Export & LUTs", "Xuất phim hành trình 4K 60fps & bộ lọc màu điện ảnh"),
                tr(lang, "Priority FastAPI Vision RAG & Cloud Archive", "Ưu tiên xử lý AI tốc độ cao & lưu trữ đám mây")
            )
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_i3_subscription")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.I3_SUBSCRIPTION,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = tr(lang, "FLOW 7 • PLAN COMPARISON", "LUỒNG 7 • SO SÁNH GÓI HỘI VIÊN"),
                title = tr(lang, "Elevate Every Expedition with Journey+", "Nâng Tầm Trải Nghiệm Cùng Journey+")
            )
            Spacer(modifier = Modifier.height(JourneySpacing.md))

            plans.forEach { (planId, title, perks) ->
                val isSelected = uiState.selectedPlanTier == planId
                Surface(
                    shape = RoundedCornerShape(JourneyRadius.xl),
                    color = if (isSelected) colors.accentContainer else colors.surface,
                    border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) colors.accent else colors.border),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { onSelectPlan(planId) }
                        .testTag("plan_card_${planId.lowercase()}")
                ) {
                    Column(modifier = Modifier.padding(JourneySpacing.xl)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge,
                                color = colors.primaryText,
                                modifier = Modifier.weight(1f)
                            )
                            if (isSelected) {
                                JourneyBadgePill(
                                    text = tr(lang, "ACTIVE PLAN", "ĐANG CHỌN"),
                                    icon = Icons.Default.Star
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        perks.forEach { perk ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = perk,
                                    tint = colors.accent,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = perk,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.secondaryText
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        JourneyButton(
                            text = if (isSelected) tr(lang, "Selected Plan", "Gói Đang Chọn")
                            else tr(lang, "Select $planId", "Chọn gói này"),
                            variant = if (isSelected) JourneyButtonVariant.PRIMARY else JourneyButtonVariant.SECONDARY,
                            onClick = { onSelectPlan(planId) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

// ============================================================================
// I4: PRIVACY AND DATA
// ============================================================================
@Composable
fun PrivacyAndDataScreen(
    uiState: JourneyUiState,
    onBack: () -> Unit,
    onTogglePermission: (String) -> Unit,
    onShowToast: (String) -> Unit
) {
    val colors = JourneyTheme.colors
    val lang = uiState.language

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp)
            .testTag("screen_i4_privacy")
    ) {
        JourneyTopBar(
            screenId = JourneyScreenId.I4_PRIVACY_AND_DATA,
            language = lang,
            onBackClick = onBack
        )

        Column(modifier = Modifier.padding(horizontal = JourneySpacing.md)) {
            JourneySectionHeader(
                eyebrow = "SUPABASE RLS & ZERO-TRUST PRIVACY",
                title = tr(lang, "Privacy, Hardware Permissions & Data Sovereignty", "Quyền Riêng Tư & Chủ Quyền Dữ Liệu")
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
                        text = tr(lang, "DATA PROTECTION ARCHITECTURE", "KIẾN TRÚC BẢO MẬT DỮ LIỆU"),
                        style = MaterialTheme.typography.labelMedium,
                        color = colors.accent
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = tr(
                            lang,
                            "• Personal journals and EXIF coordinates are protected by Supabase PostgreSQL Row-Level Security (RLS).\n• Visual recognition frames sent to FastAPI are ephemeral unless explicitly saved to a Discovery Collection.\n• Full compliance with Vietnam Decree 13/2023/ND-CP on Personal Data Protection and GDPR.",
                            "• Nhật ký cá nhân và tọa độ EXIF được bảo vệ bằng cơ chế Row-Level Security (RLS) trên Supabase.\n• Ảnh chụp nhận diện qua FastAPI không tự động lưu trữ nếu bạn không đưa vào Bộ sưu tập.\n• Tuân thủ đầy đủ Nghị định 13/2023/NĐ-CP về Bảo vệ dữ liệu cá nhân."
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.secondaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(JourneySpacing.lg))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                JourneyButton(
                    text = tr(lang, "Export Archive (JSON/ZIP)", "Xuất Dữ Liệu Cá Nhân"),
                    leadingIcon = Icons.Default.Download,
                    variant = JourneyButtonVariant.PRIMARY,
                    onClick = {
                        onShowToast(
                            if (lang == com.example.model.AppLanguage.EN)
                                "Prepared personal archive export package"
                            else
                                "Đã chuẩn bị gói xuất dữ liệu cá nhân"
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
                JourneyButton(
                    text = tr(lang, "Clear AI Cache", "Xóa Bộ Nhớ Đệm"),
                    leadingIcon = Icons.Default.DeleteSweep,
                    variant = JourneyButtonVariant.SECONDARY,
                    onClick = {
                        onShowToast(
                            if (lang == com.example.model.AppLanguage.EN)
                                "Cleared temporary vision cache"
                            else
                                "Đã dọn sạch bộ nhớ đệm nhận diện"
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
