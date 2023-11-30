package com.example.myapplication.ui.map


//import com.example.myapplication.mapView


//ViewAnnotationBasic
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URL
import java.util.Timer

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

    //Add timer
    private val timer = Timer()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

    //From view annotation example
        super.onCreate(savedInstanceState)

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

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

//        Retrieve myRoutes file content from internal storage
        val filename = "routesFile"

        val fileContents= context?.openFileInput(filename)?.bufferedReader()?.useLines { lines ->
            lines.fold(""){some,text ->
                "$some\n$text"
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

//                GlobalScope.async {
//                    delay(20000)
                    for (entity in feed.entityList) {
                        if(entity.hasVehicle()){
                            val tripUpdate = entity.vehicle.trip
                            val longitude = entity.vehicle.position.longitude
                            val latitude = entity.vehicle.position.latitude
                            val routeId = entity.vehicle.trip.routeId;
                            val point : Point = Point.fromLngLat(longitude.toDouble(),latitude.toDouble())
//                            addViewAnnotation(point,routeId)
                            // Check if routeId exists in fileContents
                            val isRouteIdExist = fileContents?.contains(routeId)
                            if (isRouteIdExist == true){
                                addViewAnnotation(point, routeId,R.drawable.rounded_corner_view_highlighted )
                            }
                            else{ addViewAnnotation(point,routeId,R.drawable.rounded_corner_view)
                            }

                        }

                    }

                }
            }



        return root
    }

    //
    //Add a view annotation to the mapview
    //

    private fun addViewAnnotation(point: Point, routeId: String, drawableId : Int) {

//private fun addViewAnnotation(point: Point, routeId: String) {
        // Define the view annotation
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            // Specify the layout resource id
        resId = R.layout.layout_annotation,
            // Set any view annotation options
            options = viewAnnotationOptions {
                geometry(point)
            }
        )
        val backgroudView =viewAnnotation.findViewById<ConstraintLayout>(R.id.annotationLayout)
        val backgroudColor = ContextCompat.getDrawable(requireContext(),drawableId)
        backgroudView.background = backgroudColor
        val textViewAnnotation= viewAnnotation.findViewById<TextView>(R.id.annotationRoute)
        textViewAnnotation.text=routeId
    }

// Put add annotation view in a function

//   private fun updateRoute(fileContents : List<String>) = coroutineScope {
//        launch {
//            delay(10000)
//            mapboxMap = binding.mapView.getMapboxMap().apply {
//                // Load a map style
//                loadStyleUri(Style.MAPBOX_STREETS) {
//
//                    // Add the view annotation at the center point
//                    val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
//                    val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())//cannot proceed feed
//
//                        for (entity in feed.entityList) {
//                            if(entity.hasVehicle()){
//                                val tripUpdate = entity.vehicle.trip
//                                val longitude = entity.vehicle.position.longitude
//                                val latitude = entity.vehicle.position.latitude
//                                val routeId = entity.vehicle.trip.routeId;
//                                val point : Point = Point.fromLngLat(longitude.toDouble(),latitude.toDouble())
//
//                                // Check if routeId exists in fileContents
//                                val isRouteIdExist = fileContents.contains(routeId)
//                                if (isRouteIdExist){
//                                    addViewAnnotation(point, routeId,R.drawable.rounded_corner_view_highlighted )
//                                }else{ addViewAnnotation(point,routeId,R.drawable.rounded_corner_view)
//                                }
//
//                            }
//
//                        }
//
//                    }
//                }
//            }
//        }
//    }


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
