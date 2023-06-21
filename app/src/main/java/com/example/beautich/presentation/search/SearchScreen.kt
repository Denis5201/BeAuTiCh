package com.example.beautich.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.AppTextField
import com.example.beautich.presentation.AppointmentCard
import com.example.beautich.presentation.ErrorDialog
import com.example.beautich.presentation.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel) {

    val appointment by viewModel.appointments.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
        .padding(top = 16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.filter),
                contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.FilterScreen.route)
                }
            )

            Spacer(modifier = Modifier.padding(start = 16.dp))

            AppTextField(
                input = viewModel.search.collectAsStateWithLifecycle().value,
                valChange = { viewModel.setSearch(it) },
                name = stringResource(R.string.write_client_name).dropLast(1),
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.padding(20.dp))

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = 8.dp),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            items(appointment) {
                AppointmentCard(navController, it, true)
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!uiState.isLoading) {
            viewModel.getAppointmentWithFilter()
        }
    }

    if (uiState.isErrorDialogOpen) {
        ErrorDialog(message = uiState.errorMessage) {
            viewModel.closeErrorDialog()
        }
    }
}