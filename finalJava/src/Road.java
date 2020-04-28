public class Road {

    private int width, height, rowWid, rowHt;
    private int posX = 0;
    private int posY = 0;

    public Road(int width, int height, int rowWid, int rowHt) {

        this.width = width;
        this.height = height;
        this.rowWid = rowWid;
        this.rowHt = rowHt;

    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setCourse() {

        posY = (int) Math.round(Math.random()*9)*rowHt;

    }
}