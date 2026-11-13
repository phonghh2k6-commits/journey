package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.R
import com.example.model.AppLanguage
import com.example.model.ChatMessageItem
import com.example.model.DailyMoment
import com.example.model.DiscoveryCollection
import com.example.model.JourneySampleRepository
import com.example.model.JourneyScreenId
import com.example.model.PlaceItem
import com.example.model.PrimaryTab
import com.example.model.PrototypeFlowSpec
import com.example.model.SavedConversation
import com.example.model.ScreenStateMode
import com.example.model.TripJournal
import com.example.model.ViewportPreset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class JourneyUiState(
    val currentScreen: JourneyScreenId = JourneyScreenId.B1_HOME_DASHBOARD,
    val backStack: List<JourneyScreenId> = emptyList(),
    val isDarkTheme: Boolean = false,
    val language: AppLanguage = AppLanguage.VI,
    val screenStateMode: ScreenStateMode = ScreenStateMode.DEFAULT,
    val viewportPreset: ViewportPreset = ViewportPreset.FULL,
    val isInspectorExpanded: Boolean = false,
    val isQuickProfileOpen: Boolean = false,
    val isMinimalViewEnabled: Boolean = false,
    val fontScaleStep: Int = 0, // 0 = Vừa, 1 = Lớn, 2 = Rất lớn
    val activeFlow: PrototypeFlowSpec? = null,
    val activeFlowStepIndex: Int = 0,
    val isAuthenticated: Boolean = true,
    val userEmail: String = "minh.nguyen@journey.ai",
    val userName: String = "Minh Nguyễn",
    val selectedPlanTier: String = "JOURNEY_PRO_ANNUAL",
    // Permissions simulation
    val cameraPermissionGranted: Boolean = true,
    val locationPermissionGranted: Boolean = true,
    val micPermissionGranted: Boolean = true,
    // Domain interactive state
    val places: List<PlaceItem> = JourneySampleRepository.places,
    val selectedPlaceId: String = "place_hoian",
    val trips: List<TripJournal> = JourneySampleRepository.trips,
    val selectedTripId: String = "trip_heritage_north_central",
    val selectedMomentId: String = "m_01",
    val collections: List<DiscoveryCollection> = JourneySampleRepository.collections,
    val selectedCollectionId: String = "coll_imperial",
    val chatMessages: List<ChatMessageItem> = JourneySampleRepository.initialChatMessages,
    val savedConversations: List<SavedConversation> = JourneySampleRepository.savedConversations,
    val searchQuery: String = "",
    val selectedCategoryFilter: String = "ALL",
    val isVoicePlaying: Boolean = true,
    val voiceSpeedMultiplier: Float = 1.0f,
    val selectedVideoAspect: String = "9:16 Vertical Reel",
    val selectedVideoMusic: String = "Đàn Tranh & Ambient Strings",
    val videoExportProgress: Float = 1.0f,
    val capturedImageUri: String? = null,
    val liveGpsCoordinates: String? = null,
    val toastBannerMessage: String? = null
) {
    val selectedPlace: PlaceItem
        get() = places.find { it.id == selectedPlaceId } ?: places.first()

    val selectedTrip: TripJournal
        get() = trips.find { it.id == selectedTripId } ?: trips.first()

    val selectedMoment: DailyMoment
        get() = trips.flatMap { it.moments }.find { it.id == selectedMomentId }
            ?: selectedTrip.moments.first()

    val selectedCollection: DiscoveryCollection
        get() = collections.find { it.id == selectedCollectionId } ?: collections.first()
}

class JourneyViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(JourneyUiState())
    val uiState: StateFlow<JourneyUiState> = _uiState.asStateFlow()

    fun toggleQuickProfile() {
        _uiState.update { it.copy(isQuickProfileOpen = !it.isQuickProfileOpen) }
    }

    fun closeQuickProfile() {
        _uiState.update { it.copy(isQuickProfileOpen = false) }
    }

    fun toggleMinimalView() {
        _uiState.update { state ->
            val next = !state.isMinimalViewEnabled
            val msg = if (state.language == AppLanguage.VI) {
                if (next) "Đã bật Chế độ xem tối giản" else "Đã tắt Chế độ xem tối giản"
            } else {
                if (next) "Minimal View enabled" else "Minimal View disabled"
            }
            state.copy(isMinimalViewEnabled = next, toastBannerMessage = msg)
        }
    }

    fun setFontScaleStep(step: Int) {
        _uiState.update { it.copy(fontScaleStep = step.coerceIn(0, 2)) }
    }

    fun setCapturedImageUri(uriString: String?) {
        _uiState.update {
            it.copy(
                capturedImageUri = uriString,
                currentScreen = JourneyScreenId.D2_IMAGE_PREVIEW,
                backStack = (it.backStack + it.currentScreen).takeLast(24),
                isQuickProfileOpen = false
            )
        }
    }

    fun updateLiveLocation(coords: String) {
        _uiState.update { it.copy(liveGpsCoordinates = coords, locationPermissionGranted = true) }
    }

    fun navigateTo(screen: JourneyScreenId, clearBackStack: Boolean = false) {
        _uiState.update { state ->
            val updatedStack = if (clearBackStack) {
                emptyList()
            } else if (state.currentScreen != screen) {
                (state.backStack + state.currentScreen).takeLast(24)
            } else {
                state.backStack
            }
            val stepIdx = state.activeFlow?.steps?.indexOf(screen) ?: -1
            state.copy(
                currentScreen = screen,
                backStack = updatedStack,
                isQuickProfileOpen = false,
                activeFlowStepIndex = if (stepIdx >= 0) stepIdx else state.activeFlowStepIndex
            )
        }
    }

    fun navigateBack(): Boolean {
        val current = _uiState.value
        if (current.backStack.isEmpty()) return false
        val previous = current.backStack.last()
        _uiState.update { state ->
            state.copy(
                currentScreen = previous,
                backStack = state.backStack.dropLast(1)
            )
        }
        return true
    }

    fun selectPrimaryTab(tab: PrimaryTab) {
        navigateTo(tab.defaultScreen)
    }

    fun toggleTheme() {
        _uiState.update { it.copy(isDarkTheme = !it.isDarkTheme) }
    }

    fun setTheme(isDark: Boolean) {
        _uiState.update { it.copy(isDarkTheme = isDark) }
    }

    fun toggleLanguage() {
        _uiState.update {
            val next = if (it.language == AppLanguage.EN) AppLanguage.VI else AppLanguage.EN
            it.copy(language = next)
        }
    }

    fun setScreenStateMode(mode: ScreenStateMode) {
        _uiState.update { it.copy(screenStateMode = mode) }
    }

    fun setViewportPreset(preset: ViewportPreset) {
        _uiState.update { it.copy(viewportPreset = preset) }
    }

    fun toggleInspector() {
        _uiState.update { it.copy(isInspectorExpanded = !it.isInspectorExpanded) }
    }

    fun startGuidedFlow(flow: PrototypeFlowSpec) {
        val firstScreen = flow.steps.firstOrNull() ?: JourneyScreenId.B1_HOME_DASHBOARD
        _uiState.update {
            it.copy(
                activeFlow = flow,
                activeFlowStepIndex = 0,
                currentScreen = firstScreen,
                isInspectorExpanded = false,
                screenStateMode = ScreenStateMode.DEFAULT
            )
        }
    }

    fun advanceGuidedFlow() {
        val state = _uiState.value
        val flow = state.activeFlow ?: return
        val nextIndex = state.activeFlowStepIndex + 1
        if (nextIndex < flow.steps.size) {
            val nextScreen = flow.steps[nextIndex]
            _uiState.update {
                it.copy(
                    currentScreen = nextScreen,
                    backStack = (it.backStack + it.currentScreen).takeLast(24),
                    activeFlowStepIndex = nextIndex
                )
            }
        } else {
            showToast(
                if (state.language == AppLanguage.EN) "Completed ${flow.titleEn}"
                else "Đã hoàn thành ${flow.titleVi}"
            )
            _uiState.update { it.copy(activeFlow = null, activeFlowStepIndex = 0) }
        }
    }

    fun exitGuidedFlow() {
        _uiState.update { it.copy(activeFlow = null, activeFlowStepIndex = 0) }
    }

    fun selectPlace(placeId: String, openDetails: Boolean = false) {
        _uiState.update { state ->
            state.copy(
                selectedPlaceId = placeId,
                currentScreen = if (openDetails) JourneyScreenId.C3_PLACE_DETAILS else state.currentScreen,
                backStack = if (openDetails) state.backStack + state.currentScreen else state.backStack
            )
        }
    }

    fun toggleSavePlace(placeId: String) {
        _uiState.update { state ->
            val updated = state.places.map { place ->
                if (place.id == placeId) place.copy(isSaved = !place.isSaved) else place
            }
            val target = updated.find { it.id == placeId }
            val msg = if (target?.isSaved == true) {
                if (state.language == AppLanguage.EN) "Saved to Discovery Atlas" else "Đã lưu vào Bản đồ Khám phá"
            } else {
                if (state.language == AppLanguage.EN) "Removed from Saved Places" else "Đã xóa khỏi mục Đã lưu"
            }
            state.copy(places = updated, toastBannerMessage = msg)
        }
    }

    fun selectTrip(tripId: String, openDetails: Boolean = true) {
        _uiState.update { state ->
            state.copy(
                selectedTripId = tripId,
                currentScreen = if (openDetails) JourneyScreenId.F3_TRIP_DETAIL else state.currentScreen,
                backStack = if (openDetails) state.backStack + state.currentScreen else state.backStack
            )
        }
    }

    fun selectMoment(momentId: String, openDetail: Boolean = true) {
        _uiState.update { state ->
            state.copy(
                selectedMomentId = momentId,
                currentScreen = if (openDetail) JourneyScreenId.F6_MEMORY_DETAIL else state.currentScreen,
                backStack = if (openDetail) state.backStack + state.currentScreen else state.backStack
            )
        }
    }

    fun createNewTrip(title: String, location: String, dateRange: String) {
        if (title.isBlank()) return
        val newId = "trip_${System.currentTimeMillis()}"
        val newTrip = TripJournal(
            id = newId,
            titleEn = title.trim(),
            titleVi = title.trim(),
            dateRange = dateRange.ifBlank { "Nov 20 – Nov 26, 2026" },
            locationEn = location.ifBlank { "Huế • Đà Nẵng • Hội An" },
            locationVi = location.ifBlank { "Huế • Đà Nẵng • Hội An" },
            coverRes = R.drawable.img_hoian_lanterns,
            daysCount = 7,
            momentsCount = 1,
            discoveriesCount = 2,
            storyExcerptEn = "Curated coastal and imperial chronicle created in Journey AI.",
            storyExcerptVi = "Hành trình di sản miền Trung được khởi tạo trên Journey AI.",
            moments = listOf(
                DailyMoment(
                    id = "m_${System.currentTimeMillis()}",
                    dayNumber = 1,
                    timeLabel = "09:00 AM",
                    titleEn = "Departure & First Discovery",
                    titleVi = "Khởi hành & Điểm chạm Đầu tiên",
                    locationTag = location.ifBlank { "Central Vietnam" },
                    coordinates = "16.4637° N, 107.5909° E",
                    noteEn = "Began the journey with AI lens calibration and local heritage download.",
                    noteVi = "Khởi đầu chuyến đi cùng bộ dữ liệu di sản ngoại tuyến.",
                    photoRes = R.drawable.img_hoian_lanterns,
                    weatherTag = "24°C • Clear Sky"
                )
            )
        )
        _uiState.update { state ->
            state.copy(
                trips = listOf(newTrip) + state.trips,
                selectedTripId = newId,
                currentScreen = JourneyScreenId.F3_TRIP_DETAIL,
                backStack = state.backStack + state.currentScreen,
                toastBannerMessage = if (state.language == AppLanguage.EN) "Trip journal created" else "Đã tạo nhật ký chuyến đi mới"
            )
        }
    }

    fun addDailyMoment(title: String, note: String, locationTag: String) {
        if (title.isBlank()) return
        val momentId = "m_${System.currentTimeMillis()}"
        _uiState.update { state ->
            val currentTrip = state.selectedTrip
            val newMoment = DailyMoment(
                id = momentId,
                dayNumber = currentTrip.moments.size + 1,
                timeLabel = "04:45 PM",
                titleEn = title.trim(),
                titleVi = title.trim(),
                locationTag = locationTag.ifBlank { currentTrip.locationEn },
                coordinates = "15.8771° N, 108.3260° E",
                noteEn = note.ifBlank { "Captured golden hour architectural geometry and ambient street sounds." },
                noteVi = note.ifBlank { "Ghi lại khoảnh khắc nắng chiều buông trên mái ngói cổ." },
                photoRes = R.drawable.img_hanoi_temple,
                weatherTag = "23°C • Golden Hour"
            )
            val updatedTrips = state.trips.map { trip ->
                if (trip.id == currentTrip.id) {
                    trip.copy(
                        momentsCount = trip.momentsCount + 1,
                        moments = listOf(newMoment) + trip.moments
                    )
                } else trip
            }
            state.copy(
                trips = updatedTrips,
                selectedMomentId = momentId,
                currentScreen = JourneyScreenId.F4_TRIP_TIMELINE,
                backStack = state.backStack + state.currentScreen,
                toastBannerMessage = if (state.language == AppLanguage.EN) "Moment added to timeline" else "Đã lưu khoảnh khắc vào dòng thời gian"
            )
        }
    }

    fun selectCollection(collectionId: String) {
        _uiState.update { state ->
            state.copy(
                selectedCollectionId = collectionId,
                currentScreen = JourneyScreenId.G2_COLLECTION_DETAILS,
                backStack = state.backStack + state.currentScreen
            )
        }
    }

    fun saveCurrentPlaceToCollection(collectionId: String) {
        _uiState.update { state ->
            val placeId = state.selectedPlaceId
            val updated = state.collections.map { coll ->
                if (coll.id == collectionId && !coll.placeIds.contains(placeId)) {
                    coll.copy(placeIds = coll.placeIds + placeId)
                } else coll
            }
            state.copy(
                collections = updated,
                selectedCollectionId = collectionId,
                currentScreen = JourneyScreenId.G2_COLLECTION_DETAILS,
                backStack = state.backStack + state.currentScreen,
                toastBannerMessage = if (state.language == AppLanguage.EN) "Added discovery to collection" else "Đã thêm khám phá vào bộ sưu tập"
            )
        }
    }

    fun sendChatMessage(input: String) {
        if (input.isBlank()) return
        val userMsg = ChatMessageItem(
            id = "chat_${System.currentTimeMillis()}",
            isUser = true,
            textEn = input.trim(),
            textVi = input.trim(),
            timestamp = "Now"
        )
        val place = _uiState.value.selectedPlace
        val aiReply = ChatMessageItem(
            id = "chat_ai_${System.currentTimeMillis() + 1}",
            isUser = false,
            textEn = "[Design Prototype Sample Response] Regarding ${place.nameEn}: ${place.architecturalNotesEn} In production, this connects to FastAPI `/v1/assistant/stream` with RAG over UNESCO & Vietnam National Heritage archives.",
            textVi = "[Phản hồi Mẫu Bản Thiết kế] Về ${place.nameVi}: ${place.architecturalNotesVi} Khi vận hành thực tế, tính năng này kết nối tới FastAPI `/v1/assistant/stream` cùng cơ sở dữ liệu Di sản Văn hóa.",
            timestamp = "Now",
            referencedPlaceId = place.id
        )
        _uiState.update { state ->
            state.copy(chatMessages = state.chatMessages + userMsg + aiReply)
        }
    }

    fun saveCurrentConversation() {
        _uiState.update { state ->
            val place = state.selectedPlace
            val newConv = SavedConversation(
                id = "conv_${System.currentTimeMillis()}",
                titleEn = "Field Notes: ${place.nameEn}",
                titleVi = "Ghi chú thực địa: ${place.nameVi}",
                dateLabel = "Just now",
                locationTag = place.regionEn,
                messagesCount = state.chatMessages.size,
                previewEn = state.chatMessages.lastOrNull()?.textEn ?: place.summaryEn,
                previewVi = state.chatMessages.lastOrNull()?.textVi ?: place.summaryVi
            )
            state.copy(
                savedConversations = listOf(newConv) + state.savedConversations,
                currentScreen = JourneyScreenId.E3_CONVERSATION_HISTORY,
                backStack = state.backStack + state.currentScreen,
                toastBannerMessage = if (state.language == AppLanguage.EN) "Conversation archived to Journal" else "Đã lưu cuộc trò chuyện vào Lịch sử"
            )
        }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun updateCategoryFilter(filter: String) {
        _uiState.update { it.copy(selectedCategoryFilter = filter) }
    }

    fun togglePermission(type: String) {
        _uiState.update { state ->
            when (type) {
                "CAMERA" -> state.copy(cameraPermissionGranted = !state.cameraPermissionGranted)
                "LOCATION" -> state.copy(locationPermissionGranted = !state.locationPermissionGranted)
                "MIC" -> state.copy(micPermissionGranted = !state.micPermissionGranted)
                else -> state
            }
        }
    }

    fun toggleVoicePlayback() {
        _uiState.update { it.copy(isVoicePlaying = !it.isVoicePlaying) }
    }

    fun cycleVoiceSpeed() {
        _uiState.update {
            val next = when (it.voiceSpeedMultiplier) {
                1.0f -> 1.25f
                1.25f -> 1.5f
                else -> 1.0f
            }
            it.copy(voiceSpeedMultiplier = next)
        }
    }

    fun updateVideoSettings(aspect: String? = null, music: String? = null) {
        _uiState.update {
            it.copy(
                selectedVideoAspect = aspect ?: it.selectedVideoAspect,
                selectedVideoMusic = music ?: it.selectedVideoMusic
            )
        }
    }

    fun selectSubscriptionPlan(planId: String) {
        _uiState.update { state ->
            state.copy(
                selectedPlanTier = planId,
                toastBannerMessage = if (state.language == AppLanguage.EN) "Selected $planId plan" else "Đã chọn gói $planId"
            )
        }
    }

    fun showToast(message: String?) {
        _uiState.update { it.copy(toastBannerMessage = message) }
    }
}
