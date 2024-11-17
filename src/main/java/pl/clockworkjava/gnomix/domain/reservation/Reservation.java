package pl.clockworkjava.gnomix.domain.reservation;

import jakarta.persistence.*;
import lombok.Data;
import pl.clockworkjava.gnomix.domain.guest.Guest;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.time.LocalDate;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate fromDate;
    private LocalDate toDate;

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

    Reservation() {
    }
}
