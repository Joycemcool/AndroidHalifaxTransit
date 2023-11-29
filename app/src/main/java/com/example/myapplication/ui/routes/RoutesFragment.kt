package com.example.myapplication.ui.routes

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.transition.Visibility.Mode
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
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        _binding = FragmentRoutesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //
        //Populate autocompleteTextView
        //
        val autoTransitNum = binding.autoCompleteTextViewSearch
        val transitRoutesArray : Array<String> = resources.getStringArray((R.array.transit_numbers_array))
        val adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,transitRoutesArray)
        autoTransitNum.setAdapter(adapter)
        autoTransitNum.threshold=1

        //Create empty file in internal storage if it doesn't exist

        val filename = "myRoute"
        //Read content if file exist
        val fileContents : List<String>
        fileContents= context?.openFileInput(filename)?.bufferedReader()?.useLines { lines ->
                lines.map {it.toString() }
                    .filterNotNull()
                    .toList()
            } ?: emptyList()

        val linearLayoutMyRoutes: LinearLayout = binding.linearLayoutMyRoutes

        if (fileContents.isNotEmpty()) {
            // Test data is in 'fileContents'
            for (line in fileContents) {
                val textView = TextView(requireContext())
                textView.text = line
                linearLayoutMyRoutes.addView(textView)
            }
        }else{
            context?.openFileOutput(filename, Context.MODE_APPEND).use {      }
        }

        //
        //SetOnItemClickListener for autocompleteTextView
        //
        autoTransitNum.setOnItemClickListener {parent,view, position,id ->
           // var routeNumber : String;
            routeNumber=(adapter.getItem(position)?:"").toString()
//            binding.textViewMyroutes.text = routeNumber
            binding.buttonAdd.setOnClickListener {
                //Append selected route to file
                context?.openFileOutput(filename, Context.MODE_APPEND)?.use {
                    it.write(routeNumber!!.toByteArray())
                    it.write("\n".toByteArray())
                    val textView = TextView(requireContext())
                    textView.text = routeNumber
                    linearLayoutMyRoutes.addView(textView)
                }
            }
        }//End autocompletetext view

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