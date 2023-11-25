package com.example.myapplication.ui.map


//import com.example.myapplication.mapView


//ViewAnnotationBasic
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        Log.i("Passtomagfragment",latitude.toString())

        //Get mobile location from mainActivity

        val initialCameraOptions = CameraOptions.Builder()
            .center( Point.fromLngLat(longitude?:0.0,latitude?:0.0))
            .pitch(45.0)
            .zoom(15.5)
            .bearing(-17.6)
            .build()

        mapView = binding.mapView;
        mapView?.getMapboxMap()?.setCamera(initialCameraOptions);
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)

        // Create view annotation manager
        viewAnnotationManager = binding.mapView.viewAnnotationManager

        mapboxMap = binding.mapView.getMapboxMap().apply {
            // Load a map style
            loadStyleUri(Style.MAPBOX_STREETS) {

                // Get the center point of the map
                //val center = mapboxMap.cameraState.center
//                val center = LngLat(-73.9397, 40.8002);

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
//                        Log.i("TextView",textViewAnnotation.text.toString())
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

    private fun addViewAnnotation(point: Point, string: String) {
        // Define the view annotation
        val viewAnnotation = viewAnnotationManager.addViewAnnotation(
            // Specify the layout resource id
            resId = R.layout.layout_annotation,

            // Set any view annotation options
            options = viewAnnotationOptions {
                geometry(point)
            }
        )
        val binding = LayoutAnnotationBinding.bind(viewAnnotation)
//        LayoutAnnotationBinding.bind(viewAnnotation)
        binding.annotationRoute.text = string

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
