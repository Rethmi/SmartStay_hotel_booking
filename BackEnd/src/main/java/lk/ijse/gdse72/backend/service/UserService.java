//package lk.ijse.gdse72.backend.service;
//
//import lk.ijse.gdse72.backend.dto.UserDTO;
//
//import java.util.List;
//
//public interface UserService {
//    UserDTO saveUser(UserDTO userDTO);
//    List<UserDTO> getAllUsers();
//    UserDTO getUserById(Long id);
//}
package lk.ijse.gdse72.backend.service;

import lk.ijse.gdse72.backend.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByEmail(String email);
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO updateUserByEmail(String email, UserDTO userDTO);
    void deleteUser(Long id);
    void deleteUserByEmail(String email);
}