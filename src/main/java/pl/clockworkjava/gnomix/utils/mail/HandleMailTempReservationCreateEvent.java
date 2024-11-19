package pl.clockworkjava.gnomix.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.clockworkjava.gnomix.domain.reservation.events.TempReservationCreatedEvent;

@Component
public class HandleMailTempReservationCreateEvent implements ApplicationListener<TempReservationCreatedEvent> {

    private final EmailService emailService;

    @Autowired
    public HandleMailTempReservationCreateEvent(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(TempReservationCreatedEvent event) {
        System.out.println("MAIL: Handle event by implementing AppListener");
        this.emailService.sendConfirmationEmail(event.getEmail(), event.getReservationId());
    }
}