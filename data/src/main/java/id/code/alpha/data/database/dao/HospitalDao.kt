package id.code.alpha.data.database.dao

import androidx.paging.DataSource
import androidx.room.*
import id.code.alpha.data.model.local.HospitalEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface HospitalDao {
    @Query("SELECT * from tb_hospital LIMIT 10")
    fun getAllHospital(): Flow<List<HospitalEntity>>

    @Query("SELECT * from tb_hospital where isFavorite = 1")
    fun getFavoriteHospital(): Flow<List<HospitalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHospitals(tourism: List<HospitalEntity>)

    @Update
    suspend fun updateHospital(tourism: HospitalEntity)

    @Query("SELECT * FROM tb_hospital WHERE name LIKE '%' || :textSearch || '%' OR address LIKE '%' || :textSearch || '%' ORDER by name ASC")
    fun getHospital(textSearch: String?): DataSource.Factory<Int, HospitalEntity>

    @Query("SELECT * FROM tb_hospital WHERE (type = :type  OR region = :region) ORDER by name ASC")
    fun getHospital(
        type: String? = "",
        region: String? = ""
    ): DataSource.Factory<Int, HospitalEntity>

    @Query("SELECT * FROM tb_hospital ORDER by name ASC")
    fun getHospital(): DataSource.Factory<Int, HospitalEntity>

    @Query("SELECT * FROM tb_hospital WHERE isFavorite = :isSaved AND (name LIKE '%' || :textSearch || '%' OR address LIKE '%' || :textSearch || '%') ORDER by name ASC")
    fun getHospitalFavorite(
        isSaved: Boolean,
        textSearch: String?
    ): DataSource.Factory<Int, HospitalEntity>

    @Query("SELECT * FROM tb_hospital WHERE isFavorite = :isSaved ORDER by name ASC")
    fun getHospitalFavorite(isSaved: Boolean): DataSource.Factory<Int, HospitalEntity>

    @Query("SELECT DISTINCT type FROM tb_hospital ORDER BY type DESC")
    fun getReferenceHospitalType(): DataSource.Factory<Int, String>

    @Query("SELECT DISTINCT region FROM tb_hospital ORDER BY type ASC")
    fun getProvinces(): DataSource.Factory<Int, String>
}