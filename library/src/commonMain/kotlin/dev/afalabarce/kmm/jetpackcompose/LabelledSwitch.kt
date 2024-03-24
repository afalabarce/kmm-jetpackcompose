package dev.afalabarce.kmm.jetpackcompose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LabelledSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.labelMedium,
    leadingIcon: @Composable () -> Unit = {},
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    onCheckedChange: ((Boolean) -> Unit)
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .padding(horizontal = 8.dp)
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Switch,
                enabled = enabled
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        leadingIcon()
        Text(
            text = label,
            style = labelStyle,
            modifier = Modifier.weight(1f)
                .padding(end = 16.dp)
        )
        Switch(
            checked = checked,
            onCheckedChange = null,
            enabled = enabled,
            colors = colors,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewSwitchChecked() {
    Surface {
        Column {
            LabelledSwitch(
                modifier = Modifier.fillMaxWidth(),
                checked = true,
                label = "Hola Checks",
            ) {

            }
        }
    }
}

@Preview
@Composable
fun PreviewSwitchUnChecked() {
    Surface {
        Column {
            LabelledSwitch(
                modifier = Modifier.fillMaxWidth(),
                checked = false,
                label = "Hola Checks",
            ) {

            }
        }
    }
}