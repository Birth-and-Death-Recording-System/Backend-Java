package api.backend.backendjava.service.implementations;

import api.backend.backendjava.entity.User;
import api.backend.backendjava.repository.UserRepository;
import api.backend.backendjava.service.interfaces.UserInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserInterface {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user){
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + user.getId()));

        // Perform validation
        validateUser(user);

        // Update the existing user
        existingUser.setUsername(user.getUsername());
        existingUser.setGender(user.getGender());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());

        // Add other fields to update as needed

        // Save and return the updated user
        return userRepository.save(existingUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    @Override
    public User findByEmail(Long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    private void validateUser(User user) {
        // Check if email is valid
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email address");
        }

        // Check if email is unique
        Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserByEmail.isPresent() && !existingUserByEmail.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Email address is already in use");
        }

        // Check if username is unique
        Optional<User> existingUserByUsername = userRepository.findByUsername(user.getUsername());
        if (existingUserByUsername.isPresent() && !existingUserByUsername.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Username is already in use");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }


}
