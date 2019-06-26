package application.imageManipulation;

public class Pixel {
    private int x;
    private int y;

    Pixel (int xCo, int yCo) {
        this.x = xCo;
        this.y = yCo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
