package pl.clockworkjava.gnomix.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.clockworkjava.gnomix.domain.reservation.RoomService;
import pl.clockworkjava.gnomix.domain.room.Bed;
import pl.clockworkjava.gnomix.domain.room.Room;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService service) {
        this.roomService = service;
    }

    @GetMapping
    public String rooms(Model model) {
        model.addAttribute("rooms", roomService.findAll());
        return "rooms";
    }

    @GetMapping("/create")
    public String createNewRoom() {
        return "createNewRoom";
    }

    @PostMapping
    public String createNewRoom(String roomNumber, String bedSetup) {
        log.info("Got create new room request {} {}", roomNumber, bedSetup);

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
        return "redirect:/rooms";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable long id, RedirectAttributes ra) {
        log.info("Trying to delete room with id {}", id);
        boolean result = this.roomService.removeById(id);
        ra.addFlashAttribute("removalResult", result);
        return "redirect:/rooms";
    }

    @GetMapping("/edit/{id}")
    public String editGuest(@PathVariable long id, Model model, RedirectAttributes ra) {
        log.info("Trying to show edit form for room with id {}", id);
        Optional<Room> roomToEdit = this.roomService.findById(id);
        if(roomToEdit.isPresent()) {
            model.addAttribute("room", roomToEdit.get());
            model.addAttribute("bedsAsStr", roomToEdit.get().getBedsAsString());
            return "editRoom";
        } else {
            log.warn("Room with id {} not found. Unable to edit", id);
            ra.addFlashAttribute("editResult", false);
            return "redirect:/rooms";
        }

    }

    @PostMapping("/edit")
    public String editGuest(long id, String roomNumber, String bedSetup) {
        log.info("Trying to edit room with id {}", id);
        List<Bed> beds = Arrays.stream(bedSetup.split("\\+")).map(type -> {
            if ("1".equals(type)) {
                return Bed.SINGLE;
            } else if ("2".equals(type)) {
                return Bed.DOUBLE;
            } else {
                throw new IllegalArgumentException("Only '1' and '2' values are supported");
            }
        }).toList();
        this.roomService.editRoom(id, roomNumber, beds);
        return "redirect:/rooms";
    }

}
