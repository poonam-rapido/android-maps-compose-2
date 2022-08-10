package com.google.maps.android.compose.compose.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.compose.map.CustomerMapView
import com.google.maps.android.compose.compose.utility.requireContentView

class ComposeFragment : Fragment() {

    /*
        arguments = Bundle().apply {
                    putParcelable(ARG_PICK_UP_LAT_LNG, pickUpLatLng)
                    putParcelable(ARG_DROP_LAT_LNG, dropLatLng)
                    putString(ARG_PICK_UP_ADDRESS, pickUpAddress)
                    putString(ARG_DROP_ADDRESS, dropAddress)
                    putString(ARG_MAP_TYPE, mapType)
                }


                 const val ARG_PICK_UP_LAT_LNG = "pickup_lat_lng"
        const val ARG_DROP_LAT_LNG = "drop_lat_lng"
        const val ARG_PICK_UP_ADDRESS = "pickup_address"
        const val ARG_DROP_ADDRESS = "drop_address"
        const val ARG_MAP_TYPE = "map_type"
     */

    companion object {

        fun newInstance(
            composeContentOnMapReady: @Composable () -> Unit,
            cameraPositionState: CameraPositionState,


            ): ComposeFragment {
            return ComposeFragment().apply {
                setComposableContent(composeContentOnMapReady, cameraPositionState)
            }
        }
    }

    private var composableContent: @Composable () -> Unit = {}
    private lateinit var cameraPositionState: CameraPositionState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return requireContentView(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
            requireContext()
        ) {
            Box(Modifier.fillMaxSize()) {
                var isMapLoaded by remember { mutableStateOf(false) }
                SetGoogleMapView(
                    modifier = Modifier.matchParentSize(),
                    onMapLoaded = {
                        isMapLoaded = true
                    },
                    composableContent
                )
                if (isMapLoaded.not()) {
                    CustomerMapView.AnimatedCircularProgress(modifier = Modifier.matchParentSize())
                }

            }
        }
    }

    @Composable
    fun SetGoogleMapView(
        modifier: Modifier,
        onMapLoaded: () -> Unit = {},
        content: @Composable () -> Unit = {}
    ) {

        GoogleMap(
            modifier = modifier,
            onMapLoaded = onMapLoaded,
            cameraPositionState = cameraPositionState
        ) {
            content()
        }

//        pickUpState = rememberMarkerState(position = pickUpLatLng)
//        dropState = rememberMarkerState(position = dropLatLng)
//
//        val cameraPositionState = rememberCameraPositionState {
//            position = CameraPosition.fromLatLngZoom(pickUpLatLng, 14f)
//        }
//
//        val latLngBounds = getLatLngBounds(listOf(pickUpLatLng, dropLatLng))
//        var centerMap by remember { mutableStateOf(false) }
//
//        CustomerMapView.PickUpDropMapView(
//            modifier = modifier,
//            onMapLoaded = {
//                onMapLoaded()
//                centerMap = true
//            },
//            cameraPositionState = cameraPositionState,
//            uiSettings = MapUiSettings(zoomControlsEnabled = false),
//            pickUpState = pickUpState,
//            dropState = dropState,
//            pickUpAddress = pickUpAddress,
//            dropAddress = dropAddress,
//        )
//
//        if (centerMap) {
//            CenterMap(cameraPositionState = cameraPositionState, latLngBounds = latLngBounds)
//        }
    }


    private fun setComposableContent(
        content: @Composable () -> Unit,
        cameraState: CameraPositionState
    ) {
        composableContent = content
        cameraPositionState = cameraState
    }

}

interface MapReadyListener {
    fun onMapReady()
}