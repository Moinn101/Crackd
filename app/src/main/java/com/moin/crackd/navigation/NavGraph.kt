package com.moin.crackd.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moin.crackd.ui.screens.GoogleSignInScreen
import com.moin.crackd.ui.screens.MainScreen
import com.moin.crackd.ui.screens.SplashScreen
import com.moin.crackd.ui.screens.UserFormScreen
import com.moin.crackd.ui.screens.ProfileScreen

object Routes {
    const val Splash = "splash"
    const val Login = "login"
    const val Form = "form"
    const val Main = "main"
    const val Profile = "profile"
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.Splash) {
        composable(Routes.Splash) { SplashScreen(navController) }
        composable(Routes.Login) { GoogleSignInScreen(navController) }
        composable(Routes.Form) { UserFormScreen(navController) }
        composable(Routes.Main) { MainScreen(navController) }
        composable(Routes.Profile) { ProfileScreen(navController) }
    }
}