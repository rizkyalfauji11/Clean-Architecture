package id.code.alpha.data.sources.local

import com.google.gson.annotations.SerializedName
import id.code.alpha.data.model.local.HospitalEntity
import id.code.alpha.domain.utils.ModelParser

data class CovidHospitalResponse(
    @SerializedName("features")
    val hospitals: List<HospitalData>? = null
)

data class HospitalData(
    @SerializedName("attributes")
    val attributes: Attributes? = null,
    @SerializedName("geometry")
    val geometry: Geometry? = null
) : ModelParser<HospitalEntity> {
    data class Attributes(
        @SerializedName("alamat")
        val address: String? = null,
        @SerializedName("kode_rs")
        val hospitalId: Int? = null,
        @SerializedName("lat")
        val latitude: Double? = null,
        @SerializedName("lon")
        val longitude: Double? = null,
        @SerializedName("nama")
        val name: String? = null,
        @SerializedName("telepon")
        val phoneNumber: String? = null,
        @SerializedName("tempat_tidur")
        val bedCount: Int? = null,
        @SerializedName("tipe")
        val type: String? = null,
        @SerializedName("wilayah")
        val region: String? = null,
        @SerializedName("isFavorite")
        val isFavorite: Boolean? = false,
    )

    data class Geometry(
        @SerializedName("x")
        val x: Double? = null,
        @SerializedName("y")
        val y: Double? = null
    )

    override fun convert() = HospitalEntity(
        attributes?.address,
        attributes?.hospitalId,
        attributes?.latitude,
        attributes?.longitude,
        attributes?.name,
        attributes?.phoneNumber,
        attributes?.bedCount,
        attributes?.type,
        attributes?.region,
        attributes?.isFavorite ?: false
    )
}
