package com.example.newsApp
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsApp.screens.AboutAppComposable
import com.example.newsApp.screens.EditComposable
import com.example.newsApp.screens.HomeScreen
import com.example.newsApp.screens.Overview
import com.example.newsApp.screens.SiteComposable
import com.example.newsApp.ui.theme.AppTheme
import com.example.newsApp.viewmodels.OverviewMode
import com.google.gson.GsonBuilder
import java.util.UUID


class MainActivity : ComponentActivity() {

    companion object {
        val aboutDeveloper = listOf(
            R.string.app_name,
            R.string.developer_name,
            R.string.developer_email
        )
        val AcmeFont = FontFamily(
            Font(R.font.acme_regular)
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(true) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "HomeScreen"
                ) {
                    composable(
                        "HomeScreen",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                                animationSpec = tween(400)
                            )
                        },

                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                                animationSpec = tween(400)
                            )
                        }
                    ) {
                        HomeScreen(navController)
                    }

                    composable(
                        "AboutAppScreen",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                                animationSpec = tween(400)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                                animationSpec = tween(400)
                            )
                        }
                    ) {
                        AboutAppComposable(navController)
                    }

                    composable(
                        "EditScreen?id={id}",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                                animationSpec = tween(400)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                                animationSpec = tween(400)
                            )
                        }
                    ) { navBackStackEntry ->
                        val idString = navBackStackEntry.arguments?.getString("id")
                        val converter = GsonBuilder().create()
                        val id = converter.fromJson(idString, UUID::class.java)
                        EditComposable(navController, id = id)
                    }
                    composable(
                        "SiteScreen",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                                animationSpec = tween(400)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                                animationSpec = tween(400)
                            )
                        }
                    ) {
                        SiteComposable(navController)
                    }
                    composable(
                        "Overview?id={id},mode={mode}",
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                                animationSpec = tween(400)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                                animationSpec = tween(400)
                            )
                        }
                    ) { navBackStackEntry ->
                        val idString = navBackStackEntry.arguments?.getString("id")
                        val converter = GsonBuilder().create()
                        val id = converter.fromJson(idString, UUID::class.java)

                        val modeString = navBackStackEntry.arguments?.getString("mode")
                        val mode = converter.fromJson(modeString,OverviewMode::class.java)
                        Overview(navController, id = id, mode = mode)
                    }
                }
            }
        }
    }
}