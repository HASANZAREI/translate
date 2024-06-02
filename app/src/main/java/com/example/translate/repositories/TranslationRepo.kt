package com.example.translate.repositories

import com.example.translate.models.BaseModel
import com.example.translate.models.ResponseData

interface TranslationRepo {
    suspend fun translate(query: String, from: String, to: String): BaseModel<ResponseData>
}