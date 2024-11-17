package pl.clockworkjava.gnomix.domain.reservation;

import jakarta.persistence.*;
import lombok.Data;
import pl.clockworkjava.gnomix.domain.guest.Guest;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate fromDate;
    private LocalDate toDate;
    private boolean confirmed;
    private LocalDateTime creationDate;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    private Guest owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    public Reservation(LocalDate fromDate, LocalDate toDate, Guest owner, Room room) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.owner = owner;
        this.room = room;
    }

    public Reservation(LocalDate fromDate, LocalDate toDate, boolean confirmed, LocalDateTime creationDate, String email, Guest owner, Room room) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.confirmed = confirmed;
        this.creationDate = creationDate;
        this.email = email;
        this.owner = owner;
        this.room = room;
    }

    public Reservation(LocalDate fromDate, LocalDate toDate, boolean confirmed, LocalDateTime creationDate, Guest owner, Room room) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.confirmed = confirmed;
        this.creationDate = creationDate;
        this.owner = owner;
        this.room = room;
    }

    Reservation() {
    }

    public Reservation(LocalDate fromDate, LocalDate toDate, Room room, String email) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.room = room;
        this.email = email;
        this.confirmed = false;
        this.creationDate = LocalDateTime.now();
    }
}
