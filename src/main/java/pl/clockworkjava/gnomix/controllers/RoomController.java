package pl.clockworkjava.gnomix.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.clockworkjava.gnomix.domain.reservation.RoomService;
import pl.clockworkjava.gnomix.domain.room.Bed;

import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
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

    @GetMapping("/createNewRoom")
    public String createNewRoom() {
        return "createNewRoom";
    }

    @PostMapping("/createNewRoom")
    public String createNewRoom(String roomNumber, String bedSetup) {
        log.info("Got create new room request " + roomNumber + " " + bedSetup);

        List<Bed> beds = Arrays.stream(bedSetup.split("\\+")).map(type -> {
            if ("1".equals(type)) {
                return Bed.SINGLE;
            } else if ("2".equals(type)) {
                return Bed.DOUBLE;
            } else {
                throw new IllegalArgumentException("Only '1' and '2' values are supported");
            }
        }).toList();
        this.roomService.create(roomNumber, beds);
        return "redirect:rooms";
    }

}
