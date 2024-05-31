package com.android.model

import com.android.model.response.JourneyResponse

class Ticket(
    var id: Int = 0,
    var fullName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var dateOrder: String ="",
    var paymentStatus: Int = 0,
    var seatNameList: Set<String> = HashSet(),
    var transactionId: String = "",
    var journey: JourneyResponse? = null)