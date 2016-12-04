class Producer extends Circle {


    Producer(Buffer buffer,GraphicRepresentation gR, int id, double x, double y) {
        super(buffer,gR,id,x,y);
    }

    public void run() {
        while (!kill) {
            if(!full&&isTouchingWall()) full=true;
            while (freeze) sleepWell(1);
            changeVector(getRandom(-200,200)*0.0001,getRandom(-200,200)*0.0001);
            if(!isOutsideBuffer()) buffer.put(id,this);
            move();
            gR.repaint();
            sleepWell(10);
        }
    }
}//TODO: Sprawić aby wątki się kończyły





