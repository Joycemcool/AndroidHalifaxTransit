package com.example.myapplication.ui.map


//import com.example.myapplication.mapView
import android.annotation.SuppressLint

import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.gestures.*
import com.mapbox.maps.viewannotation.ViewAnnotationUpdateMode
import com.mapbox.maps.viewannotation.viewAnnotationOptions


//ViewAnnotationBasic
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapBinding
import com.example.myapplication.databinding.LayoutAnnotationBinding
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.viewannotation.ViewAnnotationManager
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

//    val viewAnnotationManager: AnnotationManager
//        get() = binding.mapView.viewAnnotationManager
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
                val center = mapboxMap.cameraState.center
                // Add the view annotation at the center point
                addViewAnnotation(center)
            }
    }
//    https://docs.mapbox.com/android/maps/examples/view-annotation-basic-add/

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
