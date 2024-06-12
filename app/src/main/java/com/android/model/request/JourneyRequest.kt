package com.android.model.request

data class JourneyRequest (var originProvinceId: Int = 0,
                           var destProvinceId: Int = 0,
                           var startDate: String = "",
                           var endDate: String = "")