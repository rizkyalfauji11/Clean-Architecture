package id.code.alpha.cleanarchitecture.screen.detail.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.code.alpha.domain.model.Hospital
import id.code.alpha.domain.usecase.HospitalUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailHospitalViewModel @Inject constructor(private val useCase: HospitalUseCase) :
    ViewModel() {
    val data = MutableLiveData<Hospital?>()
    fun changeSavedHospitalState(hospital: Hospital?) {
        viewModelScope.launch {
            data.value = useCase.setFavoriteHospital(hospital)
        }
    }

}