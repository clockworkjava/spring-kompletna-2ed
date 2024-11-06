package pl.clockworkjava.gnomix.domain.guest;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Guest {

    private final long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;

    public Guest(String firstName, String lastName, LocalDate birthDate, Gender gender) {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
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
}