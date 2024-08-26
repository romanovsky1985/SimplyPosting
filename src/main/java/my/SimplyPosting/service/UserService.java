package my.SimplyPosting.service;

import my.SimplyPosting.dto.UserCreateDTO;
import my.SimplyPosting.dto.UserOpenDTO;
import my.SimplyPosting.exception.ResourceNotFoundException;
import my.SimplyPosting.mapper.UserMapper;
import my.SimplyPosting.model.UserModel;
import my.SimplyPosting.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    public List<UserOpenDTO> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::map)
                .toList();
    }

    public List<UserOpenDTO> getAllExisting(PageRequest pageRequest) {
        return userRepository.findAllByDeleted(false, pageRequest).stream()
                .map(userMapper::map)
                .toList();
    }

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

    public UserOpenDTO create(UserCreateDTO createDTO) {
        UserModel user = userMapper.map(createDTO);
        userRepository.save(user);
        return userMapper.map(user);
    }

    public void setDeletedById(Long id) {
        UserModel user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User id: " + id + " not found"));
        UserModel deleted = new UserModel();
        deleted.setId(user.getId());
        deleted.setDeleted(true);
        deleted.setDeletedEmail(user.getEmail());
        userRepository.save(deleted);
    }
}
