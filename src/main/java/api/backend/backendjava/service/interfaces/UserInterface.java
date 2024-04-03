package api.backend.backendjava.service.interfaces;

import api.backend.backendjava.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserInterface {
    User createUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();

    void deleteUser(Long userId);

    User findByEmail(Long id);

    User findByUsername(String username);
}
