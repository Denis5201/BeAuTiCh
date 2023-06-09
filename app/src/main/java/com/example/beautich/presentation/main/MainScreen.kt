package com.example.beautich.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.AppointmentCard
import com.example.beautich.presentation.ErrorDialog
import com.example.beautich.presentation.navigation.Screen
import com.example.beautich.ui.theme.GrayLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(
        initialPage = uiState.currentDay.dayOfWeek.value - 1
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.plus),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.End)
                .statusBarsPadding()
                .padding(top = 24.dp, end = 16.dp, bottom = 8.dp)
                .clickable {
                    navController.navigate(Screen.AppointmentDevelopScreen.route)
                }
        )
        HorizontalPager(
            pageCount = 7,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            state = pagerState
        ) { page ->
            if (uiState.appointmentsForWeek.isEmpty()) {
                return@HorizontalPager
            }
            if (uiState.appointmentsForWeek[page].isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.empty_appointment),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = stringResource(R.string.you_can_rest),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    items(uiState.appointmentsForWeek[page]) {
                        AppointmentCard(navController, it)
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.small_left_arrow),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        viewModel.loadPreviousWeek()
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(6)
                        }
                    }
                    .padding(start = 8.dp, end = 4.dp),
                tint = Color.White
            )

            Row(
                modifier = Modifier.fillMaxWidth(0.95f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                uiState.currentDays.forEachIndexed { index, localDate ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                            .background(
                                if (pagerState.currentPage == index ||
                                    localDate == uiState.currentDay
                                ) {
                                    Color.White
                                } else {
                                    Color.Transparent
                                }
                            )
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                            .padding(
                                horizontal = 5.dp,
                                vertical = if (pagerState.currentPage == index) 3.dp else 2.dp
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = localDate.dayOfMonth.toString(),
                            style = if (pagerState.currentPage == index)
                                MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp)
                            else
                                MaterialTheme.typography.labelMedium,
                            color = if (localDate == uiState.currentDay) {
                                MaterialTheme.colorScheme.tertiaryContainer
                            } else if (pagerState.currentPage == index) {
                                Color.Black
                            } else {
                                GrayLight
                            }
                        )
                        Text(
                            text = stringArrayResource(R.array.day_of_week)[localDate.dayOfWeek.value - 1],
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 9.sp,
                                lineHeight = 11.sp
                            ),
                            color = if (pagerState.currentPage == index ||
                                localDate == uiState.currentDay
                            ) {
                                Color.Black
                            } else {
                                Color.White.copy(alpha = 0.75f)
                            }
                        )
                    }
                }


            }
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.small_right_arrow),
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        viewModel.loadNextWeek()
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                    .padding(start = 4.dp, end = 8.dp),
                tint = Color.White
            )
        }
    }

    LaunchedEffect(Unit) {
        if (!uiState.isLoading) {
            viewModel.refresh()
        }
    }

    if (uiState.isErrorDialogOpen) {
        ErrorDialog(message = uiState.errorMessage) {
            viewModel.closeErrorDialog()
        }
    }
}