package com.bigbratan.emulair.fragmentAllGames

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bigbratan.emulair.R
import com.bigbratan.emulair.managerLayout.DynamicGridLayoutManager
import com.bigbratan.emulair.managerLayout.GridSpaceDecoration
import com.bigbratan.emulair.managerInteraction.GameInteractor
import com.bigbratan.emulair.managerCovers.CoverLoader
import com.bigbratan.emulair.common.utils.coroutines.launchOnState
import com.bigbratan.emulair.common.metadataRetrograde.db.RetrogradeDatabase
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AllGamesFragment : Fragment() {

    @Inject
    lateinit var retrogradeDb: RetrogradeDatabase

    @Inject
    lateinit var gameInteractor: GameInteractor

    @Inject
    lateinit var coverLoader: CoverLoader

    private lateinit var allGamesViewModel: AllGamesViewModel

    protected var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_basic, container, false)
        recyclerView = root.findViewById(R.id.recycler_view)
        return root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = AllGamesViewModel.Factory(retrogradeDb)
        allGamesViewModel = ViewModelProvider(this, factory)[AllGamesViewModel::class.java]
        val gamesAdapter = AllGamesAdapter(R.layout.layout_card_game_vert_medium, gameInteractor, coverLoader)

        launchOnState(Lifecycle.State.RESUMED) {
            allGamesViewModel.allGames
                .collect {
                    gamesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
        }

        recyclerView?.apply {
            this.adapter = gamesAdapter
            this.layoutManager = DynamicGridLayoutManager(context)

            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            GridSpaceDecoration.setSingleGridSpaceDecoration(this, spacingInPixels)
        }
    }

    @dagger.Module
    class Module
}
