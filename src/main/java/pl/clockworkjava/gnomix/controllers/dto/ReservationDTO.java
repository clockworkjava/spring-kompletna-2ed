package pl.clockworkjava.gnomix.controllers.dto;

import java.time.LocalDate;

public record ReservationDTO(long id, String roomNumber, String guestName, LocalDate fromDate, LocalDate toDate, boolean confirmed) {
}
