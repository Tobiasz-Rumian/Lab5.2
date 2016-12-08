/*
 *  Klasa Producer
 *  Tworzy wątki producentów i zajmuje się ich przemieszczaniem
 *
 *  @author Tobiasz Rumian
 *  @version 1.1
 *   Data: 08 Grudzień 2016 r.
 *   Indeks: 226131
 *   Grupa: śr 13:15 TN
 */
class Producer extends Circle {


    Producer(Buffer buffer, GraphicRepresentation gR, int id, double x, double y) {
        super(buffer, gR, id, x, y);
    }

    public void run() {
        while (!kill) {
            if (!full && isTouchingWall()) full = true;
            while (freeze) sleepWell(1);
            changeVector(getRandom(-200, 200) * 0.0001, getRandom(-200, 200) * 0.0001);
            if (!isOutsideBuffer() && isFull()) buffer.put(id, this);
            move();
            gR.repaint();
            sleepWell(110 - gR.getGui().getSpeed());
        }
    }
}





