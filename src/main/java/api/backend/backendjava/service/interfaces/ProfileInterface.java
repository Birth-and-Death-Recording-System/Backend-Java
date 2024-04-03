package api.backend.backendjava.service.interfaces;

import api.backend.backendjava.entity.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProfileInterface {
    Profile createProfile(Profile profile);

    Profile getProfileById(Long id);

    List<Profile> getAllProfiles();

    Profile updateProfile(Profile profile);

    Profile findByEmail(String email);

    Profile findByUsername(String username);

    Profile findByUserId(Long userId);

    String deleteProfileByUsername(String username);

    String deleteProfileById(Long id);
}
