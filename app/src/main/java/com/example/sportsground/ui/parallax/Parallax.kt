package com.example.sportsground.ui.parallax


import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.sportsground.R
import kotlin.math.roundToInt

val imageList = listOf(
    R.drawable.pic_1,
    R.drawable.pic_2,
    R.drawable.pic_3,
    R.drawable.pic_4,
    R.drawable.pic_5,
)

// Padding values
private val cardPadding = 25.dp
private val imagePadding = 10.dp

// Shadow and shape values for the card
private val shadowElevation = 15.dp
private val borderRadius = 15.dp
private val shape = RoundedCornerShape(borderRadius)

// Offset for the parallax effect
private val xOffset = cardPadding.value * 2

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Parallax() {
    // Get screen dimensions and density
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val density = LocalDensity.current.density

    Log.d(
        "screen dimensions ",
        "screenWidth = $screenWidth , screenHeight = $screenHeight, density = $density"
    )

    val pagerState = rememberPagerState { imageList.size }

    val pagerHeight = screenHeight / 1.5f


    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(pagerHeight),
    ) { page ->

        val parallaxOffset = pagerState.getOffsetFractionForPage(page) * screenWidth.value

        Log.i("offset", parallaxOffset.toString())
//        ImageFrame(image = imageList[page])
        ParallaxCarouselItem(
            imageList[page],
            parallaxOffset,
            pagerHeight,
            screenWidth,
            density
        )
    }
}


private fun ImageBitmap.calculateDrawSize(
    density: Float,
    screenWidth: Dp,
    pagerHeight: Dp
): IntSize {
    val originalImageWidth = width / density
    val originalImageHeight = height / density

    val frameAspectRatio = screenWidth / pagerHeight
    val imageAspectRatio = originalImageWidth / originalImageHeight

    val drawWidth = xOffset + if (frameAspectRatio > imageAspectRatio) {
        screenWidth.value
    } else {
        pagerHeight.value * imageAspectRatio
    }

    val drawHeight = if (frameAspectRatio > imageAspectRatio) {
        screenWidth.value / imageAspectRatio
    } else {
        pagerHeight.value
    }

    return IntSize(drawWidth.toIntPx(density), drawHeight.toIntPx(density))
}

// Extension function to convert Float to Int in pixels
private fun Float.toIntPx(density: Float) = (this * density).roundToInt()


@Composable
fun ParallaxCarouselItem(
    imageResId: Int,
    parallaxOffset: Float,
    pagerHeight: Dp,
    screenWidth: Dp,
    density: Float,
) {
    // Load the image bitmap
    val imageBitmap = ImageBitmap.imageResource(id = imageResId)

    // Calculate the draw size for the image
    val drawSize = imageBitmap.calculateDrawSize(density, screenWidth, pagerHeight)

    // Card composable for the item
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(cardPadding)
            .background(Color.White, shape)
            .shadow(elevation = shadowElevation, shape = shape)
    ) {
        // Canvas for drawing the image with parallax effect
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(imagePadding)
                .clip(shape)
        ) {
            // Translate the canvas for parallax effect
            translate(left = parallaxOffset * density) {
                // Draw the image
                drawImage(
                    image = imageBitmap,
                    srcSize = IntSize(imageBitmap.width, imageBitmap.height),
                    dstOffset = IntOffset(-xOffset.toIntPx(density), 0),
                    dstSize = drawSize,
                )
            }
        }
    }
}