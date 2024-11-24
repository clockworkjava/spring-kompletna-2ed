package pl.clockworkjava.gnomix.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.clockworkjava.gnomix.domain.guest.Gender;
import pl.clockworkjava.gnomix.domain.guest.Guest;
import pl.clockworkjava.gnomix.domain.guest.GuestService;
import pl.clockworkjava.gnomix.domain.reservation.ReservationService;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(GuestController.class)
public class GuestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GuestService guestService;

    @MockBean
    ReservationService reservationService;

    @Test
    public void testGuests() throws Exception {

        Guest guest = new Guest("Pawel", "Cwik", LocalDate.of(1986, 11, 13), Gender.MALE);

        Mockito.when(guestService.findAll()).thenReturn(List.of(guest));

        mockMvc
                .perform(get("/guests"))
                .andExpect(status().isOk())
                .andExpect(view().name("guests"))
                .andExpect(model().attributeExists("guests"))
                .andExpect(content().string(containsString("Pawel")));
    }

    @Test
    public void testCreateGuest() throws Exception {
        String postContent = "firstName=Pawel&lastName=Cwik&dateOfBirth=2021-09-15&gender=MALE";

        MockHttpServletRequestBuilder request =
                post("/guests")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .content(postContent);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/guests"));

        Mockito.verify(guestService, Mockito.times(1))
                .create("Pawel", "Cwik", LocalDate.parse("2021-09-15"), Gender.MALE);

    }
}
