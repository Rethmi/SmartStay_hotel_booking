package lk.ijse.gdse72.backend.dto;

import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String password;
    private String ProfileImage;


    public UserDTO(Long id, String username, String email, String role, String password, String profileImage) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        ProfileImage = profileImage;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", ProfileImage='" + ProfileImage + '\'' +
                '}';
    }
}
