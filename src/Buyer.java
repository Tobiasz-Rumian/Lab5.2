public class Buyer extends Circle {

    Buyer(Buffer buffer,GraphicRepresentation gR, int id, double x, double y) {
        super(buffer,gR,id,x,y);
    }

    public void run() {
        while (!kill) {
            while (freeze) sleepWell(1);
            if(full&&isTouchingWall()) full=false;
            changeVector(getRandom(-200,200)*0.0001,getRandom(-200,200)*0.0001);
            move();
            gR.repaint();
            if(!isOutsideBuffer()) buffer.get(id,this);
            sleepWell(10);
        }
    }
}//TODO: Sprawić aby wątki się kończyły
