package com.example.aufgabe3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.aufgabe3.navigation.AppNavHost
import com.example.aufgabe3.ui.theme.Aufgabe3Theme

/**
 * MainActivity is the entry point, setting up the UI with Jetpack Compose and managing navigation with `AppNavHost`.
 */
class MainActivity : ComponentActivity() {
    /**
     * This method is called when the activity is created.
     * It sets the content of the activity and configures the UI.
     * @param savedInstanceState The saved state of the activity, used to restore UI state if necessary.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Aufgabe3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}

