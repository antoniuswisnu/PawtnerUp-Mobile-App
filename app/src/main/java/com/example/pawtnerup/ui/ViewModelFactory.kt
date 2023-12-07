//package com.example.pawtnerup.ui
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.pawtnerup.data.repository.PetRepository
//import com.example.pawtnerup.di.Injection
//import com.example.pawtnerup.ui.profile.ProfileViewModel
//import com.example.pawtnerup.ui.questionnaire.BreedQuestionnaireViewModel
//import com.google.firebase.database.core.Context
//
//class ViewModelFactory private constructor(private val petRepository: PetRepository):
//    ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when{
//            modelClass.isAssignableFrom(BreedQuestionnaireViewModel::class.java)
//            -> BreedQuestionnaireViewModel(petRepository) as T
//
//            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
//        }
//    }
//
//    companion object {
//        @Volatile
//        private var INSTANCE: ViewModelFactory? = null
//        @JvmStatic
//        fun getInstance(context: Context): ViewModelFactory {
//            if (INSTANCE == null) {
//                synchronized(ViewModelFactory::class.java) {
//                    INSTANCE = ViewModelFactory(Injection.providePetRepository(context))
//                }
//            }
//            return INSTANCE as ViewModelFactory
//        }
//    }
//
//}