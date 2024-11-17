package pl.clockworkjava.gnomix.domain.reservation.events;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TempReservationCreatedEvent {

    public final LocalDateTime creationDate;
    public final String email;
    public final long reservationId;

    public TempReservationCreatedEvent(String email, long reservationId) {
        this.reservationId = reservationId;
        this.email = email;
        this.creationDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "TempReservationCreatedEvent{" +
                "creationDate=" + creationDate +
                ", email='" + email + '\'' +
                ", reservationId=" + reservationId +
                '}';
    }
}