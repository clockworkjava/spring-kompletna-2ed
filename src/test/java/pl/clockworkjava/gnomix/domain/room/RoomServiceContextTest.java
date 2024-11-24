package pl.clockworkjava.gnomix.domain.room;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class RoomServiceContextTest {

    @Autowired
    private RoomRepository repository;

    @Test
    void contextLoads() {
    }

    @Test
    @Sql("/create_rooms.sql")
    public void testSize() {
        RoomService roomService = new RoomService(repository);

        //when
        List<Room> result = roomService.getRoomsForSize(2);

        //then
        assertEquals(2, result.size());
    }

    @Test
    @Sql("/create_rooms.sql")
    public void testSize2() {

        RoomService roomService = new RoomService(repository);
        //when
        List<Room> result = roomService.getRoomsForSize(1);

        //then
        assertEquals(3, result.size());
    }
}