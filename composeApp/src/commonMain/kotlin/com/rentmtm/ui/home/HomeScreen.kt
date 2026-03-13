package com.rentmtm.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.Close

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
    // 1. NOVO PARÂMETRO: Função para abrir a webview (recebe URL e Título)
    onNavigateToWeb: (String, String) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var showFab by remember { mutableStateOf(true) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                windowInsets = WindowInsets(0, 0, 0, 0)
            ) {
                // CABEÇALHO DA DRAWER
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.size(80.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = userEmail.take(1).uppercase(),
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Hi, $userEmail",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

                // OPÇÃO: LOGIN
                if (!isLoggedIn) {
                    NavigationDrawerItem(
                        label = { Text("Login", fontFamily = FontFamily.Serif, fontSize = 16.sp) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            onNavigateToLogin()
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                if (currentRoute != "Home") {
                    NavigationDrawerItem(
                        label = { Text("Home", fontFamily = FontFamily.Serif, fontSize = 16.sp) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            showFab = true
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                // 2. NOVAS OPÇÕES: ABRIR WEBVIEWS
                NavigationDrawerItem(
                    label = { Text("RentMTM", fontFamily = FontFamily.Default, fontSize = 16.sp, color = MaterialTheme.colorScheme.tertiary) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        // IMPORTANTE: Troque pelo link real da sua aplicação web!
                        onNavigateToWeb("http://www.rentmtm.com/", "RentMTM")
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("My MarketPlace", fontFamily = FontFamily.Default, fontSize = 16.sp, color = MaterialTheme.colorScheme.tertiary) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        // IMPORTANTE: Troque pelo link real do seu marketplace!
                        onNavigateToWeb("http://www.rentmtm.com/tourRentMTM", "My MarketPlace")
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                // OPÇÕES GENÉRICAS RESTANTES
                val menuOptions = listOf("My Account", "Lilo Virtual Assistent", "Work With Us", "Prof. Allocated Area", "Talk to us")

                menuOptions.forEach { option ->
                    NavigationDrawerItem(
                        label = { Text(option, fontFamily = FontFamily.Default, fontSize = 16.sp, color = MaterialTheme.colorScheme.tertiary) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (option == "Lilo Virtual Assistent") {
                                showFab = false
                            } else {
                                showFab = true
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (isLoggedIn) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary)
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .padding(16.dp)
                    ) {
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = "Logout",
                                    fontFamily = FontFamily.Default,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                                onLogout()
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            ),
                            modifier = Modifier.padding(0.dp)
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
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            floatingActionButton = {
                if (showFab) {
                    Box(modifier = Modifier.size(80.dp)) {

                        FloatingActionButton(
                            onClick = { /* TODO: Abrir o chat da Lilo */ },
                            containerColor = Color.Transparent,
                            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
                            modifier = Modifier.align(Alignment.BottomStart)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.logo_lilo_ai),
                                contentDescription = "Lilo Virtual Assistant",
                                modifier = Modifier.size(70.dp)
                            )
                        }

                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.TopEnd)
                                .clickable {
                                    showFab = false
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remover Lilo",
                                modifier = Modifier.padding(4.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.logomtm),
                    contentDescription = "Month To Month Logo",
                    modifier = Modifier.height(130.dp).fillMaxWidth(0.95f)
                )
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
            onNavigateToWeb = { _, _ -> } // Preview falso
        )
    }
}