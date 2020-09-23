package com.aungpyaesone.hilt_demo_app.repo

import com.aungpyaesone.hilt_demo_app.datasource.CharacterRemoteDataSource
import com.aungpyaesone.hilt_demo_app.persistence.CharacterDao
import com.aungpyaesone.hilt_demo_app.util.performGetOperation
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource,
    private val localDataSource: CharacterDao
){
    fun getCharacterById(id: Int) = performGetOperation(
        databaseQuery = {localDataSource.getCharacterById(id)},
        networkCall = {remoteDataSource.getCharacterById(id)},
        saveCallResult = {localDataSource.insert(it)}
    )

    fun getCharacters() = performGetOperation(
        databaseQuery = {localDataSource.getAllCharacters()},
        networkCall = {remoteDataSource.getCharacters()},
        saveCallResult = {
           if(it.results.isNotEmpty()){
               localDataSource.insertAll(it.results)
           }
            }
    )
}