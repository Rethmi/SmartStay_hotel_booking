package lk.ijse.gdse72.backend.controller;

import lk.ijse.gdse72.backend.service.PaymentReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports/payments")
public class PaymentReportController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentReportController.class);
    private final PaymentReportService reportService;

    public PaymentReportController(PaymentReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> generatePaymentReport(@PathVariable("id") Long paymentId) {
        try {
            logger.info("Generating payment report for ID: {}", paymentId);

            byte[] pdfBytes = reportService.generatePaymentReport(paymentId);

            if (pdfBytes == null || pdfBytes.length == 0) {
                logger.warn("Generated PDF is empty for payment ID: {}", paymentId);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "PaymentReceipt_" + paymentId + ".pdf");
            headers.setContentLength(pdfBytes.length);

            logger.info("Successfully generated payment report for ID: {}", paymentId);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            logger.error("Error generating payment report for ID: {}", paymentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}