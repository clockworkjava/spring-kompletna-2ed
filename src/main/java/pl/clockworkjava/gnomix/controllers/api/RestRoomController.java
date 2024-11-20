package pl.clockworkjava.gnomix.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.clockworkjava.gnomix.controllers.dto.AvailableRoomDTO;
import pl.clockworkjava.gnomix.domain.reservation.ReservationService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class RestRoomController {

    private final ReservationService reservationService;

    @Autowired
    public RestRoomController(ReservationService service) {
        this.reservationService = service;
    }

    @GetMapping("api/getFreeRooms")
    public List<AvailableRoomDTO> getAvailableRooms(
            LocalDate from,
            LocalDate to,
            int size
    ) {
        try {
            return reservationService.getAvailableRooms(from, to, size)
                    .stream()
                    .map(room -> new AvailableRoomDTO(room.getNumber(), room.getId(), room.getBeds(), room.size()))
                    .toList();
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }
}
