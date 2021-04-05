package id.code.alpha.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.code.alpha.data.model.local.HospitalEntity
import id.code.alpha.data.model.remote.base.ApiResponse
import id.code.alpha.data.sources.hospital.local.HospitalLocalDataSource
import id.code.alpha.data.sources.hospital.remote.HospitalRemoteDataSource
import id.code.alpha.data.sources.local.HospitalData
import id.code.alpha.data.utils.AppExecutors
import id.code.alpha.data.utils.NetworkBoundResource
import id.code.alpha.domain.Resource
import id.code.alpha.domain.model.Hospital
import id.code.alpha.domain.repository.HospitalRepository
import id.code.alpha.domain.utils.mapList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HospitalRepositoryImpl @Inject constructor(
    private val remoteDataSource: HospitalRemoteDataSource,
    private val localDataSource: HospitalLocalDataSource,
    private val appExecutors: AppExecutors
) : HospitalRepository {
    override fun getAllHospitals(): Flow<Resource<List<Hospital>>> =
        object :
            NetworkBoundResource<List<Hospital>, List<HospitalData>>(
                appExecutors
            ) {
            override fun loadFromDB(): Flow<List<Hospital>> =
                localDataSource.getAllHospitals().map {
                    it.mapList()
                }

            override fun shouldFetch(data: List<Hospital>?) =
                /*data?.isEmpty() == */true

            override suspend fun createCall(): Flow<ApiResponse<List<HospitalData>>> =
                remoteDataSource.getHospitalList()

            override suspend fun saveCallResult(data: List<HospitalData>) =
                localDataSource.insertHospital(data.mapList())

        }.asFlow()

    override fun getFavoriteHospitals(): Flow<List<Hospital>> =
        localDataSource.getFavoriteHospitals().map {
            it.mapList()
        }

    override suspend fun changeFavoriteHospitalState(data: Hospital?): Hospital {
        data?.isFavorite = data?.isFavorite != true
        val newState = HospitalEntity(
            data?.address,
            data?.hospitalId,
            data?.latitude,
            data?.longitude,
            data?.name,
            data?.phoneNumber,
            data?.bedCount,
            data?.type,
            data?.region,
            data?.isFavorite
        )
        localDataSource.setFavoriteHospital(newState)
        return newState.convert()
    }

    override fun getHospital(isSaved: Boolean, textSearch: String?): LiveData<PagedList<Hospital>> {
        return LivePagedListBuilder(
            localDataSource.getHospital(isSaved, textSearch).map {
                it.convert()
            }, 20
        ).build()
    }

    override fun getReferenceHospitalType(): LiveData<PagedList<String>> {
        return LivePagedListBuilder(
            localDataSource.getReferenceHospitalType(),
            20
        ).build()
    }
}