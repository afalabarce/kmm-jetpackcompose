package dev.afalabarce.kmm.jetpackcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardPager(
    modifier: Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    showPagerIndicator: Boolean = true,
    pagerIndicatorActiveColor: Color = Color.Black,
    pagerIndicatorInactiveColor: Color = Color.Gray,
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    indicatorRadius: Dp = 6.dp,
    pageComposables: Array<@Composable () -> Unit>
) {
    Card(
        modifier = modifier,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        border = border,
        elevation = CardDefaults.cardElevation(elevation),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            val pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f
            ) { pageComposables.size }

            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,

                ) { pagerScope ->
                if (pagerScope in pageComposables.indices) {
                    pageComposables[pagerScope]()
                }
            }
            if (showPagerIndicator) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier.wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp),
                    activeColor = pagerIndicatorActiveColor,
                    inactiveColor = pagerIndicatorInactiveColor,
                    indicatorRadius = indicatorRadius
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier,
    activeColor: Color,
    inactiveColor: Color,
    indicatorRadius: Dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) activeColor else inactiveColor
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(indicatorRadius)
            )
        }
    }
}

@Preview
@Composable
private fun CardPagerPreview() {
    MaterialTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                CardPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .padding(vertical = 4.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    pageComposables = arrayOf(
                        {
                            Text(text = "Page 1.1")
                        },
                        {
                            Text(text = "Page 1.2")
                        },
                        {
                            Text(text = "Page 1.3")
                        }
                    )
                )

                CardPager(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .padding(vertical = 4.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    pagerIndicatorActiveColor = Color.Red,
                    pagerIndicatorInactiveColor = Color.Cyan,
                    pageComposables = arrayOf(
                        {
                            Text(text = "Page 2.1")
                        },
                        {
                            Text(text = "Page 2.2")
                        },
                        {
                            Text(text = "Page 2.3")
                        }
                    )
                )
            }

        }
    }
}