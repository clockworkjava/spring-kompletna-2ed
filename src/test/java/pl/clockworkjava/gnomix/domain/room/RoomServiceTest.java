package pl.clockworkjava.gnomix.domain.room;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoomServiceTest {

    @Test
    public void createNewRoomValidData() {

        //give
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        ArgumentCaptor<Room> roomCaptor = ArgumentCaptor.forClass(Room.class);
        RoomService rs = new RoomService(roomRepository);
        List<Bed> bedTypes = Arrays.asList(Bed.DOUBLE, Bed.SINGLE, Bed.SINGLE);
        Room r = new Room("102", bedTypes);

        //when
        rs.create("102", bedTypes);



        //then
        Mockito.verify(roomRepository).save(roomCaptor.capture());
        assertEquals("102", roomCaptor.getValue().getNumber());
        assertEquals(3, roomCaptor.getValue().getBeds().size());
        assertEquals(Bed.DOUBLE, roomCaptor.getValue().getBeds().get(0));
        assertEquals(Bed.SINGLE, roomCaptor.getValue().getBeds().get(1));
        assertEquals(Bed.SINGLE, roomCaptor.getValue().getBeds().get(2));
    }

    @Test
    public void testGetNoRoomsForSize() {
        //given
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("101", List.of(Bed.DOUBLE)));
        rooms.add(new Room("102", List.of(Bed.SINGLE)));
        rooms.add(new Room("103", Arrays.asList(Bed.DOUBLE, Bed.SINGLE)));
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);

        RoomService roomService = new RoomService(roomRepository);

        //when
        List<Room> result = roomService.getRoomsForSize(4);

        //then
        assertEquals(0, result.size());

    }

    @Test
    public void testGetNoRoomsWrongSize() {
        //given
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room("101", Arrays.asList(Bed.DOUBLE)));
        rooms.add(new Room("102", Arrays.asList(Bed.SINGLE)));
        rooms.add(new Room("103", Arrays.asList(Bed.DOUBLE, Bed.SINGLE)));
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        Mockito.when(roomRepository.findAll()).thenReturn(rooms);
        RoomService roomService = new RoomService(roomRepository);

        //when
        List<Room> result = roomService.getRoomsForSize(-1);

        //then
        assertEquals(0, result.size());

    }
}
