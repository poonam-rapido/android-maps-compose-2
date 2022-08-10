package com.google.maps.android.compose.compose.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.R
import com.google.maps.android.compose.compose.factories.MarkerInfoWindowFactory
import com.google.maps.android.compose.compose.markers.SimpleMarker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


object CustomerMapView {

    @Composable
    fun PickUpDropMapView(
        modifier: Modifier = Modifier,
        onMapLoaded: () -> Unit = {},
        cameraPositionState: CameraPositionState,
        uiSettings: MapUiSettings,
        pickUpState: MarkerState,
        dropState: MarkerState,
        pickUpAddress: String,
        dropAddress: String,
        content: @Composable () -> Unit = {},
    ) {
        GoogleMap(
            modifier = modifier,
            onMapLoaded = onMapLoaded,
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
        ) {

            val onclickPickUp: (Marker) -> Boolean = { false }
            MarkerInfoWindowFactory.DrawMarker(
                type = SimpleMarker,
                state = pickUpState,
                vectorResId = R.drawable.pickup_marker,
                address = pickUpAddress,
                onClick = onclickPickUp,
                title = "Pick Up"
            )

            val onclickDrop: (Marker) -> Boolean = { false }
            MarkerInfoWindowFactory.DrawMarker(
                type = SimpleMarker,
                state = dropState,
                vectorResId = R.drawable.drop_marker,
                address = dropAddress,
                onClick = onclickDrop,
                title = "Drop"
            )
            Polyline(
                points = listOf(pickUpState.position, dropState.position),
                color = Color.Gray,
                width = 5f
            )
            content()
        }

    }

    @Composable
    fun AnimatedCircularProgress(modifier: Modifier) {
        AnimatedVisibility(
            modifier = modifier,
            visible = true,
            enter = EnterTransition.None,
            exit = fadeOut()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .wrapContentSize()
            )
        }
    }

    @Composable
    fun CenterMap(cameraPositionState: CameraPositionState, latLngBounds: LatLngBounds) {
        LaunchedEffect(Unit) {
            this.launch {
                delay(2000)
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngBounds(
                        latLngBounds,
                        200,
                    ), 1_000
                )
            }

        }

    }


}


