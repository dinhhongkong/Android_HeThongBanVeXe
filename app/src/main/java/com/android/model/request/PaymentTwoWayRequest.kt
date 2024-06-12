package com.android.model.request

data class PaymentTwoWayRequest(
    val paymentStatus: Int,
    val seatDepartureName: Set<String>,
    val seatReturnName: Set<String>,
    val customerId: Int,
    val journeyDepartureTrip: Int,
    val journeyReturnTrip: Int,
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val transactionId: String
)
