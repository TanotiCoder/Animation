package com.example.sportsground

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sportsground.data.AppContainer
import com.example.sportsground.ui.grounds.cricket.CricketGround
import com.example.sportsground.ui.grounds.footballGround.FootballGround
import com.example.sportsground.ui.home.HomeRoute
import com.example.sportsground.ui.home.HomeViewModel
import com.example.sportsground.ui.theme.SportsGroundTheme

@Composable
fun GroundApp(appContainer: AppContainer) {
    SportsGroundTheme {
        val navController = rememberNavController()
        val coroutineScope = rememberCoroutineScope()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute =
            navBackStackEntry?.destination?.route ?: "home"

        GroundNavGraph(appContainer = appContainer)

    }
}

@Composable
fun GroundNavGraph(
    appContainer: AppContainer,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable(
            route = "home"
        ) {
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(groundRepository = appContainer.groundList)
            )
            HomeRoute(homeViewModel = homeViewModel) {
                navController.navigate(it.toString())
            }
        }

        composable(route = "1") {
            FootballGround()
        }
        composable("2") {
            CricketGround()
        }
    }
}