//package lk.ijse.gdse72.backend.dto;
//
//import java.time.LocalDateTime;
//
//public class PaymentDTO {
//
//    private Long id;
//    private Long bookingId;
//    private double amount;
//    private String method; // CREDIT_CARD, PAYPAL, BANK_TRANSFER
//    private LocalDateTime paymentDate;
//    private String MerchantId;
//    private String Currency;
//    private String ReturnUrl;
//    private String CancelUrl;
//    private String NotifyUrl;
//    private String hash;
//
//    public PaymentDTO(Long id, Long bookingId, double amount, String method, LocalDateTime paymentDate, String merchantId, String currency, String returnUrl, String cancelUrl, String notifyUrl, String hash) {
//        this.id = id;
//        this.bookingId = bookingId;
//        this.amount = amount;
//        this.method = method;
//        this.paymentDate = paymentDate;
//        MerchantId = merchantId;
//        Currency = currency;
//        ReturnUrl = returnUrl;
//        CancelUrl = cancelUrl;
//        NotifyUrl = notifyUrl;
//        this.hash = hash;
//    }
//
//    public PaymentDTO() {
//    }
//
//    public String getHash() {
//        return hash;
//    }
//
//    public void setHash(String hash) {
//        this.hash = hash;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getBookingId() {
//        return bookingId;
//    }
//
//    public void setBookingId(Long bookingId) {
//        this.bookingId = bookingId;
//    }
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public String getMethod() {
//        return method;
//    }
//
//    public void setMethod(String method) {
//        this.method = method;
//    }
//
//    public LocalDateTime getPaymentDate() {
//        return paymentDate;
//    }
//
//    public void setPaymentDate(LocalDateTime paymentDate) {
//        this.paymentDate = paymentDate;
//    }
//
//    public String getMerchantId() {
//        return MerchantId;
//    }
//
//    public void setMerchantId(String merchantId) {
//        MerchantId = merchantId;
//    }
//
//    public String getCurrency() {
//        return Currency;
//    }
//
//    public void setCurrency(String currency) {
//        Currency = currency;
//    }
//
//    public String getReturnUrl() {
//        return ReturnUrl;
//    }
//
//    public void setReturnUrl(String returnUrl) {
//        ReturnUrl = returnUrl;
//    }
//
//    public String getCancelUrl() {
//        return CancelUrl;
//    }
//
//    public void setCancelUrl(String cancelUrl) {
//        CancelUrl = cancelUrl;
//    }
//
//    public String getNotifyUrl() {
//        return NotifyUrl;
//    }
//
//    public void setNotifyUrl(String notifyUrl) {
//        NotifyUrl = notifyUrl;
//    }
//
//    @Override
//    public String toString() {
//        return "PaymentDTO{" +
//                "id=" + id +
//                ", bookingId=" + bookingId +
//                ", amount=" + amount +
//                ", method='" + method + '\'' +
//                ", paymentDate=" + paymentDate +
//                ", MerchantId='" + MerchantId + '\'' +
//                ", Currency='" + Currency + '\'' +
//                ", ReturnUrl='" + ReturnUrl + '\'' +
//                ", CancelUrl='" + CancelUrl + '\'' +
//                ", NotifyUrl='" + NotifyUrl + '\'' +
//                ", hash='" + hash + '\'' +
//                '}';
//    }
//}
