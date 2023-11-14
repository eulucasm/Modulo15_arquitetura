package com.lucao.hqawasomeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.lucao.hqawasomeapp.placeholder.PlaceholderContent

class HQFragment : Fragment(), HQItemListener {

    private var columnCount = 1
    private val viewModel by navGraphViewModels<HQViewModel>(R.id.hq_graph) { defaultViewModelProviderFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyhqRecyclerViewAdapter(PlaceholderContent.ITEMS, this@HQFragment)
            }
        }
        return view
    }

    override fun onItemSelected(position: Int) {
        findNavController().navigate(R.id.HQDetailsFragment)
    }

}