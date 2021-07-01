package id.code.alpha.cleanarchitecture.screen.dialog

import androidx.lifecycle.ViewModel
import id.code.alpha.domain.usecase.HospitalUseCase
import javax.inject.Inject

class FilterViewModel @Inject constructor(private val useCase: HospitalUseCase) : ViewModel() {
    fun getReferenceHospitalType() = useCase.getReferenceHospitalType()
    fun getRegion() = useCase.getProvinces()
}