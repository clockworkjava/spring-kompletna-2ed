package pl.clockworkjava.gnomix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.clockworkjava.gnomix.domain.guest.GuestService;

@Controller
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

}
