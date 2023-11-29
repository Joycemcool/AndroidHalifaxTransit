package com.example.myapplication.ui.alerts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentAlertsBinding
import com.google.transit.realtime.GtfsRealtime
import com.mapbox.geojson.Point
import java.net.URL


class AlertsFragment : Fragment() {

    private var _binding: FragmentAlertsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAlertsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        binding.textNotifications.text = "This is the Alerts fragment"
//        val textView: TextView = binding.textNotifications

        val url = URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb")
        val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())//cannot proceed feed

        for (entity in feed.entityList) {
            Log.i("alert test", "Feed in")
                if(entity.hasAlert()){
                    val alert = entity.alert.headerText.toString()
                    val linearLayoutAlerts: LinearLayout = binding.linearLayoutAlerts
                    Log.i("alert test", alert)
                    val textView = TextView(requireContext())
                    textView.text = alert
                    linearLayoutAlerts.addView(textView)
                }
                else{
                    val linearLayoutAlerts: LinearLayout = binding.linearLayoutAlerts
                    val textView = TextView(requireContext())
                    textView.text = "No alert, good luck"
                    linearLayoutAlerts.addView(textView)
                }

        }

            return root
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
}
