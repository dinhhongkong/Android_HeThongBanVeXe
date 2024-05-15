package com.android.model.request

data class JourneyRequest (var originProvinceId: Int,
                           var destProvinceId: Int,
                           var date: String,
                           var isRoundTrip: Boolean )