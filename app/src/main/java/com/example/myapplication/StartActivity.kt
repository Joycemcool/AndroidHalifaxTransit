package com.example.myapplication
//First week submit Nov.12

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
class StartActivity : AppCompatActivity() {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        // Allow network operations on the main thread
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

//        //
//        //GTFS-realtime Language Binding
//        //
//        val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
//        val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())//cannot proceed feed
//        // val tripUpdates = mutableListOf<GtfsRealtime.TripUpdate>()
//        val intentList = ArrayList<Intent>()
//        for (entity in feed.entityList) {
//            // Log.i("TRIP" + "id" + entity.id.toString())
//            Log.i("TRIP", "longitude - StartActivity" + entity.id + entity.vehicle.position.longitude);
//            Log.i("TRIP", "latitude" + entity.id + entity.vehicle.position.latitude);
//            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("latitude", entity.vehicle.position.longitude)
//            intent.putExtra("longitude", entity.vehicle.position.latitude)
//            if (entity.hasTripUpdate()) {
//                Log.i("entity realtime", "latitude and longitude are " + entity.tripUpdate);
//                Log.i("ENTITY", "oK");
//            }
//            intentList.add(intent)
//        }
//        val mainIntent = Intent(this, MainActivity::class.java)
//        mainIntent.putParcelableArrayListExtra("intent_list", intentList)
//        startActivity(mainIntent)
//
//        startActivity(intent)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            }
        }
    }//Ed onRequestPermissionResult

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission Granted - Get location from device
            fusedLocationProviderClient!!.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    val latitude = location.latitude
                    val longitude = location.longitude
                    val intent = Intent(this@StartActivity, MainActivity::class.java)
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    startActivity(intent)
                    Log.i("Intent", "latitude and longitude are " + latitude.toString()+longitude.toString());
                }
        } else {
            // Permission Denied - Ask the user for permission
            askPermission()
        }
    }//End getLocation function

    private fun askPermission() {
        // Display screen to request permission
        ActivityCompat.requestPermissions(
            this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }
}