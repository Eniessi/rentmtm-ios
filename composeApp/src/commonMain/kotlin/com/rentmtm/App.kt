package com.rentmtm

import InMemoryBudgetRepository
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.rentmtm.ui.inbox.ProfessionalInboxScreen
import com.rentmtm.ui.login.LoginScreen
import com.rentmtm.ui.newpassword.NewPasswordScreen
import com.rentmtm.ui.profile.ProfileFlow
import com.rentmtm.ui.request.RequestServiceFlow
import com.rentmtm.ui.search.FindProfessionalsScreen
import com.rentmtm.ui.serviceorder.ServiceOrderScreen
import com.rentmtm.ui.serviceorder.ServiceTimelineScreen
import com.rentmtm.viewmodel.*
import com.rentmtm.ui.signup.SignUpScreen
import com.rentmtm.ui.webview.WebBrowserScreen
import io.ktor.client.request.request
import io.ktor.http.decodeURLPart
import io.ktor.http.encodeURLParameter
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.ic_logo_mtm_color

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
    SupportChat,
    CustomerReview,
    ProfessionalReview,
    ReviewSuccess,
    ServiceTimeline,
    ProfessionalInbox,
    StartingExecution
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
                modifier = Modifier.fillMaxSize()
            ) {
                val budgetRepository = remember { InMemoryBudgetRepository() }
                val navController = rememberNavController()
                val registerViewModel = remember { RegisterViewModel() }
                val budgetViewModel = remember { BudgetViewModel(budgetRepository) }
                val searchViewModel = remember { SearchProfessionalsViewModel(budgetRepository) }
                val serviceOrderViewModel = remember { ServiceOrderViewModel() }
                val chatViewModel = remember { ChatViewModel() }
                val supportChatViewModel = remember { SupportChatViewModel() }
                val loginViewModel = remember { LoginViewModel() }
                val inboxViewModel = remember { ProfessionalInboxViewModel(budgetRepository) }

                NavHost(
                    navController = navController,
                    startDestination = Routes.Login.name
                ) {
                // --- LOGIN ---
                composable(route = Routes.Login.name) {
                    LoginScreen(
                        viewModel = loginViewModel,
                        onNavigateToSignUp = { navController.navigate( Routes.SignUp.name) },
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
                    val serviceId = backStackEntry.arguments?.let { NavType.LongType.get(it, "serviceId") } ?: 0L

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
                    val budgetId = backStackEntry.arguments?.let { NavType.LongType.get(it, "budgetId") } ?: 0L

                    // Carrega os dados do orçamento para a OS antes de exibir a tela
                    LaunchedEffect(budgetId) {
                        serviceOrderViewModel.loadOrderFromBudget(budgetId)
                    }

                    ServiceOrderScreen(
                        viewModel = serviceOrderViewModel,
                        onBack = { navController.popBackStack() },
                        onOpenChat = { navController.navigate("${Routes.ChatP2P.name}/$budgetId") },
                        onNavigateToReview = { isCustomer, orderId ->
                            if (isCustomer) {
                                navController.navigate("${Routes.StartingExecution.name}/$orderId")
                            } else {
                                navController.navigate("${Routes.ServiceTimeline.name}/$orderId")
                            }
                        }
                    )
                }

                // --- TELA DE TRANSIÇÃO (SPLASH / INICIALIZAÇÃO DA OS) ---
                composable(
                    route = "${Routes.StartingExecution.name}/{orderId}",
                    arguments = listOf(navArgument("orderId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val orderId = backStackEntry.arguments?.let { NavType.LongType.get(it, "orderId") } ?: 0L

                    // 1. Controle de Estado
                    var startAnimation by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

                    // 2. A Coreografia no Compose (Idêntica ao XML nativo)
                    val scale by animateFloatAsState(
                        targetValue = if (startAnimation) 1f else 0.6f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy, // Controla o "quique" (elasticidade)
                            stiffness = Spring.StiffnessLow // Control a "rigidez" (mais baixo = mais lento e fluido)
                        ),
                        label = "LogoScale"
                    )

                    val alpha by androidx.compose.animation.core.animateFloatAsState(
                        targetValue = if (startAnimation) 1f else 0f,
                        animationSpec = androidx.compose.animation.core.tween(
                            durationMillis = 750,
                            easing = androidx.compose.animation.core.FastOutSlowInEasing
                        ),
                        label = "LogoAlpha"
                    )

                    // 3. O Gatilho e o Salto para a Timeline
                    LaunchedEffect(Unit) {
                        startAnimation = true
                        kotlinx.coroutines.delay(3000) // Tempo que o cliente fica vendo a animação
                        navController.navigate("${Routes.ServiceTimeline.name}/$orderId") {
                            popUpTo(Routes.StartingExecution.name) { inclusive = true }
                        }
                    }

                    // 4. A UI com Fundo Azul
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary), // Seu fundo AzulP
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                            // Usamos a versão ESTÁTICA copiada para o composeResources
                            Image(
                                painter = org.jetbrains.compose.resources.painterResource(
                                    Res.drawable.ic_logo_mtm_color
                                ),
                                contentDescription = "RentMTM Logo",
                                modifier = Modifier
                                    .size(288.dp) // Mantemos o tamanho do quadro original
                                    .graphicsLayer(
                                        scaleX = scale,
                                        scaleY = scale,
                                        alpha = alpha
                                    )
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            Text(
                                text = "Initializing Service Order...",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White // Texto branco para contrastar com o fundo azul
                            )
                            Text(
                                text = "Connecting with professional",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                }

                // ⬅️ ROTA DA TELA DE TIMELINE
                composable(
                    route = "${Routes.ServiceTimeline.name}/{orderId}",
                    arguments = listOf(navArgument("orderId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val orderId = backStackEntry.arguments?.let { NavType.LongType.get(it, "orderId") } ?: 0L
                    val timelineViewModel = remember { ServiceTimelineViewModel() }

                    LaunchedEffect(orderId) {
                        timelineViewModel.loadServiceTimeline(orderId)
                    }

                    ServiceTimelineScreen(
                        viewModel = timelineViewModel,
                        onBack = { navController.popBackStack() },
                        onNavigateHome = { navController.navigate(Routes.Home.name) },
                        onOpenChat = { id ->
                            navController.navigate("${Routes.ChatP2P.name}/$id")
                        },
                        onNavigateToReview = { id ->
                            // Como estamos testando o fluxo do Cliente, chamamos o CustomerReview
                            navController.navigate("${Routes.CustomerReview.name}/$id")
                        }
                    )
                }

                // --- TELA DE AVALIAÇÃO DO CLIENTE ---
                composable(
                    route = "${Routes.CustomerReview.name}/{orderId}",
                    arguments = listOf(navArgument("orderId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val orderId = backStackEntry.arguments?.let { NavType.LongType.get(it, "orderId") } ?: 0L

                    // Nota: O ViewModel deveria ser injetado/lembrado corretamente aqui
                    val reviewViewModel = remember { com.rentmtm.viewmodel.ServiceReviewViewModel() }

                    LaunchedEffect(Unit) { reviewViewModel.loadServiceDetails(orderId) }

                    com.rentmtm.ui.review.ServiceReviewScreen(
                        viewModel = reviewViewModel,
                        onBack = { navController.popBackStack() },
                        onFinish = {
                            navController.navigate(Routes.ReviewSuccess.name) {
                                popUpTo(Routes.CustomerReview.name) { inclusive = true }
                            }
                        }
                    )
                }

                // --- TELA DE AVALIAÇÃO DO PROFISSIONAL ---
                composable(
                    route = "${Routes.ProfessionalReview.name}/{orderId}",
                    arguments = listOf(navArgument("orderId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val orderId = backStackEntry.arguments?.let { NavType.LongType.get(it, "orderId") } ?: 0L

                    val profViewModel = remember { com.rentmtm.viewmodel.ProfessionalReviewViewModel() }

                    LaunchedEffect(Unit) { profViewModel.loadOrderData(orderId) }

                    com.rentmtm.ui.review.ProfessionalReviewScreen(
                        viewModel = profViewModel,
                        onBack = { navController.popBackStack() },
                        onFinish = {
                            navController.navigate(Routes.ReviewSuccess.name) {
                                popUpTo(Routes.ProfessionalReview.name) { inclusive = true }
                            }
                        }
                    )
                }

                // --- TELA DE SUCESSO FINAL ---
                composable(route = Routes.ReviewSuccess.name) {
                    com.rentmtm.ui.review.ReviewSuccessScreen(
                        onFinish = {
                            // Volta para a Home limpando a pilha de navegação
                            navController.navigate(Routes.Home.name) {
                                popUpTo(0)
                            }
                        }
                    )
                }

                // --- BUDGET SCREEN ---
                composable(
                    route = "${Routes.Budget.name}?budgetId={budgetId}",
                    arguments = listOf(
                        navArgument("budgetId") {
                            type = NavType.StringType
                            nullable = true
                            defaultValue = null
                        }
                    )
                ) { backStackEntry ->
                    val budgetId = backStackEntry.arguments?.let { NavType.StringType.get(it, "budgetId") }?.toLongOrNull()

                    LaunchedEffect(budgetId) {
                        budgetViewModel.initializeBudget(budgetId)
                    }

                    BudgetScreen(
                        viewModel = budgetViewModel,
                        onBack = { navController.popBackStack() },
                        onNextToPhotos = {
                            if (budgetId == null) {
                                budgetViewModel.submitBudgetRequest()
                            }
                            navController.navigate(Routes.BudgetPhotos.name)
                        },
                        // EVENTO: O Profissional enviou o preço
                        onQuoteSent = { sentBudgetId ->
                            // Navega para a OS para ele ficar aguardando o cliente aceitar
                            navController.navigate("${Routes.FindProfessionals.name}/$sentBudgetId") {
                                popUpTo(Routes.Home.name)
                            }
                        },
                        // EVENTO: O Cliente aceitou o preço
                        onAcceptQuote = { newOrderId ->
                            // Navega para a OS para acompanhar a execução
                            navController.navigate("${Routes.ServiceOrder.name}/$newOrderId") {
                                popUpTo(Routes.Home.name)
                            }
                        }
                    )
                }

                // --- BUDGET PHOTOS SCREEN (PASSO 2: FOTOS) ---
                composable(route = Routes.BudgetPhotos.name) {
                    BudgetPhotosScreen(
                        viewModel = budgetViewModel,
                        onBack = { navController.popBackStack() },
                        onNext = {
                            // Observe o ID gerado e navegue
                            val id = budgetViewModel.createdBudgetId.value ?: 101L
                            navController.navigate("${Routes.FindProfessionals.name}/$id")
                        }
                    )
                }

                // --- STEP 3: FIND PROFESSIONALS (RADAR) -> SERVICE ORDER ---
                composable(
                    route = "${Routes.FindProfessionals.name}/{budgetId}",
                    arguments = listOf(navArgument("budgetId") { type = NavType.LongType })
                ) { backStackEntry ->
                    val budgetId = backStackEntry.arguments?.let { NavType.LongType.get(it, "budgetId") } ?: 0L

                    LaunchedEffect(budgetId) {
                        searchViewModel.checkForBudgetResponse(budgetId)
                    }

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
                    val emailPassado = backStackEntry.arguments?.let { NavType.StringType.get(it, "email") } ?: ""
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
                        },
                        onNavigateToProfAllocatedArea = {
                            navController.navigate(Routes.ProfessionalInbox.name)
                        }
                    )
                }

                composable(route = Routes.ProfessionalInbox.name) {
                    // 1. A MÁGICA ACONTECE AQUI
                    // Toda vez que o usuário navegar para esta rota, este bloco é executado
                    LaunchedEffect(Unit) {
                        inboxViewModel.loadIncomingRequests()
                    }

                    ProfessionalInboxScreen(
                        viewModel = inboxViewModel,
                        onBudgetSelected = { budgetId ->
                            navController.navigate("${Routes.Budget.name}?budgetId=$budgetId")
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

                composable(
                    route = "${Routes.WebBrowser.name}/{url}/{title}",
                    arguments = listOf(
                        navArgument("url") { type = NavType.StringType },
                        navArgument("title") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val urlPassed = backStackEntry.arguments?.let { NavType.StringType.get(it, "url") } ?: ""
                    val titlePassed = backStackEntry.arguments?.let { NavType.StringType.get(it, "title") } ?: ""

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