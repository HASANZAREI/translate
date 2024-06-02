package com.example.translate.repositories

import com.example.translate.models.BaseModel
import com.example.translate.models.ResponseData
import com.example.translate.network.TranslationApi

class TranslationRepoImpl(
    private val api: TranslationApi
): TranslationRepo {
    override suspend fun translate(
        query: String,
        from: String,
        to: String
    ): BaseModel<ResponseData> {
        try {
            api.translate(
                query = query,
                langPair = "$from|$to"
            ).also {
                return if (it.isSuccessful) {
                    BaseModel.Success(data = it.body()!!)
                } else {
                    BaseModel.Error(message = it.errorBody()?.string().toString())
                }
            }
        } catch (e: Exception) {
            return BaseModel.Error(message = e.message.toString())
        }
    }
}