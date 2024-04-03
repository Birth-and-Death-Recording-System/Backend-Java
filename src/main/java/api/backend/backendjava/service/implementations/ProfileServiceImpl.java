package api.backend.backendjava.service.implementations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import api.backend.backendjava.entity.Profile;
import api.backend.backendjava.repository.ProfileRepository;
import api.backend.backendjava.service.interfaces.ProfileInterface;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileInterface {

    private final ProfileRepository profileRepository;

    @Override
    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Profile getProfileById(Long id) {
        Optional<Profile> optionalProfile = profileRepository.findById(id);
        return optionalProfile.orElseThrow(() -> new ProfileNotFoundException(getString() + id));
    }



    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile updateProfile(Profile profile) {
        Profile existingProfile = profileRepository.findById(profile.getId()).orElseThrow(() -> new ProfileNotFoundException(getString() + profile.getId()));
        // Perform validation
        validateProfile(profile);
        // Update the existing profile
        existingProfile.setFirstName(profile.getFirstName());
        existingProfile.setLastName(profile.getLastName());
        existingProfile.setPhoneNumber(profile.getPhoneNumber());
        existingProfile.setDateOfBirth(profile.getDateOfBirth());
        existingProfile.setEmail(profile.getEmail());
        existingProfile.setGender(profile.getGender());
        existingProfile.setProfilePicture(profile.getProfilePicture());
        existingProfile.setUsername(profile.getUsername());
        // Save and return the updated profile
        return profileRepository.save(existingProfile);
    }

    @Override
    public Profile findByEmail(String email) {
        Optional<Profile> optionalProfile = profileRepository.findByEmail(email);
        return optionalProfile.orElse(null); // Handle case when profile is not found
    }

    @Override
    public Profile findByUsername(String username) {
        Optional<Profile> optionalProfile = profileRepository.findByUsername(username);
        return optionalProfile.orElse(null); // Handle case when profile is not found
    }

    @Override
    public Profile findByUserId(Long userId) {
        Optional<Profile> optionalProfile = profileRepository.findByUserId(userId);
        return optionalProfile.orElse(null); // Handle case when profile is not found
    }

    @Override
    public String deleteProfileByUsername(String username) {
        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found with username: " + username));
        profileRepository.delete(profile);
        return "Successfully deleted profile with username: " + username + "!";
    }

    @Override
    public String deleteProfileById(Long id) {
        if (!profileRepository.existsById(id)) {
            throw new ProfileNotFoundException(getString() + id);
        }
        profileRepository.deleteById(id);
        return "Successfully deleted profile with id: " + id + "!";
    }

    public static class ProfileNotFoundException extends RuntimeException {
        public ProfileNotFoundException(String message) {
            super(message);
        }
    }

    private void validateProfile(Profile profile) {
        if (profile.getDateOfBirth() != null && profile.getDateOfBirth().after(new Date())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
    }
    @NotNull
    private static String getString() {
        return "Profile not found with id: ";
    }

}
