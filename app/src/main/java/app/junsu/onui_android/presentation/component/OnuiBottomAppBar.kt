package app.junsu.onui_android.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import app.junsu.onui.R
import app.junsu.onui_android.presentation.ui.theme.onPrimaryContainer
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.primaryContainer
import app.junsu.onui_android.presentation.ui.theme.surface

@Composable
fun OnuiBottomAppBar() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = surface,
                contentColor = onSurfaceVariant,
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "calendar",
                        )
                    }
                    IconButton(onClick = {  }) {
                        Icon(
                            painter = painterResource(id = R.drawable.timeline),
                            contentDescription = "timeline",
                        )
                    }
                    IconButton(onClick = {  }) {
                        Icon(
                            painter = painterResource(id = R.drawable.analytics),
                            contentDescription = "analytics",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {  },
                        containerColor = primaryContainer,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                        contentColor = onPrimaryContainer,
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "add",
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Text(
            modifier = Modifier.padding(innerPadding),
            text = "Example of a scaffold with a bottom app bar."
        )
    }
}