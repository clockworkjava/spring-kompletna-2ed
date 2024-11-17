package pl.clockworkjava.gnomix.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import pl.clockworkjava.gnomix.domain.guest.Gender;

import java.time.LocalDate;

public record CreateNewGuestDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Past(message = "Data urodzenia musi byc w przeszłości") LocalDate dateOfBirth,
        @NotNull Gender gender) {
}
