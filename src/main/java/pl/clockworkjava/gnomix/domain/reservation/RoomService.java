package pl.clockworkjava.gnomix.domain.reservation;

import org.springframework.stereotype.Service;
import pl.clockworkjava.gnomix.domain.room.Bed;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    public List<Room> findAll() {
        return this.repository.findAll();
    }

    public Room create(String roomNumber, List<Bed> roomSetup) {
        return this.repository.create(roomNumber, roomSetup);
    }
}
