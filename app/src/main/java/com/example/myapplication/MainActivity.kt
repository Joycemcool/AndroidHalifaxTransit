package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


//var mapView: MapView? = null
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        // Allow network operations on the main thread
//        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
//        StrictMode.setThreadPolicy(policy)
//
//        //
//        //GTFS-realtime Language Binding
//        //
//        val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
//        val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())//cannot proceed feed
//        // val tripUpdates = mutableListOf<GtfsRealtime.TripUpdate>()
//        for (entity in feed.entityList) {
//           // Log.i("TRIP" + "id" + entity.id.toString())
//            Log.i("TRIP", "longitude" + entity.id + entity.vehicle.position.longitude);
//            Log.i("TRIP", "latitude" + entity.id + entity.vehicle.position.latitude);
//            if (entity.hasTripUpdate()) {
//                Log.i("entity realtime", "latitude and longitude are " + entity.tripUpdate);
//                Log.i("ENTITY", "oK");
//            }
//        }


        // Get location from Start Activity
        val intent = intent
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val currentLocation = "$latitude,$longitude"
        Log.i("Intent-Main", "latitude and longitude are " + latitude.toString()+longitude.toString());


//        mapView = findViewById(R.id.mapView)
//        mapView?.getMapboxMap()?.loadStyleUri(Style.MAPBOX_STREETS)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_map, R.id.navigation_routes, R.id.navigation_alerts
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

//    override fun onStart() {
//        super.onStart()
//        mapView?.onStart()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mapView?.onStop()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView?.onLowMemory()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView?.onDestroy()
//    }
}

