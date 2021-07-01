package id.code.alpha.cleanarchitecture.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.code.alpha.cleanarchitecture.R


fun LatLng.getMarkerOption(context: Context?): MarkerOptions = MarkerOptions()
    .position(this)

fun getCameraOption(): CameraPosition =
    CameraPosition.Builder()
        .target(LatLng(-6.200000, 106.816666))
        .build()