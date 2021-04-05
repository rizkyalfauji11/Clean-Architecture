package id.code.alpha.cleanarchitecture.screen.splash

import androidx.lifecycle.ViewModel
import id.code.alpha.domain.usecase.HospitalUseCase
import androidx.lifecycle.asLiveData
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor(hospitalUseCase: HospitalUseCase) : ViewModel() {
    val hospitals = hospitalUseCase.getAllHospitals().asLiveData()
}