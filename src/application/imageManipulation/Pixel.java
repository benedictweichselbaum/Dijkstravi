package application.imageManipulation;

class Pixel {
    private int x;
    private int y;

    Pixel (int xCo, int yCo) {
        this.x = xCo;
        this.y = yCo;
    }

    int getX() {
        return x;
    }
    int getY() {
        return y;
    }
}
