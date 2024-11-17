package pl.clockworkjava.gnomix.domain.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.clockworkjava.gnomix.domain.reservation.events.TempReservationCreatedEvent;
import pl.clockworkjava.gnomix.domain.room.Room;
import pl.clockworkjava.gnomix.domain.room.RoomService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final RoomService roomService;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public ReservationService(
            ReservationRepository repository,
            RoomService roomService,
            ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.roomService = roomService;
        this.publisher = publisher;
    }

    public List<Reservation> getAll() {
        return this.repository.findAll();
    }

    public List<Room> getAvailableRooms(LocalDate from, LocalDate to, int size) {

        List<Room> availableRooms = new ArrayList<>();

        if(size<0 || size>10) {
            throw new IllegalArgumentException("Wrong size param [1-10]");
        }

        if(from.isEqual(to) || to.isBefore(from)) {
            throw new IllegalArgumentException("Wrong dates");
        }

        List<Room> roomsWithProperSize = this.roomService.getRoomsForSize(size);

        for(Room room : roomsWithProperSize) {
            if(this.checkIfRoomAvailableForDates(room,from,to)) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    public boolean checkIfRoomAvailableForDates(Room room, LocalDate from, LocalDate to) {

        List<Reservation> reservations = this.getAllReservationsForRoom(room);

        Optional<Reservation> any = reservations
                .stream()
                .filter(
                        reservationStartsAtTheSameDate(from)
                                .or(reservationEndsAtTheSameDate(to))
                                .or(reservationStartsBetween(from, to))
                                .or(reservationEndsBetween(from, to))
                                .or(reservationContains(from, to))
                )
                .findAny();

        return any.isEmpty();
    }

    static Predicate<Reservation> reservationEndsAtTheSameDate(LocalDate to) {
        return reservation -> reservation.getToDate().equals(to);
    }

    static Predicate<Reservation> reservationContains(LocalDate from, LocalDate to) {
        return reservation -> reservation.getFromDate().isBefore(from) && reservation.getToDate().isAfter(to);
    }

    static Predicate<Reservation> reservationStartsBetween(LocalDate from, LocalDate to) {
        return reservation -> reservation.getFromDate().isAfter(from) && reservation.getFromDate().isBefore(to);
    }

    static Predicate<Reservation> reservationEndsBetween(LocalDate from, LocalDate to) {
        return reservation -> reservation.getToDate().isAfter(from) && reservation.getToDate().isBefore(to);
    }

    static Predicate<Reservation> reservationStartsAtTheSameDate(LocalDate from) {
        return reservation -> reservation.getFromDate().equals(from);
    }

    private List<Reservation> getAllReservationsForRoom(Room room) {
        return this.repository.findAll()
                .stream()
                .filter(reservation -> Objects.equals(reservation.getRoom().getId(), room.getId()))
                .collect(Collectors.toList());
    }

    public boolean createTemporaryReservation(long roomId, LocalDate fromDate, LocalDate toDate, String email) {

        Optional<Room> room = this.roomService.findById(roomId);

        room.ifPresent( r -> {
            Reservation tmp = new Reservation(fromDate, toDate, r, email);
            this.repository.save(tmp);
            TempReservationCreatedEvent trce = new TempReservationCreatedEvent(this, email, tmp.getId());
            publisher.publishEvent(trce);
        });

        return room.isPresent();
    }
}