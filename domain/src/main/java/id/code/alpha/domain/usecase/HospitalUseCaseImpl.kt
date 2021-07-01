package id.code.alpha.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import id.code.alpha.domain.Resource
import id.code.alpha.domain.model.Hospital
import id.code.alpha.domain.repository.HospitalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HospitalUseCaseImpl @Inject constructor(private val repository: HospitalRepository) :
    HospitalUseCase {
    override fun getAllHospitals(): Flow<Resource<List<Hospital>>> {
        return repository.getAllHospitals()
    }

    override fun getFavoriteHospitals(): Flow<List<Hospital>> = repository.getFavoriteHospitals()

    override suspend fun setFavoriteHospital(hospital: Hospital?): Hospital? {
        return repository.changeFavoriteHospitalState(hospital)
    }

    override fun getHospital(
        isSaved: Boolean,
        textSearch: String?,
        type: String?,
        region: String?
    ): LiveData<PagedList<Hospital>> {
        return repository.getHospital(isSaved, textSearch, type, region)
    }

    override fun getReferenceHospitalType(): LiveData<PagedList<String>> {
        return repository.getReferenceHospitalType()
    }

    override fun getProvinces(): LiveData<PagedList<String>> {
        return repository.getProvinces()
    }

}