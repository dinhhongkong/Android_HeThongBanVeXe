package com.android.model.response;

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


@Keep
data class JourneyResponse  (
    @SerializedName("id")
    var id: Int,
    @SerializedName("ngayKhoiHanh")
    var startDate: String,
    @SerializedName("gioXuatPhat")
    var startTime: String,
    @SerializedName("thoiGianDuKien")
    var estimatedTime: Float,
    @SerializedName("gia")
    var price: Double,
    @SerializedName("tenTinhDi")
    var departureProvince: String,
    @SerializedName("tenTinhDen")
    var destProvince: String,
)
