package com.aungpyaesone.hilt_demo_app.datasource

import com.aungpyaesone.hilt_demo_app.network.CharacterService
import com.aungpyaesone.hilt_demo_app.util.BaseDataSource
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(private val characterService: CharacterService)
    : BaseDataSource()
{
    suspend fun getCharacters() = getResult { characterService.getAllCharacters() }
    suspend fun getCharacterById(id:Int) = getResult { characterService.getCharacter(id) }
}