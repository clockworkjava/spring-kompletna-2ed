package pl.clockworkjava.gnomix.domain.room;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Room newOne = new Room(roomNumber, roomSetup);
        return this.repository.save(newOne);
    }

    public void removeById(long id) {
        this.repository.deleteById(id);
    }

    public Optional<Room> findById(long id) {
        return this.repository.findById(id);
    }

    public void editRoom(long id, String number, List<Bed> beds) {
        this.findById(id).ifPresent( room -> {
            room.modify(number, beds);
            this.repository.save(room);
        });
    }

    public List<Room> getRoomsForSize(int size) {

        if(size <= 0) {
            return new ArrayList<>();
        }

        return this.repository.findAll()
                .stream()
                .filter( r -> r.size()>=size)
                .collect(Collectors.toList());
    }
}
