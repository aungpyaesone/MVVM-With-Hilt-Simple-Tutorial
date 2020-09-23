package com.aungpyaesone.hilt_demo_app.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.aungpyaesone.hilt_demo_app.R
import com.aungpyaesone.hilt_demo_app.data.Character
import com.aungpyaesone.hilt_demo_app.util.Resource
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_character.progress_bar
import kotlinx.android.synthetic.main.fragment_character_detail.*

@AndroidEntryPoint
class CharacterDetailFragments : Fragment() {

    private val viewModel : CharacterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_character_detail,container,false)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObserver()
        return v
    }

    private fun setupObserver(){
        viewModel.character.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS ->{
                    bindCharacter(it.data!!)
                    progress_bar.visibility = View.GONE
                    character_cl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->{
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING ->{
                    progress_bar.visibility = View.VISIBLE
                    character_cl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindCharacter(character: Character){
        name.text = character.name
        species.text = character.species
        status.text = character.status
        gender.text = character.gender
        Glide.with(image)
            .load(character.image)
            .transform(CircleCrop())
            .into(image)
    }
}