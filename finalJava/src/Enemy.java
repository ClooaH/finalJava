import java.util.ArrayList;

public class Enemy {

    private int width, height, rowWid, rowHt;
    private int health = 100;
    private int posX;
    private int posY;
    private int count = 0;
    private ArrayList<Integer> arrayY = new ArrayList<>();
    private ArrayList<Integer> arrayX = new ArrayList<>();

    public Enemy(int width, int height, int rowWid, int rowHt, ArrayList<Integer> arrayY, ArrayList<Integer> arrayX) {

        this.width = width;
        this.height = height;
        this.rowWid = rowWid;
        this.rowHt = rowHt;
        this.arrayY = arrayY;
        this.arrayX = arrayX;
    }

    public void setStartValues() {

        posY = arrayY.get(count);
        posX = arrayX.get(count);

    }

    public void movement() {

        if (posX == width) {
            health = health - 10;
            if (health == 0) {
                System.exit(1);
            } else {
                return;
            }
        }

        if (posY < arrayY.get(count+1)) {
            posY++;
            if (posY == arrayY.get(count+1)) {
                count++;
            }
        } else if (posY > arrayY.get(count+1)) {
            posY--;
            if (posY == arrayY.get(count+1)) {
                count++;
            }
        } else if (posX < rowWid/2 + arrayX.get(count+1) - 15) {
            posX++;
            if (posX == rowWid/2 + arrayX.get(count+1) - 15) {
                count++;
            }
        }
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
