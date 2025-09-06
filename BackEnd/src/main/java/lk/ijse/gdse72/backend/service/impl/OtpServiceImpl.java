package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.service.OtpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class OtpServiceImpl implements OtpService {
    private final Map<String, String> otpStorage = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public String generateOtp(String email) {
        String otp = String.format("%06d", random.nextInt(1000000)); // 6-digit OTP
        otpStorage.put(email, otp);
        return otp;
    }

    public boolean verifyOtp(String email, String enteredOtp) {
        String storedOtp = otpStorage.get(email);
        System.out.println("Stored OTP: "+storedOtp);
        System.out.println("Entered OTP: "+enteredOtp);
        return storedOtp != null && storedOtp.equals(enteredOtp);
    }

    public void clearOtp(String email) {
        otpStorage.remove(email);
    }
}
