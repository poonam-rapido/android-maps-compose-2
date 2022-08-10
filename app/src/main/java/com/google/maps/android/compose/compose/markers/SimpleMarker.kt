package com.google.maps.android.compose.compose.markers

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.compose.utility.bitmapDescriptorFromVector

object SimpleMarker : BaseMarker {
    @Composable
    fun SimpleMarkerView(
        state: MarkerState,
        @DrawableRes vectorResId: Int,
        address: String,
        onClick: (Marker) -> Boolean = { false },
        title : String,
    ) {
        MarkerInfoWindowContent(
            state = state,
            icon = bitmapDescriptorFromVector(LocalContext.current, vectorResId),
            onClick = onClick,
        ) {
            Surface(Modifier.width(200.dp).height(200.dp), color = Color.Green) {
                Column(Modifier.padding(4.dp)) {
                    Text(title, color = Color.Green)
                    Text(
                        text = address,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}