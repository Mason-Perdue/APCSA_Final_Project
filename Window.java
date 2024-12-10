import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.lang.Math;
import javax.swing.*;

// makes window and instance of MyGame
public class Window{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyGame game = new MyGame();
        frame.add(game);
        frame.pack();
        game.requestFocus();
        frame.setVisible(true);
    }
}

class MyGame extends JPanel implements ActionListener, KeyListener{
    // vars
    private static int px = 20;
    private static int cols = 25;
    private static int rows = 25;
    private static int width = px * cols;
    private static int height = px * rows;
    private Image playerImg;
    private ArrayList<Image> enemyImgArr;
    private Player player;
    private ArrayList<Enemy> enemyArr;
    private ArrayList<Bullet> bulletArr;
    private int score;
    private boolean gameOver;
    private Timer gameTimer;

    // classes
    // parent class for objects on screen
    // x, y, w, h, img
    class Aspect{
        int x;
        int y;
        int w;
        int h;
        Image img;
        static int abc = 10;

        Aspect(int x, int y, int w, int h, Image img){
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.img = img;
        }
    }

    // alive
    class Enemy extends Aspect{
        boolean alive;

        Enemy(int w, int h, Image img){
            super(((int) (Math.random() * cols) * px), px, w, h, img);
            alive = true;
        }
    }

    class Player extends Aspect{
        Player(int x, int y, int w, int h, Image img){
            super(x, y, w, h, img);
        }
    }

    // used
    // static: w, h
    class Bullet extends Aspect{
        boolean used;

        Bullet(int x, int y){
            super(x, y, MyGame.px / 8, MyGame.px / 2, null);
            used = false;
        }
    }

    // constructor
    public MyGame(){
        // window setup
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        // import images
        playerImg = new ImageIcon(getClass().getResource("./rsc/ship.png")).getImage();
        enemyImgArr = new ArrayList<Image>();
        enemyImgArr.add(new ImageIcon(getClass().getResource("./rsc/alien.png")).getImage());
        enemyImgArr.add(new ImageIcon(getClass().getResource("./rsc/alien-cyan.png")).getImage());
        enemyImgArr.add(new ImageIcon(getClass().getResource("./rsc/alien-magenta.png")).getImage());
        enemyImgArr.add(new ImageIcon(getClass().getResource("./rsc/alien-yellow.png")).getImage());

        // create Player object
        player = new Player(((px * cols) / 2 - px), (height - (px * 2)), (px * 2), px, playerImg);
        
        // create Enemy object array
        enemyArr = new ArrayList<Enemy>();
        enemyArr.add(new Enemy(2 * px, px, enemyImgArr.get((int) (Math.random() * enemyImgArr.size()))));
        enemyArr.add(new Enemy(2 * px, px, enemyImgArr.get((int) (Math.random() * enemyImgArr.size()))));
        enemyArr.add(new Enemy(2 * px, px, enemyImgArr.get((int) (Math.random() * enemyImgArr.size()))));
        enemyArr.add(new Enemy(2 * px, px, enemyImgArr.get((int) (Math.random() * enemyImgArr.size()))));
        enemyArr.add(new Enemy(2 * px, px, enemyImgArr.get((int) (Math.random() * enemyImgArr.size()))));

        // create Bullet object array
        bulletArr = new ArrayList<Bullet>();

        // reset game
        score = 0;
        gameOver = false;

        // start game timer
        gameTimer = new Timer(1000/60, this);
        gameTimer.start();
    }

    // loop
    @Override
    public void actionPerformed(ActionEvent e){
        move();

        repaint();

        if(gameOver){
            gameTimer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(player.img, player.x, player.y, player.w, player.h, null);

        for(int i = 0; i < enemyArr.size(); i++){
            if(!enemyArr.get(i).alive){
                
            }else{

            }
            g.drawImage(enemyArr.get(i).img, enemyArr.get(i).x, enemyArr.get(i).y, enemyArr.get(i).w, enemyArr.get(i).h, null);
        }

        g.setColor(Color.white);
        for(Bullet bullet : bulletArr){
            if(!bullet.used){
                g.fillRect(bullet.x, bullet.y, bullet.w, bullet.h);
            }
        }

        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if(gameOver){
            g.drawString("Game Over! Score: " + score, 10, 35);
        }else{
            g.drawString("" + score, 10, 35);
        }
    }

    public void move(){
        for(Enemy enemy : enemyArr){
            if(enemy.y >= (height - px)){
                enemy.alive = false;
            }

            if(enemy.x >= player.x && enemy.x <= player.x + player.w && enemy.y <= player.y){
                gameOver = true;
            }

            if(enemy.alive){
                enemy.y += 1;
            }
        }

        for(Bullet bullet : bulletArr){
            if(!bullet.used){
                bullet.y -= 1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(gameOver){
            score = 0;
            gameOver = false;
            gameTimer.start();
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(player.x > px){
                player.x -= px;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(player.x < (width - px * 3)){
                player.x += px;
            }
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            bulletArr.add(new Bullet(player.x, player.y));
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}
    
    @Override
    public void keyPressed(KeyEvent e){}
}
