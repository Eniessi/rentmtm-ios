package com.rentmtm

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.rentmtm.ui.account.MyAccountScreen
import com.rentmtm.ui.budget.BudgetPhotosScreen
import com.rentmtm.ui.budget.BudgetScreen
import com.rentmtm.ui.chat.ChatScreen
import com.rentmtm.ui.chat.SupportChatScreen
import com.rentmtm.ui.forgotpassword.ForgotPasswordScreen
import com.rentmtm.ui.home.HomeScreen
import com.rentmtm.ui.login.LoginScreen
import com.rentmtm.ui.newpassword.NewPasswordScreen
import com.rentmtm.ui.profile.ProfileFlow
import com.rentmtm.ui.request.RequestServiceFlow
import com.rentmtm.ui.search.FindProfessionalsScreen
import com.rentmtm.ui.serviceorder.ServiceOrderScreen
import com.rentmtm.viewmodel.*
import com.rentmtm.ui.signup.SignUpScreen
import com.rentmtm.ui.webview.WebBrowserScreen
import io.ktor.http.decodeURLPart
import io.ktor.http.encodeURLParameter

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
    WebBrowser,
    Home,
    RequestServices,
    Budget,
    BudgetPhotos,
    MyAccount,
    FindProfessionals,
    ChatP2P,
    ServiceOrder,
    SupportChat
}

