package com.bigbratan.emulair.fragmentCloudStates

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.EmulairApplication
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managerLayout.DynamicGridLayoutManager
import com.bigbratan.emulair.managerLayout.GridSpaceDecoration
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import android.widget.EditText


class CloudStatesFragment : Fragment(), SelectListener {
    @Inject
    lateinit var application: EmulairApplication

    private var cloudStatesAdapter: ImageAdapter? = null

    private lateinit var cloudStatesViewModel: CloudStatesViewModel

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_cloudstates, container, false)
        recyclerView = root.findViewById(R.id.recycler_view)
        return root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val factory = CloudStatesViewModel.Factory(requireActivity().application as EmulairApplication)
        cloudStatesViewModel = ViewModelProvider(this, factory)[CloudStatesViewModel::class.java]
        cloudStatesViewModel.fetchPhotos(null)
        cloudStatesViewModel.getAllFilesNames()


        val btnSearch = view.findViewById<View>(R.id.btnSearch)
        var editTextQuery = view.findViewById<EditText>(R.id.etSearch)
        btnSearch.setOnClickListener {
            val query = editTextQuery.text.toString()
            for (i in cloudStatesViewModel.allFileNames){
                if (i.contains(query)){
                    Log.d("myapp", "WITH PARAM" + i)
                }
            }
        }
        //observe the list and notify the adapter when the list changes
        cloudStatesViewModel.CloudStateList.observe(viewLifecycleOwner) { imageList ->
            cloudStatesAdapter = ImageAdapter(imageList, this )
            recyclerView?.adapter = cloudStatesAdapter
        }











        recyclerView?.apply {
            this.layoutManager = DynamicGridLayoutManager(context, 3)

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            GridSpaceDecoration.setSingleGridSpaceDecoration(this, spacingInPixels)
        }

        var visibleItemCount = 0
        var totalItemCount = 0
        var loadedItemCount = 0

        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = recyclerView.layoutManager!!.childCount
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val pastVisibleItems = (recyclerView.layoutManager as DynamicGridLayoutManager).findFirstVisibleItemPosition()
                loadedItemCount = visibleItemCount + pastVisibleItems

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !cloudStatesViewModel.isFetching){
                    cloudStatesViewModel.fetchPhotos(cloudStatesViewModel.nextPageToken)
                    cloudStatesAdapter?.notifyDataSetChanged()
                }
            }
        })







    }

    @dagger.Module
    class Module

    override fun onItemClicked(position: Int) {
        val clickedItem = cloudStatesViewModel.CloudStateList.value?.get(position)
        if (clickedItem != null) {
            cloudStatesViewModel.fetchStateToDisk(clickedItem.title,clickedItem.image)
        }

    }
}
