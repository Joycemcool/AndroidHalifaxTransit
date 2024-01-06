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
import android.widget.Toast
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

        val filename = "routesFile"
        val file = File(context?.filesDir, filename)
        val defaultContent = ""
        var fileContents = ""

        if(file.exists()){
            fileContents= context?.openFileInput(filename)?.bufferedReader()?.useLines { lines ->
                lines.fold(""){some,text ->
                    "$some\n$text"
                }
            }.toString()
        }

        val linearLayoutMyRoutes: LinearLayout = binding.linearLayoutMyRoutes

//      Delete Route Button functionality
        binding.btnDeleteRoutes.setOnClickListener(){
            context?.openFileOutput(filename, Context.MODE_PRIVATE)?.use {
            it.write(defaultContent!!.toByteArray())
             }
            //Set fileContent empty
            fileContents=""
            linearLayoutMyRoutes.removeAllViews()
        }

        //Populate ScrollView
        if (fileContents.isNotEmpty()) {
            val contentArray = fileContents.split(",").toTypedArray()
            for(item in contentArray){
                val textView = TextView(requireContext())
                textView.text = item
                textView.textSize=18f
                linearLayoutMyRoutes.addView(textView)
            }
        }else{
            context?.openFileOutput(filename, Context.MODE_PRIVATE).use {
                if (it != null) {
                    it.write(defaultContent!!.toByteArray())
                }
            }
        }

        //
        //SetOnItemClickListener for autocompleteTextView
        //

        autoTransitNum.setOnItemClickListener {parent,view, position,id ->
           // var routeNumber : String;
            routeNumber=(adapter.getItem(position)?:"").toString()
            val routeExist =false;
            if (!fileContents.contains(routeNumber!!,ignoreCase = true)){
                fileContents += ",$routeNumber"
                binding.buttonAdd.setOnClickListener {
                    //Write updated string into file

                    context?.openFileOutput(filename, Context.MODE_PRIVATE)?.use {
                        it.write(fileContents!!.toByteArray())
                    }
                    //Add text view to linearLayout
                    val textView = TextView(requireContext())
                    textView.text = routeNumber
                    textView.textSize=18f

                    linearLayoutMyRoutes.addView(textView)
                }
            }
            else{
                Toast.makeText(context, "Route already in favorites", Toast.LENGTH_SHORT).show();
            }

            }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}//End RoutesFragment
