package com.rentmtm.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rentmtm.ui.register.*
import com.rentmtm.viewmodel.RegisterViewModel

/**
 * Maestro da navegação para o fluxo de Inquilino / Usuário Comum.
 */
fun NavGraphBuilder.registerUserNavGraph(
    navController: NavHostController,
    viewModel: RegisterViewModel
) {
    // Agrupamos o fluxo sob a rota "RegisterUser"
    navigation(
        startDestination = RegisterRoutes.STEP_2,
        route = "RegisterUser"
    ) {
        // --- STEP 2: Personal & Identity Details ---
        composable(RegisterRoutes.STEP_2) {
            RegisterUserStep2Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(RegisterRoutes.STEP_3) }
            )
        }

        // --- STEP 3: Address Information ---
        composable(RegisterRoutes.STEP_3) {
            RegisterUserStep3Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(RegisterRoutes.STEP_4) }
            )
        }

        // --- STEP 4: Identity & Security (Instructions) ---
        composable(RegisterRoutes.STEP_4) {
            RegisterUserStep4Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(RegisterRoutes.STEP_5) }
            )
        }

        // --- STEP 5: File Submission Selection ---
        composable(RegisterRoutes.STEP_5) {
            RegisterUserStep5Screen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNext = {
                    // Antes de ir para o sucesso, podemos chamar o save
                    viewModel.finalizeRegistration()
                    navController.navigate(RegisterRoutes.SUCCESS)
                },
                onCompleteLater = {
                    // Navega direto para a Home sem passar pelo Sucesso agora
                    navController.navigate("Home") {
                        popUpTo("RegisterUser") { inclusive = true }
                    }
                }
            )
        }

        // --- SUCCESS SCREEN ---
        composable(RegisterRoutes.SUCCESS) {
            WelcomeSuccessScreen(
                viewModel = viewModel,
                onFinish = {
                    // Limpa a pilha do registro e vai para a Home
                    navController.navigate("Home") {
                        popUpTo("RegisterUser") { inclusive = true }
                    }
                }
            )
        }
    }
}