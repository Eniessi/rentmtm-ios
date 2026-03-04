package com.rentmtm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.rentmtm.ui.forgotpassword.ForgotPasswordScreen
import com.rentmtm.ui.home.HomeScreen
import com.rentmtm.ui.login.LoginScreen
import com.rentmtm.ui.newpassword.NewPasswordScreen
import com.rentmtm.ui.profile.ProfileSelectionScreen
import com.rentmtm.ui.signup.SignUpScreen

val AzulP = Color(0xFF004AAD)
val AzulS = Color(0xEB0B93F2)
val AzulT = Color(0xFF223060)

val MtmColorScheme = lightColorScheme(
    primary = AzulP,
    secondary = AzulS,
    tertiary = AzulT
)

enum class Routes {
    Login,
    SignUp,
    ProfileSelection,
    ForgotPassword,
    NewPassword,
    Home
}

@Composable
fun App() {
    MaterialTheme(
        colorScheme = MtmColorScheme
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Routes.Login.name
            ) {

                composable(route = Routes.Login.name) {
                    LoginScreen(
                        onNavigateToSignUp = { navController.navigate(Routes.SignUp.name) },
                        onNavigateToForgotPassword = { navController.navigate(Routes.ForgotPassword.name) },
                        onLoginSuccess = { navController.navigate(Routes.Home.name) }
                    )
                }

                composable(route = Routes.SignUp.name) {
                    SignUpScreen(
                        onNavigateToLogin = { navController.popBackStack() },
                        onNavigateToProfile = { navController.navigate(Routes.ProfileSelection.name) }
                    )
                }

                composable(route = Routes.ProfileSelection.name) {
                    ProfileSelectionScreen(
                        onNextClicked = { perfilEscolhido ->
                            println("Perfil selecionado: $perfilEscolhido")
                            navController.navigate(Routes.Home.name)
                        }
                    )
                }

                composable(route = Routes.ForgotPassword.name) {
                    ForgotPasswordScreen(
                        onBackToLogin = { navController.popBackStack() },

                        onNavigateToNewPassword = { emailDigitado ->
                            navController.navigate("${Routes.NewPassword.name}/$emailDigitado")
                        }
                    )
                }

                composable(
                    route = "${Routes.NewPassword.name}/{email}",
                    arguments = listOf(navArgument("email") { type = NavType.StringType })
                ) { backStackEntry ->

                    val emailPassado = backStackEntry.arguments?.getString("email") ?: ""

                    NewPasswordScreen(
                        emailReceived = emailPassado,
                        onPasswordChanged = {
                            navController.navigate(Routes.Login.name) {
                                popUpTo(0)
                            }
                        }
                    )
                }

                composable(route = Routes.Home.name) {
                    HomeScreen(
                        userEmail = "usuario@mtm.com",
                        isLoggedIn = true,
                        currentRoute = Routes.Home.name,
                        onNavigateToLogin = { navController.navigate(Routes.Login.name) },
                        onLogout = {
                            navController.navigate(Routes.Login.name) {
                                popUpTo(0)
                            }
                        }
                    )
                }
            }
        }
    }
}