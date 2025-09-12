//package lk.ijse.gdse72.backend.service.impl;
//
//import lk.ijse.gdse72.backend.dto.UserDTO;
//import lk.ijse.gdse72.backend.entity.User;
//import lk.ijse.gdse72.backend.repository.UserRepository;
//import lk.ijse.gdse72.backend.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDTO saveUser(UserDTO userDTO) {
//        User user = User.builder()
//                .id(userDTO.getId())
//                .username(userDTO.getUsername())
//                .email(userDTO.getEmail())
//                .role(userDTO.getRole())
//                .password(userDTO.getPassword())
//                .profileImage(userDTO.getProfileImage())
//                .build();
//        user = userRepository.save(user);
//        userDTO.setId(user.getId());
//        return userDTO;
//    }
//
//    @Override
//    public List<UserDTO> getAllUsers() {
//        return userRepository.findAll().stream()
//                .map(user -> UserDTO.builder()
//                        .id(user.getId())
//                        .username(user.getUsername())
//                        .email(user.getEmail())
//                        .role(user.getRole())
//                        .profileImage(user.getProfileImage())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public UserDTO getUserById(Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
//        return UserDTO.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .email(user.getEmail())
//                .role(user.getRole())
//                .profileImage(user.getProfileImage())
//                .build();
//    }
//}
package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.UserDTO;
import lk.ijse.gdse72.backend.entity.Role;
import lk.ijse.gdse72.backend.entity.User;
import lk.ijse.gdse72.backend.repository.UserRepository;
import lk.ijse.gdse72.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(Role.valueOf(user.getRole()))
//                .profileImage(user.getProfileImage())
                .build();
    }

    private User convertToEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .role(String.valueOf(dto.getRole()))
                .password(dto.getPassword())
//                .profileImage(dto.getProfileImage())
                .build();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        return convertToDTO(userRepository.save(user));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(String.valueOf(userDTO.getRole()));
//        existingUser.setProfileImage(userDTO.getProfileImage());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(userDTO.getPassword());
        }

        return convertToDTO(userRepository.save(existingUser));
    }

    @Override
    public UserDTO updateUserByEmail(String email, UserDTO userDTO) {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setRole(String.valueOf(userDTO.getRole()));
//        existingUser.setProfileImage(userDTO.getProfileImage());

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(userDTO.getPassword());
        }

        return convertToDTO(userRepository.save(existingUser));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        userRepository.delete(user);
    }
}