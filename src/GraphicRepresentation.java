import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Random;

public class GraphicRepresentation extends JPanel {
    private ArrayList<Producer> producers = new ArrayList<>();
    private ArrayList<Buyer> buyers = new ArrayList<>();
    private Random random= new Random();
    private boolean currentFreezeState = false;
    private static Buffer buffer = null;
    private Dimension frameSize;
    GraphicRepresentation(Gui gui) {
        buffer = new Buffer(gui);
        this.addComponentListener(new CustomComponentListener());
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
            Producer producer = new Producer(buffer,this,i,random.nextInt((int)Gui.getFrameSize().getWidth()),random.nextInt((int)Gui.getFrameSize().getHeight()));
            producer.setDefaultColor(Color.green);
            producers.add(producer);
        }
        for (int i = 0; i < Gui.getHowManyBuyers(); i++) {
            Buyer buyer = new Buyer(buffer,this,i,random.nextInt((int)Gui.getFrameSize().getWidth()),random.nextInt((int)Gui.getFrameSize().getHeight()));
            buyer.setDefaultColor(Color.yellow);
            buyers.add(buyer);
        }
        for (Producer p : producers) p.start();
        for (Buyer k : buyers) k.start();
        repaint();
    }
    void exit() {
        for (Producer p : producers) p.kill();
        for (Buyer k : buyers) k.kill();
        producers.clear();
        buyers.clear();
    }

    void freeze() {
        currentFreezeState = !currentFreezeState;
        for (Producer p : producers) p.setFreeze(currentFreezeState);
        for (Buyer k : buyers) k.setFreeze(currentFreezeState);
    }

    void kill() {
        buyers.clear();
        producers.clear();
        repaint();
    }
    public void refreshCoordinates(){
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
    public Dimension getFrameSize(){
        return frameSize;
    }
}

