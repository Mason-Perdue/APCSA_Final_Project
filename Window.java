import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

// makes window and instance of MyGame
public class Window{
    public static void main(String[] args) {
        // 2 player
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
	private int px = 20;
    private int cols = 25;
    private int rows = 25;
    private int width = px * cols;
    private int height = px * rows;
    private Image playerImg;
    private ArrayList<Image> enemyImgArr;
    private Player player;
    private ArrayList<Enemy> enemyArr;
    private Timer gameTimer;

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
        
        // create Enemy objects
        enemyArr = new ArrayList<Enemy>();
        for(Image enemyImg : enemyImgArr){
            enemyArr.add(new Enemy(px, px, (px * 2), px, enemyImg));
        }

        // start game timer
        gameTimer = new Timer(1000/60, this);
        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        // loop
    }

    @Override
    public void keyTyped(KeyEvent e){}
    
    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            // left arrow
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            // right arrow
        }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            // space
        }
    }
}

// parent class for objects on screen
// x, y, w, h, img
class Aspect{
    private int x;
    private int y;
    private int w;
    private int h;
    private Image img;

    public Aspect(int x, int y, int w, int h, Image img){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.img = img;
    }

    public int getX(){ return this.x; }
    public void setX(int x){ this.x = x; }
    public int getY(){ return this.y; }
    public void setY(int y){ this.y = y; }
    public int getW(){ return this.w; }
    public void setW(int w){ this.w = w; }
    public int getH(){ return this.h; }
    public void setH(int h){ this.h = h; }
    public Image getImg(){ return this.img; }
    public void setImg(Image img){ this.img = img; }
}

// alive
class Enemy extends Aspect{
    private boolean alive;

    public Enemy(int x, int y, int w, int h, Image img){
        super(x, y, w, h, img);
        alive = true;
    }

    public boolean getAlive(){ return this.alive; }
    public void setAlive(boolean alive){ this.alive = alive; }

}

class Player extends Aspect{
    public Player(int x, int y, int w, int h, Image img){
        super(x, y, w, h, img);
    }
}

// used
class Bullet extends Aspect{
    private boolean used;

    public Bullet(int x, int y, int w, int h, Image img){
        super(x, y, w, h, img);
        used = false;
    }

    public boolean getUsed(){ return this.used; }
    public void setUsed(boolean used){ this.used = used; }
}
