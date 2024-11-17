package pl.clockworkjava.gnomix.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.clockworkjava.gnomix.domain.room.RoomService;
import pl.clockworkjava.gnomix.domain.room.Bed;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoomService roomService;

    @Test
    public void testGuests() throws Exception {

        Room r = new Room("1408", List.of(Bed.SINGLE));

        Mockito.when(roomService.findAll()).thenReturn(List.of(r));

        mockMvc
                .perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(view().name("rooms"))
                .andExpect(model().attributeExists("rooms"))
                .andExpect(content().string(containsString("1408")));
    }

    @Test
    public void testCreateRoom() throws Exception {
        String postContent = "roomNumber=1234&bedSetup=1";

        MockHttpServletRequestBuilder request =
                post("/createNewRoom")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content(postContent);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("rooms"));

        Mockito.verify(roomService, Mockito.times(1))
                .create("1234", List.of(Bed.SINGLE));

    }

    @Test
    public void testCreateRoomBigRoom() throws Exception {
        String postContent = "roomNumber=1234&bedSetup=2%2B1%2B1";

        MockHttpServletRequestBuilder request =
                post("/createNewRoom")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content(postContent);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("rooms"));

        Mockito.verify(roomService, Mockito.times(1))
                .create("1234", List.of(Bed.DOUBLE, Bed.SINGLE, Bed.SINGLE));

    }
}
