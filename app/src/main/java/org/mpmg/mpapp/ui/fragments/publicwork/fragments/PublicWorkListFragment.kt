package org.mpmg.mpapp.ui.fragments.publicwork.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_public_work_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.mpmg.mpapp.R
import org.mpmg.mpapp.ui.fragments.publicwork.adapters.PublicWorkListAdapter
import org.mpmg.mpapp.ui.viewmodels.PublicWorkViewModel

class PublicWorkListFragment : Fragment(), PublicWorkListAdapter.PublicWorkListAdapterListener {

    private val TAG = PublicWorkListFragment::class.java.name

    private val publicWorkViewModel: PublicWorkViewModel by sharedViewModel()

    private lateinit var publicWorkListAdapter: PublicWorkListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_public_work_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModels()
    }

    private fun setupViewModels() {
        publicWorkViewModel.getPublicWorkList()
            .observe(viewLifecycleOwner, Observer { publicWorkList ->
                publicWorkList ?: return@Observer

                publicWorkListAdapter.updatePublicWorksList(publicWorkList)
            })
    }

    private fun setupRecyclerView() {
        initPublicWorkAdapter()
        recyclerView_publicWorkListFragment_worksList.adapter = publicWorkListAdapter
        recyclerView_publicWorkListFragment_worksList.layoutManager = LinearLayoutManager(activity)
    }

    private fun initPublicWorkAdapter() {
        publicWorkListAdapter = PublicWorkListAdapter()
        publicWorkListAdapter.setPublicWorkListAdapterListener(this)
    }

    override fun onPublicWorkClicked(publicWorkId: String) {
        activity?.findNavController(R.id.nav_host_fragment)?.navigate(R.id.action_baseFragment_to_collectMainFragment)
    }
}