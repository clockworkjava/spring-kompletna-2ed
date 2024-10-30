package pl.clockworkjava.gnomix.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.clockworkjava.gnomix.domain.guest.GuestService;

@Controller
@Slf4j
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("/guests")
    public String guests(Model model) {

        model.addAttribute("guests", this.guestService.findAll());
        return "guests";
    }

    @GetMapping("/createNewGuest")
    public String createNewGuest() {
        return "createNewGuest";
    }

    @PostMapping("/createNewGuest")
    public String createNewGuest(
          @RequestParam String firstName,
          @RequestParam String lastName,
          @RequestParam String dateOfBirth
    ) {
        log.info(String.format("Got create new guest request, with params: %s %s %s", firstName, lastName, dateOfBirth));
        this.guestService.create(firstName, lastName, dateOfBirth);
        return "redirect:guests";
    }

}
