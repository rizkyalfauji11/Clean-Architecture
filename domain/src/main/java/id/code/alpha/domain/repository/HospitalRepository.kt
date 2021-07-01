package id.code.alpha.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.code.alpha.domain.Resource
import id.code.alpha.domain.model.Hospital
import kotlinx.coroutines.flow.Flow

interface HospitalRepository {
    fun getAllHospitals(): Flow<Resource<List<Hospital>>>

    fun getFavoriteHospitals(): Flow<List<Hospital>>

    suspend fun changeFavoriteHospitalState(data: Hospital?): Hospital?

    fun getHospital(isSaved: Boolean, textSearch: String?, type: String?, region: String?): LiveData<PagedList<Hospital>>
    fun getReferenceHospitalType(): LiveData<PagedList<String>>
    fun getProvinces(): LiveData<PagedList<String>>
}