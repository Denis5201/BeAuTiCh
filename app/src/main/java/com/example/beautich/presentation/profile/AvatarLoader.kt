package com.example.beautich.presentation.profile

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.example.beautich.Constants
import com.example.beautich.R
import com.example.beautich.ui.theme.Gray
import java.io.File

@Composable
fun AvatarLoader(
    uiState: ProfileUiState,
    uploadNewPhoto: (Bitmap) -> Unit,
    closePhotoDialog: () -> Unit,
    setUri: (Uri) -> Unit
) {
    val context = LocalContext.current
    val getGalleryImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it != null) {
            val bitmap = context.contentResolver.openInputStream(it)?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
            val normalizedBitmap = context.contentResolver.openInputStream(it)?.use { stream ->
                val exif = ExifInterface(stream)
                normalizeRotationImage(bitmap!!, exif)
            }

            val scaledBitmap = scalingGalleryBitmap(normalizedBitmap!!)
            uploadNewPhoto(scaledBitmap)
        }
    }
    val getCameraImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) {
        if (it) {
            val filePath = "${context.cacheDir.absolutePath}/${uiState.uri.pathSegments.last()}"

            val file = File(filePath)

            val bitmap = decodeFileToBitmap(filePath)
            val exif = ExifInterface(file)
            val adjustedBitmap = normalizeRotationImage(bitmap, exif)

            file.delete()
            uploadNewPhoto(adjustedBitmap)
        }
    }
    val permission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            val myUri = createTempFileAndGetUri(context)
            setUri(myUri)
            getCameraImage.launch(myUri)
        }
    }

    if (uiState.isPhotoDialogOpen) {
        NewPhotoDialog(
            galleryLauncher = getGalleryImage,
            cameraLauncher = getCameraImage,
            permissionLauncher = permission,
            closePhotoDialog = closePhotoDialog,
            setUri = setUri
        )
    }
}

@Composable
fun NewPhotoDialog(
    galleryLauncher: ManagedActivityResultLauncher<String, Uri?>,
    cameraLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    closePhotoDialog: () -> Unit,
    setUri: (Uri) -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = { closePhotoDialog() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray, RoundedCornerShape(2.dp))
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.choosing_where_take_photo),
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    modifier = Modifier.clickable { closePhotoDialog() },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(start = 24.dp))

                Text(
                    text =  stringResource(R.string.gallery),
                    modifier = Modifier.clickable {
                        galleryLauncher.launch("image/*")
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(start = 24.dp))

                Text(
                    text =  stringResource(R.string.camera),
                    modifier = Modifier.clickable {
                        val permissionCheck =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                            val myUri = createTempFileAndGetUri(context)
                            setUri(myUri)
                            cameraLauncher.launch(myUri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

fun createTempFileAndGetUri(context: Context): Uri {
    val file = File.createTempFile(
        Constants.IMAGE_NAME,
        Constants.JPEG,
        context.cacheDir
    )
    return FileProvider.getUriForFile(
        context,
        context.packageName + ".provider",
        file)
}


private fun decodeFileToBitmap(filePath: String): Bitmap {
    val option = BitmapFactory.Options()
    option.inJustDecodeBounds = true

    BitmapFactory.decodeFile(filePath, option)
    val height = option.outHeight
    val width = option.outWidth

    option.inJustDecodeBounds = false
    var scale = 1
    while (height / scale > 1000 || width / scale > 1000) {
        scale *= 2
    }
    option.inSampleSize = scale

    return BitmapFactory.decodeFile(filePath, option)
}

private fun normalizeRotationImage(image: Bitmap, exif: ExifInterface): Bitmap {
    val rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    val rotationInDegrees = exifToDegrees(rotation)

    val matrix = Matrix()
    if (rotation != 0) {
        matrix.preRotate(rotationInDegrees.toFloat())
    }

    return Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
}

private fun exifToDegrees(exifOrientation: Int): Int {
    return when (exifOrientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90
        ExifInterface.ORIENTATION_ROTATE_180 -> 180
        ExifInterface.ORIENTATION_ROTATE_270 -> 270
        else -> 0
    }
}

fun scalingGalleryBitmap(image: Bitmap): Bitmap {
    val oldWidth = image.width
    val oldHeight = image.height
    return if (oldWidth < 1000 || oldHeight < 1000) {
        image
    } else {
        val scale = maxOf(oldWidth, oldHeight) / 1000 + 1
        Bitmap.createScaledBitmap(image, oldWidth / scale, oldHeight / scale, true)
    }
}