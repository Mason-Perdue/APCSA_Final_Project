// imports required classes
import java.awt.*;
// for keyboard input
import java.awt.event.*;
// for keeping track of objects
import java.util.ArrayList;
// for using random values
import java.lang.Math;
// for displaying graphics to screen
import javax.swing.*;

// makes window and instance of MyGame
public class Window{
    // main method that is run at start
    public static void main(String[] args) {
        // creats a new window
        JFrame frame = new JFrame("Game");
        // sets size of window
        frame.setSize(500, 500);
        // centers window on screen
        frame.setLocationRelativeTo(null);
        // prevents user from resizing window
        frame.setResizable(false);
        // allows x on window border to quit program
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // creates instance of game
        MyGame game = new MyGame();
        // adds game frame to window
        frame.add(game);
        // sizes the game frame on the window
        frame.pack();
        // grabs focus for mouse and keyboard input
        game.requestFocus();
        // makes window visible to user
        frame.setVisible(true);
    }
}

// class that holds entire game
// extends and implements classes in order to use methods from them for advanced functionality
class MyGame extends JPanel implements ActionListener, KeyListener{
    // vars
    // size of each block in game
    private static int px = 20;
    // number of columns in window
    private static int cols = 25;
    // number of rows in window
    private static int rows = 25;
    // width of the window
    private static int width = px * cols;
    // height of the window
    private static int height = px * rows;
    // stores image for player
    private Image playerImg;
    // ArrayList to store images for enemies
    private ArrayList<Image> enemyImgArr;
    // Player object
    private Player player;
    // ArrayList to hold Enemy objects
    private ArrayList<Enemy> enemyArr;
    // ArrayList to hold Bullet objects
    private ArrayList<Bullet> bulletArr;
    // ArrayList to hold Asteroid objects
    private ArrayList<Asteroid> asteroidArr;
    // ArrayList to hold Integer objects as scores
    private ArrayList<Integer> scoreArr;
    // primative type integer to hold score for each round
    private int score;
    // primative type boolean to hold true/false value for if the game has ended or not
    private boolean gameOver;
    // Timer object to keep game refeshing
    private Timer gameTimer;

    // classes
    // parent class for objects on screen
    // x, y, w, h, img
    class Aspect{
        // instance variables unique to each object
        int x;
        int y;
        int w;
        int h;
        Image img;

        // constructor with parameters
        Aspect(int x, int y, int w, int h, Image img){
            // sets instance variables to have value of argument
            // uses this. keyword to differentiate between arguments and instance vars
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.img = img;
        }
    }

    // alive, health, xDir
    // child class of Aspect Class
    class Enemy extends Aspect{
        // new instance variables for only the child class
        boolean alive;
        int health;
        int xDir;

