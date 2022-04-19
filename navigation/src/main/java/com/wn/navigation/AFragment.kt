package com.wn.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs

class AFragment : Fragment() {
    val args: AFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("fragment", "A onCreate")
    }

    private var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("fragment", "A onCreateView")
        if (rootView == null){
            Log.i("fragment", "rootView == null")
            rootView = inflater.inflate(R.layout.fragment_a, container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val content: TextView = view.findViewById(R.id.content)

        Log.i("fragment", "A onViewCreated")
        content.text = if (args.type == 0) "车牌列表" else "选择车牌"
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.i("fragment", "A Destroy")
    }
}