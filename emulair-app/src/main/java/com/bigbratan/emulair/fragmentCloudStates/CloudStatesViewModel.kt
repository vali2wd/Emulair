package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.bigbratan.emulair.EmulairApplication
import com.bigbratan.emulair.common.managerSaves.StatesManager
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import javax.inject.Inject
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.component3

class CloudStatesViewModel(application: EmulairApplication) : AndroidViewModel(application) {

    class Factory(val application: EmulairApplication) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CloudStatesViewModel(application) as T
        }
    }

    @Inject
    lateinit var statesManager: StatesManager
    private val storagePreviewsRef = FirebaseStorage.getInstance().reference.child("State Previews")
    private val storageStatesRef = FirebaseStorage.getInstance().reference.child("States")
    var nextPageToken: String? = null
    var isLoading = MutableLiveData<Boolean>()
    var isFetching = false
    var CloudStateList = MutableLiveData<List<CloudState>>()
    var statesList = mutableListOf<CloudState>()
    public var allFileNames = mutableListOf<String>()


    // make isLoading observable

    fun fetchStateToDisk(nameOfPreview: String, image: Bitmap?) {
        //remove what comes after last dot
        val nameOfState = nameOfPreview.substringBeforeLast(".")
        storageStatesRef.child(nameOfState).getBytes(1024 * 1024).addOnSuccessListener { bytes ->
            val folderNameStates = "states/mgba"
            val folderNamePreviews = "state-previews"
            val fileNameState = nameOfState
            val fileNamePreview = nameOfPreview
//            val File = File(getApplication<EmulairApplication>().getExternalFilesDir(folderNamePreviews), fileNamePreview)
            val file = File(
                getApplication<EmulairApplication>().getExternalFilesDir(folderNameStates),
                fileNameState.replace("_vali2wd", "")
            )
            try {
                file.writeBytes(bytes)
                //TODO: show snackbar to confirm download
                Toast.makeText(
                    getApplication<EmulairApplication>().applicationContext,
                    "Downloaded state: $fileNameState",
                    Toast.LENGTH_SHORT
                ).show()
                //TODO: also write preview file to disk
                //TODO: deal with folder to which the save is written (gba is /mgba)
//                val outputStream = File.outputStream()
//                image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                outputStream.flush()
//                outputStream.close()
            } catch (e: Exception) {
                Log.d("myapp", "fetchStateToDisk: " + e.message)
            }

        }.addOnFailureListener { exception ->
            Log.d("myapp", "onFailure: " + exception.message)
        }
    }

    // You'll need to import com.google.firebase.storage.ktx.component1 and
    // com.google.firebase.storage.ktx.component2
//    fun listAllPaginated(pageToken: String?){
//        val listPageTask = if (pageToken != null) {
//            storagePreviewsRef.list(100, pageToken)
//        } else {
//            storagePreviewsRef.list(100)
//        }
//
//        listPageTask
//            .addOnSuccessListener { (items, prefixes, pageToken) ->
//
//            }
//
//    }
    fun getAllFilesNames() {
        val fileNames = mutableListOf<String>()
        storagePreviewsRef.listAll().addOnSuccessListener { listResult ->
            for (item in listResult.items) {
                allFileNames.add(item.name)
            }
        }

    }

    //    fun getAllFilesNames() {
//        val list = mutableListOf<String>()
//        storagePreviewsRef.listAll().addOnSuccessListener { listResult ->
//            for (item in listResult.items) {
//                allFileNames.add(item.name)
//            }
//        }
//    }
    // Primitive pagination for listing files
    // Actually gets a list of ALL files, but only gets bytes for the 10 at a time
    var allItemsLoaded = false
    fun fetchPhotos(pageToken: String?) {
        if (allItemsLoaded) {
            return
        }
        if (isFetching) {
            return
        }
        isFetching = true
        val listPageTask = if (pageToken != null) {
            storagePreviewsRef.list(10, pageToken)
        } else {
            storagePreviewsRef.list(10)
        }

        listPageTask
            .addOnSuccessListener { (items, prefixes, pageToken) ->
//                nextPageToken = pageToken
                isLoading.postValue(true)
                val imageList = mutableListOf<Bitmap>()
                for (item in items) {
                    item.getBytes(1024 * 1024).addOnSuccessListener { bytes ->
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                        statesList.add(CloudState(item.name, bitmap))
                        Log.d("myapp", "fetchPhotos: " + item.name)
//                        statesList.add(CloudState(item.name, bitmap))
                        CloudStateList.value = statesList
                    }

                }
                isFetching = false
                isLoading.postValue(false)
                if (pageToken == null) {
                    allItemsLoaded = true
                } else {
                    nextPageToken = pageToken
                }
            }
            .addOnFailureListener { exception ->
                isFetching = false
                isLoading.postValue(false)
            }
    }





}
