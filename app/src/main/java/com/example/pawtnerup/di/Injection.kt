//package com.example.pawtnerup.di
//
//import android.content.Context
//import com.example.pawtnerup.api.retrofit.ApiService
//import com.example.pawtnerup.data.repository.PetRepository
//
//object Injection {
//    fun providePetRepository(context: Context): PetRepository {
//        return PetRepository(ApiService.getInstance(context))
//    }
//}