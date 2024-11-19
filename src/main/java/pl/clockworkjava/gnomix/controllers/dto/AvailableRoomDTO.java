package pl.clockworkjava.gnomix.controllers.dto;

import pl.clockworkjava.gnomix.domain.room.Bed;

import java.util.List;

public record AvailableRoomDTO(String number, long id, List<Bed> beds, int size) {
}
