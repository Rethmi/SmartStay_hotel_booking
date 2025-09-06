package lk.ijse.gdse72.backend.dto;

public class ForgotPasswordRequestDTO {
    private String email;

    public ForgotPasswordRequestDTO(String email) {
        this.email = email;
    }

    public ForgotPasswordRequestDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ForgotPasswordRequestDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
