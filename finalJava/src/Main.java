import java.awt.*;
import java.awt.image.BufferStrategy;
import java.sql.*;
import java.util.Scanner;
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
    private int fps = 60;
    private int ups = 60;
    private boolean running = false;
    private Thread thread;
    private int count = 0;
    private Road road;
    private Enemy enemy;

    GridsCanvas(int w, int h, int r, int c) {
        setSize(width = w, height = h);
        rows = r;
        cols = c;
        rowWid = width / (cols);
        rowHt = height / (rows);
        road = new Road(width, height, rowWid, rowHt);
        enemy = new Enemy(width, height, rowWid, rowHt, road.getArrayY(), road.getArrayX());
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
        road.setCourse();
        enemy.setStartValues();

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

    if (enemy.getPosX() != 800)
    enemy.movement();
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

        //draw road
        g.setColor(darkGray);
        for (i = 0; i < road.getArrayXSize(); i++) {
            g.fillRect(road.getArrayX().get(i), road.getArrayY().get(i), 80, 60);
        }
        // draw the rows
        g.setColor(black);
        for (i = 0; i < rows; i++)
            g.drawLine(0, i * rowHt, width, i * rowHt);

        // draw the columns
        for (i = 0; i < cols; i++)
            g.drawLine(i * rowWid, 0, i * rowWid, height);

        // draw enemy
        g.setColor(orange);
        g.fillOval(enemy.getPosX(), enemy.getPosY(), 30, 60);

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

        try {
            // Set up connection to database
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://" + DatabaseLoginData.DBURL + ":" + DatabaseLoginData.port + "/" + DatabaseLoginData.DBname +
                            "? allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    DatabaseLoginData.user, DatabaseLoginData.password);

            int count = 1;

            // Setup statement
            Statement stmt = conn.createStatement();
            // Create query and execute
            String strSelect = "select * from user";
            System.out.println("The SQL statement is: " + strSelect + "\n");
            ResultSet rset = stmt.executeQuery(strSelect);

            // Loop through the result set and print

            System.out.println("The records selected are:");
            while (rset.next()) {
                String id = rset.getString("id");
                String tag = rset.getString("tag");
                String score = rset.getString("score");
                System.out.println(id + " " + tag + " " + score);
                count++;
            }

            Scanner in = new Scanner(System.in);
                System.out.println("Please type whatever 3-letter tag you're using!");
                String tag2 = in.nextLine();
                System.out.println("Please type whatever score you got!");
                String score2 = in.nextLine();
                //
                // using the same database as my rpg game in webbutvecklingen as it makes it easier and i can still show i can deal with databases.
                //
                stmt.execute("insert into user values ('" + count + "', '" + tag2 + "', '" + score2 + "')");
                ResultSet rset2 = stmt.executeQuery(strSelect);

                System.out.println("The records selected are:");
                while (rset2.next()) {
                    String id = rset2.getString("id");
                    String tag = rset2.getString("tag");
                    String score = rset2.getString("score");
                    System.out.println(id + " " + tag + " " + score);
                }

            // Close conn and stmt
            conn.close();
            stmt.close();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}