@Composable
fun App() {
    MaterialTheme(colorScheme = MtmColorScheme) {
        // 1. A Surface agora não tem mais padding. Ela pinta a tela de ponta a ponta (Edge-to-Edge).
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // 2. O Box protege apenas o CONTEÚDO, deixando o fundo livre por trás das barras.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
            ) {
                val navController = rememberNavController()
                val registerViewModel = remember { RegisterViewModel() }
                val budgetViewModel = remember { BudgetViewModel() }
                val searchViewModel = remember { SearchProfessionalsViewModel() }
                val serviceOrderViewModel = remember { ServiceOrderViewModel() }
                val chatViewModel = remember { ChatViewModel() }
                val supportChatViewModel = remember { SupportChatViewModel() }

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

                composable(
                    route = "${Routes.ChatP2P.name}/{serviceId}",
                    arguments = listOf(navArgument("serviceId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val serviceId = backStackEntry.arguments?.getLong("serviceId") ?: 0L

                    // Tech Lead Note: Num cenário real, o chatViewModel chamaria algo como:
                    // LaunchedEffect(serviceId) { chatViewModel.loadChatHistory(serviceId) }

                    ChatScreen(
                        viewModel = chatViewModel,
                        onBack = { navController.popBackStack() } // Volta para a Ordem de Serviço
                    )
                }

                // --- SERVICE ORDER SCREEN ---
                // Esta rota recebe um budgetId para carregar os dados corretos
                composable(
                    route = "${Routes.ServiceOrder.name}/{budgetId}",
                    arguments = listOf(navArgument("budgetId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val budgetId = backStackEntry.arguments?.getLong("budgetId") ?: 0L

                    // Carrega os dados do orçamento para a OS antes de exibir a tela
                    LaunchedEffect(budgetId) {
                        serviceOrderViewModel.loadOrderFromBudget(budgetId)
                    }

                    ServiceOrderScreen(
                        viewModel = serviceOrderViewModel,
                        onBack = { navController.popBackStack() },
                        onOpenChat = { navController.navigate(Routes.ChatP2P.name)}
                    )
                }

                // --- BUDGET SCREEN (PASSO 1: TEXTOS) ---
                composable(route = Routes.Budget.name) {
                    BudgetScreen(
                        viewModel = budgetViewModel,
                        onBack = { navController.popBackStack() },
                        onNextToPhotos = {
                            navController.navigate(Routes.BudgetPhotos.name)
                        }
                    )
                }

                // --- BUDGET PHOTOS SCREEN (PASSO 2: FOTOS) ---
                composable(route = Routes.BudgetPhotos.name) {
                    BudgetPhotosScreen(
                        viewModel = budgetViewModel, //
                        onBack = { navController.popBackStack() },
                        onNext = {
                            budgetViewModel.submitBudgetRequest()

                            // Tech Lead Note: Em produção, pegue o ID real retornado pela persistência.
                            val generatedBudgetId = 999L

                            // Navega para a busca de profissionais levando o ID do orçamento
                            navController.navigate("${Routes.FindProfessionals.name}/$generatedBudgetId")
                        }
                    )
                }

                // --- STEP 3: FIND PROFESSIONALS (RADAR) -> SERVICE ORDER ---
                composable(
                    route = "${Routes.FindProfessionals.name}/{budgetId}",
                    arguments = listOf(navArgument("budgetId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val budgetId = backStackEntry.arguments?.getLong("budgetId") ?: 0L

                    FindProfessionalsScreen(
                        viewModel = searchViewModel, //
                        onBack = { navController.popBackStack() },
                        onProfessionalSelected = { professionalId ->
                            // Ao escolher o profissional, navegamos para gerar a OS
                            // O ideal aqui seria uma chamada de API para vincular o prof ao budget
                            navController.navigate("${Routes.ServiceOrder.name}/$budgetId") {
                                // Limpa a tela de busca da pilha para o usuário não voltar ao radar
                                popUpTo(Routes.FindProfessionals.name) { inclusive = true }
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
                        isLoggedIn = true, // Lembre-se que você mockou isso como true
                        currentRoute = Routes.Home.name,
                        onNavigateToLogin = { navController.navigate(Routes.Login.name) },
                        onLogout = { navController.navigate(Routes.Login.name) { popUpTo(0) } },
                        onNavigateToWeb = { url, title ->
                            val encodedUrl = url.encodeURLParameter()
                            navController.navigate("${Routes.WebBrowser.name}/$encodedUrl/$title")
                        },
                        onNavigateToRequestServices = {
                            navController.navigate(Routes.RequestServices.name)
                        },
                        onNavigateToMyAccount = { // ⬅️ LIGANDO A NAVEGAÇÃO
                            navController.navigate(Routes.MyAccount.name)
                        },
                        onNavigateToSupportChat = {
                            navController.navigate(Routes.SupportChat.name)
                        }
                    )
                }

                composable(route = Routes.SupportChat.name) {
                    SupportChatScreen(
                        viewModel = supportChatViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }

                // --- MY ACCOUNT (CONFIG) ---
                composable(route = Routes.MyAccount.name) {
                    val configViewModel = remember { ConfigViewModel() }
                    MyAccountScreen(
                        viewModel = configViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }

                // --- REQUEST SERVICES ---
                composable(route = Routes.RequestServices.name) {
                    RequestServiceFlow(
                        onBackToHome = { navController.popBackStack() },
                        onNavigateToBudget = { selectedProfession ->
                            // Você pode registrar no BudgetViewModel o selectedProfession aqui antes de navegar
                            navController.navigate(Routes.Budget.name)
                        }
                    )
                }

                // --- STEP 4: SERVICE ORDER (TELA FINAL) ---
                composable(
                    route = "${Routes.ServiceOrder.name}/{budgetId}",
                    arguments = listOf(navArgument("budgetId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val budgetId = backStackEntry.arguments?.getLong("budgetId") ?: 0L

                    LaunchedEffect(budgetId) {
                        serviceOrderViewModel.loadOrderFromBudget(budgetId)
                    }

                    ServiceOrderScreen(
                        viewModel = serviceOrderViewModel,
                        onBack = {
                            navController.navigate(Routes.Home.name) {
                                popUpTo(0)
                            }
                        },
                        onOpenChat = { // ⬅️ Gatilho para abrir a tela de chat
                            // Passamos o budgetId ou serviceOrderId para o chat carregar o contexto correto
                            navController.navigate("${Routes.ChatP2P.name}/$budgetId")
                        }
                    )
                }

                composable(
                    route = "${Routes.WebBrowser.name}/{url}/{title}",
                    arguments = listOf(
                        navArgument("url") { type = NavType.StringType },
                        navArgument("title") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val urlPassed = backStackEntry.arguments?.getString("url") ?: ""
                    val titlePassed = backStackEntry.arguments?.getString("title") ?: "Web Page"

                    val decodedUrl = urlPassed.decodeURLPart()

                    WebBrowserScreen(
                        url = decodedUrl,
                        pageTitle = titlePassed,
                        onBack = { navController.popBackStack() }
                    )
                }

            }
        }
    }
}
}