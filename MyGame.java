import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class MyGame extends JPanel implements ActionListener, KeyListener{
    // vars
	private int px = 20;
    private int cols = 25;
    private int rows = 25;
    private int width = px * cols;
    private int height = px * rows;
    private Image playerImg;
    private ArrayList<Image> enemyImgArr;
    private Timer gameTimer;

    // constructor
    public MyGame(){
        // window setup
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.gray);
        setFocusable(true);
        addKeyListener(this);

        // import images
        playerImg = new ImageIcon(getClass().getResource("./ship.png")).getImage();
        enemyImgArr = new ArrayList<Image>();
        enemyImgArr.add(new ImageIcon(getClass().getResource("./alien.png")).getImage());
        enemyImgArr.add(new ImageIcon(getClass().getResource("./alien-cyan.png")).getImage());
        enemyImgArr.add(new ImageIcon(getClass().getResource("./alien-magenta.png")).getImage());
        enemyImgArr.add(new ImageIcon(getClass().getResource("./alien-yellow.png")).getImage());
        
        

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
class Aspect{
    public Aspect(){

    }
}

class Enemy extends Aspect{
    public Enemy(){

    }
}

class Player extends Aspect{
    public Player(){

    }
}

class Bullet extends Aspect{
    public Bullet(){

    }
}
