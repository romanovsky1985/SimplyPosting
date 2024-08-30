package my.SimplyPosting.service;

import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.dto.UserFilterDTO;
import my.SimplyPosting.dto.UserOpenDTO;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.mapper.UserMapper;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.repository.UserRepository;
import my.SimplyPosting.specification.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserSpecification userSpecification;

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

    public void setDeletedById(Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User id: " + id + " not found"));
        user.setDeleted(true);
        user.setUsername(null);
        user.setDeletedEmail(user.getEmail());
        user.setEmail(null);
        userRepository.save(user);
    }
}
