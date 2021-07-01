package id.code.alpha.data.sources.hospital.remote

import id.code.alpha.data.di.NetworkModule
import id.code.alpha.data.model.remote.base.ApiResponse
import id.code.alpha.data.network.ApiServices
import id.code.alpha.data.sources.local.HospitalData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class HospitalRemoteDataSource @Inject constructor(
    @Named(NetworkModule.HOSPITAL_API)
    private val apiService: ApiServices
) {
    suspend fun getHospitalList(): Flow<ApiResponse<List<HospitalData>>> {
        return flow {
            try {
                val response = apiService.getHospitalList()
                if (response.isSuccessful) {
                    val dataArray = response.body()?.hospitals
                    if (dataArray?.isNotEmpty() == true) {
                        emit(ApiResponse.Success(dataArray))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                } else {
                    emit(ApiResponse.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)
    }
}