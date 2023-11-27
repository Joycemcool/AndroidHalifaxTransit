package com.example.myapplication.ui.routes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRoutesBinding


class RoutesFragment : Fragment() {

    private var _binding: FragmentRoutesBinding? = null
    private val binding get() = _binding!!
    private var routeNumber : String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRoutesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //
        //Populate autocompleteTextView
        //
        val autoTransitNum = binding.autoCompleteTextViewSearch
        val transitRoutesArray : Array<String> = resources.getStringArray((R.array.transit_numbers_array))
        Log.i("ReadFromString",transitRoutesArray.toString())

        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,transitRoutesArray)
        autoTransitNum.setAdapter(adapter)
        autoTransitNum.threshold=1

        //
        //SetOnItemClickListener for autocompleteTextView
        //
        autoTransitNum.setOnItemClickListener {parent,view, position,id ->
           // var routeNumber : String;
            routeNumber=(adapter.getItem(position)?:"").toString()
            binding.textViewMyroutes.text = routeNumber
        }




        binding.textDashboard.text ="This is the Routes fragment"
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}