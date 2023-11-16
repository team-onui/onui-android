package app.junsu.onui_android.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import app.junsu.onui.R

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val lighten2: Color
    @Composable get() = colorResource(id = R.color.lighten_2)
val lighten1: Color
    @Composable get() = colorResource(id = R.color.lighten_1)
val default: Color
    @Composable get() = colorResource(id = R.color.Default)
val darken1: Color
    @Composable get() = colorResource(id = R.color.darken_1)
val darken2: Color
    @Composable get() = colorResource(id = R.color.darken_2)

val gray0: Color
    @Composable get() = colorResource(id = R.color.gray_0)
val gray3: Color
    @Composable get() = colorResource(id = R.color.gray_3)
val gray5: Color
    @Composable get() = colorResource(id = R.color.gray_5)
val gray7: Color
    @Composable get() = colorResource(id = R.color.gray_7)
val gray10: Color
    @Composable get() = colorResource(id = R.color.gray_10)

val errorLight2: Color
    @Composable get() = colorResource(id = R.color.error_lighten_2)
val errorLight1: Color
    @Composable get() = colorResource(id = R.color.error_lighten_1)
val errorDefault: Color
    @Composable get() = colorResource(id = R.color.error_default)
val errorDarken1: Color
    @Composable get() = colorResource(id = R.color.error_darken_1)
val errorDarken2: Color
    @Composable get() = colorResource(id = R.color.error_darken_2)

val primary: Color
    @Composable get() = colorResource(id = R.color.color_primary)

val onPrimary: Color
    @Composable get() = colorResource(id = R.color.color_on_primary)

val primaryContainer: Color
@Composable get() = colorResource(id = R.color.color_primary_container)

val onPrimaryContainer: Color
    @Composable get() = colorResource(id = R.color.color_on_primary_container)

val error: Color
    @Composable get() = colorResource(id = R.color.color_error)

val onError: Color
    @Composable get() = colorResource(id = R.color.color_on_error)

val errorContainer: Color
    @Composable get() = colorResource(id = R.color.color_error_container)

val onErrorContainer: Color
    @Composable get() = colorResource(id = R.color.color_on_error_container)

val background: Color
    @Composable get() = colorResource(id = R.color.background)
val onBackground: Color
    @Composable get() = colorResource(id = R.color.color_on_background)
val surface: Color
    @Composable get() = colorResource(id = R.color.color_surface)
val onSurface: Color
    @Composable get() = colorResource(id = R.color.color_on_surface)
val backgroundVariant: Color
    @Composable get() = colorResource(id = R.color.color_background_variant)
val onBackgroundVariant: Color
    @Composable get() = colorResource(id = R.color.color_on_background_variant)
val surfaceVariant: Color
    @Composable get() = colorResource(id = R.color.color_surface_variant)
val onSurfaceVariant: Color
    @Composable get() = colorResource(id = R.color.color_on_surface_variant)
val outline: Color
    @Composable get() = colorResource(id = R.color.color_outline)
val outlineVariant: Color
    @Composable get() = colorResource(id = R.color.color_outline_variant)