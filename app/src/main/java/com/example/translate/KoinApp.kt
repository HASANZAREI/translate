package com.example.translate

import android.app.Application
import com.example.translate.network.TranslationApi
import com.example.translate.repositories.TranslationRepo
import com.example.translate.repositories.TranslationRepoImpl
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KoinApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                modules = module {
                    single {
                        Retrofit
                            .Builder()
                            .baseUrl("https://api.mymemory.translated.net/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    }
                    single {
                        val retrofit: Retrofit = get()
                        retrofit.create(TranslationApi::class.java)
                    }
                    single {
                        TranslationRepoImpl(api = get())
                    } bind TranslationRepo::class
                }
            )
        }
    }
}