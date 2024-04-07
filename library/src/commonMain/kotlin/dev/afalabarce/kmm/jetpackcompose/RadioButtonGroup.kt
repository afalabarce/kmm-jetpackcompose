package dev.afalabarce.kmm.jetpackcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
inline fun <reified T> RadioButtonGroup(
    modifier: Modifier = Modifier,
    crossinline radioButtonLabel: @Composable (T) -> Unit = { },
    crossinline radioButtonBody: @Composable (T) -> Unit = { },
    radioButtonValues: Array<T>,
    selectedValue: T?,
    borderStroke: BorderStroke? = null,
    dividerHeight: Dp = 4.dp,
    excludedValues: Array<T> = emptyArray(),
    radioButtonItemShape: Shape = MaterialTheme.shapes.medium,
    crossinline onCheckedChanged: (T) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        radioButtonValues
            .filter { notExcluded -> !excludedValues.any { excluded -> excluded == notExcluded } }
            .forEachIndexed { index, item ->
                if (index > 0)
                    Spacer(modifier = Modifier.size(dividerHeight))
                Column(
                    modifier = Modifier
                        .clip(radioButtonItemShape)
                        .border(borderStroke ?: BorderStroke(0.dp, Color.Unspecified), radioButtonItemShape)
                        .fillMaxWidth()
                        .clickable { onCheckedChanged(item) },
                ) {
                    var radioButtonWidth: Dp by remember { mutableStateOf(48.dp) }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = item == selectedValue,
                            onClick = { onCheckedChanged(item) }
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        radioButtonLabel(item)
                    }
                    Row(
                        modifier = Modifier.padding(start = radioButtonWidth.plus(2.dp)).fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        radioButtonBody(item)
                    }
                }
            }
    }
}

@Preview
@Composable
fun RadioGroupPreview() {
    val data = arrayOf("Hello", "World")
    RadioButtonGroup(
        modifier = Modifier.padding(end = 8.dp).fillMaxWidth(),
        radioButtonValues = data,
        selectedValue = null,
        radioButtonItemShape = RoundedCornerShape(4.dp),
        radioButtonLabel = {
            Text(text = it)
        },
        radioButtonBody = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            ) {
                Text(text = "Click me, $it")
            }
        }

    ) {

    }
}

