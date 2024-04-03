package api.backend.backendjava.controller;

import api.backend.backendjava.entity.Profile;
import api.backend.backendjava.service.implementations.ProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileServiceImpl profileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Profile createProfile(@RequestBody Profile profile) {
        return profileService.createProfile(profile);
    }

    @GetMapping("/{id}")
    public Profile getProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    @PutMapping("/{id}")
    public Profile updateProfile(@PathVariable Long id, @RequestBody Profile profile) {
        profile.setId(id); // Ensure the ID in the path matches the ID in the request body
        return profileService.updateProfile(profile);
    }

    @DeleteMapping("/{id}")
    public String deleteProfileById(@PathVariable Long id) {
        return profileService.deleteProfileById(id);
    }

    @DeleteMapping("/username/{username}")
    public String deleteProfileByUsername(@PathVariable String username) {
        return profileService.deleteProfileByUsername(username);
    }
}
