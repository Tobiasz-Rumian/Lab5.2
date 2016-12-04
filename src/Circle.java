import java.awt.*;
import java.util.Random;

abstract class Circle extends Thread{

    int id;
    boolean kill = false;
    boolean freeze = false;
    boolean full=false;
    double x, y;
    float r = 15;
    Color defaultColor;
    GraphicRepresentation gR;
    Buffer buffer;
    static Random random = new Random();
    double vectorX=2d;
    double vectorY=2d;
    double volume=Math.PI*Math.pow(r,2);

    Circle(Buffer buffer,GraphicRepresentation gR,int id,double px, double py) {
        this.x = px;
        this.y = py;
        this.gR=gR;
        this.buffer=buffer;
        this.id=id;
    }

    void changeVector(double px,double py) {
        vectorX += px;
        vectorY += py;
    }

    void move() {
        if(x<=0||x>=gR.getFrameSize().getWidth()) vectorX=-vectorX;
        if(y<=0||y>=gR.getFrameSize().getHeight()) vectorY=-vectorY;
        x += vectorX;
        y += vectorY;
    }

    void draw(Graphics g) {
        g.setColor(defaultColor);
        g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        if(full){
            g.setColor(defaultColor);
            g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        }
    }

    void setDefaultColor(Color color) {
        defaultColor = color;
    }


    boolean isOutsideBuffer(){
        return (int)Math.hypot(buffer.getPoint(true)-x, buffer.getPoint(false)-y)>=(int)(r+buffer.getRadius());
    }

    boolean isInsideBuffer(){
        return (int)Math.hypot(buffer.getPoint(true)-x, buffer.getPoint(false)-y)<(int)(r+r+buffer.getRadius());
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

    public static int getRandom(int from, int to) {
        if (from < to)
            return from + random.nextInt(Math.abs(to - from));
        return from - random.nextInt(Math.abs(to - from));
    }
    boolean isTouchingWall(){
        return x <= 0 || x >= gR.getFrameSize().getWidth() || y <= 0 || y >= gR.getFrameSize().getHeight();
    }
    boolean getFull(){
        return full;
    }

    void setFull(){
        full=!full;
    }
}

