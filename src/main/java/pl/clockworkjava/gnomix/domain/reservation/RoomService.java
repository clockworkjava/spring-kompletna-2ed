package pl.clockworkjava.gnomix.domain.reservation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.clockworkjava.gnomix.domain.room.Bed;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
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

    public boolean removeById(long id) {
        try {
            this.repository.removeById(id);
            return true;
        } catch (NoSuchElementException ex) {
            log.info("Room marked for removal not found");
            return false;
        }
    }

    public Optional<Room> findById(long id) {
        try {
            return Optional.of(this.repository.findById(id));
        } catch (NoSuchElementException ex) {
            return Optional.empty();
        }
    }

    public void editRoom(long id, String number, List<Bed> beds) {
        this.findById(id).ifPresent( room -> room.modify(number, beds));
    }
}
