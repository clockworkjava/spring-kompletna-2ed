package pl.clockworkjava.gnomix.controllers.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.clockworkjava.gnomix.controllers.dto.CreateNewGuestDTO;
import pl.clockworkjava.gnomix.controllers.dto.ReservationCreateTmpRestDTO;
import pl.clockworkjava.gnomix.controllers.dto.ReservationDTO;
import pl.clockworkjava.gnomix.controllers.dto.TmpReservationCreatedDTO;
import pl.clockworkjava.gnomix.domain.guest.Guest;
import pl.clockworkjava.gnomix.domain.guest.GuestService;
import pl.clockworkjava.gnomix.domain.reservation.Reservation;
import pl.clockworkjava.gnomix.domain.reservation.ReservationService;

import java.util.List;

@RestController
public class RestReservationController {

    private final ReservationService reservationService;
    private final GuestService guestService;

    @Autowired
    public RestReservationController(ReservationService reservationService, GuestService guestService) {
        this.reservationService = reservationService;
        this.guestService = guestService;
    }

    @PostMapping("api/createTemporaryReservation")
    public TmpReservationCreatedDTO createTempReservation(@Valid @RequestBody ReservationCreateTmpRestDTO payload) {

        String confirmationUrl = this.reservationService
                .createTemporaryReservation(payload.roomId(), payload.fromDate(), payload.toDate(), payload.email());

        if (confirmationUrl == null || confirmationUrl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find room with such ID");
        }

        return new TmpReservationCreatedDTO("Success", confirmationUrl);
    }

    @PostMapping("api/confirmReservation/{reservationId}")
    public ResponseEntity<Void> confirmReservation(@PathVariable long reservationId, @RequestBody CreateNewGuestDTO guestDto) {

        var result = this.reservationService.confirmReservation(reservationId);

        Guest g = this.guestService.create(guestDto.firstName(), guestDto.lastName(), guestDto.dateOfBirth(), guestDto.gender());

        this.reservationService.attachGuestToReservation(g, reservationId);

        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("api/reservations")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_RECEPTION')")
    public List<ReservationDTO> findAll() {
        List<Reservation> reservations = this.reservationService.getAll();
        return reservations
                .stream()
                .map(r -> new ReservationDTO(r.getId(), r.getRoom().getNumber(),
                        r.getOwner()!=null?r.getOwner().getFirstName() + " "  + r.getOwner().getLastName():"",
                        r.getFromDate(), r.getToDate(), r.isConfirmed()))
                .toList();
    }

}