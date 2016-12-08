/*
 *  Klasa GraphicRepresentation
 *
 *  Klasa zajmuje się graficzną reprezentacją wątków.
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Random;

public class GraphicRepresentation extends JPanel {
    private ArrayList<Producer> producers = new ArrayList<>();
    private ArrayList<Buyer> buyers = new ArrayList<>();
    private Random random = new Random();
    private boolean currentFreezeState = false;
    private static Buffer buffer = null;
    private Dimension frameSize;
    private Gui gui = null;

    GraphicRepresentation(Gui gui) {
        this.gui = gui;
        buffer = new Buffer(gui);
        buffer.refreshCoordinates();
        this.addComponentListener(new CustomComponentListener());
        frameSize = this.getSize();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        for (Circle c : producers) {
            c.draw(g);
        }
        for (Circle c : buyers) c.draw(g);
        buffer.draw(g);
    }

    void createFigures() {

        for (int i = 0; i < Gui.getHowManyProducers(); i++) {
            Producer producer = new Producer(buffer, this, i, random.nextInt(this.getWidth()), random.nextInt(this.getHeight()));
            producer.setDefaultColor(Color.green);
            producers.add(producer);
        }
        for (int i = 0; i < Gui.getHowManyBuyers(); i++) {
            Buyer buyer = new Buyer(buffer, this, i, random.nextInt(this.getWidth()), random.nextInt(this.getHeight()));
            buyer.setDefaultColor(Color.yellow);
            buyers.add(buyer);
        }
        producers.forEach(Thread::start);
        buyers.forEach(Thread::start);
        repaint();
    }

    void freeze() {
        currentFreezeState = !currentFreezeState;
        for (Producer p : producers) p.setFreeze(currentFreezeState);
        for (Buyer k : buyers) k.setFreeze(currentFreezeState);
    }

    void kill() {
        for (Circle c:producers) {
            c.kill();
        }
        for (Circle c:buyers) {
            c.kill();
        }
        buyers = new ArrayList<>();
        producers = new ArrayList<>();
        repaint();
    }

    void refreshCoordinates() {
        buffer.refreshCoordinates();
    }

    class CustomComponentListener implements ComponentListener {

        public void componentResized(ComponentEvent e) {
            frameSize = e.getComponent().getBounds().getSize();
        }

        public void componentMoved(ComponentEvent e) {
        }

        public void componentShown(ComponentEvent e) {
        }

        public void componentHidden(ComponentEvent e) {
        }
    }

    Dimension getFrameSize() {
        return frameSize;
    }

    Gui getGui() {
        return gui;
    }
}