        // constructor
        Enemy(int w, int h, Image img){
            // super keyword used to call parent class constructor with arguements
            // Math.random() used to generate random x position
            super((((int) (Math.random() * (cols - 1))) * px), px, w, h, img);
            // sets instance variables
            alive = true;
            health = 4;
            xDir = 1;
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

    class Asteroid extends Aspect{
        public Asteroid(int randR){
            super(0, ((int) (Math.random() * (height - 50))), randR, randR, null);
        }
    }

    // constructor for game
    public MyGame(){
        // window setup
        // sets size of window
        setPreferredSize(new Dimension(width, height));
        // sets background of window
        setBackground(Color.black);
        // allows window to be focused on
        setFocusable(true);
        // starts listening for input from keyboard
        addKeyListener(this);

        // import images
        // creates new ImageIcon object then uses getImage() to make it an Image object
        // getClass() gets file location of this file, getResource gets file based on location of this file, ./ is the working directory
        playerImg = new ImageIcon(getClass().getResource("./rsc/ship.png")).getImage();
        // instantiates ArrayList for holding Image objects
        enemyImgArr = new ArrayList<Image>();
        enemyImgArr.add(new ImageIcon(getClass().getResource("./rsc/alien.png")).getImage());
        enemyImgArr.add(new ImageIcon(getClass().getResource("./rsc/alien-cyan.png")).getImage());
        enemyImgArr.add(new ImageIcon(getClass().getResource("./rsc/alien-magenta.png")).getImage());
        enemyImgArr.add(new ImageIcon(getClass().getResource("./rsc/alien-yellow.png")).getImage());

        // create Player object by calling Player() constructor
        player = new Player(((px * cols) / 2 - px), (height - (px * 2)), (px * 2), px, playerImg);
        
        // create Enemy object array
        enemyArr = new ArrayList<Enemy>();
        // calls Enemy constructor 10 times and then adds the new objects to the ArrayList
        for(int i = 0; i < 10; i++){
            enemyArr.add(new Enemy(2 * px, px, enemyImgArr.get((int) (Math.random() * enemyImgArr.size()))));
        }

        // create Bullet object array
        bulletArr = new ArrayList<Bullet>();

        // create Asteroid object array
        asteroidArr = new ArrayList<Asteroid>();
        for(int i = 0; i < 5; i++){
            asteroidArr.add(new Asteroid((int) (Math.random() * 10) + 10));
        }

        // reset game
        score = 0;
        // instantiates ArrayList for Integer score values
        scoreArr = new ArrayList<Integer>();
        // begins game by setting primative boolean type gameOver to false
        gameOver = false;

        // start game timer
        // starts at 60 Hz
        gameTimer = new Timer(1000/60, this);
        // begins timer that will run for entire round
        gameTimer.start();
    }

    // refreshees screen
    // overrides method from parent class
    @Override
    public void actionPerformed(ActionEvent e){
        // calls move() and repaint() functions
        move();
        repaint();

        // conditional to check if game has ended
        if(gameOver){
            // stops gameTimer
            gameTimer.stop();
            // adds privative int score to ArrayList of Integers by auto type casting 
            scoreArr.add(score);
        }
    }

    // updates screen objects
    @Override
    public void paintComponent(Graphics g){
        // class parent method that is being overridden
        super.paintComponent(g);
        // adds call to method from this child class
        draw(g);
    }

    // draws objects on screen
    public void draw(Graphics g){
        // draws player image based on 
        g.drawImage(player.img, player.x, player.y, player.w, player.h, null);

        // loops through each enemy object
        for(int i = 0; i < enemyArr.size(); i++){
            // null Image object to hold Image
            Image img = null;

            // if enemy at index i is not alive then replaces it with new Enemy object
            if(!enemyArr.get(i).alive){
                enemyArr.set(i, new Enemy(2 * px, px, enemyImgArr.get((int) (Math.random() * enemyImgArr.size()))));
            }
            
            
            if(enemyArr.get(i).health == 4){
                img = enemyImgArr.get(0);
            }else if(enemyArr.get(i).health == 3){
                img = enemyImgArr.get(1);
            }else if(enemyArr.get(i).health == 2){
                img = enemyImgArr.get(2);
            }else if(enemyArr.get(i).health == 1){
                img = enemyImgArr.get(3);
            }

            g.drawImage(img, enemyArr.get(i).x, enemyArr.get(i).y, enemyArr.get(i).w, enemyArr.get(i).h, null);
        }

        g.setColor(Color.white);
        for(int i = 0; i < bulletArr.size(); i++){
            if(!bulletArr.get(i).used){
                g.fillRect(bulletArr.get(i).x, bulletArr.get(i).y, bulletArr.get(i).w, bulletArr.get(i).h);
            }else{
                bulletArr.remove(i);
                i--;
            }
        }

        g.setColor(Color.gray);
        for(int i = 0; i < asteroidArr.size(); i++){
            g.fillOval(asteroidArr.get(i).x, asteroidArr.get(i).y, asteroidArr.get(i).w, asteroidArr.get(i).h);
        }

        g.setColor(Color.green);
        if(gameOver){
            String scores = "";
            for(Integer i : scoreArr){
                scores += i + ", ";
            }
            g.setFont(new Font("Cascadia Code", Font.PLAIN, 32));
            g.drawString("Game Over!", 10, 35);
            g.setFont(new Font("Cascadia Code", Font.PLAIN, 20));
            g.drawString("Scores: " + scores.substring(0, scores.length() - 2), 10, 75);
            g.drawString("Press ENTER to Restart", 10, 100);
        }else{
            g.setFont(new Font("Cascadia Code", Font.PLAIN, 32));
            g.drawString("" + score, 10, 35);
        }
    }

    public void move(){
        for(Enemy enemy : enemyArr){
            if(enemy.y >= (height - px)){
                enemy.alive = false;
            }

            if((enemy.x + enemy.w / 2 ) >= player.x && (enemy.x + enemy.w / 2 ) <= player.x + player.w && enemy.y + enemy.h >= player.y){
                gameOver = true;
            }

            for(Bullet bullet : bulletArr){
                if(bullet.x + bullet.w >= enemy.x && bullet.x <= enemy.x + enemy.w && bullet.y <= enemy.y + enemy.h && bullet.y >= enemy.y){
                    bullet.used = true;
                    enemy.health--;
                    score += 1;

                    if(enemy.health == 0){
                        enemy.alive = false;
                    }
                }
            }

            if(enemy.x <= 0 || enemy.x + enemy.w >= width){
                enemy.xDir *= -1;
            }else{
                if(Math.random() > 0.98){
                    enemy.xDir *= -1;
                }
            }

            if(enemy.alive){
                enemy.y += 1;
                enemy.x += enemy.xDir;
            }
        }

        for(Bullet bullet : bulletArr){
            if(bullet.y <= 0){
                bullet.used = true;
            }

            if(!bullet.used){
                bullet.y -= 3;
            }
        }

        for(int i = 0; i < asteroidArr.size(); i++){
            if((asteroidArr.get(i).x + asteroidArr.get(i).w / 2 ) >= player.x && (asteroidArr.get(i).x + asteroidArr.get(i).w / 2 ) <= player.x + player.w && asteroidArr.get(i).y + asteroidArr.get(i).h >= player.y){
                gameOver = true;
            }

            if(asteroidArr.get(i).y >= (height - px)){
                asteroidArr.remove(i);
                asteroidArr.add(i, new Asteroid((int) (Math.random() * 10) + 10));
            }

            asteroidArr.get(i).x += 1;
            asteroidArr.get(i).y += 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        if(gameOver){
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                enemyArr.clear();
                bulletArr.clear();
                asteroidArr.clear();
                for(int i = 0; i < 10; i++){
                    enemyArr.add(new Enemy(2 * px, px, enemyImgArr.get((int) (Math.random() * enemyImgArr.size()))));
                }
                for(int i = 0; i < 5; i++){
                    asteroidArr.add(new Asteroid((int) (Math.random() * 10) + 10));
                }
                player.x = (px * cols) / 2 - px;
                score = 0;
                gameOver = false;
                gameTimer.start();
            }   
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
            bulletArr.add(new Bullet(player.x + px, player.y));
        }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            gameOver = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}
    
    @Override
    public void keyPressed(KeyEvent e){}
}
