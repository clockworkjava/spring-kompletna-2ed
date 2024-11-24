package pl.clockworkjava.gnomix.domain.room;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository repository;

    @Test
    public void getByRoomNumber() {

        Room r1 = new Room("1A", List.of(Bed.DOUBLE));

        this.repository.save(r1);

        Optional<Room> result = this.repository.findFirstByNumberCaseInsensitive("1a");

        assertTrue(result.isPresent());

    }
}