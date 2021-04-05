package id.code.alpha.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hospital(
    var address: String?,
    var hospitalId: Int,
    var latitude: Double?,
    var longitude: Double?,
    var name: String?,
    var phoneNumber: String?,
    var bedCount: Int?,
    var type: String?,
    var region: String?,
    var isFavorite: Boolean
) : Parcelable