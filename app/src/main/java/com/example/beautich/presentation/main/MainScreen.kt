package com.example.beautich.presentation.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.ErrorDialog
import com.example.beautich.ui.theme.GrayLight
import com.example.beautich.ui.theme.Orange
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
        HorizontalPager(
            pageCount = 7,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            state = pagerState
        ) { page ->
            val currentAppointments = uiState.appointmentsForWeek.filter {
                it.startDateTime.toLocalDate() == uiState.currentDays[page]
            }
            if (currentAppointments.isEmpty()) {
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
                    modifier = Modifier.padding(horizontal = 24.dp),
                    verticalItemSpacing = 16.dp,
                    horizontalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    items(currentAppointments) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                        ) {
                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.background_appointment),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds
                            )
                            Column {
                                Text(
                                    text = it.clientName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.padding(top = 2.dp))

                                it.services.forEach { service ->
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
                                        text = "${it.startDateTime.hour}:${it.startDateTime.minute}",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Orange
                                    )
                                    Text(
                                        text = "${it.price.toInt()}р",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Orange
                                    )
                                }
                            }
                        }
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

                    }
                    .padding(start = 4.dp, end = 8.dp),
                tint = Color.White
            )
        }
    }

    if (uiState.isErrorDialogOpen) {
        ErrorDialog(message = uiState.errorMessage) {
            viewModel.closeErrorDialog()
        }
    }
}