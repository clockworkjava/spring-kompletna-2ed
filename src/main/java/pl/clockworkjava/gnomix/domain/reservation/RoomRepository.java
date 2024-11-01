package pl.clockworkjava.gnomix.domain.reservation;

import org.springframework.stereotype.Repository;
import pl.clockworkjava.gnomix.domain.room.Bed;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.util.Arrays;
import java.util.List;

@Repository
public class RoomRepository {

    private final List<Room> rooms;

    public RoomRepository() {
        Room r = new Room("1408", List.of(Bed.SINGLE));
        Room r1 = new Room("1432", List.of(Bed.DOUBLE, Bed.SINGLE));

        this.rooms = Arrays.asList(r, r1);
    }

    public List<Room> findAll() {
        return this.rooms;
    }
}
