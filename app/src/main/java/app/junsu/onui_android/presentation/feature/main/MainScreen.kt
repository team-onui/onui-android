package app.junsu.onui_android.presentation.feature.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.junsu.onui.R
import app.junsu.onui_android.presentation.navigation.AppNavigationItem
import app.junsu.onui_android.presentation.ui.theme.background
import app.junsu.onui_android.presentation.ui.theme.backgroundVariant
import app.junsu.onui_android.presentation.ui.theme.headline2
import app.junsu.onui_android.presentation.ui.theme.headline3
import app.junsu.onui_android.presentation.ui.theme.onBackground
import app.junsu.onui_android.presentation.ui.theme.onBackgroundVariant
import app.junsu.onui_android.presentation.ui.theme.onPrimaryContainer
import app.junsu.onui_android.presentation.ui.theme.onSurface
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.primaryContainer
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.surfaceVariant
import app.junsu.onui_android.presentation.ui.theme.title2
import app.junsu.onui_android.presentation.ui.theme.title3
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {

    Scaffold(
        bottomBar = {
            BottomAppBar(navController = navController)
        }
    ) {
        BottomSheetScaffold(navController)
    }
}

@Composable
fun BottomAppBar(navController: NavController) {
    BottomAppBar(
        containerColor = surface,
        contentColor = onSurfaceVariant,
        actions = { },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppNavigationItem.Remind.route)
                },
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
fun BottomSheetScaffold(navController: NavController) {
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
                LastDays(navController = navController)
                TaskScreen()
            }
        },
        sheetPeekHeight = 220.dp,
        containerColor = backgroundVariant,
        sheetSwipeEnabled = true,
        sheetContainerColor = background,
        sheetDragHandle = { },
    ) {
        BottomSheetContent(navController = navController)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomSheetContent(navController: NavController) {
    val pagerContent = listOf(
        R.drawable.background_green,
        R.drawable.background_pink,
        R.drawable.background_yellow,
        R.drawable.background_gray,
    )
    val pagerState = rememberPagerState()
    Column(modifier = Modifier.padding(12.dp)) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White)
        ) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxHeight(0.15f)
                    .clip(RoundedCornerShape(32.dp))
                    .fillMaxWidth(),
                count = pagerContent.size,
                state = pagerState,
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(id = pagerContent[it]),
                    contentDescription = "pagerContent",
                    contentScale = ContentScale.Crop,
                )
            }
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 16.dp),
                pagerState = pagerState,
                activeColor = onBackground,
                inactiveColor = onBackgroundVariant,
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    OnuiSmallCard(
                        title = "한눈에",
                        subTitle = "달력",
                        image = R.drawable.mdi_calendar_blank,
                        onClick = {
                            navController.navigate(AppNavigationItem.Calendar.route)
                        }
                    )
                    OnuiSmallCard(
                        title = "하나씩",
                        subTitle = "과제",
                        image = R.drawable.ic_sharp_task_alt,
                        onClick = { }
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                OnuiBigCard(
                    title = "떡 꾸미기",
                    subTitle = "햇님 방앗간",
                    image = R.drawable.sun,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(AppNavigationItem.SunStore.route)
                        }
                        .fillMaxWidth(0.5f)
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f)) {
                OnuiBigCard(
                    title = "앱 꾸미기",
                    subTitle = "달님 만물상",
                    image = R.drawable.moon,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(AppNavigationItem.MoonStore.route)
                        }
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(32.dp))
                            .background(surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mdi_chart_donut),
                            contentDescription = "chart",
                            alignment = Alignment.Center
                        )
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(32.dp))
                            .background(surface)
                            .clickable {
                                navController.navigate(AppNavigationItem.Settings.route)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_baseline_settings),
                            contentDescription = "setting",
                            alignment = Alignment.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                OnuiSmallCard(
                    title = "나누기",
                    subTitle = "게시판",
                    image = R.drawable.mdi_message_text_fast_outline,
                    onClick = {
                        navController.navigate(AppNavigationItem.Timeline.route)
                    }
                )
            }
        }
    }
}

@Composable
fun OnuiBigCard(
    title: String,
    subTitle: String,
    image: Int,
    modifier: Modifier,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = surface,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp,
                )
            ) {
                Text(
                    text = title,
                    color = surfaceVariant,
                    style = title2,
                )
                Text(
                    text = subTitle,
                    color = onSurface,
                    style = headline2,
                )
            }
            Image(
                modifier = Modifier.align(Alignment.BottomEnd),
                painter = painterResource(id = image),
                contentDescription = "img",
            )
        }
    }
}


@Composable
fun OnuiSmallCard(
    title: String,
    subTitle: String,
    image: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(32.dp))
            .aspectRatio(2.1f)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = surface),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(
                        start = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp,
                    )
            ) {
                Text(
                    text = title,
                    color = surfaceVariant,
                    style = title3,
                )
                Text(
                    text = subTitle,
                    color = onSurface,
                    style = headline3,
                )
            }
            Image(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(start = 12.dp, end = 16.dp),
                painter = painterResource(id = image),
                contentDescription = "intro",
            )
        }
    }
    Spacer(modifier = Modifier.size(8.dp))
}