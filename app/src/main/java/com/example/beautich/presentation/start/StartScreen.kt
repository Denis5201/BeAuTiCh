package com.example.beautich.presentation.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.beautich.R
import com.example.beautich.presentation.WhiteButton
import com.example.beautich.presentation.navigation.Screen

@Composable
fun StartScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.background_with_duck),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.fillMaxHeight(0.25f))
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.beautich),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 70.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.arrow_spiral),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 55.dp)
            )
            Text(
                text = stringResource(R.string.start_text),
                modifier = Modifier
                    .padding(horizontal = 48.dp)
                    .align(Alignment.CenterHorizontally)
                    .graphicsLayer {
                        translationY = -15.dp.toPx()
                    },
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
        WhiteButton(
            text = stringResource(R.string.forward),
            modifier = Modifier
                .width(175.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            navController.navigate(Screen.SignInScreen.route)
        }
    }
}