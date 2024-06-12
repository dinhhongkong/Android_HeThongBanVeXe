package com.android.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Province(
    @SerializedName("maTinh")
    var id: Int,
    @SerializedName("tenTinh")
    var name: String
) {
    override fun toString(): String {
        return name;
    }
}