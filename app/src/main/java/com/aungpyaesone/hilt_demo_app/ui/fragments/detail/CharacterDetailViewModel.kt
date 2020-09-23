package com.aungpyaesone.hilt_demo_app.ui.fragments.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.aungpyaesone.hilt_demo_app.data.Character
import com.aungpyaesone.hilt_demo_app.repo.CharacterRepository
import com.aungpyaesone.hilt_demo_app.util.Resource

class CharacterDetailViewModel @ViewModelInject constructor(private val repository: CharacterRepository):ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _character = _id.switchMap { id ->
        repository.getCharacterById(id)
    }

    val character: LiveData<Resource<Character>> = _character

    fun start(id:Int){
        _id.value = id
    }

}