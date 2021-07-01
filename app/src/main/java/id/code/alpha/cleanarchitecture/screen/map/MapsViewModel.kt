package id.code.alpha.cleanarchitecture.screen.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.code.alpha.domain.model.Hospital
import id.code.alpha.domain.usecase.HospitalUseCase
import javax.inject.Inject

class MapsViewModel @Inject constructor(private val useCase: HospitalUseCase) : ViewModel() {
    private var type = MutableLiveData<String?>()
    private var region = MutableLiveData<String?>()

    init {
        type.value = ""
        region.value = ""
    }
    fun getHospital(isSaved: Boolean, textSearch: String?): LiveData<PagedList<Hospital>> {
        val data = useCase.getHospital(isSaved, textSearch, type = type.value, region = region.value)
        return data
    }


    fun setType(name: String?) {
        region.value = ""
        type.value = name
    }

    fun setRegion(name: String?) {
        region.value = name
    }
}