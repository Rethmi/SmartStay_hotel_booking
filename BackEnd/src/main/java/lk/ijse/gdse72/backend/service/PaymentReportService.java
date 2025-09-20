package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.entity.Payment;
import lk.ijse.gdse72.backend.repository.PaymentRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentReportService {

    private final PaymentRepository paymentRepository;

    public PaymentReportService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public byte[] generatePaymentReport(Long paymentId) throws Exception {
        // Load and compile the JRXML
        InputStream reportStream = new ClassPathResource("reports/payment_report.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Fetch payment data
        List<Payment> data = paymentRepository.findById(paymentId)
                .map(List::of)
                .orElse(List.of());

        // Wrap in JRBeanCollectionDataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        // Create a mutable map for parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("paymentId", paymentId);

        // Fill report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}