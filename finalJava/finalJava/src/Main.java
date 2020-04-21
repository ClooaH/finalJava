import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static java.awt.Color.*;

class GridsCanvas extends Canvas implements Runnable{
    private int width, height;
    private int rows;
    private int rowHt;
    private int rowWid;
    private int cols;
    private JFrame frame;
    private int fps = 1;
    private int ups = 30;
    private boolean running = false;
    private Thread thread;
    private int count = 0;
    private Road road = new Road();

    GridsCanvas(int w, int h, int r, int c) {
        setSize(width = w, height = h);
        rows = r;
        cols = c;
        rowWid = width / (cols);
        rowHt = height / (rows);
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double frameUpdateInterval = 1000000000.0 / fps;
        double stateUpdateInterval = 1000000000.0 / ups;
        double deltaFrame = 0;
        double deltaUpdate = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            deltaFrame += (now - lastTime) / frameUpdateInterval;
            deltaUpdate += (now - lastTime) / stateUpdateInterval;
            lastTime = now;

            while (deltaUpdate >= 1) {
                update();
                deltaUpdate--;
            }

            while (deltaFrame >= 1) {
                draw();
                deltaFrame--;
            }
        }
        stop();
    }

    private void update() {
    //yet to do

    }

    private void draw() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        paint(g);
        g.dispose();
        bs.show();
    }

    public void paint(Graphics g) {
        int i;
        width = getSize().width;
        height = getSize().height;

        //refresh
        g.clearRect(0,0, width, height);

        //background
        g.setColor(green);
        g.fillRect(0,0,800,600);

        /* g.fillRect(
        (int) Math.round(Math.random()*(cols-1))*rowWid,
        (int) Math.round(Math.random()*(rows-1))*rowHt,
        80, 60); */


        g.setColor(black);

        // draw the rows
        for (i = 0; i < rows; i++)
            g.drawLine(0, i * rowHt, width, i * rowHt);

        // draw the columns
        int rowWid = width / (cols);
        for (i = 0; i < cols; i++)
            g.drawLine(i * rowWid, 0, i * rowWid, height);


    }
}

public class Main extends JFrame {
    GridsCanvas xyz;
    private Main() {
        xyz = new GridsCanvas(800, 600, 10, 10);
        add(xyz);
        pack();
    }
    public static void main(String[] a) {
        Main m = new Main();
        m.setVisible(true);
        m.xyz.start();
    }
}