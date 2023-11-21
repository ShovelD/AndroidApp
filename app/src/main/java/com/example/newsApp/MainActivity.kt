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
                    AboutAppComposable {
                        navController.navigate("HomeScreen") {
                            popUpTo("HomeScreen") {
                                this.inclusive = true
                            }
                        }
                    }
                }

                composable(
                    "EditScreen",
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
                    EditComposable {
                        navController.navigate("HomeScreen") {
                            popUpTo("HomeScreen") {
                                inclusive = true
                            }
                        }
                    }
                }
            }
        }
    }
}