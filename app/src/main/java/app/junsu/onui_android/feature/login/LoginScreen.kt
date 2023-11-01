package app.junsu.onui_android.feature.login

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.junsu.onui.R
import app.junsu.onui_android.ui.theme.background
import app.junsu.onui_android.ui.theme.headline2
import app.junsu.onui_android.ui.theme.headline3
import app.junsu.onui_android.ui.theme.onBackground
import app.junsu.onui_android.ui.theme.onBackgroundVariant
import app.junsu.onui_android.ui.theme.onSurface
import app.junsu.onui_android.ui.theme.onSurfaceVariant
import app.junsu.onui_android.ui.theme.surface
import app.junsu.onui_android.ui.theme.surfaceVariant
import app.junsu.onui_android.ui.theme.title2
import app.junsu.onui_android.ui.theme.title3
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
) {
    val pagerState = rememberPagerState()
    val context = LocalContext.current
    val pager = listOf("a", "b", "c", "d")
    var code: String? by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
    ) {
        Box(
            modifier = Modifier.fillMaxHeight(0.7f),
            contentAlignment = Alignment.Center
        ) {
            Column {
                HorizontalPager(
                    state = pagerState,
                    count = pager.count(),
                    modifier = Modifier
                        .fillMaxHeight(0.7f),
                    contentPadding = PaddingValues(horizontal = 52.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(32.dp))
                            .fillMaxSize(0.9f)
                            .background(Color(0xFFD9D9D9)),
                    )
                }
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = onBackground,
                    inactiveColor = onBackgroundVariant,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
        ) {
            OnuiStart(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.size(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                OnuiIntro(
                    title = "Google",
                    subTitle = "인증하기",
                    image = R.drawable.google,
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://accounts.google.com/o/oauth2/v2/auth?client_id=797489065606-1nnd5031dnc8ljrbrbgkba4rar2a6pbe.apps.googleusercontent.com&redirect_uri=https://onui.kanghyuk.co.kr/auth/oauth/google/token&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile")
                        )
                        context.startActivity(intent)
                    },
                )
                OnuiIntro(
                    title = "서비스",
                    subTitle = "소개",
                    image = R.drawable.open,
                    onClick = { },
                )
            }
        }
    }
}

@Composable
fun OnuiStart(modifier: Modifier) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(32.dp))
            .aspectRatio(1f),
        colors = CardDefaults.cardColors(
            containerColor = surfaceVariant,
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
                    text = "시작하기",
                    color = onSurfaceVariant,
                    style = title2,
                )
                Text(
                    text = "오누이",
                    color = surface,
                    style = headline2,
                )
            }
            Image(
                modifier = Modifier
                    .padding(end = 16.dp, bottom = 12.dp)
                    .align(Alignment.BottomEnd),
                painter = painterResource(id = R.drawable.onui),
                contentDescription = "logo",
            )
        }
    }
}

@Composable
fun OnuiIntro(
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
        colors = CardDefaults.cardColors(
            containerColor = surface,
        ),
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