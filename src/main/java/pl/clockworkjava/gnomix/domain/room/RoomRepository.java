package pl.clockworkjava.gnomix.domain.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findFirstByNumber(String number);

    @Query("SELECT r FROM Room r WHERE LOWER(r.number) = LOWER(:number)")
    Optional<Room> findFirstByNumberCaseInsensitive(@Param("number") String number);
}
