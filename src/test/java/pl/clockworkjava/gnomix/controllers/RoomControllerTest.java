package pl.clockworkjava.gnomix.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.clockworkjava.gnomix.domain.reservation.RoomService;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoomService roomService;

    @Test
    public void testGuests() throws Exception {

        Room r = new Room("1408");

        Mockito.when(roomService.findAll()).thenReturn(List.of(r));

        mockMvc
                .perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms"))
                .andExpect(model().attributeExists("rooms"))
                .andExpect(content().string(containsString("1408")));
    }
}
