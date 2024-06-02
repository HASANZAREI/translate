package com.example.translate.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.translate.models.BaseModel
import com.example.translate.models.ResponseData
import com.example.translate.repositories.TranslationRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.koin.core.component.KoinComponent

class HomeViewModel : ViewModel(), KoinComponent {
    private val repo: TranslationRepo by inject()
    private val _translation: MutableStateFlow<BaseModel<ResponseData>?> = MutableStateFlow(null)
    val translation = _translation.asStateFlow()

    fun translate(query: String, source: String, target: String) {
        _translation.update { BaseModel.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            repo.translate(query = query, from = source, to = target).also { response ->
                _translation.update { response }
            }
        }
    }

    fun clear() {
        _translation.update { null }
    }
}