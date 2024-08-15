package com.example.sportsground.ui.parallax

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Parallax3(modifier: Modifier = Modifier) {
    val state = rememberPagerState { imageList.size }
    val offsetFromStart = state.offsetForPage(0).absoluteValue

    val lazyListState = rememberLazyListState()
    HorizontalPager(state = state, contentPadding = PaddingValues(horizontal = 64.dp)) {
        Card(
            modifier = Modifier
//                .padding(8.dp)
                .aspectRatio( 1f)
                .graphicsLayer {
                    val pageOffset = state.offsetForPage(it).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also {
                        scaleX = it
                        scaleY = it
                    }

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Image(
                painter = painterResource(id = imageList[it]),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Stable
class ParallaxAlignment(
    private val horizontalBias: () -> Float = { 0f },
    private val verticalBias: () -> Float = { 0f },
) : Alignment {
    override fun align(
        size: IntSize,
        space: IntSize,
        layoutDirection: LayoutDirection,
    ): IntOffset {

        val centerX = (space.width - size.width).toFloat() / 2f
        val centerY = (space.height - size.height).toFloat() / 2f
        val resolvedHorizontalBias = if (layoutDirection == LayoutDirection.Ltr) {
            horizontalBias()
        } else {
            -1 * horizontalBias()
        }


        val x = centerX * (1 + resolvedHorizontalBias)
        val y = centerY * (1 + verticalBias())

        return IntOffset(x.roundToInt(), y.roundToInt())
    }

}
