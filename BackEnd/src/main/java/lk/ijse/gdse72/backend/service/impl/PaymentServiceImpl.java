//package lk.ijse.gdse72.backend.service.impl;
//
//import org.example.smarthotelbookingwebsite.dto.PaymentDTO;
//import org.example.smarthotelbookingwebsite.entity.Booking;
//import org.example.smarthotelbookingwebsite.entity.Payment;
//import org.example.smarthotelbookingwebsite.repo.BookingRepository;
//import org.example.smarthotelbookingwebsite.repo.PaymentRepository;
//import org.example.smarthotelbookingwebsite.service.PaymentService;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeToken;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//
//@Service
//@Transactional
//
//public class PaymentServiceImpl implements PaymentService {
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Autowired
//    private PaymentRepository paymentRepository;
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Value("${payhere.merchant.secret}")
//    private static String merchantSecret;
//
//
//    @Override
//    public boolean save(PaymentDTO paymentDTO) {
//        try {
//            Payment payment = modelMapper.map(paymentDTO, Payment.class);
//            paymentRepository.save(payment);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    @Override
//    public void delete(Long id) {
//        paymentRepository.deleteById(id);
//    }
//
//    @Override
//    @Transactional
//    public void update(Long id, PaymentDTO paymentDTO) {
//        Payment existingPayment = paymentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
//
//        existingPayment.setAmount(paymentDTO.getAmount());
//
//        try {
//            existingPayment.setMethod(Payment.PaymentMethod.valueOf(paymentDTO.getMethod()));
//        } catch (IllegalArgumentException e) {
//            throw new RuntimeException("Invalid payment method: " + paymentDTO.getMethod());
//        }
//
//        Booking booking = bookingRepository.findById(paymentDTO.getBookingId())
//                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + paymentDTO.getBookingId()));
//
//        existingPayment.setBooking(booking);
//        existingPayment.setPaymentDate(paymentDTO.getPaymentDate());
//
//        paymentRepository.save(existingPayment);
//    }
//
//
//    @Override
//    public List<PaymentDTO> getAll() {
//        return modelMapper.map(paymentRepository.findAll(),new TypeToken<List<PaymentDTO>>() {}.getType());
//    }
//    public List<Payment> getPaymentsByHotelId(Long hotelId) {
//        return paymentRepository.findPaymentsByHotelId(hotelId);
//    }
//
//
//
//   // public static PaymentResponse createPayment(PaymentDTO paymentDTO) {
//
//
//      //  System.out.println(paymentDTO);
////        String payhereUrl = "https://sandbox.payhere.lk/pay/checkout?" +
////                "merchant_id=" + paymentDTO.getMerchantId() +
////                "&return_url=" + paymentDTO.getReturnUrl() +
////                "&cancel_url=" + paymentDTO.getCancelUrl() +
////                "&notify_url=" + paymentDTO.getNotifyUrl() +
////                "&order_id=" + paymentDTO.getBookingId() +
////                "&items=Hotel+Booking" +
////                "&amount=" + String.format("%.2f", paymentDTO.getAmount())+
////                "&currency=" + paymentDTO.getCurrency() +
////                "&country=Sri+Lanka" +
////                "&first_name=Chanuka" +
////                "&last_name=Wimalagunarathna" +
////                "&address=123+Main+Street" +
////                "&city=Colombo" +
////                "&phone=0771234567" +
////                "&email=chanucsj@gmail.com" +
////                "&hash=" + hash;
////
////        PaymentResponse response = new PaymentResponse();
////        response.setPaymentUrl(payhereUrl);
//////        response.getBookingId(paymentDTO.getBookingId());
////        return response;
//
//
////                 String payhereUrl = "https://sandbox.payhere.lk/pay/checkout?" +
////                "merchant_id=" + paymentDTO.getMerchantId() +
////                "&return_url=" + URLEncoder.encode(paymentDTO.getReturnUrl(), StandardCharsets.UTF_8) +
////                "&cancel_url=" + URLEncoder.encode(paymentDTO.getCancelUrl(), StandardCharsets.UTF_8) +
////                "&notify_url=" + URLEncoder.encode(paymentDTO.getNotifyUrl(), StandardCharsets.UTF_8) +
////                "&order_id=" + paymentDTO.getBookingId() +
////                "&items=" + URLEncoder.encode("Hotel Booking", StandardCharsets.UTF_8) +
////                "&amount=" + String.format("%.2f", paymentDTO.getAmount()) +
////                "&currency=" + paymentDTO.getCurrency() +
////                "&country=" + URLEncoder.encode("Sri Lanka", StandardCharsets.UTF_8) +
////                "&first_name=" + URLEncoder.encode("Chanuka", StandardCharsets.UTF_8) +
////                "&last_name=" + URLEncoder.encode("Wimalagunarathna", StandardCharsets.UTF_8) +
////                "&email=" + URLEncoder.encode("chanucsj@gmail.com", StandardCharsets.UTF_8) +
////                "&phone=0771234567" +
////                "&address=" + URLEncoder.encode("123 Main Street", StandardCharsets.UTF_8) +
////                "&city=" + URLEncoder.encode("Colombo", StandardCharsets.UTF_8) +
////                "&hash=" + hash;
////
////        PaymentResponse res = new PaymentResponse();
////        res.setPaymentUrl(payhereUrl);
////        res.getBookingId(paymentDTO.getBookingId());
////        System.out.println("Payment URL: " + payhereUrl);
////        return res;
////
////    }
//}
