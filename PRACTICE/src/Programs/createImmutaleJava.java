package Programs;

public class createImmutaleJava {

    private final int x;
    private final int y;
    private final String master;

    public createImmutaleJava(int x, int y, String master) {
        this.x = x;
        this.y = y;
        this.master = master;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getMaster() {
        return master;
    }
}

