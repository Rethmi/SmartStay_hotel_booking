//package lk.ijse.gdse72.backend.controller;
//
//import org.example.smarthotelbookingwebsite.dto.PaymentDTO;
//import org.example.smarthotelbookingwebsite.dto.ResponseDTO;
//import org.example.smarthotelbookingwebsite.service.impl.PaymentServiceImpl;
//import org.example.smarthotelbookingwebsite.util.VarList;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.text.DecimalFormat;
//import java.time.LocalDateTime;
//import java.util.Map;
//
//@CrossOrigin(origins = "*")
//@RestController
//@RequestMapping("api/v1/payment")
//public class PaymentController {
//
//    private final PaymentServiceImpl paymentServiceimpl;
//    @Value("${app.domain}")
//    private String appDomain;
//
//
//    @Autowired
//    public PaymentController(PaymentServiceImpl paymentServiceimpl) {
//        this.paymentServiceimpl = paymentServiceimpl;
//    }
//
//
//    public static String getMd5(String input) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] messageDigest = md.digest(input.getBytes());
//            BigInteger no = new BigInteger(1, messageDigest);
//            String hashtext = no.toString(16);
//            while (hashtext.length() < 32) {
//                hashtext = "0" + hashtext;
//            }
//            return hashtext.toUpperCase();
//        }
//        catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @GetMapping("/get")
//    public ResponseEntity<ResponseDTO> getPayment( @RequestParam double amount,
//                                                   @RequestParam long bookingId) {
//
//        System.out.println("bookingId: " + bookingId);
//        System.out.println("amount: " + amount);
//
//        String merahantID     = "1230019";
//        String merchantSecret = "MzY3MTUwNDg0MTIzMjQ5MDY3OTE1MTI1ODU3OTYxODc3MzY3Mjkx";
//        String orderID        = String.valueOf(bookingId);
//        double amount1         = amount;
//        String currency       = "LKR";
//        DecimalFormat df       = new DecimalFormat("0.00");
//        String amountFormatted = df.format(amount1);
//        System.out.println("amountFor"+amountFormatted);
//        String hash    = getMd5(merahantID + orderID + amountFormatted + currency + getMd5(merchantSecret));
//        System.out.println("Generated Hash: " + hash);
//
//
//        PaymentDTO payment = new PaymentDTO();
//        payment.setMerchantId("1230019");
//        payment.setCurrency("LKR");
//        payment.setReturnUrl("https://smart-hotel-booking-28c26.web.app/PaymentCancel.html");
//        payment.setCancelUrl("https://smart-hotel-booking-28c26.web.app/PaymentSuccess.html");
//        payment.setNotifyUrl("https://a6d9-2402-4000-2170-6d3-609f-35ae-c74d-5324.ngrok-free.app/api/v1/payment/notify");
////        https://localhost:8080/api/v1/paymentent/notify
//        payment.setHash(hash);
//        payment.setAmount(Double.parseDouble(amountFormatted));
//        payment.setBookingId(bookingId);
//
//        System.out.println("payment "+payment);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseDTO(VarList.OK, "Payment Initiated",payment));
//
//    }
//
////    @PostMapping("/save")
////    public ResponseEntity<ResponseDTO> savePayment(@RequestBody @Valid PaymentDTO paymentDTO) {
////
////        System.out.println("Payment Amount: " + paymentDTO.getAmount());
////        System.out.println("Payment Method: " + paymentDTO.getMethod());
////        System.out.println("Booking ID: " + paymentDTO.getBookingId());
////        System.out.println("Payment Date: " + paymentDTO.getPaymentDate());
////
////        paymentServiceimpl.save(paymentDTO);
////        return ResponseEntity.status(HttpStatus.OK)
////                .body(new ResponseDTO(VarList.OK, "Payment Successfully", null));
////
////    }
//
//    @GetMapping("/success")
//    public ResponseEntity<String> paymentSuccess(@RequestParam("payment_id") String paymentId) {
//        System.out.println("Payment Success: " + paymentId);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("Payment Successful! Payment ID: " + paymentId);
//    }
//
//    @GetMapping("/cancel")
//    public ResponseEntity<String> paymentCancel() {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("Payment Cancelled!");
//    }
//
//    @PostMapping("/notify")
//    public ResponseEntity<String> handleNotification(@RequestParam Map<String, String> params) {
//        System.out.println("Received notification: ");
//        String status = params.get("status_code");
//        String orderId = params.get("order_id");
//        String method = params.get("method");
//        String amountStr = params.get("payhere_amount");
//
//        if ("2".equals(status)) {
//            System.out.println("✅ Payment successful: Order ID = " + orderId + ", Amount = " + amountStr);
//
//            // Convert to DTO
//            PaymentDTO paymentDTO = new PaymentDTO();
//            paymentDTO.setBookingId(Long.parseLong(orderId));
//            paymentDTO.setAmount(Double.parseDouble(amountStr));
//            paymentDTO.setMethod(method);
//            paymentDTO.setPaymentDate(LocalDateTime.parse(LocalDateTime.now().toString()));
//
//            // Save to DB
//            paymentServiceimpl.save(paymentDTO);
//        } else {
//            System.out.println("❌ Payment not successful: Order ID = " + orderId);
//        }
//
//        return ResponseEntity.ok("Received");
//    }
//    @GetMapping("getAll")
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    public ResponseEntity<ResponseDTO> getAllPayments(@RequestHeader("Authorization") String token) {
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseDTO(VarList.OK, "Success", paymentServiceimpl.getAll()));
//    }
//}
