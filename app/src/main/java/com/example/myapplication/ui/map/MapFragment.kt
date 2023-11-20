package com.example.myapplication.ui.map


//import com.example.myapplication.mapView


//ViewAnnotationBasic
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentMapBinding
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.viewannotation.ViewAnnotationManager
//End example

var mapView: MapView? = null
class MapFragment : Fragment() {
    private var mapView: MapView? = null
    private var _binding: FragmentMapBinding? = null

    //viewAnnotationExample
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

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textHome.text = "This is the Home fragment"

        //Mapbox.getInstance(requireContext(), mapboxAccessToken)
        mapView = binding.mapView;
        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)

        viewAnnotationManager = binding.mapView.viewAnnotationManager

//    https://docs.mapbox.com/android/maps/examples/view-annotation-basic-add/

        return root
    }

    //
    //Add a view annotation to the mapview
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
