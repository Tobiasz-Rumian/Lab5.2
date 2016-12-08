/*
 *  Klasa Buffer
 *  Zajmuje się przechowywaniem materiału i obsługą poleceń pobierania i oddawania towaru.
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */
import java.awt.*;


class Buffer {
    private volatile boolean full = false, empty = true;
    private Gui gui = null;
    private volatile double x, y, rZ = 50, rW = 0;
    private Color colorInside = Color.red;
    private Color colorOutside = Color.black;
    private volatile double volume = Math.PI * Math.pow(rW, 2);
    private volatile double volumeMax = Math.PI * Math.pow(rZ, 2);
    private boolean wasRefreshed = false;

    Buffer(Gui gui) {
        this.gui = gui;
    }

    synchronized void get(int id, Circle circle) {

        while (empty) {
            try {
                gui.addToTextArea("Konsument #" + id + "   bufor pusty - czekam");
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        while (!circle.isOutsideBuffer() && !circle.isInsideBuffer()) {
            circle.moveInBuffer();
        }
        if (circle.isInsideBuffer()) {
            gui.addToTextArea("Konsument #" + id + "zabrał towar");
            volume -= circle.volume;
            circle.setFull();
            rW = Math.sqrt(volume / Math.PI);
            full = rW >= rZ || volume + circle.volume >= volumeMax;
            empty = rW <= 0 || volume - circle.volume < 0;
        }
        notifyAll();
    }

    synchronized void put(int id, Circle circle) {
        while (full) {
            try {
                gui.addToTextArea("Producent #" + id + "bufor pełny - czekam");
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        while (!circle.isOutsideBuffer() && !circle.isInsideBuffer()) {
            circle.moveInBuffer();
        }
        if (circle.isInsideBuffer()) {
            volume += circle.volume;
            rW = Math.sqrt(volume / Math.PI);
            gui.addToTextArea("Producent #" + id + "oddal towar");
            circle.setFull();
            full = rW >= rZ || volume + circle.volume >= volumeMax;
            empty = rW <= 0 || volume - circle.volume < 0;
        }
        notifyAll();
    }

    void refreshCoordinates() {
        if (!wasRefreshed) {
            this.x = Gui.getFrameSize().getWidth() / 2;
            this.y = ((Gui.getFrameSize().getHeight() - (Gui.getFrameSize().getHeight() * 0.25) - gui.getJmenuSize().getHeight()) / 2);
            wasRefreshed = true;
        }
    }

    void draw(Graphics g) {
        g.setColor(colorOutside);
        g.drawOval((int) (x - rZ), (int) (y - rZ), (int) (2 * rZ), (int) (2 * rZ));
        g.setColor(colorInside);
        g.fillOval((int) (x - rW), (int) (y - rW), (int) (2 * rW), (int) (2 * rW));
    }

    double getRadius() {
        return rZ;
    }

    double getPoint(boolean d) {
        if (d) return x;
        else return y;

    }
}
