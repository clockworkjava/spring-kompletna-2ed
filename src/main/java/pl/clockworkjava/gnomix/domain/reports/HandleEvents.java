package pl.clockworkjava.gnomix.domain.reports;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.clockworkjava.gnomix.domain.reservation.events.TempReservationCreatedEvent;

@Component
public class HandleEvents {

    @EventListener
    public void handleTempReservationCreatedEvent(TempReservationCreatedEvent event) {
        System.out.println("Handling event created at " + event.toString());
    }
}