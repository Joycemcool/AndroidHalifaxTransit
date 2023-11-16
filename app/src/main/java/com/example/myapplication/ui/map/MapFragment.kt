package com.example.myapplication.ui.map

//import com.example.myapplication.mapView

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentMapBinding
import com.google.transit.realtime.GtfsRealtime
import com.google.transit.realtime.GtfsRealtime.FeedMessage
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import java.net.URL


var mapView: MapView? = null
class MapFragment : Fragment() {
    private var mapView: MapView? = null
    private var _binding: FragmentMapBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textHome.text = "This is the Home fragment"

        //Mapbox.getInstance(requireContext(), mapboxAccessToken)
        mapView = binding.mapView;
//        mapView?.getMapAsync { mapboxMap ->
//            mapboxMap.setStyle(Style.MAPBOX_STREETS) {
//                // Your map style initialization code here
//                GtfsRealtimeExample(); // Call your function here
//            }
//        }
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)


        return root
    }

    //
    //


    //
    //
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }




}

//object GtfsRealtimeExample {
//    @Throws(Exception::class)
//    @JvmStatic
//    fun loadRealtimeData(): List<GtfsRealtime.TripUpdate> {
//        val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
//        val feed = FeedMessage.parseFrom(url.openStream())
//       // val tripUpdates = mutableListOf<GtfsRealtime.TripUpdate>()
//        for (entity in feed.entityList) {
//            if (entity.hasTripUpdate()) {
//                Log.i("entity realtime", "latitude and longitude are " +entity.tripUpdate);
//                println(entity.tripUpdate)
//            }
//        }
//        //return tripUpdates
//    }
//}

