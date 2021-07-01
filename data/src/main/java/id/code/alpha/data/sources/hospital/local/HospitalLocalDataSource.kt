package id.code.alpha.data.sources.hospital.local

import androidx.paging.DataSource
import id.code.alpha.data.database.dao.HospitalDao
import id.code.alpha.data.di.DatabaseModule.Companion.HOSPITAL_DAO
import id.code.alpha.data.model.local.HospitalEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class HospitalLocalDataSource @Inject constructor(
    @Named(HOSPITAL_DAO) val hospitalDao: HospitalDao
) {
    fun getAllHospitals(): Flow<List<HospitalEntity>> = hospitalDao.getAllHospital()
    fun getFavoriteHospitals(): Flow<List<HospitalEntity>> =
        hospitalDao.getFavoriteHospital()

    suspend fun insertHospital(hospitals: List<HospitalEntity>) =
        hospitalDao.insertHospitals(hospitals)

    suspend fun setFavoriteHospital(hospital: HospitalEntity) {
        hospitalDao.updateHospital(hospital)
    }

    fun getHospital(
        isSaved: Boolean,
        textSearch: String?,
        type: String? = "",
        region: String? = ""
    ): DataSource.Factory<Int, HospitalEntity> {
        return if (textSearch?.isNotEmpty() == true) {
            if (isSaved)
                hospitalDao.getHospitalFavorite(isSaved, textSearch)
            else
                hospitalDao.getHospital(textSearch)
        } else {
            if (isSaved)
                hospitalDao.getHospitalFavorite(isSaved)
            else {
                if (type?.isNotEmpty() == true || region?.isNotEmpty() == true)
                    hospitalDao.getHospital(type, region)
                else
                    hospitalDao.getHospital()
            }

        }

    }

    fun getReferenceHospitalType(): DataSource.Factory<Int, String> {
        return hospitalDao.getReferenceHospitalType()
    }

    fun getProvinces(): DataSource.Factory<Int, String> {
        return hospitalDao.getProvinces()
    }
}