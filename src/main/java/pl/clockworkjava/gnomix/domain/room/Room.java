package pl.clockworkjava.gnomix.domain.room;

import lombok.Data;

import java.util.List;

@Data
public class Room {
    private final String number;
    private final List<Bed> beds;

    public int size() {
        return beds.stream().mapToInt(Bed::getSize).sum();
    }
}