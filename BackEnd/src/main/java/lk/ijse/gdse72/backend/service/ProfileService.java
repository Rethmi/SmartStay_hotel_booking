package lk.ijse.gdse72.backend.service;


import lk.ijse.gdse72.backend.dto.RegisterDTO;

public interface ProfileService {
    void updateUserProfile(String email, RegisterDTO dto);
    RegisterDTO getUserProfileByEmail(String email);
}