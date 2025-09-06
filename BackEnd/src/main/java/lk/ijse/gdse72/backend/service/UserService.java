package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.UserDTO;

import java.util.List;


public interface UserService {
    int saveUser(UserDTO userDTO);
    UserDTO searchUser(String username);
    void deleteUser(String email);
    void updateUserRole(String email, String newRole);
    List<UserDTO> getAll();
    boolean allReadyUsedEmail(String email);
    UserDTO getUserByEmail(String email);

    UserDTO getUserNameById(Long id);
}