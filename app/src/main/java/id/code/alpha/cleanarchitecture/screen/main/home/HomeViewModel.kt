package id.code.alpha.cleanarchitecture.screen.main.home

import androidx.lifecycle.ViewModel
import id.code.alpha.domain.usecase.HospitalUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val useCase: HospitalUseCase) : ViewModel() {
    fun getHospital(isSaved: Boolean, textSearch: String?) = useCase.getHospital(isSaved, textSearch)
}