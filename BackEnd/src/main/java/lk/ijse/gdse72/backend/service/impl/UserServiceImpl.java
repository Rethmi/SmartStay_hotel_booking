// UserServiceImpl.java
package lk.ijse.gdse72.backend.service.impl;

import lk.ijse.gdse72.backend.dto.UserDTO;
import lk.ijse.gdse72.backend.entity.User;
import lk.ijse.gdse72.backend.repository.UserRepository;
import lk.ijse.gdse72.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
//    }
@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).orElseThrow();
    if(user == null){
        throw new UsernameNotFoundException("User not found: " + email);
    }
    return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            getAuthority(user)
    );
}


    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow();
        return modelMapper.map(user,UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }

    @Override
    public UserDTO searchUser(String username) {
        if (userRepository.existsByEmail(username)) {
            User user=userRepository.findByEmail(username).orElseThrow();
            return modelMapper.map(user,UserDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public void deleteUser(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteByEmail(email);
    }

//    @Override
//    public int saveUser(UserDTO userDTO) {
//        if (userRepository.existsByEmail(userDTO.getEmail())) {
//            System.out.println("Email Already Used");
//            return 406; // HttpStatus.NOT_ACCEPTABLE.value()
//        } else {
//            System.out.println("Created");
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//            userDTO.setRole("USER");
//            userRepository.save(modelMapper.map(userDTO, User.class));
//            return 201; // HttpStatus.CREATED.value()
//        }
//    }
@Override
public int saveUser(UserDTO userDTO) {
    if (userRepository.existsByEmail(userDTO.getEmail())) {
        System.out.println("Email Already Used: " + userDTO.getEmail());
        return 409; // HttpStatus.CONFLICT.value()
    } else {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userDTO.setRole("USER");
        userRepository.save(modelMapper.map(userDTO, User.class));
        System.out.println("User Created: " + userDTO.getEmail());
        return 201; // HttpStatus.CREATED.value()
    }
}

    @Override
    public void updateUserRole(String email, String newRole) {
        User user = userRepository.findByEmail(String.valueOf(email)).orElseThrow();
        user.setRole(newRole);
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> getAll() {
        return modelMapper.map(userRepository.findAll(),new TypeToken<List<UserDTO>>() {}.getType());
    }

    @Override
    public boolean allReadyUsedEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        if (user != null) {
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }

    @Override
    public UserDTO getUserNameById(Long id) {
        User user = userRepository.findById(Long.valueOf(String.valueOf(id))).orElse(null);
        if (user != null) {
            return modelMapper.map(user, UserDTO.class);
        } else {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
    }
}
