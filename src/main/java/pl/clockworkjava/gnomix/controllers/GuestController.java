package pl.clockworkjava.gnomix.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.clockworkjava.gnomix.controllers.dto.CreateNewGuestDTO;
import pl.clockworkjava.gnomix.controllers.dto.UpdateGuestDTO;
import pl.clockworkjava.gnomix.domain.guest.Guest;
import pl.clockworkjava.gnomix.domain.guest.GuestService;
import pl.clockworkjava.gnomix.domain.reservation.ReservationService;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/guests")
public class GuestController {

    private final GuestService guestService;
    private final ReservationService reservationService;

    @Autowired
    public GuestController(GuestService guestService, ReservationService reservationService) {

        this.guestService = guestService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String guests(Model model) {
        model.addAttribute("guests", this.guestService.findAll());
        return "guests";
    }

    @GetMapping("/create")
    public String createNewGuest() {
        return "createNewGuest";
    }

    @PostMapping
    public String createNewGuest(@Valid CreateNewGuestDTO dto, BindingResult result, Model model) {
        log.info("Got create new guest request {}", dto);
        if(result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "createNewGuest";
        } else {
            this.guestService.create(dto.firstName(), dto.lastName(), dto.dateOfBirth(), dto.gender());
            return "redirect:/guests";
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteGuest(@PathVariable long id, RedirectAttributes ra) {
        log.info("Trying to delete guest with id {}", id);
        this.guestService.removeById(id);
        return "redirect:/guests";
    }

    @GetMapping("/edit/{id}")
    public String editGuest(@PathVariable long id, Model model, RedirectAttributes ra) {
        log.info("Trying to show edit form for guest with id {}", id);
        Optional<Guest> guestToEdit = this.guestService.findById(id);
        if(guestToEdit.isPresent()) {
            model.addAttribute("guest", guestToEdit.get());
            return "editGuest";
        } else {
            log.warn("Guest with id {} not found. Unable to edit", id);
            ra.addFlashAttribute("editResult", false);
            return "redirect:/guests";
        }

    }

    @PostMapping("/edit")
    public String editGuest(UpdateGuestDTO updateDto) {
        log.info("Trying to edit guest with id {}", updateDto.id());
        this.guestService.editGuest(updateDto.id(), updateDto.firstName(), updateDto.lastName(), updateDto.dateOfBirth(), updateDto.gender());
        return "redirect:/guests";
    }

    @PostMapping("/createAndAttachToReservation")
    public String createAndAttachToReservation(
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            long reservationId
    ) {
        Guest g = this.guestService.createNewGuest(firstName, lastName, dateOfBirth);
        this.reservationService.attachGuestToReservation(g, reservationId);
        return "thankyoupage";
    }

}
