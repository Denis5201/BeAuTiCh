package com.example.beautich.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.domain.model.Appointment
import com.example.beautich.presentation.navigation.Screen
import com.example.beautich.ui.theme.Orange

@Composable
fun AppointmentCard(
    navController: NavController,
    appointment: Appointment
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                navController.navigate(
                    "${Screen.AppointmentDetailsScreen.route}/${appointment.id}"
                )
            }
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.background_appointment),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Column {
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
                    text = "${appointment.startDateTime.hour}:${appointment.startDateTime.minute}",
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