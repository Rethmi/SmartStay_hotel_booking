package lk.ijse.gdse72.backend.dto;

public class ResetPasswordDTO {
    private String email;
    private String newPassword;

    public ResetPasswordDTO(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

    public ResetPasswordDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ResetPasswordDTO{" +
                "email='" + email + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
