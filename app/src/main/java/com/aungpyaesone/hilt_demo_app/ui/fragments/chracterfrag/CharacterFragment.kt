package com.aungpyaesone.hilt_demo_app.ui.fragments.chracterfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aungpyaesone.hilt_demo_app.R
import com.aungpyaesone.hilt_demo_app.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_character.*
import kotlinx.android.synthetic.main.fragment_character.view.*

@AndroidEntryPoint
class CharacterFragment : Fragment(),CharactersAdapter.CharacterItemListener {

    private lateinit var adapter: CharactersAdapter
    private val viewModel: CharacterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_character,container,false)
        setupRecyclerView(v)
        subscribeObserver(v)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun subscribeObserver(view: View) {
        viewModel.characters.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS ->{
                    view.progress_bar.visibility = View.GONE
                    if(it.data != null){
                        adapter.setItems(ArrayList(it.data))
                    }
                }
                Resource.Status.ERROR ->{
                    view.progress_bar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->{
                    view.progress_bar.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupRecyclerView(view: View) {
        adapter = CharactersAdapter(this)
        view.characters_rv.layoutManager = LinearLayoutManager(activity)
        view.characters_rv.adapter = adapter
    }

    override fun onClickedCharacter(characterId: Int) {
        findNavController().navigate(
            R.id.action_characterFragment_to_characterDetailFragments2,
            bundleOf("id" to characterId)
        )
    }

}