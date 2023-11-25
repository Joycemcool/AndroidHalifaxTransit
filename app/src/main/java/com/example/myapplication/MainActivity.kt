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
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


//var mapView: MapView? = null
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Get location from Start Activity
        val intent = intent
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        val currentLocation = "$latitude,$longitude"
        Log.i("Intent-Main", "latitude and longitude are " + latitude.toString()+longitude.toString());

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

        val bundle = Bundle().apply {
            putDouble("latitude",latitude)
            putDouble("longitude",longitude)
        }

        navController.navigate(R.id.navigation_map, bundle)
    }

//    private fun readTransitNumbersFromFile(fileName: String): List<String> {
//    val transitNumbers = mutableListOf<String>()
//
//    try {
//        // Open the file from the assets folder
//        val inputStream = assets.open(fileName)
//        val reader = BufferedReader(InputStreamReader(inputStream))
//
//        // Read each line, extract the first number, and add it to the list
//        reader.useLines { lines ->
//            lines.forEach { line ->
//                val transitNumber = line.split(",")[0].trim()
//                transitNumbers.add(transitNumber)
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//
//    return transitNumbers
//}

}

