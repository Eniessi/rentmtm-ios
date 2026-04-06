package com.rentmtm.ui.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.model.TestimonialUiModel
import com.rentmtm.ui.home.components.TestimonialCard
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userEmail: String,
    isLoggedIn: Boolean,
    currentRoute: String,
    onNavigateToLogin: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToWeb: (String, String) -> Unit,
    onNavigateToRequestServices: () -> Unit,
    onNavigateToMyAccount: () -> Unit,
    onNavigateToSupportChat: () -> Unit,
    onNavigateToProfAllocatedArea: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showFab by remember { mutableStateOf(true) }

    // 1. Dados Mockados com os nomes da sua imagem da Web
    val testimonials = remember {
        listOf(
            TestimonialUiModel(1, "Emily Martinez", "Houston, Texas", Res.drawable.avatar_1, 5, "I needed tools for a DIY project, and the process was quick and easy. The platform was super user-friendly!"),
            TestimonialUiModel(2, "Sarah Johnson", "Austin, Texas", Res.drawable.avatar_3, 5, "I rented a car for a weekend trip, and the process was so smooth. Highly recommend RentMTM for fast rentals."),
            TestimonialUiModel(3, "Ema Davis", "Dallas, Texas", Res.drawable.avatar_2, 5, "Renting a home for a short stay was a breeze. The platform was user-friendly, and the house was exactly as described. Highly recommend!"),
            TestimonialUiModel(4, "Michael S.", "Tenant", Res.drawable.avatar_4, 4, "RentMTM completely changed how I find equipment for my weekend projects. Safe, fast, and reliable!")
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(windowInsets = WindowInsets(0, 0, 0, 0)) {
                // CABEÇALHO DA DRAWER
                Column(
                    modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary).windowInsetsPadding(WindowInsets.statusBars).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(80.dp)) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = userEmail.take(1).uppercase(), fontSize = 32.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Hi, $userEmail", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onPrimary)
                }

                HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

                // MENU ITEMS
                if (!isLoggedIn) NavigationDrawerItem(label = { Text("Login", fontFamily = FontFamily.Serif, fontSize = 16.sp) }, selected = false, onClick = { scope.launch { drawerState.close() }; onNavigateToLogin() }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))
                if (currentRoute != "Home") NavigationDrawerItem(label = { Text("Home", fontFamily = FontFamily.Serif, fontSize = 16.sp) }, selected = false, onClick = { scope.launch { drawerState.close() }; showFab = true }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))

                NavigationDrawerItem(label = { Text("RentMTM", fontSize = 16.sp, color = MaterialTheme.colorScheme.tertiary) }, selected = false, onClick = { scope.launch { drawerState.close() }; onNavigateToWeb("http://www.rentmtm.com/", "RentMTM") }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))
                NavigationDrawerItem(label = { Text("My MarketPlace", fontSize = 16.sp, color = MaterialTheme.colorScheme.tertiary) }, selected = false, onClick = { scope.launch { drawerState.close() }; onNavigateToWeb("http://www.rentmtm.com/tourRentMTM", "My MarketPlace") }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))

                if (isLoggedIn) NavigationDrawerItem(label = { Text("My Account", fontSize = 16.sp, color = MaterialTheme.colorScheme.tertiary) }, selected = false, onClick = { scope.launch { drawerState.close() }; onNavigateToMyAccount() }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding))

                val menuOptions = listOf("Request Services", "Lilo Virtual Assistent", "Work With Us", "Prof. Allocated Area", "Talk to us")
                menuOptions.forEach { option ->
                    NavigationDrawerItem(
                        label = { Text(option, fontSize = 16.sp, color = MaterialTheme.colorScheme.tertiary) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            when (option) {
                                "Request Services" -> onNavigateToRequestServices()
                                "Talk to us", "Lilo Virtual Assistent" -> onNavigateToSupportChat()
                                "Prof. Allocated Area" -> onNavigateToProfAllocatedArea()
                                else -> showFab = true
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (isLoggedIn) {
                    Column(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary).windowInsetsPadding(WindowInsets.navigationBars).padding(16.dp)) {
                        NavigationDrawerItem(
                            label = { Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondary) },
                            selected = false, onClick = { scope.launch { drawerState.close() }; onLogout() }, colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Home", fontWeight = FontWeight.Bold) },
                    navigationIcon = { IconButton(onClick = { scope.launch { drawerState.open() } }) { Icon(Icons.Default.Menu, "Menu") } },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, navigationIconContentColor = MaterialTheme.colorScheme.onPrimary)
                )
            },
            floatingActionButton = {
                if (showFab) {
                    Box(modifier = Modifier.size(80.dp)) {
                        FloatingActionButton(onClick = onNavigateToSupportChat, containerColor = Color.Transparent, elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp), modifier = Modifier.align(Alignment.BottomStart)) {
                            Image(painter = painterResource(Res.drawable.logo_lilo_ai), contentDescription = "Lilo Virtual Assistant", modifier = Modifier.size(70.dp))
                        }
                        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.size(20.dp).align(Alignment.TopEnd).clickable { showFab = false }) {
                            Icon(Icons.Default.Close, "Remove Lilo", modifier = Modifier.padding(4.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        ) { paddingValues ->

            // CONTEÚDO PRINCIPAL ROLÁVEL
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ==================================================
                // SEÇÃO 1: HERO IMAGE & DESCRIÇÃO
                // ==================================================
                Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
                    Image(
                        painter = painterResource(Res.drawable.hero_bg),
                        contentDescription = "Cityscape Background",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF001F3F).copy(alpha = 0.6f)))

                    Column(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Welcome to RentMTM", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "The ultimate ecosystem for renting, buying, selling, and hiring verified professionals.",
                            fontSize = 16.sp, color = Color.White.copy(alpha = 0.9f), textAlign = TextAlign.Center, lineHeight = 24.sp
                        )
                    }
                }

                // ==================================================
                // SEÇÃO 2: LOGOTIPO (Banda de Contexto)
                // ==================================================
                // Adicionamos um fundo sutil e um título de autoridade para o logo não parecer "voando"
                Surface(
                    color = Color(0xFFF8F9FA), // Cinza muito claro
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "YOUR TRUSTED PLATFORM",
                            color = Color.Gray,
                            fontSize = 12.sp,
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(Res.drawable.logomtm),
                            contentDescription = "Month To Month Logo",
                            modifier = Modifier
                                .height(110.dp) // Reduzido levemente para encaixar melhor
                                .fillMaxWidth(0.9f),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                // ==================================================
                // SEÇÃO 3: CARROSSEL COM EFEITO DE SOBREPOSIÇÃO (OVERLAP)
                // ==================================================
                Box(modifier = Modifier.fillMaxWidth()) {

                    // 3.1 - A Imagem de Fundo (Apenas na metade superior do Box)
                    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                        Image(
                            painter = painterResource(Res.drawable.testimonial_bg),
                            contentDescription = "Testimonial Background",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        // Overlay ajustado: azul clássico (0xFF003366) com transparência ideal para ver a foto
                        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF003366).copy(alpha = 0.75f)))

                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 48.dp, start = 24.dp, end = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "What Customers Are Saying",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Don't just take our word for it—hear from those who've rented with us!",
                                fontSize = 14.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // 3.2 - O Carrossel (Posicionado para quebrar a linha da imagem)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 160.dp) // Este padding empurra o carrossel para baixo, causando o Overlap
                    ) {
                        val pagerState = rememberPagerState(pageCount = { testimonials.size })

                        HorizontalPager(
                            state = pagerState,
                            contentPadding = PaddingValues(horizontal = 32.dp),
                            pageSpacing = 16.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) { page ->
                            TestimonialCard(
                                testimonial = testimonials[page],
                                modifier = Modifier.height(300.dp) // Altura controlada para o texto não vazar
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Indicadores
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(testimonials.size) { iteration ->
                                val isSelected = pagerState.currentPage == iteration
                                val width by animateDpAsState(targetValue = if (isSelected) 24.dp else 8.dp)
                                val color = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray

                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .height(8.dp)
                                        .width(width)
                                        .clip(CircleShape)
                                        .background(color)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(48.dp)) // Respiro final antes do fim da tela
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            userEmail = "usuario@mtm.com",
            isLoggedIn = true,
            currentRoute = "Home",
            onNavigateToLogin = {},
            onLogout = {},
            onNavigateToWeb = { _, _ -> }, // Preview falso
            onNavigateToRequestServices = {},
            onNavigateToMyAccount = {},
            onNavigateToSupportChat = {},
            onNavigateToProfAllocatedArea = {}
        )
    }
}