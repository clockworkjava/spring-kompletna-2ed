package pl.clockworkjava.gnomix.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.clockworkjava.gnomix.domain.room.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
