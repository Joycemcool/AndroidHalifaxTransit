package com.example.myapplication.ui.map


//import com.example.myapplication.mapView


//ViewAnnotationBasic
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.databinding.LayoutAnnotationBinding
import com.google.transit.realtime.GtfsRealtime
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.net.URL

//End example

var mapView: MapView? = null
class MapFragment : Fragment() {
    private var mapView: MapView? = null
    private var _binding: FragmentMapBinding? = null

    //Add the view annotation on view load
    private lateinit var mapboxMap: MapboxMap
    private lateinit var viewAnnotationManager: ViewAnnotationManager
    private val viewAnnotationViews = mutableListOf<View>()
    //end

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

    //From view annotation example
        super.onCreate(savedInstanceState)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textHome.text = "This is the Home fragment"

        //Mapbox.getInstance(requireContext(), mapboxAccessToken)
        mapView = binding.mapView;
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)

        // Create view annotation manager
        viewAnnotationManager = binding.mapView.viewAnnotationManager

        mapboxMap = binding.mapView.getMapboxMap().apply {
            // Load a map style
            loadStyleUri(Style.MAPBOX_STREETS) {
                // Get the center point of the map
                //val center = mapboxMap.cameraState.center

                // Add the view annotation at the center point
                val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
                val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())//cannot proceed feed

                for (entity in feed.entityList) {
                    val routeNum = entity.vehicle.vehicle.id
                    val longitude = entity.vehicle.position.longitude
                    val latitude = entity.vehicle.position.latitude
                    val point : Point = Point.fromLngLat(longitude.toDouble(),latitude.toDouble())
                    addViewAnnotation(point)
//                    updateTextView(routeNum.toString())
                }

            }
        }
        return root
    }

    //
    //Add a view annotation to the mapview
    //

    private fun addViewAnnotation(point: Point) {
        // Define the view annotation
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            // Specify the layout resource id
            resId = R.layout.layout_annotation,

            // Set any view annotation options
            options = viewAnnotationOptions {
                geometry(point)
            }
        )

        LayoutAnnotationBinding.bind(viewAnnotation)

    }

    private fun updateTextView(routeId: String) {
        // Access TextView directly from the binding class
        val textView : TextView? = view?.findViewById(R.id.annotationRoute)
        textView?.text = routeId;
    }


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
