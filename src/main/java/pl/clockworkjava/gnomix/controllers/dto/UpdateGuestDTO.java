package pl.clockworkjava.gnomix.controllers.dto;

import pl.clockworkjava.gnomix.domain.guest.Gender;

import java.time.LocalDate;

public record UpdateGuestDTO(long id, String firstName, String lastName, LocalDate dateOfBirth, Gender gender) {
}