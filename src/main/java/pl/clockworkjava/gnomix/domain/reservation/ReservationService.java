package pl.clockworkjava.gnomix.domain.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.clockworkjava.gnomix.domain.guest.Guest;
import pl.clockworkjava.gnomix.domain.room.Room;
import pl.clockworkjava.gnomix.domain.room.RoomService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final RoomService roomService;
    private final ApplicationEventPublisher publisher;

    @Value("${gnomix.protocol}")
    private String protocol;

    @Value("${gnomix.domain}")
    private String domain;

    @Value("${gnomix.port}")
    private String port;

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

    public List<Room> getAvailableRooms(LocalDate from, LocalDate to, int size) throws IllegalArgumentException {

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
        return this.repository.findByRoom_Id(room.getId());
    }

    public String createTemporaryReservation(long roomId, LocalDate fromDate, LocalDate toDate, String email) {

        Optional<Room> room = this.roomService.findById(roomId);

        if(room.isPresent()) {
            Reservation tmp = new Reservation(fromDate, toDate, room.get(), email);
            this.repository.save(tmp);
            return String.format("%s://%s:%s/%s/%d", protocol, domain, port, "/api/confirmReservation", tmp.getId());
        } else {
            return null;
        }
    }

    public boolean confirmReservation(long reservationId) {

        Optional<Reservation> byId = this.repository.findById(reservationId);

        if(byId.isPresent()) {
            byId.get().confirm();
            this.repository.save(byId.get());
            return true;
        } else {
            return false;
        }

    }

    public void removeById(long id) {
        this.repository.deleteById(id);
    }

    public void removeUnconfirmedReservations() {

        this.repository.findByConfirmed(false).stream()
                .filter(reservation -> reservation.getCreationDate().plusMinutes(60)
                        .isBefore(LocalDateTime.now()))
                .forEach(reservation ->
                        this.repository.deleteById(reservation.getId())
                );
    }

    public void attachGuestToReservation(Guest g, long reservationId) {
        Optional<Reservation> byId = this.repository.findById(reservationId);

        if(byId.isPresent()) {
            byId.get().setOwner(g);
            this.repository.save(byId.get());
        }
    }

    public Optional<Reservation> getAnyConfirmedReservation(long id) {
        return this.repository.findFirstByConfirmedAndOwner_Id(true, id);
    }

    public List<Reservation> getConfirmedReservations() {
        return this.repository.findByConfirmed(true);
    }

    public Optional<Reservation> getAnyConfirmedReservationForRoom(Long id) {
        return repository.findFirstByConfirmedAndRoom_Id(true, id);
    }
}