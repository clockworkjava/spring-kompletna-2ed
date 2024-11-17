package pl.clockworkjava.gnomix.domain.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.clockworkjava.gnomix.domain.room.Room;
import pl.clockworkjava.gnomix.domain.room.RoomService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private ReservationRepository repository;
    private RoomService roomService;

    @Autowired
    public ReservationService(
            ReservationRepository repository,
            RoomService roomService) {
        this.repository = repository;
        this.roomService = roomService;
    }

    public List<Reservation> getAll() {
        return this.repository.findAll();
    }

    public List<Room> getAvailableRooms(LocalDate from, LocalDate to, int size) {

        if(size<0 || size>10) {
            throw new IllegalArgumentException("Wrong size param [1-10]");
        }

        if(from.isEqual(to) || to.isBefore(from)) {
            throw new IllegalArgumentException("Wrong dates");
        }

        return this.roomService.findAll();
    }
}