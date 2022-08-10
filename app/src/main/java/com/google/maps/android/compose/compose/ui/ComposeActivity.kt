package com.google.maps.android.compose.compose.ui


import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.R
import com.google.maps.android.compose.compose.factories.MarkerInfoWindowFactory
import com.google.maps.android.compose.compose.map.CustomerMapView.CenterMap
import com.google.maps.android.compose.compose.markers.SimpleMarker
import com.google.maps.android.compose.compose.utility.getLatLngBounds
import com.google.maps.android.compose.rememberMarkerState

class ComposeActivity : FragmentActivity() {

    private var pickUpLatLng = LatLng(12.9012, 77.6106)
    private var dropLatLng = LatLng(12.9856, 77.5337)
    private var pickUpAddress =
        "5th Cross Rd, Phase 2, Anugraha Layout, Ramanashree Enclave, Bilekahalli"

    private var cameraPositionState: CameraPositionState =
        CameraPositionState(position = CameraPosition.fromLatLngZoom(pickUpLatLng, 14f))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        val frag = ComposeFragment.newInstance(
            composeContentOnMapReady = {
                ComposeMapAfterReady()
            },
            cameraPositionState
        )

        supportFragmentManager.apply {
            beginTransaction()
                .add(R.id.composeFrag, frag)
                .commit()
        }

    }


    @Composable
    fun ComposeMapAfterReady() {
        val pickUpState = rememberMarkerState(position = pickUpLatLng)
        val dropState = rememberMarkerState(position = dropLatLng)

        val onclickPickUp: (Marker) -> Boolean = {
            Log.d("poonam_log", "marker clicked")
            false
        }

        MarkerInfoWindowFactory.DrawMarker(
            type = SimpleMarker,
            state = pickUpState,
            vectorResId = R.drawable.pickup_marker,
            address = pickUpAddress,
            onClick = onclickPickUp,
            title = "Pick Up"
        )


        MarkerInfoWindowFactory.DrawMarker(
            type = SimpleMarker,
            state = dropState,
            vectorResId = R.drawable.drop_marker,
            address = pickUpAddress,
            onClick = onclickPickUp,
            title = "Drop"
        )
        val latLngBounds = getLatLngBounds(listOf(pickUpLatLng, dropLatLng))
        CenterMap(cameraPositionState = cameraPositionState, latLngBounds = latLngBounds)
    }

}