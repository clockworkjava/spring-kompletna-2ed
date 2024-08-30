package pl.clockworkjava.gnomix.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.clockworkjava.gnomix.domain.reservation.RoomService;

@Controller
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService service) {
        this.roomService = service;
    }

    @GetMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "rooms";
    }

}
