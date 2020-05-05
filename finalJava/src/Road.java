import java.util.ArrayList;

public class Road {

    private int width, height, rowWid, rowHt;
    private int posX = 0;
    private int posY = 0;
    private ArrayList<Integer> arrayY = new ArrayList<>();
    private ArrayList<Integer> arrayX = new ArrayList<>();

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

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setCourse() {
        posY = (int) Math.round(Math.random()*9)*rowHt;
        arrayY.add(posY);
        arrayX.add(0);
        while (posX != width - rowWid) {
            posX = posX + rowWid;
            arrayX.add(posX);
            posY = (int) Math.round(Math.random()*9)*rowHt;
            arrayY.add(posY);
        }

        Integer[] array = arrayY.toArray(new Integer[0]);
    }

    public ArrayList<Integer> getArrayY() {
        return arrayY;
    }

    public ArrayList<Integer> getArrayX() {
        return arrayX;
    }
}