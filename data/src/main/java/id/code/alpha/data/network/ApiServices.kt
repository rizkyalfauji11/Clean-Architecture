package id.code.alpha.data.network

import id.code.alpha.data.sources.local.CovidHospitalResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET("FeatureServer/0/query?where=1%3D1&outFields=*&outSR=4326&f=json")
    suspend fun getHospitalList(): Response<CovidHospitalResponse>
}