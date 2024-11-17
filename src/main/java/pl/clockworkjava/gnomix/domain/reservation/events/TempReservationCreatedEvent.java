package pl.clockworkjava.gnomix.domain.reservation.events;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class TempReservationCreatedEvent extends ApplicationEvent {

    public final LocalDateTime creationDate;
    public final String email;
    public final long reservationId;


    public TempReservationCreatedEvent(Object context, String email, long reservationId) {
        super(context);
        this.reservationId = reservationId;
        this.email = email;
        this.creationDate = LocalDateTime.now();
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getEmail() {
        return email;
    }

    public long getReservationId() {
        return reservationId;
    }
}