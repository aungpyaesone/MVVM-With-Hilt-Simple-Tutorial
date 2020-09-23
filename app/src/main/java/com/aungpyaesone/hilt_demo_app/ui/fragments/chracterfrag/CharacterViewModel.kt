package com.aungpyaesone.hilt_demo_app.ui.fragments.chracterfrag

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.aungpyaesone.hilt_demo_app.repo.CharacterRepository

class CharacterViewModel @ViewModelInject constructor(
    private val repository: CharacterRepository
) : ViewModel(){
    val characters = repository.getCharacters()
}