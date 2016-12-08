import java.awt.*;


public class Buffer {
    private volatile boolean full = false,empty=true;
    private Gui gui = null;
    private volatile double x,y,rZ=50,rW=0;
    private Color colorInside = Color.red;
    private Color colorOutside = Color.black;
    private volatile double volume=Math.PI*Math.pow(rW,2);
    private volatile double volumeMax=Math.PI*Math.pow(rZ,2);

    Buffer(Gui gui) {
        this.gui = gui;
    }

    synchronized void get(int id,Circle circle) {
        if(circle.isFull()) return;

        while (empty) {
            try {
                gui.addToTextArea("Konsument #" + id + "   bufor pusty - czekam");
                wait();
            } catch (InterruptedException ignored) {}
        }
        if (circle.isInsideBuffer()&&volume-circle.volume>=0) {
            gui.addToTextArea("Konsument #" + id + " chce zabrac");
            volume -= circle.volume;
            circle.setFull();
            rW = Math.sqrt(volume / Math.PI);
            full = rW >= rZ||volume+circle.volume>=volumeMax;
            empty = rW<=0||volume-circle.volume<0;
        }

        notifyAll();
    }

    synchronized void put(int id,Circle circle) {
        if(!circle.isFull()) return;
        while (full) {
            try {
                gui.addToTextArea("Producent #" + id + "   bufor zajety - czekam");
                wait();
            } catch (InterruptedException ignored) {}
        }
        if (circle.isInsideBuffer()&&volume+circle.volume<=volumeMax){
            gui.addToTextArea("Producent #" + id + "  chce oddac");
            volume+=circle.volume;
            rW=Math.sqrt(volume/Math.PI);
            gui.addToTextArea("Producent #" + id + "       oddal");
            circle.setFull();
            full = rW >= rZ||volume+circle.volume>=volumeMax;
            empty = rW<=0||volume-circle.volume<0;
        }
        notifyAll();
    }

    public void refreshCoordinates(){
        this.x=Gui.getFrameSize().getWidth()/2;
        this.y=((Gui.getFrameSize().getHeight()-(Gui.getFrameSize().getHeight() * 0.25)-gui.getJmenuSize().getHeight())/2);
    }

    void draw(Graphics g) {
        g.setColor(colorOutside);
        g.drawOval((int) (x - rZ), (int) (y - rZ), (int) (2 * rZ), (int) (2 * rZ));
        g.setColor(colorInside);
        g.fillOval((int) (x - rW), (int) (y - rW), (int) (2 * rW), (int) (2 * rW));
    }
    double getRadius(){
        return rZ;
    }
    double getPoint(boolean d){
        if(d) return x;
        else return y;

    }
}
