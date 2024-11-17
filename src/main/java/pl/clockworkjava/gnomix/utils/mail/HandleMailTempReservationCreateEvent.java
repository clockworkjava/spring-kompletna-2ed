package pl.clockworkjava.gnomix.utils.mail;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.clockworkjava.gnomix.domain.reservation.events.TempReservationCreatedEvent;

@Component
public class HandleMailTempReservationCreateEvent implements ApplicationListener<TempReservationCreatedEvent> {
    @Override
    public void onApplicationEvent(TempReservationCreatedEvent event) {
        System.out.println("MAIL: Handle event by implementing AppListener " + event.getTimestamp());
    }
}