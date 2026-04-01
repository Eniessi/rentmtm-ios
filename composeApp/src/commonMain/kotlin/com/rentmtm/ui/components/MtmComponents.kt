package com.rentmtm.ui.components // ⬅️ O novo pacote que criamos!

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ==========================================
// 1. CAMPO DE TEXTO PADRÃO (48.dp)
// ==========================================
@Composable
fun MtmTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Text),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty() && placeholder.isNotEmpty()) {
                            Text(text = placeholder, color = Color.Gray, fontSize = 14.sp)
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}

// ==========================================
// 2. MENU SUSPENSO (DROPDOWN - 48.dp)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MtmDropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(text = label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            BasicTextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .height(48.dp),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                            .clickable { expanded = true }
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (selectedOption.isEmpty()) {
                                Text("Select", fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
                            }
                            innerTextField()
                        }
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// ==========================================
// 3. SELETOR DE DATA (DATE PICKER - 48.dp)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MtmDateField(
    label: String,
    value: String,
    onDateSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(text = label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (value.isEmpty()) {
                                Text("mm/dd/yyyy", fontSize = 14.sp, color = MaterialTheme.colorScheme.outline)
                            }
                            innerTextField()
                        }
                        Icon(Icons.Default.DateRange, contentDescription = "Select Date", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { showDialog = true }
            )
        }
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    val millis = datePickerState.selectedDateMillis
                    if (millis != null) {
                        onDateSelected("Date Selected") // Futuramente vamos formatar a data real aqui
                    }
                    showDialog = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

// ==========================================
// 4. CHECKBOX CUSTOMIZADO
// ==========================================
@Composable
fun MtmCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
        )
        Text(text = text, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

// ==========================================
// 5. RADIO BUTTON CUSTOMIZADO
// ==========================================
@Composable
fun MtmRadioButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
        )
        Text(text = text, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun MtmTextArea(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(text = label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface),
            // Permitimos múltiplas linhas!
            singleLine = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp), // Altura maior para simular um parágrafo
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    if (value.isEmpty() && placeholder.isNotEmpty()) {
                        Text(text = placeholder, color = Color.Gray, fontSize = 14.sp)
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun MtmStarRatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxStars: Int = 5,
    starSize: androidx.compose.ui.unit.Dp = 40.dp,
    activeColor: Color = Color(0xFFFFC107), // Amarelo/Dourado padrão
    inactiveColor: Color = Color.LightGray
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Outlined.StarBorder
            val tint = if (isSelected) activeColor else inactiveColor

            Icon(
                imageVector = icon,
                contentDescription = "Star $i",
                tint = tint,
                modifier = Modifier
                    .size(starSize)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}