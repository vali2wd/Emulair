package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.bigbratan.emulair.EmulairApplication
import com.bigbratan.emulair.common.managerSaves.StatesManager
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject

class CloudStatesViewModel(application: EmulairApplication) : AndroidViewModel(application) {

    class Factory(val application: EmulairApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CloudStatesViewModel(application) as T
        }
    }




    // BEGIN OF OLD CODE
    // BEGIN OF OLD CODE
    // BEGIN OF OLD CODE
    // BEGIN OF OLD CODE
    // BEGIN OF OLD CODE
    // BEGIN OF OLD CODE
    @Inject
    lateinit var statesManager: StatesManager
    var storageRepository: StorageRepository = StorageRepository()
    var cloudStatesToShow = MutableLiveData<List<CloudState>>()
    private var allCloudStateNames: List<String>? = null
    private var allCloudStateNamesDeferred: Deferred<List<String>>? = null

    init{
        GlobalScope.launch {
            fetchPaginated(0, 7)
        }
    }

    fun fetchPaginated(page: Int, pageSize: Int) {
        GlobalScope.launch {
            var x = storageRepository.fetchStatesByPage(page, pageSize)
            cloudStatesToShow.postValue(x)
            if (x.size > 0) Log.d("StorageRepository", "fetched these: ${x[0].title} ${x[1].title} ...")
            else Log.d("StorageRepository", "fetched nothing")
        }
    }

//    init {
//        allCloudStateNamesDeferred = viewModelScope.async {
//            withContext(Dispatchers.IO) {
//                storageRepository.getNameOfAllFiles()
//
//            }
//        }
//    }





    //    @Inject
//    lateinit var statesManager: StatesManager
    private val storagePreviewsRef = FirebaseStorage.getInstance().reference.child("State Previews")
    private val storageStatesRef = FirebaseStorage.getInstance().reference.child("States")
//    var nextPageToken: String? = null
//    var isLoading = MutableLiveData<Boolean>()
//    var isFetching = false
//    var CloudStateList = MutableLiveData<List<CloudState>>()
//    var statesList = mutableListOf<CloudState>()
//    public var allFileNames = mutableListOf<String>()
//
//
//    // make isLoading observable
//
    // functia de salvare a starilor
    fun searchByName(Key: String){
        GlobalScope.launch {
            var x = storageRepository.fetchByName(Key)
            cloudStatesToShow.postValue(x)
            Log.d("StorageRepository", "fetched these: ${x.size} amt items")
        }
    }
    fun fetchStateToDisk(nameOfPreview: String, image: Bitmap?) {
        //remove what comes after last dot
//        val nameOfState = nameOfPreview.substringBeforeLast(".")
    // atentie ca nameofpreview e de fapt nameofstate
        val nameOfState = nameOfPreview
        storageStatesRef.child(nameOfState).getBytes(1024 * 1024).addOnSuccessListener { bytes_state ->
            val folderNameStates = "states/mgba"
            val folderNamePreviews = "state-previews/mgba"
            val fileNameState = nameOfState
            val fileNamePreview = nameOfPreview + ".jpg"
//            val File = File(getApplication<EmulairApplication>().getExternalFilesDir(folderNamePreviews), fileNamePreview)
            // salvarea state-ului la states/mgba
            val file_state = File(
                getApplication<EmulairApplication>().getExternalFilesDir(folderNameStates),
                fileNameState.replace(Regex("_[^.]*"), "")
            )
            val file_preview = File(
                getApplication<EmulairApplication>().getExternalFilesDir(folderNamePreviews),
                fileNamePreview.replace(Regex("_[^.]*"), "")
            )
            try {
                // scrierea efectiva a bitilor
                file_state.writeBytes(bytes_state)
                image?.compress(Bitmap.CompressFormat.PNG, 100, file_preview.outputStream())
                Toast.makeText(
                    getApplication<EmulairApplication>().applicationContext,
                    "Downloaded state: $fileNameState",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                Log.d("myapp", "fetchStateToDisk: " + e.message)
            }

        }.addOnFailureListener { exception ->
            Log.d("myapp", "onFailure: " + exception.message)
        }


    }
//
//
//    fun getByFileName(filename: List<String>){
//        if (filename.isEmpty()){
//            nextPageToken = null
//            fetchPhotos(nextPageToken)
//        }
//        else{
//            statesList.clear()
//            CloudStateList.value = statesList
//
//            for (item in filename){
//                Log.d("myapp", "getByFileName: " + item)
//                storagePreviewsRef.child(item).getBytes(1024 * 1024).addOnSuccessListener { bytes ->
//                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                    statesList.add(CloudState(item, bitmap))
////                    CloudStateList.value = statesList
//                    if ( statesList.size % 8 == 0){
//                        Log.d("myapp", "transfer to cloudstatelist")
//                        CloudStateList.value = statesList
//                    }
//                }
//            }
////            CloudStateList.value = statesList
//
//
//        }
//    }
//    fun getAllFilesNames() {
//        val fileNames = mutableListOf<String>()
//        storagePreviewsRef.listAll().addOnSuccessListener { listResult ->
//            for (item in listResult.items) {
//                allFileNames.add(item.name)
//            }
//        }
//
//    }
//
//    //    fun getAllFilesNames() {
////        val list = mutableListOf<String>()
////        storagePreviewsRef.listAll().addOnSuccessListener { listResult ->
////            for (item in listResult.items) {
////                allFileNames.add(item.name)
////            }
////        }
////    }
//    // Primitive pagination for listing files
//    // Actually gets a list of ALL files, but only gets bytes for the 10 at a time
//    var allItemsLoaded = false
//    fun fetchPhotos(pageToken: String?) {
//        if (allItemsLoaded) {
//            return
//        }
//        if (isFetching) {
//            return
//        }
//        isFetching = true
//        val listPageTask = if (pageToken != null) {
//            storagePreviewsRef.list(10, pageToken)
//        } else {
//            storagePreviewsRef.list(10)
//        }
//
//        listPageTask
//            .addOnSuccessListener { (items, prefixes, pageToken) ->
////                nextPageToken = pageToken
//                isLoading.postValue(true)
//                val imageList = mutableListOf<Bitmap>()
//                var sizeofresp = items.size
//                for (item in items) {
//                    item.getBytes(1024 * 1024).addOnSuccessListener { bytes ->
//                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//
//                        statesList.add(CloudState(item.name, bitmap))
//                        Log.d("myapp", "fetchPhotos: " + item.name)
////                        statesList.add(CloudState(item.name, bitmap))
////                        AICI FACEAM  ASTA ->CloudStateList.value = statesList
//                        if ( statesList.size % 10 == 0){
//                            Log.d("myapp", "transfer to cloudstatelist")
//                            CloudStateList.value = statesList
//                        }
//                    }.addOnFailureListener { exception ->
//                        //pass this
//                        sizeofresp--
//                        if (statesList.size == sizeofresp){
//                            CloudStateList.value = statesList
//                        }
//
//                        Log.d("myapp", "onFailure: " + exception.message) }
//
//                }
////                CloudStateList.value = statesList
//                //notify observers that the list has changed
//                // this is enough? or do I need to set the value of the list again?
////                CloudStateList.postValue(statesList)
////                Log.d("myapp", "Finish transfer to cloudstatelist")
//                isFetching = false
//                isLoading.postValue(false)
//                if (pageToken == null) {
//                    allItemsLoaded = true
//                } else {
//                    nextPageToken = pageToken
//                }
//            }
//            .addOnFailureListener { exception ->
//                isFetching = false
//                isLoading.postValue(false)
//            }
//    }





}
