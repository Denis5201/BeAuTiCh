package com.example.beautich.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beautich.R

@Composable
fun WhiteButton(
    text: String,
    modifier: Modifier,
    click: () -> Unit
) {
    Button(
        onClick = { click() },
        modifier = modifier.height(55.dp),
        shape = RoundedCornerShape(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    input: String?,
    valChange: (String) -> Unit,
    name: String,
    modifier: Modifier,
    isPassword: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Box {
        val interactionSource = remember { MutableInteractionSource() }
        var showPassword by remember { mutableStateOf(false) }
        val visualTransformation = if (isPassword && !showPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.textfield_background),
            contentDescription = null,
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp),
            contentScale = ContentScale.FillBounds
        )
        BasicTextField(
            value = input ?: "",
            onValueChange = { valChange(it) },
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = if (isPassword && !showPassword) 2.sp else 0.sp
            ),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            visualTransformation = visualTransformation,
            decorationBox = {
                TextFieldDefaults.DecorationBox(
                    value = input ?: "",
                    innerTextField = it,
                    enabled = true,
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    },
                    trailingIcon = {
                        if (isPassword) {
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.password_eye),
                                contentDescription = null,
                                modifier = Modifier.clickable { showPassword = !showPassword }
                            )
                        }
                    },
                    colors = colors(
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(horizontal = 13.dp, vertical = 8.dp),
                    visualTransformation = visualTransformation
                )
            }
        )
    }

}