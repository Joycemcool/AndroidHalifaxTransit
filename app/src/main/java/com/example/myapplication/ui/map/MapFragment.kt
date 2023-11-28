package com.example.myapplication.ui.map


//import com.example.myapplication.mapView


//ViewAnnotationBasic
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.databinding.LayoutAnnotationBinding
import com.google.transit.realtime.GtfsRealtime
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
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
    private lateinit var view: View

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

//        view = inflater.inflate(R.layout.layout_annotation, container, false)

//        binding.textHome.text = "This is the Home fragment"

        val latitude = arguments?.getDouble("latitude", 0.0)
        val longitude = arguments?.getDouble("longitude", 0.0)

        //Get mobile location from mainActivity
        val initialCameraOptions = CameraOptions.Builder()
            .center( Point.fromLngLat(longitude?:0.0,latitude?:0.0))
            .pitch(45.0)
            .zoom(15.5)
            .bearing(-17.6)
            .build()

        //Center map location
        mapView = binding.mapView;
        mapView?.getMapboxMap()?.setCamera(initialCameraOptions);
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)

        //Retrieve myRoutes file content from internal storage
        val filename = "myRoute"
        //Read content if file exist
        val fileContents : List<String>
        fileContents= context?.openFileInput(filename)?.bufferedReader()?.useLines { lines ->
            lines.map {it.toString() }
                .filterNotNull()
                .toList()
        } ?: emptyList()



        if (fileContents.isNotEmpty()) {
            // Test data is in 'fileContents'
            for (line in fileContents) {
                val textView = TextView(requireContext())

            }
        }



        // Create view annotation manager
        viewAnnotationManager = binding.mapView.viewAnnotationManager
        mapboxMap = binding.mapView.getMapboxMap().apply {
            // Load a map style
            loadStyleUri(Style.MAPBOX_STREETS) {

                // Add the view annotation at the center point
                val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
                val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())//cannot proceed feed

                for (entity in feed.entityList) {
                    if(entity.hasVehicle()){
                        val tripUpdate = entity.vehicle.trip
                        val longitude = entity.vehicle.position.longitude
                        val latitude = entity.vehicle.position.latitude
                        val routeId = entity.vehicle.trip.routeId;
                        val point : Point = Point.fromLngLat(longitude.toDouble(),latitude.toDouble())
//                        view = inflater.inflate(R.layout.layout_annotation, container, false)
//                        val textViewAnnotation: TextView = view.findViewById(R.id.annotationRoute)

//                        textViewAnnotation.text=routeId
                        addViewAnnotation(point,routeId)


                    }
                }
            }
        }


        return root
    }

    //
    //Add a view annotation to the mapview
    //

    private fun addViewAnnotation(point: Point, routeId: String) {
        // Define the view annotation
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            // Specify the layout resource id
        resId = R.layout.layout_annotation,
            // Set any view annotation options
            options = viewAnnotationOptions {
                geometry(point)
            }
        )
        val textViewAnnotation= viewAnnotation.findViewById<TextView>(R.id.annotationRoute)
        textViewAnnotation.text=routeId
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
