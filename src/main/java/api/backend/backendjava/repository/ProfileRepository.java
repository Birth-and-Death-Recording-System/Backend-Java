package api.backend.backendjava.repository;

import api.backend.backendjava.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmail(String email);
    Optional<Profile> findByUsername(String username);
    Optional<Profile> findByUserId(Long userId);
    void deleteByUsername(String username);

}
