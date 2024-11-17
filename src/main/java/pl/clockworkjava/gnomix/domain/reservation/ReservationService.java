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

    public List<Room> getAvaiableRooms(LocalDate from, LocalDate to, int size) {
        return this.roomService.findAll();
    }
}