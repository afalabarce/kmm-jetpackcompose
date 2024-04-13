package dev.afalabarce.kmm.jetpackcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScaffoldWizard(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    snackBarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    previousButtonColors: ButtonColors = ButtonDefaults.buttonColors(),
    previousButtonShape: Shape = MaterialTheme.shapes.medium,
    previousButtonElevation: ButtonElevation? = null,
    previousButtonBorder: BorderStroke? = null,
    previousButtonContent: @Composable () -> Unit,
    nextButtonColors: ButtonColors = ButtonDefaults.buttonColors(),
    nextButtonShape: Shape = MaterialTheme.shapes.medium,
    nextButtonElevation: ButtonElevation? = null,
    nextButtonBorder: BorderStroke? = null,
    nextButtonContent: @Composable () -> Unit,
    finishButtonContent: @Composable () -> Unit,
    onPrevious: () -> Unit = {},
    onNext: () -> Unit = {},
    onFinish: () -> Unit = {},
    pagerIndicatorActiveColor: Color = MaterialTheme.colorScheme.primary,
    pagerIndicatorInactiveColor: Color = MaterialTheme.colorScheme.secondary,
    pagerIndicatorRadius: Dp = 4.dp,
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    bottomBarPaddingValues: PaddingValues = PaddingValues(),
    contentPaddingValues: PaddingValues = PaddingValues(),
    pages: List<@Composable () -> Unit>,
) {
    var previousEnabled by remember { mutableStateOf(false) }
    var nextEnabled by remember { mutableStateOf(true) }
    var addPage by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(0) {
        pages.size
    }

    previousEnabled = pagerState.currentPage != 0
    nextEnabled = pagerState.currentPage != pages.size - 1

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        snackbarHost = snackBarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottomBarPaddingValues),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(
                    modifier = Modifier.padding(horizontal = 6.dp).weight(0.4f),
                    enabled = previousEnabled,
                    colors = previousButtonColors,
                    shape = previousButtonShape,
                    elevation = previousButtonElevation,
                    border = previousButtonBorder,
                    onClick = {
                        if (previousEnabled) {
                            addPage = -1
                            onPrevious()
                        }

                    }
                ) {
                    previousButtonContent()
                }

                Button(
                    modifier = Modifier.padding(horizontal = 6.dp).weight(0.4f),
                    colors = nextButtonColors,
                    shape = nextButtonShape,
                    elevation = nextButtonElevation,
                    border = nextButtonBorder,
                    onClick = {
                        if (nextEnabled) {
                            addPage = 1
                            onNext()
                        } else {
                            onFinish()
                        }
                    }
                ) {
                    if (nextEnabled)
                        nextButtonContent()
                    else
                        finishButtonContent()
                }
            }
        }
    ) { containerPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(containerPaddingValues)
                .padding(contentPaddingValues),
        ) {


            HorizontalPager(
                modifier = Modifier.weight(1f),
                userScrollEnabled = true,
                state = pagerState,
            ) {
                if (!pagerState.isScrollInProgress) {
                    addPage = 0
                    pages[pagerState.currentPage]()
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(bottom = 4.dp).align(Alignment.CenterHorizontally),
                activeColor = pagerIndicatorActiveColor,
                inactiveColor = pagerIndicatorInactiveColor,
                indicatorRadius = pagerIndicatorRadius
            )

            LaunchedEffect(addPage) {
                pagerState.animateScrollToPage(pagerState.currentPage + addPage)
            }
        }
    }
}

@Preview
@Composable
fun ScaffoldWizardPreview() {
    ScaffoldWizard(
        modifier = Modifier.fillMaxSize(),
        previousButtonContent = { Text("Previous") },
        nextButtonContent = { Text("Next") },
        finishButtonContent = { Text("Finish") },
        pagerIndicatorRadius = 8.dp,
        pages = listOf(
            { Text("Page 1") },
            { Text("Page 2") },
        )
    )
}