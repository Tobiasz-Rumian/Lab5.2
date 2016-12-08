/*
 *  Klasa Circle
 *  Klasa abstrakcyjna zawierająca podwaliny pod klasy producer i buyer.
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */
import java.awt.*;
import java.util.Random;

abstract class Circle extends Thread {

    int id;
    boolean kill = false;
    boolean freeze = false;
    boolean full = false;
    private double x, y;
    private float r = 15;
    private Color defaultColor;
    GraphicRepresentation gR;
    Buffer buffer;
    private static Random random = new Random();
    private double vectorX = 2d;
    private double vectorY = 2d;
    double volume = Math.PI * Math.pow(r, 2);

    Circle(Buffer buffer, GraphicRepresentation gR, int id, double px, double py) {
        this.x = px;
        this.y = py;
        this.gR = gR;
        this.buffer = buffer;
        this.id = id;
    }

    void changeVector(double px, double py) {
        vectorX += px;
        vectorY += py;
    }

    void move() {
        if (x <= 0 || x >= gR.getFrameSize().getWidth()) vectorX = -vectorX;
        if (y <= 0 || y >= gR.getFrameSize().getHeight()) vectorY = -vectorY;
        x += vectorX;
        y += vectorY;
    }

    void draw(Graphics g) {
        g.setColor(defaultColor);
        g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        if (full) {
            g.setColor(defaultColor);
            g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        }
    }

    void setDefaultColor(Color color) {
        defaultColor = color;
    }


    boolean isOutsideBuffer() {
        return (Math.sqrt(Math.pow(buffer.getPoint(true) - x, 2) + Math.pow(buffer.getPoint(false) - y, 2))) >= (r + buffer.getRadius());
    }

    boolean isInsideBuffer() {
        return (Math.sqrt(Math.pow(buffer.getPoint(true) - x, 2) + Math.pow(buffer.getPoint(false) - y, 2))) < buffer.getRadius() - r;
    }

    void sleepWell(int time) {
        try {
            sleep((int) (Math.random() * time));
        } catch (InterruptedException ignored) {
        }
    }

    void kill() {
        kill = true;
    }

    void setFreeze(boolean freeze) {
        this.freeze = freeze;
    }

    static int getRandom(int from, int to) {
        if (from < to) return from + random.nextInt(Math.abs(to - from));
        return from - random.nextInt(Math.abs(to - from));
    }

    boolean isTouchingWall() {
        return x <= 0 || x >= gR.getFrameSize().getWidth() || y <= 0 || y >= gR.getFrameSize().getHeight();
    }

    boolean isFull() {
        return full;
    }

    void setFull() {
        full = !full;
    }

    void moveInBuffer() {
        changeVector(getRandom(-200, 200) * 0.0001, getRandom(-200, 200) * 0.0001);
        move();
        gR.repaint();
        sleepWell(110 - gR.getGui().getSpeed());
    }
}

