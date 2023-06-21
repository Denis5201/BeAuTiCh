package com.example.beautich.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.beautich.R
import com.example.beautich.presentation.DialogTextField
import com.example.beautich.ui.theme.Gray

@Composable
fun ChangePasswordDialog(viewModel: ProfileViewModel) {
    Dialog(onDismissRequest = { viewModel.closeChangePasswordDialog() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray, RoundedCornerShape(10.dp))
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.changing_password),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(5.dp))

            DialogTextField(input = viewModel.oldPassword.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setOldPassword(it) },
                name = stringResource(R.string.input_old_password),
                isPassword = true
            )

            Spacer(modifier = Modifier.padding(4.dp))

            DialogTextField(input = viewModel.newPassword.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setNewPassword(it) },
                name = stringResource(R.string.input_new_password),
                isPassword = true
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = stringResource(R.string.change),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { viewModel.changePassword() },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}