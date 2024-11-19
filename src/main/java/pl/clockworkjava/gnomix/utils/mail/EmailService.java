package pl.clockworkjava.gnomix.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender sender;

    @Value("${gnomix.protocol}")
    private String protocol;

    @Value("${gnomix.domain}")
    private String domain;

    @Value("${gnomix.port}")
    private String port;

    private String confirmationEndpoint = "reservations/confirm";

    @Autowired
    public EmailService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendConfirmationEmail(String email, long reservationId) {

        SimpleMailMessage mail = new SimpleMailMessage();

        String endpoint = String.format("%s://%s:%s/%s/%d", protocol, domain, port, confirmationEndpoint, reservationId);

        mail.setTo(email);
        mail.setFrom("systemgnomix@gmail.com");
        mail.setSubject("Potwierdź rezerwację");
        mail.setText("Dziękujemy za dokonanie rezerwacji, by ją potwierdzić kliknij w link: " + endpoint);

        this.sender.send(mail);

    }
}
