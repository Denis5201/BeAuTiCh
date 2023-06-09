package com.example.beautich.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.beautich.Constants
import com.example.beautich.R
import com.example.beautich.domain.model.Appointment
import com.example.beautich.presentation.navigation.Screen
import com.example.beautich.ui.theme.Orange
import java.time.format.DateTimeFormatter

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
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp)),
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

@Composable
fun ErrorDialog(
    title: String = stringResource(R.string.error),
    message: String,
    dismiss: () -> Unit
) {
    Dialog(onDismissRequest = dismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(2.dp))
                .padding(16.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Red
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = stringResource(R.string.ok),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { dismiss() },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogTextField(
    input: String?,
    valChange: (String) -> Unit,
    name: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isPassword: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val visualTransformation = if (isPassword) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    BasicTextField(
        value = input ?: "",
        onValueChange = { valChange(it) },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp)),
        textStyle = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = if (isPassword) 2.sp else 0.sp
        ),
        keyboardOptions = keyboardOptions,
        singleLine = true,
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
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                },
                colors = colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 13.dp, vertical = 5.dp),
                visualTransformation = visualTransformation
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeTextField(
    input: String?,
    click: () -> Unit
) {
    Box(modifier = Modifier.clickable { click() }) {
        val interactionSource = remember { MutableInteractionSource() }
        val visualTransformation = VisualTransformation.None
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.textfield_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.FillBounds
        )
        BasicTextField(
            value = input ?: "",
            onValueChange = {  },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            enabled = false,
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            singleLine = true,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            visualTransformation = visualTransformation,
            decorationBox = {
                TextFieldDefaults.DecorationBox(
                    value = input ?: "",
                    innerTextField = it,
                    enabled = true,
                    singleLine = true,
                    trailingIcon = {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.calendar),
                            contentDescription = null
                        )
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

@Composable
fun AppointmentCard(
    navController: NavController,
    appointment: Appointment,
    fullDate: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                navController.navigate(
                    "${Screen.AppointmentDetailsScreen.route}/${appointment.id}"
                )
            },
        propagateMinConstraints = true
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.background_appointment),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp, end = 12.dp, bottom = 8.dp)
        ) {
            Text(
                text = appointment.clientName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.padding(top = 2.dp))

            appointment.services.forEach { service ->
                Text(
                    text = service.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.padding(top = 12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (fullDate)
                        appointment.startDateTime.format(Constants.dateTimeFormat)
                    else
                        appointment.startDateTime.format(DateTimeFormatter.ofPattern("H:mm")),
                    style = MaterialTheme.typography.labelMedium,
                    color = Orange
                )
                Text(
                    text = "${appointment.price.toInt()}р",
                    style = MaterialTheme.typography.labelMedium,
                    color = Orange
                )
            }
        }
    }
}