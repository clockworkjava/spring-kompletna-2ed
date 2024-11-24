package pl.clockworkjava.gnomix.controllers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.clockworkjava.gnomix.controllers.dto.AvailableRoomDTO;
import pl.clockworkjava.gnomix.domain.reservation.ReservationService;
import pl.clockworkjava.gnomix.domain.room.Room;
import pl.clockworkjava.gnomix.domain.room.RoomService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(RestRoomController.class)
public class RestRoomControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getFreeRoomsHappyPath() throws Exception {

        //given
        String url = "/api/rooms/getFreeRooms?from=2022-03-12&to=2022-03-13&size=2";

        LocalDate fromDate = LocalDate.parse("2022-03-12");
        LocalDate toDate = LocalDate.parse("2022-03-13");
        int size = 2;


        Room r = new Room("101", new ArrayList<>());
        r.setId(101L);

        Mockito.when(reservationService.getAvailableRooms(fromDate, toDate, size)).thenReturn(Arrays.asList(r));

        MockHttpServletRequestBuilder request = get(url);


        //when

        MvcResult result = mockMvc.perform(request).andReturn();

        //then

        MockHttpServletResponse response = result.getResponse();

        CollectionType dtoCollection = mapper.getTypeFactory().constructCollectionType(List.class, AvailableRoomDTO.class);
        List<AvailableRoomDTO> results = mapper.readValue(response.getContentAsString(), dtoCollection);

        assertTrue(response.getStatus() == HttpStatus.OK.value());
        assertTrue(response.getContentType().equals("application/json"));
        assertTrue(results.size()==1);
        assertTrue(results.get(0).number().equals("101"));

        Mockito.verify(reservationService, Mockito.times(1))
                .getAvailableRooms(fromDate, toDate, size);

    }

    @Test
    public void getFreeRoomsInvalidSize() throws Exception {

        String url = "/api/rooms/getFreeRooms?from=2022-03-12&to=2022-03-13&size=20";

        MockHttpServletRequestBuilder request = get(url);

        LocalDate fromDate = LocalDate.parse("2022-03-12");
        LocalDate toDate = LocalDate.parse("2022-03-13");
        int size = 20;

        Mockito.when(reservationService.getAvailableRooms(fromDate, toDate, size)).thenThrow(new IllegalArgumentException("Wrong size"));

        mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(status().reason("Wrong size"));

    }


}