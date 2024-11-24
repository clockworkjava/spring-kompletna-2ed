package pl.clockworkjava.gnomix.domain.guest;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter(AccessLevel.NONE)
@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;

    @Column(name = "phone")
    private String phoneNumber;

    Guest() {

    }

    public Guest(String firstName, String lastName, LocalDate birthDate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public void modify(String firstName, String lastName, LocalDate birthDate, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public void patch(String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
        if( firstName != null && !firstName.isEmpty() ) this.firstName = firstName;
        if( lastName != null && !lastName.isEmpty() ) this.lastName = lastName;
        if( dateOfBirth != null ) this.birthDate = dateOfBirth;
        if( gender != null ) this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {

        if(phoneNumber.length()>20) {
            throw new IllegalArgumentException("Phone number to long");
        }

        this.phoneNumber = phoneNumber;
    }
}