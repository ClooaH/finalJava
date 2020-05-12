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
        arrayX.add(posX);
        posX = posX + rowWid;
        arrayY.add(posY);
        arrayX.add(posX);
        posX = posX + rowWid;

        for (int i = 1; i <= 5; i++) {
            posY = (int) Math.round(Math.random()*9)*rowHt;
            arrayY.add(posY);
            arrayX.add(posX);
            int ynumber1 = arrayY.get(arrayY.size()-1);
            int ynumber2 = arrayY.get(arrayY.size()-2);
            while (ynumber2 > ynumber1) {
                arrayY.add(arrayY.size()-1, ynumber2);
                arrayX.add(arrayX.size()-1, posX);
                posY = posY + 60;
                ynumber2 = ynumber2 - 60;
            }
            while (ynumber2 < ynumber1) {
                arrayY.add(arrayY.size()-1, ynumber2);
                arrayX.add(arrayX.size()-1, posX);
                posY = posY - 60;
                ynumber2 = ynumber2 + 60;
            }
            posX = posX + rowWid;
            arrayY.add(posY);
            arrayX.add(posX);
            posX = posX + rowWid;
        }
    }

    public ArrayList<Integer> getArrayY() {
        return arrayY;
    }

    public ArrayList<Integer> getArrayX() {
        return arrayX;
    }
}