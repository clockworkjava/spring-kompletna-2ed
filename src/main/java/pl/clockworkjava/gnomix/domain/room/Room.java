package pl.clockworkjava.gnomix.domain.room;

import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class Room {
    private final long id;
    private String number;
    private List<Bed> beds;


    public Room(String number, List<Bed> beds) {
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        this.number = number;
        this.beds = beds;
    }

    public int size() {
        return beds.stream().mapToInt(Bed::getSize).sum();
    }

    public String getBedsAsString() {
        return this.beds.stream().map(Bed::getSize).map(String::valueOf).collect(Collectors.joining("+"));
    }

    public void modify(String number, List<Bed> beds) {
        this.number = number;
        this.beds = beds;
    }
}