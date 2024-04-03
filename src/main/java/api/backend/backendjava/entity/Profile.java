package api.backend.backendjava.entity;

import api.backend.backendjava.entity.enums.Gender;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date dateOfBirth;
    private String email;
    private Gender gender;
    private String profilePicture;
    private String username;


    @OneToOne(targetEntity = User.class)
    private User user;

}
