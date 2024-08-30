package pl.clockworkjava.gnomix.domain.guest;

public enum Gender {
    MALE("Mężyczna"), FEMALE("Kobieta");

    private final String asText;

    Gender(String asText) {
        this.asText = asText;
    }

    public String toString() {
        return this.asText;
    }
}
