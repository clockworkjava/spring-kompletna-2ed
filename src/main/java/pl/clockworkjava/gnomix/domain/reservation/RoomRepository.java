package pl.clockworkjava.gnomix.domain.reservation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import pl.clockworkjava.gnomix.domain.room.Bed;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.util.*;

@Repository
public class RoomRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Room> findAll() {
        return this.entityManager.createQuery("SELECT room FROM Room room", Room.class).getResultList();
    }

    @Transactional
    public Room create(String roomNumber, List<Bed> roomSetup) {
        Room result = new Room(roomNumber, roomSetup);
        this.entityManager.persist(result);
        return result;
    }

    public Room findById(long id) throws NoSuchElementException {
        Room r = this.entityManager.find(Room.class, id);

        if(r == null) {
            throw new NoSuchElementException();
        } else {
            return r;
        }
    }

    @Transactional
    public void removeById(long id) {
        Room roomToBeRemoved = this.findById(id);
        this.entityManager.remove(roomToBeRemoved);
    }

    @Transactional
    public void update(Room r) {
        this.entityManager.merge(r);
    }
}
