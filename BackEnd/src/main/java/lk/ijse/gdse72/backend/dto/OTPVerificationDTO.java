package lk.ijse.gdse72.backend.dto;

public class OTPVerificationDTO {
    private String email;
    private String otp;

    public OTPVerificationDTO(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public OTPVerificationDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "OTPVerificationDTO{" +
                "email='" + email + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
