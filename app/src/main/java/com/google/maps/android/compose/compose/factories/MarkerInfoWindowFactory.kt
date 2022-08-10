package com.google.maps.android.compose.compose.factories

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.compose.markers.BaseMarker
import com.google.maps.android.compose.compose.markers.SimpleMarker
import com.google.maps.android.compose.compose.markers.SimpleMarker.SimpleMarkerView


object MarkerInfoWindowFactory {

    @Composable
    fun DrawMarker(
        type: BaseMarker,
        state: MarkerState,
        @DrawableRes vectorResId: Int,
        address: String,
        onClick: (Marker) -> Boolean = { false },
        title: String
    ) {
        when (type) {
            SimpleMarker -> {
                SimpleMarkerView(
                    state = state,
                    vectorResId = vectorResId,
                    address = address,
                    onClick = onClick,
                    title = title
                )
            }
        }
    }

}