package dev.samyak.unagi.compose.components

import android.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.gigamole.navigationtabstrip.NavigationTabStrip

@Composable
fun CustomTabLayout(
    color: Int = Color.RED,
    tabNavigator: (Int) -> Unit
) {
    val context = AmbientContext.current
    val selectedPage = remember { mutableStateOf(0) }
    val tabTitleSize = with(AmbientDensity.current) {
        12.dp.toPx()
    }

    val customView = remember {
        NavigationTabStrip(context).apply {
            setTitles("About", "Episodes")
            setTabIndex(0, true)
            stripType = NavigationTabStrip.StripType.LINE
            stripColor = color
            titleSize = tabTitleSize
            stripGravity = NavigationTabStrip.StripGravity.BOTTOM
            animationDuration = 200
            onTabStripSelectedIndexListener = object :
                NavigationTabStrip.OnTabStripSelectedIndexListener {
                override fun onStartTabSelected(title: String?, index: Int) {}

                override fun onEndTabSelected(title: String?, index: Int) {
                    selectedPage.value = index
                }

            }
        }
    }

    Column(modifier = Modifier.height(50.dp).fillMaxWidth()) {
        AndroidView({ customView })
        tabNavigator(selectedPage.value)
    }
}