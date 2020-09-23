package com.aungpyaesone.hilt_demo_app.network

import com.aungpyaesone.hilt_demo_app.data.CharacterList
import com.aungpyaesone.hilt_demo_app.data.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

    @GET("character")
    suspend fun getAllCharacters() : Response<CharacterList>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id:Int) : Response<Character>
}