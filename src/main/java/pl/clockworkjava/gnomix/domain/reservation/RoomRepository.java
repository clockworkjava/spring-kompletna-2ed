package pl.clockworkjava.gnomix.domain.reservation;

import org.springframework.stereotype.Repository;
import pl.clockworkjava.gnomix.domain.room.Bed;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.util.*;

@Repository
public class RoomRepository {

    private final List<Room> rooms;

    public RoomRepository() {
        Room r = new Room("1408", List.of(Bed.SINGLE));
        Room r1 = new Room("1432", List.of(Bed.DOUBLE, Bed.SINGLE));

        this.rooms = new ArrayList<>(List.of(r, r1));
    }

    public List<Room> findAll() {
        return Collections.unmodifiableList(this.rooms);
    }


    public Room create(String roomNumber, List<Bed> roomSetup) {
        Room result = new Room(roomNumber, roomSetup);
        this.rooms.add(result);
        return result;
    }

    public Room findById(long id) throws NoSuchElementException {
        return this.rooms.stream().filter(room -> room.getId() == id).findFirst().orElseThrow();
    }

    public void removeById(long id) {
        Room roomToBeRemoved = this.findById(id);
        this.rooms.remove(roomToBeRemoved);
    }
}
