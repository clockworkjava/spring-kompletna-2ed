package pl.clockworkjava.gnomix.controllers.dto;

import pl.clockworkjava.gnomix.domain.room.Bed;

import java.util.List;

public record CreateRoomDTO(String number, List<Bed> beds) {
}
