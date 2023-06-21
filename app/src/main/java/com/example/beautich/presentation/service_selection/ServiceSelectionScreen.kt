package com.example.beautich.presentation.service_selection

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.appointment.develop.AppointmentDevelopViewModel
import com.example.beautich.presentation.search.SearchViewModel
import com.example.beautich.ui.theme.SelectedService

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServiceSelectionScreen(
    navController: NavController,
    viewModel: ServiceSelectionViewModel,
    developViewModel: AppointmentDevelopViewModel? = null,
    searchViewModel: SearchViewModel? = null
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val devServices = developViewModel?.uiState?.collectAsStateWithLifecycle()?.value?.servicesId
    val searchServices = searchViewModel?.uiState?.collectAsStateWithLifecycle()?.value?.servicesId

    if (devServices != null && uiState.services.isNotEmpty()) {
        LaunchedEffect(key1 = Unit) {
            viewModel.setAlreadyChosen(devServices)
        }
    }
    if (searchServices != null && uiState.services.isNotEmpty()) {
        LaunchedEffect(key1 = Unit) {
            viewModel.setAlreadyChosen(searchServices)
        }
    }

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
                text = stringResource(R.string.choose_services),
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
        }
        
        Spacer(modifier = Modifier.padding(16.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.services.forEach {
                Box(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .width(IntrinsicSize.Max)
                ) {
                    Text(
                        text = it.first.name,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .background(
                                MaterialTheme.colorScheme.tertiary,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                viewModel.changeSelection(it.first.id)
                                if (devServices != null) {
                                    if (it.second)
                                        developViewModel.deleteService(it.first)
                                    else
                                        developViewModel.addService(it.first)
                                }
                                if (searchServices != null) {
                                    if (it.second)
                                        searchViewModel.deleteService(it.first)
                                    else
                                        searchViewModel.addService(it.first)
                                }
                            }
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    if (it.second) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 8.dp)
                                .background(SelectedService, RoundedCornerShape(20.dp))
                        )
                    }
                }

            }
        }
    }
}