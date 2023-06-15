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
import com.google.firebase.storage.FirebaseStorage


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

    @dagger.Module
    class Module
    override fun onItemClicked(position: Int) {
        val cloudState = cloudStatesAdapter?.differ?.currentList?.get(position)
        //Log name
        cloudStatesViewModel.fetchStateToDisk(cloudState?.title.toString(), cloudState?.image)
        Log.d("myapp", "clicked on: " + cloudState?.title)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = CloudStatesViewModel.Factory(requireActivity().application as EmulairApplication)
        cloudStatesViewModel = ViewModelProvider(this, factory)[CloudStatesViewModel::class.java]
        cloudStatesAdapter = ImageAdapter(this)

        recyclerView?.adapter = cloudStatesAdapter
        recyclerView?.apply {
            this.layoutManager = DynamicGridLayoutManager(context, 3)
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            GridSpaceDecoration.setSingleGridSpaceDecoration(this, spacingInPixels)
        }

        cloudStatesViewModel.cloudStatesToShow.observe(viewLifecycleOwner) { imageList ->
            Log.d("myapp", "LIST CHANGED" + imageList.size)
            cloudStatesAdapter?.differ?.submitList(imageList)
        }



        val btnSearch = view.findViewById<View>(R.id.btnSearch)
        var editTextQuery = view.findViewById<EditText>(R.id.etSearch)
        val btnNext = view.findViewById<View>(R.id.btnNextPage)
        val btnPrev = view.findViewById<View>(R.id.btnPreviousPage)
        var pageNumber = 0

        btnSearch.setOnClickListener{
            val query = editTextQuery.text.toString()
            cloudStatesViewModel.searchByName(query)
        }

        btnNext.setOnClickListener {
            pageNumber++
            cloudStatesViewModel.fetchPaginated(pageNumber,7)
        }
        btnPrev.setOnClickListener {
            if (pageNumber > 0){
                pageNumber--
                cloudStatesViewModel.fetchPaginated(pageNumber,7)
            }
        }



        // BEGIN OF OLD CODE
        // BEGIN OF OLD CODE
        // BEGIN OF OLD CODE
        // BEGIN OF OLD CODE
        // BEGIN OF OLD CODE
        // BEGIN OF OLD CODE
//        val factory = CloudStatesViewModel.Factory(requireActivity().application as EmulairApplication)
//        cloudStatesViewModel = ViewModelProvider(this, factory)[CloudStatesViewModel::class.java]
//        cloudStatesViewModel.fetchPhotos(null)
//        cloudStatesViewModel.getAllFilesNames()
//        cloudStatesAdapter = ImageAdapter(this)
//        recyclerView?.adapter = cloudStatesAdapter
//
//        //observe the list and notify the adapter when the list changes with differ
//        cloudStatesViewModel.CloudStateList.observe(viewLifecycleOwner) { imageList ->
//            Log.d("myapp", "LIST CHANGED" + imageList.size)
//            cloudStatesAdapter?.differ?.submitList(imageList)
//        }
//
//        val btnSearch = view.findViewById<View>(R.id.btnSearch)
//        var editTextQuery = view.findViewById<EditText>(R.id.etSearch)
//        btnSearch.setOnClickListener {
//            val newlist = mutableListOf<String>()
//            val query = editTextQuery.text.toString()
//            for (i in cloudStatesViewModel.allFileNames){
//                if (i.contains(query)){
//                    Log.d("myapp", "WITH PARAM" + i)
//                    newlist.add(i)
//                }
//            }
////            cloudStatesAdapter!!.differ.currentList.clear()
//            // update the recycler view with the new list
////            recyclerView?.clearOnScrollListeners()
//            cloudStatesViewModel.getByFileName(newlist)
//            //cancel scroll listener
//
//        }
//
//        recyclerView?.apply {
//            this.layoutManager = DynamicGridLayoutManager(context, 3)
//
//            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
//            GridSpaceDecoration.setSingleGridSpaceDecoration(this, spacingInPixels)
//        }
//
//        var visibleItemCount = 0
//        var totalItemCount = 0
//        var loadedItemCount = 0
//
//        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val visibleItemCount = recyclerView.layoutManager!!.childCount
//                val totalItemCount = recyclerView.layoutManager!!.itemCount
//                val pastVisibleItems = (recyclerView.layoutManager as DynamicGridLayoutManager).findFirstVisibleItemPosition()
//                loadedItemCount = visibleItemCount + pastVisibleItems
//
//                if (visibleItemCount + pastVisibleItems >= totalItemCount && !cloudStatesViewModel.isFetching){
//                    cloudStatesViewModel.fetchPhotos(cloudStatesViewModel.nextPageToken)
//
//
//                }
//            }
//        })
//
//
//
//
//
//
//
//    }
//
//    @dagger.Module
//    class Module
//
//    override fun onItemClicked(position: Int) {
//        val clickedItem = cloudStatesViewModel.cloudStatesToShow.value?.get(position)
//        if (clickedItem != null) {
//            cloudStatesViewModel.fetchStateToDisk(clickedItem.title,clickedItem.image)
//        }
//
    }
}
