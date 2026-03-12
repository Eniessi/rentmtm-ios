package com.rentmtm.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rentmtm.ui.register.*
import com.rentmtm.viewmodel.RegisterViewModel

/**
 * Função de extensão que "pluga" o fluxo de registro profissional no NavHost principal.
 */
fun NavGraphBuilder.professionalRegistrationGraph(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {
    // Agrupamos tudo sob a rota "RegisterProfessional" (definida no Enum Routes)
    navigation(
        startDestination = RegisterRoutes.STEP_2,
        route = "RegisterProfessional"
    ) {
        composable(RegisterRoutes.STEP_2) {
            RegisterUserProfStep2Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(RegisterRoutes.STEP_3) }
            )
        }

        composable(RegisterRoutes.STEP_3) {
            RegisterUserProfStep3Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(RegisterRoutes.STEP_4) }
            )
        }

        composable(RegisterRoutes.STEP_4) {
            RegisterUserProfStep4Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(RegisterRoutes.STEP_5) }
            )
        }

        composable(RegisterRoutes.STEP_5) {
            RegisterUserProfStep5Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(RegisterRoutes.STEP_6) }
            )
        }

        composable(RegisterRoutes.STEP_6) {
            RegisterUserProfStep6Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(RegisterRoutes.STEP_7) }
            )
        }

        composable(RegisterRoutes.STEP_7) {
            RegisterUserProfStep7Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(RegisterRoutes.STEP_8) }
            )
        }

        composable(RegisterRoutes.STEP_8) {
            RegisterUserProfStep8Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = {
                    // Salva no banco e vai para o sucesso
                    viewModel.finalizeRegistration()
                    navController.navigate(RegisterRoutes.SUCCESS)
                }
            )
        }

        composable(RegisterRoutes.SUCCESS) {
            WelcomeSuccessScreen(
                viewModel = viewModel,
                onFinish = {
                    // Limpa o fluxo de registro e vai para a Home
                    navController.navigate("Home") {
                        popUpTo("RegisterProfessional") { inclusive = true }
                    }
                }
            )
        }
    }
}