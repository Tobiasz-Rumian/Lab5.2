/*
 *  Klasa Buyer
 *  Tworzy wątki kupców i zajmuje się ich przemieszczaniem
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */
public class Buyer extends Circle {

    Buyer(Buffer buffer, GraphicRepresentation gR, int id, double x, double y) {
        super(buffer, gR, id, x, y);
    }

    public void run() {
        while (!kill) {
            while (freeze) sleepWell(1);
            if (full && isTouchingWall()) full = false;
            changeVector(getRandom(-200, 200) * 0.0001, getRandom(-200, 200) * 0.0001);
            move();
            gR.repaint();
            if (!isOutsideBuffer() && !isFull()) buffer.get(id, this);
            sleepWell(110 - gR.getGui().getSpeed());
        }
    }
}
