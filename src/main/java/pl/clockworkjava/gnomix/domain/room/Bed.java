package pl.clockworkjava.gnomix.domain.room;

public enum Bed {

    SINGLE(1), DOUBLE(2);

    private final int size;

    Bed(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}
