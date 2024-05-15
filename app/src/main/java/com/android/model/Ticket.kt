package com.android.model

import com.android.model.response.JourneyResponse

class Ticket(
    var id: Int = 0,
    var dateOrder: String ="",
    var paymentStatus: Int = 0,
    var seatName: List<String>? = null,
    var transactionId: String = "",
    var journey: JourneyResponse? = null)