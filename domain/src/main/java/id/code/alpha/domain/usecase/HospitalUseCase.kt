package id.code.alpha.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.code.alpha.domain.Resource
import id.code.alpha.domain.model.Hospital
import kotlinx.coroutines.flow.Flow

interface HospitalUseCase {
    fun getAllHospitals(): Flow<Resource<List<Hospital>>>
    fun getFavoriteHospitals(): Flow<List<Hospital>>
    suspend fun setFavoriteHospital(hospital: Hospital?): Hospital?
    fun getHospital(
        isSaved: Boolean,
        textSearch: String?,
        type: String? = "",
        region: String? = ""
    ): LiveData<PagedList<Hospital>>

    fun getReferenceHospitalType(): LiveData<PagedList<String>>
    fun getProvinces(): LiveData<PagedList<String>>
}