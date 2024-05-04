package dev.afalabarce.kmm.jetpackcompose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> SpinnerSelector(
    modifier: Modifier,
    readOnly: Boolean = false,
    hintText: String = "",
    label: @Composable () -> Unit = {},
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
    border: BorderStroke = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    shape: Shape = MaterialTheme.shapes.small,
    componentHeight: Dp = 58.dp,
    height: Dp = 160.dp,
    selectedItem: T? = null,
    expandedTrailingIcon: ImageVector? = null,
    collapsedTrailingIcon: ImageVector? = null,
    trailingIconTint: Color = MaterialTheme.colorScheme.onBackground,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    onBackgroundColor: Color = MaterialTheme.colorScheme.background,
    items: List<T>,
    onSelectedItem: (T) -> Unit = {},
    itemComposable: @Composable (T) -> Unit = { i -> Text(i.toString()) }
) {
    var expandedDropDown by remember { mutableStateOf(false) }
    var spinnerValue: T? by remember { mutableStateOf(null) }

    if (selectedItem != null)
        spinnerValue = selectedItem
    Box(modifier = modifier.height(componentHeight.plus(12.dp))) {
        Button(
            onClick = {
                if (!readOnly)
                    expandedDropDown = !expandedDropDown
            },
            modifier = modifier
                .height(componentHeight)
                .offset(y = 6.dp)
                .align(Alignment.BottomStart),
            shape = shape,
            colors = colors,
            border = border,
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    if (spinnerValue == null)
                        label()
                    else
                        itemComposable(spinnerValue!!)
                }
                Icon(
                    imageVector = if (expandedDropDown)
                        expandedTrailingIcon ?: Icons.Rounded.ArrowDropUp
                    else
                        collapsedTrailingIcon ?: Icons.Rounded.ArrowDropDown,
                    contentDescription = null,
                    tint = trailingIconTint,
                    modifier = Modifier.size(32.dp).focusable(false)
                        .clickable(true) {
                            if (!readOnly)
                                expandedDropDown = !expandedDropDown
                        }
                )
            }

            DropdownMenu(expanded = expandedDropDown,
                onDismissRequest = { expandedDropDown = false }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    items.forEach { item ->
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                spinnerValue = item
                                expandedDropDown = false
                                onSelectedItem(item)
                            }) {
                            itemComposable(item)
                        }
                    }
                }
            }
        }

        if (spinnerValue != null && hintText.isNotEmpty()) {
            Column(
                modifier = modifier
                    .height(componentHeight.plus(12.dp))
                    .padding(start = 16.dp)
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.Top

            ) {
                Text(
                    text = hintText,
                    style = MaterialTheme.typography.labelSmall,
                    color = accentColor,
                    modifier = Modifier
                        .background(onBackgroundColor)
                        .padding(horizontal = 4.dp)
                )
            }
        }
    }
}