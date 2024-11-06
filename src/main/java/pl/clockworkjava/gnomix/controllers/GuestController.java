package pl.clockworkjava.gnomix.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.clockworkjava.gnomix.controllers.dto.CreateNewGuestDTO;
import pl.clockworkjava.gnomix.domain.guest.GuestService;

@Controller
@Slf4j
@RequestMapping("/guests")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
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
    public String createNewGuest(CreateNewGuestDTO dto) {
        log.info("Got create new guest request {}", dto);
        this.guestService.create(dto.firstName(), dto.lastName(), dto.dateOfBirth(), dto.gender());
        return "redirect:/guests";
    }

    @GetMapping("/delete/{id}")
    public String deleteGuest(@PathVariable long id, RedirectAttributes ra) {
        log.info("Trying to delete guest with id {}", id);
        boolean result = this.guestService.removeById(id);
        ra.addFlashAttribute("removalResult", result);
        return "redirect:/guests";
    }

}
