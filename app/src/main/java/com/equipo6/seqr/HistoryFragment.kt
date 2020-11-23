package com.equipo6.seqr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.equipo6.seqr.adapter.HistoryAdapter
import com.equipo6.seqr.adapter.HistoryListener
import com.equipo6.seqr.models.Tour
import com.equipo6.seqr.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_history.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment(), HistoryListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        this.viewModel.refresh()

        historyAdapter = HistoryAdapter(this)

        rvHistory.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }

        observeViewModel()
    }

    fun observeViewModel() {
        this.viewModel.listHistory.observe(viewLifecycleOwner, Observer<List<Tour>> {tour ->
            historyAdapter.updateData(tour)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            if(it != null) {
                rlLoadingHistory.visibility = View.INVISIBLE
            }
        })
    }

    override fun onHistoryClicked(tour: Tour, position: Int) {
        println(tour.toString())
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HistoryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}