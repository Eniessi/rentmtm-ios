package com.rentmtm.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun ProfileFlow(onNavigateToNextStep: (String) -> Unit) {

    val viewingInfoForState = remember { mutableStateOf<ProfileOption?>(null) }

    if (viewingInfoForState.value == null) {
        ProfileSelectionScreen(
            onNextClicked = { selectedTitle ->
                onNavigateToNextStep(selectedTitle)
            },
            onNavigateToInfo = { profileTitle ->
                val clickedProfile = profileOptions.find { it.title == profileTitle }
                viewingInfoForState.value = clickedProfile
            }
        )
    } else {
        val profile = viewingInfoForState.value!!
        val description = profileDescriptionsMap[profile.title] ?: ProfileDescription(
            summary = "Detalhes não encontrados.", features = emptyList()
        )

        ProfileInfoScreen(
            profile = profile,
            description = description,
            onBack = { viewingInfoForState.value = null },
            onNext = {
                onNavigateToNextStep(profile.title)
            }
        )
    }
}