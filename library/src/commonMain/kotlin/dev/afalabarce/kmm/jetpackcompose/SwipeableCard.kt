//@file:OptIn(ExperimentalFoundationApi::class)

package dev.afalabarce.kmm.jetpackcompose
/*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Woman
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import dev.afalabarce.kmm.jetpackcompose.extensions.systemDensity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

data class SwipeAction(
    val order: Int,
    val key: String,
    val title: String,
    val imageVector: ImageVector,
    val color: Color,
    val tint: Color,
    val dockLeft: Boolean
)

private fun actionStateValue(swipeActions: Array<SwipeAction>): Int {
    var returnValue = 0

    val withLeftActions = swipeActions.any { x -> x.dockLeft }
    val withRightActions = swipeActions.any { x -> !x.dockLeft }

    if (withLeftActions && !withRightActions)
        returnValue = 1
    else if (!withLeftActions && withRightActions)
        returnValue = 3
    else if (withLeftActions && withRightActions)
        returnValue = 2

    return returnValue
}

@OptIn(ExperimentalFoundationApi::class)
fun getAnchorMap(density: Density, buttonWidth: Dp, swipeActions: Array<SwipeAction>): DraggableAnchors<Int> {
    val actionState = actionStateValue(swipeActions)
    val sizePx = with(density) {
        buttonWidth.times(swipeActions.count { x -> x.dockLeft }).toPx()
    }
    val sizePxR = with(density) {
        buttonWidth.times(swipeActions.count { x -> !x.dockLeft }).toPx() * -1
    }

    return DraggableAnchors {
        0f to 0
        if (actionState in 1..2)
            sizePx to 1
        if (actionState in 2..3)
            sizePxR to 2
    }
}

/**
 * Swipeable Horizontal Card with custom actions
 * @param modifier Modifier to be applied to the layout of the card.
 * @param shape Defines the card's shape as well its shadow. A shadow is only
 *  displayed if the [elevation] is greater than zero.
 * @param backgroundColor The background color.
 * @param contentColor The preferred content color provided by this card to its children.
 * Defaults to either the matching content color for [backgroundColor], or if [backgroundColor]
 * is not a color from the theme, this will keep the same value set above this card.
 * @param border Optional border to draw on top of the card
 * @param elevation The z-coordinate at which to place this card. This controls
 *  the size of the shadow below the card.
 *  @param buttonWidth SwipeActions fixed Width
 *  @param swipeActions SwipeActions Array with left/right docking
 *  @param onClickSwipeAction Raised event on SwipeAction tap
 *  @param swipeBackColor Backcolor of swipe area
 *  @param content Card Content
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SwipeableCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    buttonWidth: Dp,
    swipeBackColor: Color = Color.Transparent,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    swipeActions: Array<SwipeAction> = arrayOf(),
    onClickSwipeAction: (SwipeAction) -> Unit = { },
    unSwipeOnClick: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) = SwipeableCard(
    modifier = modifier,
    shape = shape,
    backgroundColor = backgroundColor,
    buttonWidth = buttonWidth,
    swipeBackColor = swipeBackColor,
    contentColor = contentColor,
    border = border,
    anchors = getAnchorMap(LocalDensity.current, buttonWidth, swipeActions),
    elevation = elevation,
    swipeActions = swipeActions,
    onClickSwipeAction = onClickSwipeAction,
    unSwipeOnClick = unSwipeOnClick,
    content = content
)

/**
 * Swipeable Horizontal Card with custom actions
 * @param modifier Modifier to be applied to the layout of the card.
 * @param shape Defines the card's shape as well its shadow. A shadow is only
 *  displayed if the [elevation] is greater than zero.
 * @param backgroundColor The background color.
 * @param contentColor The preferred content color provided by this card to its children.
 * Defaults to either the matching content color for [backgroundColor], or if [backgroundColor]
 * is not a color from the theme, this will keep the same value set above this card.
 * @param border Optional border to draw on top of the card
 * @param elevation The z-coordinate at which to place this card. This controls
 *  the size of the shadow below the card.
 *  @param buttonWidth SwipeActions fixed Width
 *  @param swipeActions SwipeActions Array with left/right docking
 *  @param onClickSwipeAction Raised event on SwipeAction tap
 *  @param swipeBackColor Backcolor of swipe area
 *  @param content Card Content
 */
@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun SwipeableCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    buttonWidth: Dp,
    anchors: DraggableAnchors<Int>? = null,
    swipeBackColor: Color = Color.Transparent,
    contentColor: Color = contentColorFor(backgroundColor),
    unSwipeOnClick: Boolean = true,
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    swipeActions: Array<SwipeAction> = arrayOf(),
    onClickSwipeAction: (SwipeAction) -> Unit = { },
    content: @Composable ColumnScope.() -> Unit
) {

    val draggableState = rememberSaveable(
        systemDensity(),
        saver = AnchoredDraggableState.Saver(
            animationSpec = tween(),
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { 0.3f },
        )
    ) {
        AnchoredDraggableState(
            initialValue = 0,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { 0.3f },
            animationSpec = tween(),
        ).apply {
            anchors?.let { newAnchors -> updateAnchors(newAnchors) }
        }
    }


    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .background(swipeBackColor)
            .padding(0.dp)
            .anchoredDraggable(
                state = draggableState,
                orientation = Orientation.Horizontal
            )
    ) {
        val swipeActionsClean: @Composable () -> Unit = {
            swipeActions.filter { x -> x.dockLeft }.sortedBy { o -> o.order }
                .map { action ->
                    Button(
                        onClick = {
                            onClickSwipeAction(action)
                            if (unSwipeOnClick) {
                                coroutineScope.launch {
                                    launch {
                                        withContext(Dispatchers.Main) {
                                            draggableState.animateTo(0)
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(buttonWidth),
                        colors = ButtonDefaults.buttonColors(containerColor = action.color)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = action.imageVector,
                                contentDescription = null,
                                modifier = Modifier.size(buttonWidth.div(2)),
                                tint = action.tint
                            )
                            Text(
                                text = action.title,
                                fontSize = 10.sp,
                                color = action.tint
                            )
                        }
                    }
                }
        }

        if (swipeActions.isNotEmpty()) {
            Row(modifier = Modifier.fillMaxSize()) {
                if (swipeActions.any { sw -> sw.dockLeft }) {
                    Row(modifier = Modifier.fillMaxHeight()) {
                        swipeActionsClean()
                    }
                }

                if (swipeActions.any { sw -> !sw.dockLeft }) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        swipeActionsClean()
                    }
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
                .offset {
                    IntOffset(
                        if (swipeActions.isEmpty()) 0 else draggableState.offset.roundToInt(),
                        0
                    )
                },
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor,
                contentColor = contentColor,
            ),
            border = border,
            elevation = CardDefaults.cardElevation(elevation),
            content = content
        )
    }
}

@Preview
@Composable
fun SwipeableCardPreview() {
    val actions = arrayOf(
        SwipeAction(
            1,
            "k1",
            "Title 1",
            Icons.Default.Woman,
            Color.Blue,
            Color.White,
            true
        ),
        SwipeAction(
            2,
            "k2",
            "Title 2",
            Icons.Default.Mail,
            Color.Red,
            Color.White,
            true
        ),
        SwipeAction(
            3,
            "k3",
            "Title 3",
            Icons.Default.Delete,
            Color.Green,
            Color.White,
            false
        ),
    )

    SwipeableCard(
        modifier = Modifier.fillMaxWidth(),
        buttonWidth = 82.dp
    ) {
        Text("Swipeable Card")
    }
}
*/