package com.persado.sdkexample.ui.profile

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDropdown(
    modifier: Modifier = Modifier,
    placeholder: String,
    textStyle: TextStyle,
    textAlign: TextAlign = TextAlign.Start,
    textFieldColors: TextFieldColors = ExposedDropdownMenuDefaults.textFieldColors(),
    selected: String = "",
    label: @Composable (() -> Unit)? = null,
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            modifier = modifier
                .menuAnchor()
                .focusProperties { canFocus = false },
            readOnly = true,
            value = selected,
            onValueChange = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            shape = MaterialTheme.shapes.small,
            label = label,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodySmall
                )
            },
            textStyle = textStyle.copy(textAlign = textAlign),
            colors = textFieldColors
        )
        ExposedDropdownMenu(
            modifier = Modifier,
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            val itemStyle = MaterialTheme.typography.bodyMedium
            items.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(selectionOption)
                        expanded = false
                    }, text = {
                        Text(
                            text = selectionOption,
                            style = itemStyle
                        )
                    }
                )
            }
        }
    }
}