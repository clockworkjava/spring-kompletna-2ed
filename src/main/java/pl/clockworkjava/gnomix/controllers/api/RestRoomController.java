package pl.clockworkjava.gnomix.controllers.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.clockworkjava.gnomix.controllers.dto.AvailableRoomDTO;
import pl.clockworkjava.gnomix.controllers.dto.CreateRoomDTO;
import pl.clockworkjava.gnomix.domain.reservation.ReservationService;
import pl.clockworkjava.gnomix.domain.room.Room;
import pl.clockworkjava.gnomix.domain.room.RoomService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/rooms")
public class RestRoomController {

    private final ReservationService reservationService;
    private final RoomService roomService;

    @Autowired
    public RestRoomController(ReservationService service, RoomService roomService) {
        this.reservationService = service;
        this.roomService = roomService;
    }

    @GetMapping("getFreeRooms")
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

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_RECEPTION')")
    public List<Room> findAll() {
        return this.roomService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_RECEPTION')")
    public Room findById(@PathVariable Long id) {
        return this.roomService
                .findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Room create(@RequestBody CreateRoomDTO dto) {
        return this.roomService.create(dto.number(), dto.beds());
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @ApiResponse(responseCode = "200", description = "OK, removed")
    @ApiResponse(responseCode = "403", description = "Unable to remove the room, there is connected reservation")
    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        reservationService.getAnyConfirmedReservationForRoom(id).ifPresentOrElse(
                (r) ->  {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Room has reservation with id: " + r.getId());
                },
                () -> roomService.removeById(id)
        );
    }

    @PreAuthorize("hasRole('ROLE_MANAGER')")
    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody CreateRoomDTO dto) {
        this.roomService.editRoom(id, dto.number(), dto.beds());
    }
}
