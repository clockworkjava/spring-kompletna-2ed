package pl.clockworkjava.gnomix.controllers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationCreateTmpRestDTO(
        @NotNull long roomId,
        @FutureOrPresent @NotNull LocalDate fromDate,
        @Future @NotNull LocalDate toDate,
        @Email  String email) {
}
