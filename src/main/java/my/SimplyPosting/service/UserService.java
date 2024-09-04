package my.SimplyPosting.service;

import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.dto.UserFilterDTO;
import my.SimplyPosting.dto.UserOpenDTO;
import my.SimplyPosting.dto.UserUpdateDTO;
import my.SimplyPosting.exception.PermissionDeniedException;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.mapper.UserMapper;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.repository.UserRepository;
import my.SimplyPosting.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsManager {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserSpecification userSpecification;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserOpenDTO getById(Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User id: " + id + " not found"));
        return userMapper.map(user);
    }

    public UserOpenDTO getByUsername(String username) {
        UserModel user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("User username: " + username + " not found"));
        return userMapper.map(user);
    }

    public Page<UserOpenDTO> getPageByFilter(PageRequest pageRequest, UserFilterDTO filterDTO) {
        Specification<UserModel> specification = userSpecification.build(filterDTO);
        Page<UserModel> users = userRepository.findAll(specification, pageRequest);
        return users.map(userMapper::map);
    }

    public Boolean isFreeUsername(String username) {
        return username != null && !username.isBlank() &&
                !userRepository.existsByUsername(username);
    }

    public UserOpenDTO create(UserCreateDTO createDTO) {
        UserModel user = userMapper.map(createDTO);
        userRepository.save(user);
        return userMapper.map(user);
    }

    public UserOpenDTO update(UserUpdateDTO updateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserModel user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new PermissionDeniedException("Only authenticated user can update their data"));
        if (!passwordEncoder.matches(updateDTO.getOldPassword(), user.getCryptPassword())) {
            throw new PermissionDeniedException("Incorrect password ");
        }
        userMapper.update(updateDTO, user);
        userRepository.save(user);
        return userMapper.map(user);
    }

    public void setDeletedById(Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User id: " + id + " not found"));
        user.setDeleted(true);
        user.setUsername(null);
        user.setDeletedEmail(user.getEmail());
        user.setEmail(null);
        userRepository.save(user);
    }

    //////////////////// UserDetailsService Implementation ////////////////////

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User username: " + username + " not found"));
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createUser(UserDetails userDetails) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean userExists(String username) {
        throw new UnsupportedOperationException();
    }

}
