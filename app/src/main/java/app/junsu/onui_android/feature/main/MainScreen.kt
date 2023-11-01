package app.junsu.onui_android.feature.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.junsu.onui.R
import app.junsu.onui_android.component.LastDays
import app.junsu.onui_android.component.TaskScreen
import app.junsu.onui_android.ui.theme.background
import app.junsu.onui_android.ui.theme.backgroundVariant
import app.junsu.onui_android.ui.theme.onBackground
import app.junsu.onui_android.ui.theme.onPrimaryContainer
import app.junsu.onui_android.ui.theme.onSurfaceVariant
import app.junsu.onui_android.ui.theme.primaryContainer
import app.junsu.onui_android.ui.theme.surface
import app.junsu.onui_android.ui.theme.title1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    Scaffold(
        bottomBar = {
            BottomAppBar()
        }
    ) {
        BottomSheetScaffold()
    }
}

@Composable
fun BottomAppBar() {
    BottomAppBar(
        containerColor = surface,
        contentColor = onSurfaceVariant,
        actions = { },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScaffold() {
    val sheetState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = sheetState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.83f)
                    .padding(12.dp)
            ) {
                LastDays()
                TaskScreen()
            }
        },
        sheetPeekHeight = 240.dp,
        containerColor = backgroundVariant,
        sheetSwipeEnabled = true,
        sheetContainerColor = background,
        sheetDragHandle = { },
    ) {
        BottomSheetContent()
    }
}

@Composable
fun BottomSheetContent() {
    Column(modifier = Modifier.padding(12.dp)) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.15f)
                .clip(RoundedCornerShape(32.dp))
                .fillMaxWidth()
                .background(Color.White)
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.tip),
                style = title1,
                color = onBackground
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .clip(RoundedCornerShape(32.dp))
                .fillMaxWidth()
                .background(Color.White)
                .padding(24.dp)
        ) {
            Text(
                style = title1,
                text = stringResource(id = R.string.timeline),
                color = onBackground,
            )
        }
    }
}