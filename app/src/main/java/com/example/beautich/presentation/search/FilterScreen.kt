package com.example.beautich.presentation.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.AppTextField
import com.example.beautich.presentation.DateTimeTextField
import com.example.beautich.presentation.WhiteButton
import com.example.beautich.presentation.navigation.Screen

@Composable
fun FilterScreen(navController: NavController, viewModel: SearchViewModel) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.background2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        navController.navigateUp()
                    }
            )

            Spacer(modifier = Modifier.padding(start = 16.dp))

            Text(
                text = stringResource(R.string.filters),
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.choose_services_for_filters),
                modifier = Modifier.clickable {
                    navController.navigate(
                        Screen.ServiceSelectionScreen.route + "/false"
                    )
                },
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_right),
                contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.MyServicesScreen.route)
                }
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = stringResource(R.string.sorting),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = stringResource(R.string.by_price),
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(4.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.from),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
                color = Color.White
            )
            AppTextField(
                input = viewModel.startPrice.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setStartPrice(it) },
                name = stringResource(R.string.input_price),
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.to),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
                color = Color.White
            )
            AppTextField(
                input = viewModel.endPrice.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setEndPrice(it) },
                name = stringResource(R.string.input_price),
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = stringResource(R.string.by_date),
            modifier = Modifier.padding(start = 8.dp),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.from),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
                color = Color.White
            )
            DateTimeTextField(input = stringResource(R.string.choose_date)) {

            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.to),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
                color = Color.White
            )
            DateTimeTextField(input = stringResource(R.string.choose_date)) {

            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Spacer(modifier = Modifier.weight(1f))

        WhiteButton(
            text = stringResource(R.string.save),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 32.dp)
        ) {

        }
    }
}