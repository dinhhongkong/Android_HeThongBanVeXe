package com.android.model.request


//{ngayDat=Fri May 31 07:00:00 ICT 2024, trangThaiThanhToan=false, listSeat=[B09, A10], idKhachHang=-1, idChuyenXe=4, idQuanLy=-1, name='DinhHongKong', phone='0868307198', email='dinhhongkong2002@gmail.com'}
data class PaymentOneWayRequest(
    val paymentStatus: Int,
    val seatName: Set<String>,
    val customerId: Int,
    val journeyTrip: Int,
    val fullName: String,
    val phoneNumber: String,
    val email: String,
    val transactionId: String
)
