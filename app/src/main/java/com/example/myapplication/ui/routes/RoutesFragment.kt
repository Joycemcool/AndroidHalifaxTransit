package com.example.myapplication.ui.routes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRoutesBinding
import java.io.File


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


        //Create file in internal storage
        val filename = "myRoute"
        context?.openFileOutput(filename, Context.MODE_PRIVATE).use {      }

        // Create the file and write some test data
        context?.openFileOutput(filename, Context.MODE_PRIVATE)?.use {
            val testData = listOf("One", "Two", "Three")
            it.write(testData.joinToString("\n").toByteArray())
        }

        val fileContents : List<String>
        fileContents= context?.openFileInput(filename)?.bufferedReader()?.useLines { lines ->
                lines.map {it.toString() }
                    .filterNotNull()
                    .toList()
            } ?: emptyList()

        if (fileContents.isNotEmpty()) {
            // Test data is in 'fileContents'
            for (line in fileContents) {
                println(line)
            }
        } else {
            // The file is empty or doesn't exist
            println("File is empty or doesn't exist.")
        }

        //
        //SetOnItemClickListener for autocompleteTextView
        //
        autoTransitNum.setOnItemClickListener {parent,view, position,id ->
           // var routeNumber : String;
            routeNumber=(adapter.getItem(position)?:"").toString()
            binding.textViewMyroutes.text = routeNumber


//            if(routeNumber!=null){
//
//
//                val fileContents = routeNumber
//
//
//                context?.openFileOutput(filename, Context.MODE_PRIVATE).use {
//                    it?.write(fileContents?.toByteArray())
//                }
//            }

        }




       // binding.textDashboard.text ="This is the Routes fragment"
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun fileExist(fname: String?): Boolean {
        val file: File? = context?.getFileStreamPath(fname)
        if (file != null) {
            return file.exists()
        }
        else return false;
    }
}