package app.junsu.onui_android.presentation.feature.login

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import app.junsu.onui.R
import app.junsu.onui_android.data.api.ApiProvider
import app.junsu.onui_android.presentation.navigation.AppNavigationItem
import app.junsu.onui_android.presentation.ui.theme.background
import app.junsu.onui_android.presentation.ui.theme.body3
import app.junsu.onui_android.presentation.ui.theme.headline2
import app.junsu.onui_android.presentation.ui.theme.headline3
import app.junsu.onui_android.presentation.ui.theme.onBackground
import app.junsu.onui_android.presentation.ui.theme.onBackgroundVariant
import app.junsu.onui_android.presentation.ui.theme.onSurface
import app.junsu.onui_android.presentation.ui.theme.onSurfaceVariant
import app.junsu.onui_android.presentation.ui.theme.surface
import app.junsu.onui_android.presentation.ui.theme.surfaceVariant
import app.junsu.onui_android.presentation.ui.theme.title1
import app.junsu.onui_android.presentation.ui.theme.title2
import app.junsu.onui_android.presentation.ui.theme.title3
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    val pagerContent = listOf(
        "귀여운 떡을 더욱 예쁘게",
        "직관적인 사용자 경험",
        "기록한 하루를 한 눈에",
        "하루를 적어내려가는",
    )
    val pagerTitle = listOf(
        "상점", "전체", "달력", "기록"
    )
    val viewModel: LoginViewModel = viewModel()
    var token by remember { mutableStateOf("") }
    val sharedPreferences = context.getSharedPreferences("my_shared_prefs", Context.MODE_PRIVATE)

    /**
     * Retrieves a GoogleSignInClient with default sign-in options including email access.
     *
     * @return A configured GoogleSignInClient instance.
     */
    fun getGoogleClient(): GoogleSignInClient {
        val googleSignInOption =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        return GoogleSignIn.getClient(context, googleSignInOption)
    }

    /**
     * A launcher for Google Sign-In activity result handling. It processes the result and performs
     * the necessary actions, such as logging in and updating the authentication token.
     *
     * @param result The result of the Google Sign-In activity.
     */
    val googleAuthLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java)
                if (account.id != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.login(name = account.givenName!!, sub = account.id!!)
                        delay(300)
                        sharedPreferences
                            .edit()
                            .putString("token", viewModel.token)
                            .apply()
                        token = viewModel.token
                    }
                }
            } catch (e: ApiException) {
                Log.e("fail", e.stackTraceToString())
            }
        }

    /**
     * Initiates the Google Sign-In process. Signs out the user if already signed in and launches
     * the Google Sign-In activity.
     */
    fun requestGoogleLogin() {
        val googleSignInClient: GoogleSignInClient by lazy { getGoogleClient() }
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        googleAuthLauncher.launch(signInIntent)
    }


    LaunchedEffect(token) {
        if (token.isNotBlank()) {
            navController.navigate(AppNavigationItem.Main.route) { popUpTo(0) }
        }
    }
    val list = listOf(R.drawable.img, R.drawable.img_1, R.drawable.img_2, R.drawable.img_3)

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
                    count = pagerContent.count(),
                    modifier = Modifier.fillMaxHeight(0.7f),
                    verticalAlignment = Alignment.Bottom,
                    contentPadding = PaddingValues(horizontal = 52.dp),
                ) {
                    if (pagerState.currentPage == it) {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(32.dp))
                                .fillMaxSize()
                                .background(surface)
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = pagerContent[it],
                                style = body3,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                text = pagerTitle[it],
                                style = title1,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                            Image(
                                painter = painterResource(id = list[it]),
                                contentDescription = "image",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(32.dp))
                                .fillMaxSize(0.8f)
                                .background(surface),
                        ) {
                            Image(
                                painter = painterResource(id = list[it]),
                                contentDescription = "image",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
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
            OnuiStart(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        CoroutineScope(Dispatchers.IO).launch {
                            kotlin
                                .runCatching {
                                    ApiProvider
                                        .loginApi()
                                        .getToken()
                                }
                                .onSuccess {
                                    token = it
                                    sharedPreferences
                                        .edit()
                                        .putString("token", token)
                                        .apply()
                                }
                                .onFailure {
                                    Log.d("it", it.toString())
                                }
                        }
                    }
            )
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
                    onClick = { requestGoogleLogin() },
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