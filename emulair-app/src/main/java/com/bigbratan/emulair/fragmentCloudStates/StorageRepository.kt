package com.bigbratan.emulair.fragmentCloudStates

import android.graphics.BitmapFactory
import android.util.Log
import com.bigbratan.emulair.activityCrash.GameCrashActivity.Companion.launch
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import kotlin.system.measureTimeMillis

class StorageRepository(
) {
    // it would have been possible to create a Firebase Accessor class, but I decided to keep it simple

    private val storagePreviewsRef = FirebaseStorage.getInstance().reference.child("State Previews")
    private val storageStatesRef = FirebaseStorage.getInstance().reference.child("States")
    private var namesOfAllFiles = mutableListOf<String>()

    //assign name of all files from function getname
    init {
        // run blocking; va bloca main pentru a avea acces definitiv la toate numele fisierelor
        runBlocking {
            var results = getNameOfAllFiles()
            namesOfAllFiles.addAll(results)
//            Log.d("StorageRepository", "init: ${namesOfAllFiles}")
        }

        // DEMO DESPRE CUM SA IEI IMAGINILE PREVIEW
        // DEMO DESPRE CUM SA IEI IMAGINILE PREVIEW
        // DEMO DESPRE CUM SA IEI IMAGINILE PREVIEW
//        GlobalScope.launch {
//            var x = fetchStatesByPage(4, 5)
//            if (x.size > 0) Log.d("StorageRepository", "fetched these: ${x[0].title} ${x[1].title} ${x[2].title} ${x[3].title} ${x[4].title}")
//            else Log.d("StorageRepository", "fetched nothing")
//        }
    }

    // o functie suspend, dar listAll are await; se asteapta finalizarea executiei pana la continuare
    // problema generatoare de numeroase ore pierdute: unresolved reference await(); se rezolva prin import playservices
    suspend fun getNameOfAllFiles(): List<String> {
        val results = try{
            storageStatesRef.listAll().await()
        } catch (e: Exception){
            Log.d("StorageRepository", "getNameOfAllFiles: ${e.message}")
            null
        }
        return results?.items?.map { it.name } ?: emptyList()
    }

    // o functie care ofera paginare primitiva; astept sa iau toate numele de fisiere din CLOUD STORAGE
    // descarc si decodez doar previewurile, nu si states
    suspend fun fetchStatesByPage(page: Int, pageSize: Int): MutableList<CloudState> {
        //startindex is null if larger than namesofallfiles.size, or page*pageSize

        val startIndex = page * pageSize
        if (startIndex >= namesOfAllFiles.size) return mutableListOf()
        var endIndex = startIndex + pageSize
        val endIndexLimited = if (endIndex > namesOfAllFiles.size - 1) namesOfAllFiles.size - 1 else endIndex

        endIndex = endIndexLimited

        var miniListOfNames = namesOfAllFiles.subList(startIndex, endIndex)
        var miniListOfStates = mutableListOf<CloudState>()

        val job1 = GlobalScope.launch(Dispatchers.IO){
            for (name in miniListOfNames){
                Log.d("StorageRepository", "fetchStatesByPage: $name")
                val previewRef = storagePreviewsRef.child("$name.jpg")
                val bytes = previewRef.getBytes(1024*1024).await()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                miniListOfStates.add(CloudState(name, bitmap))
            }
        }
        job1.join()

        return miniListOfStates
    }

    suspend fun fetchByName(stateName: String): MutableList<CloudState> {
        var miniListOfStates = mutableListOf<CloudState>()
        val job1 = GlobalScope.launch(Dispatchers.IO){
            for (name in namesOfAllFiles){
                if (name.contains(stateName)){
                    val previewRef = storagePreviewsRef.child("$name.jpg")
                    val bytes = previewRef.getBytes(1024*1024).await()
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    miniListOfStates.add(CloudState(name, bitmap))
                }
            }
        }
        job1.join()
        Log.d("StorageRepository", "fetchByName: ${miniListOfStates.size}")
        return miniListOfStates
    }


//    suspend fun getNameOfAllFiles(): List<String> {
//        val allFileNames = mutableListOf<String>()
//        val completableDeferred = CompletableDeferred<Unit>()
//
//        storagePreviewsRef.listAll().addOnSuccessListener { listResult ->
//            listResult.items.forEach { item ->
//                allFileNames.add(item.name)
//            }
//            completableDeferred.complete(Unit)
//        }
//
//        completableDeferred.await()
//        return allFileNames
//    }
//
//    fun getSpecifiedPreviews(previewNames: List<String>): List<CloudState>{
//        var statesList = mutableListOf<CloudState>()
//        for (previewName in previewNames){
//            val previewRef = storagePreviewsRef.child(previewName)
//            previewRef.getBytes(1024*1024).addOnSuccessListener { bytes ->
//                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                statesList.add(CloudState(previewName, bitmap))
//            }
//        }
//        return statesList
//    }

}
