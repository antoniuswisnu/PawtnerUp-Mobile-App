package com.example.pawtnerup.ui.questionnaire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pawtnerup.api.response.QuestionnaireResponse
import com.example.pawtnerup.data.repository.PetRepository
import kotlinx.coroutines.launch

class BreedQuestionnaireViewModel(private val repository: PetRepository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    suspend fun getBreeds(dogId: String, dogName: String) = repository.getBreeds(dogId, dogName)

    fun postQuestionnaire(bio: String, petPersonality: String, petSizes: ArrayList<String>, petAges: ArrayList<String>, petGenders: ArrayList<String>, petBreeds: Int) : LiveData<ResultQuestionnaire>{
        val result = MutableLiveData<ResultQuestionnaire>()
        _isLoading.value = true
        viewModelScope.launch{
            try {
                _isLoading.value = false
                val response = repository.postQuestionnaire(bio, petPersonality, petSizes, petAges, petGenders, petBreeds)
                result.postValue(ResultQuestionnaire.Success(response))
            }catch (e: Exception){
                _isLoading.value = false
                result.postValue(ResultQuestionnaire.Error(e.message ?: "Error"))
            }
        }
        return result
    }

    sealed class ResultQuestionnaire{
        data class Success(val response: QuestionnaireResponse) : ResultQuestionnaire()
        data class Error(val message: String) : ResultQuestionnaire()
    }

}