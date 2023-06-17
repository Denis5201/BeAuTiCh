package com.example.beautich.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.beautich.R
import com.example.beautich.presentation.DialogTextField
import com.example.beautich.ui.theme.Gray

@Composable
fun ChangeProfileDataDialog(
    viewModel: ProfileViewModel
) {
    Dialog(onDismissRequest = { viewModel.closeChangeProfileDialog() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray, RoundedCornerShape(10.dp))
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.new_profile_data),
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(5.dp))

            DialogTextField(input = viewModel.fullName.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setFullName(it) },
                name = stringResource(R.string.input_full_name)
            )

            Spacer(modifier = Modifier.padding(4.dp))

            DialogTextField(input = viewModel.phone.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setPhone(it) },
                name = stringResource(R.string.input_phone),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = stringResource(R.string.update),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { viewModel.changeProfile() },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}