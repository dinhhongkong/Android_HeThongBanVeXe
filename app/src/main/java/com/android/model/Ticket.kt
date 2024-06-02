package com.android.model

import com.android.model.response.JourneyResponse

class Ticket(
    var id: Int = 0,
    var fullName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var seatNameList: Set<String> = HashSet(),
    var journey: JourneyResponse? = null)