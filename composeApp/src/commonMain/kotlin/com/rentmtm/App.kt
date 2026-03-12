package com.rentmtm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

// Imports de UI e Navigation
import com.rentmtm.navigation.professionalRegistrationGraph
import com.rentmtm.navigation.registerUserNavGraph
import com.rentmtm.ui.forgotpassword.ForgotPasswordScreen
import com.rentmtm.ui.home.HomeScreen
import com.rentmtm.ui.login.LoginScreen
import com.rentmtm.ui.newpassword.NewPasswordScreen
import com.rentmtm.ui.profile.ProfileFlow
import com.rentmtm.viewmodel.*
import com.rentmtm.ui.signup.SignUpScreen

// Definição das Cores
val AzulP = Color(0xFF004AAD)
val AzulS = Color(0xEB0B93F2)
val AzulT = Color(0xFF223060)

val MtmColorScheme = lightColorScheme(
    primary = AzulP,
    secondary = AzulS,
    tertiary = AzulT
)

// Enum de Rotas Principais
enum class Routes {
    Login,
    SignUp,
    ProfileSelection,
    RegisterProfessional,
    RegisterUser,
    ForgotPassword,
    NewPassword,
    Home
}

@Composable
fun App() {
    MaterialTheme(colorScheme = MtmColorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            val registerViewModel = remember { RegisterViewModel() }

            NavHost(
                navController = navController,
                startDestination = Routes.Login.name
            ) {

                // --- LOGIN ---
                composable(route = Routes.Login.name) {
                    LoginScreen(
                        onNavigateToSignUp = { navController.navigate(Routes.SignUp.name) },
                        onNavigateToForgotPassword = { navController.navigate(Routes.ForgotPassword.name) },
                        onLoginSuccess = { navController.navigate(Routes.Home.name) }
                    )
                }

                // --- SIGN UP ---
                composable(route = Routes.SignUp.name) {
                    SignUpScreen(
                        onNavigateToLogin = { navController.popBackStack() },
                        onNavigateToProfile = { navController.navigate(Routes.ProfileSelection.name) }
                    )
                }

                // --- PROFILE SELECTION ---
                composable(route = Routes.ProfileSelection.name) {
                    ProfileFlow(
                        onNavigateToNextStep = { perfilEscolhido ->
                            // 1. Mapeamos a escolha para o Enum e definimos o destino
                            when (perfilEscolhido) {
                                "Professional Allocated" -> {
                                    registerViewModel.updateProfileType(ProfileType.PROFESSIONAL)
                                    navController.navigate(Routes.RegisterProfessional.name)
                                }

                                // 2. Para QUALQUER outro perfil, usamos o fluxo de RegisterUser
                                else -> {
                                    // Mapeamos o tipo específico para o WelcomeSuccessScreen saber o que exibir
                                    val type = when (perfilEscolhido) {
                                        "Tenant / Renter" -> ProfileType.TENANT
                                        "Property Owner" -> ProfileType.OWNER
                                        "Partner" -> ProfileType.PARTNER
                                        "Buyer" -> ProfileType.BUYER
                                        "Seller" -> ProfileType.SELLER
                                        else -> ProfileType.NONE
                                    }

                                    registerViewModel.updateProfileType(type)
                                    // Navega para o grafo que criamos (RegisterUserNavGraph)
                                    navController.navigate(Routes.RegisterUser.name)
                                }
                            }
                        }
                    )
                }

                // --- NESTED REGISTRATION GRAPH ---
                // Injetamos aqui todas as telas (Step 2 ao Success)
                professionalRegistrationGraph(
                    navController = navController,
                    viewModel = registerViewModel
                )

                registerUserNavGraph(
                    navController = navController,
                    viewModel = registerViewModel
                )

                // --- FORGOT PASSWORD ---
                composable(route = Routes.ForgotPassword.name) {
                    ForgotPasswordScreen(
                        onBackToLogin = { navController.popBackStack() },
                        onNavigateToNewPassword = { emailDigitado ->
                            navController.navigate("${Routes.NewPassword.name}/$emailDigitado")
                        }
                    )
                }

                // --- NEW PASSWORD ---
                composable(
                    route = "${Routes.NewPassword.name}/{email}",
                    arguments = listOf(navArgument("email") { type = NavType.StringType })
                ) { backStackEntry ->
                    val emailPassado = backStackEntry.arguments?.getString("email") ?: ""
                    NewPasswordScreen(
                        emailReceived = emailPassado,
                        onPasswordChanged = {
                            navController.navigate(Routes.Login.name) { popUpTo(0) }
                        }
                    )
                }

                // --- HOME ---
                composable(route = Routes.Home.name) {
                    HomeScreen(
                        userEmail = "usuario@mtm.com",
                        isLoggedIn = true,
                        currentRoute = Routes.Home.name,
                        onNavigateToLogin = { navController.navigate(Routes.Login.name) },
                        onLogout = {
                            navController.navigate(Routes.Login.name) { popUpTo(0) }
                        }
                    )
                }

            }
        }
    }
}