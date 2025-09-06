//package lk.ijse.gdse72.backend.dto;
//
//public class PaymentResponse {
//    private String paymentUrl;
//    private String status;
//    private String paymentId;
//    private Long bookingId;
//
//    // Constructors, getters, and setters
//
//    public PaymentResponse(String paymentUrl, String status, String paymentId, Long bookingId) {
//        this.paymentUrl = paymentUrl;
//        this.status = status;
//        this.paymentId = paymentId;
//        this.bookingId = bookingId;
//    }
//
//    public PaymentResponse() {
//    }
//
//    public String getPaymentUrl() {
//        return paymentUrl;
//    }
//
//    public void setPaymentUrl(String paymentUrl) {
//        this.paymentUrl = paymentUrl;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getPaymentId() {
//        return paymentId;
//    }
//
//    public void setPaymentId(String paymentId) {
//        this.paymentId = paymentId;
//    }
//
//    public Long getBookingId(Long bookingId) {
//        return this.bookingId;
//    }
//
//    public void setBookingId(Long bookingId) {
//        this.bookingId = bookingId;
//    }
//
//    @Override
//    public String toString() {
//        return "PaymentResponse{" +
//                "paymentUrl='" + paymentUrl + '\'' +
//                ", status='" + status + '\'' +
//                ", paymentId='" + paymentId + '\'' +
//                ", bookingId=" + bookingId +
//                '}';
//    }
//}
