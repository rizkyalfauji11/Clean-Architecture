package id.code.alpha.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.code.alpha.domain.model.Hospital
import id.code.alpha.domain.utils.ModelParser

@Entity(tableName = "tb_hospital")
data class HospitalEntity(
    @ColumnInfo(name = "address")
    val address: String? = null,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val hospitalId: Int? = null,
    @ColumnInfo(name = "latitude")
    val latitude: Double? = null,
    @ColumnInfo(name = "longitude")
    val longitude: Double? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String? = null,
    @ColumnInfo(name = "bedCount")
    val bedCount: Int? = null,
    @ColumnInfo(name = "type")
    val type: String? = null,
    @ColumnInfo(name = "region")
    val region: String? = null,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean? = false
) : ModelParser<Hospital> {
    override fun convert() =
        Hospital(
            address,
            hospitalId ?: -1,
            latitude,
            longitude,
            name,
            phoneNumber,
            bedCount,
            type,
            region,
            isFavorite ?: false
        )

}