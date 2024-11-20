package pl.clockworkjava.gnomix.controllers.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.clockworkjava.gnomix.controllers.dto.CreateNewGuestDTO;
import pl.clockworkjava.gnomix.domain.guest.Guest;
import pl.clockworkjava.gnomix.domain.guest.GuestService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/guests")
public class RestGuestController {


    private final GuestService guestService;

    public RestGuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public List<Guest> findAll() {
        return guestService.findAll();
    }

    @GetMapping("/{id}")
    public Guest find(@PathVariable long id) {
        return guestService.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such guest"));
    }

    @PostMapping
    public Guest create(@RequestBody CreateNewGuestDTO dto) {
        return this.guestService.create(dto.firstName(), dto.lastName(), dto.dateOfBirth(), dto.gender());
    }
}
