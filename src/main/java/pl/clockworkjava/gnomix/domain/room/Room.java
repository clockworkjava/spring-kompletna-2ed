package pl.clockworkjava.gnomix.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Setter(AccessLevel.NONE)
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String description;
    @ElementCollection(targetClass = Bed.class)
    private List<Bed> beds;

    Room() {}

    public Room(String number, List<Bed> beds) {
        this.number = number;
        this.beds = new ArrayList<>(beds);
    }

    public Room(String number, List<Bed> beds, String description) {
        this.number = number;
        this.beds = new ArrayList<>(beds);
        this.description = description;
    }

    public int size() {
        return beds.stream().mapToInt(Bed::getSize).sum();
    }

    @JsonIgnore
    public String getBedsAsString() {
        return this.beds.stream().map(Bed::getSize).map(String::valueOf).collect(Collectors.joining("+"));
    }

    public void modify(String number, List<Bed> beds) {
        this.number = number;
        this.beds = new ArrayList<>(beds);
    }

    public void setId(Long i) {
        this.id = i;
    }
}