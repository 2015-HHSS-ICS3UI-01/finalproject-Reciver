
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author alled7036
 */


public class Game extends JComponent implements KeyListener, MouseMotionListener, MouseListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000)/desiredFPS;
   
    //Player position variables
    int x = 100;
    int y = 500;
    
    //Mouse variables
    int mouseX = 0;
    int mouseY = 0;
   boolean buttonPressed = false;
   
   //another player
   Rectangle player = new Rectangle(0, 200, 50, 50);
   int moveX = 0;
   int moveY = 0;
   boolean inAir = false;
   
   //Block 
   ArrayList<Rectangle> blocks = new ArrayList<>();
   
   int gravity = 1;
   
   
   
   //keyboard variables
   boolean up = false;
   boolean down = false;
   boolean right = false;
   boolean left = false;
   boolean jump = false;
   boolean prevJump = false;
   private Object BufferedImage;
   //Image Variable
   private BufferedImage img;
   //Timer Variables
   long timer = 20*1000;
   long timeRemaining = timer;
   long gameStart;
     
   public BufferedImage loadImage(String filename){
       BufferedImage = null;
       try{
           img = ImageIO.read(new File(filename));
           
       }catch(Exception e){
           System.out.println("Error Loading " + filename);;
       }
       return img; 
        
        
   }
   //Importing all the game graphics for the game
   BufferedImage BlockImageBackground = loadImage("game_background_by_jesusfc-d3evta1.jpg");
   BufferedImage CharImage = loadImage("character.png");
   BufferedImage BlockImage = loadImage("blockss.png");
   BufferedImage StartScreen = loadImage("StartScreen.png");
   BufferedImage EndScreen = loadImage("EndScreen.png");
    
    //Making different screens
    int level = 0; 
    
    
    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g)
    {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);
        
        // GAME DRAWING GOES HERE 
        //
        
        //Displaying the start screen
        if(level == 0){
            g.drawImage(StartScreen, 0, 0, null);
        }
        //where the actual game begins
        if(level==1){
            
     
        //Make the game background
        g.drawImage(BlockImageBackground, 0, 0,WIDTH, HEIGHT, null);
   
       //Making all the blocks
        g.setColor(Color .BLACK);
        for(Rectangle block: blocks){
            //The  Graphics for the blocks
            g.drawImage(BlockImage, block.x, block.y, block.width, block.height, null);
        }
       
        //Make character
        g.setColor(Color.RED);
        g.drawImage(CharImage ,player.x, player.y, player.width, player.height, null);
        
        //Timer
        g.drawString("" + (timeRemaining/1000), 50, 100);
        }
        //make end screen and when the timer stops the end screen is displayed
        if(timeRemaining <=0){
            g.drawImage(EndScreen, 0, 0, null);
        }
        
        // GAME DRAWING ENDS HERE
    }
    
    
    // The main game loop
    // In here is where all the logic for my game will go
    public void run()
    {    //things to to before game starts
        
        //add blocks  
        blocks.add(new Rectangle(25,750,200,20));
        blocks.add(new Rectangle(470,750,200,20));
        blocks.add(new Rectangle(350,650,200,20));
        blocks.add(new Rectangle(75,650,200,20));
        blocks.add(new Rectangle(400,650,200,20));
        blocks.add(new Rectangle(470,550,200,20));
        blocks.add(new Rectangle(60,450,200,20));
        blocks.add(new Rectangle(450,450,200,20));
        blocks.add(new Rectangle(90,350,200,20));
        blocks.add(new Rectangle(375,350,200,20));
        blocks.add(new Rectangle(450,250,200,20));
        blocks.add(new Rectangle(90,250,200,20));
        blocks.add(new Rectangle(60,150,200,20));
        blocks.add(new Rectangle(300,150,200,20));
        
        
        
        //Second level
        blocks.add(new Rectangle(100,550,200,20));
       
        gameStart = System.currentTimeMillis();
        
        
        
        //END INITISL THINGS TO DO
        
        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime;
        long deltaTime;
        
        // the main game loop section
        // game will end if you set done = false;
        boolean done = false; 
        while(!done)
        {
            
              // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();
    
            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            
                          
            //making the timeremaining
            timeRemaining = timer - (System.currentTimeMillis() - gameStart);
           //Movement
            x = mouseX;
            y = mouseY;
            
            if(left){
                moveX = -2;
            }else if(right){
                moveX =  2;
            }else{
                moveX = 0;
            }
           
                
            
            //gravity player down
            moveY = moveY + gravity;
             
            //Jumping
            //jump being pressed and not in air
            if(jump && prevJump == false && !inAir){
                //make a big change in y direction
                moveY = -15;
                inAir = true;
            }
            //Keeps track of jumps
            prevJump = jump;
            
            //move the player
            player.x = player.x + moveX;
            player.y = player.y + moveY;
            
            //if feet of player become lower then screen
            if(player.y + player.height > HEIGHT){
                //stops falling
                player.y = HEIGHT - player.height;
                moveY = 0;
                inAir = false;
            }
            
            //go through all blocks 
            for(Rectangle block:blocks){
                //is player hitting the block
                if(player.intersects(block)){
                    //get the collision rectangle
                    Rectangle intersection = player.intersection(block);
                    
                    //fix x movement
                    if (intersection. width < intersection.height){
                         if(player.x < block.x){
                             player.x = player.x - intersection.width;
                             
                         }else{
                             player.x = player.x + intersection.width;
                         }
                    }else{//fix y
                        //hitting with head so the char doesnt go through the bottom
                        if(moveY < 0){
                            player.y = block.y + block.height;
                            moveY = 0;
                        }else{
                           player.y = block.y - player.height;
                           moveY = 0;
                           inAir = false;
                        }
                    }
                }
            } 
            
          
            
            // GAME LOGIC ENDS HERE 
            
            // update the drawing (calls paintComponent)
            repaint();
            
            
            
            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if(deltaTime > desiredTime)
            {
                //took too much time, don't wait
            }else
            {
                try
                {
                    Thread.sleep(desiredTime - deltaTime);
                }catch(Exception e){};
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");
       
        // creates an instance of my game
        Game game = new Game();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        // adds the game to the window
        frame.add(game);
         
        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);
        
        
        //add listener
        frame.addKeyListener(game);
        game.addMouseListener(game);
        game.addMouseMotionListener(game);
        
        // starts my game loop
        game.run();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
     
    }

    @Override
    public void mouseMoved(MouseEvent me) {
       
    }

    @Override
    public void mouseClicked(MouseEvent me) {
       
    }

    @Override
    public void mousePressed(MouseEvent me) {
      if(me.getButton() == MouseEvent.BUTTON1){
        buttonPressed = true;
    } 
    }

    @Override
    public void mouseReleased(MouseEvent me) {
       if(me.getButton() == MouseEvent.BUTTON1){
        buttonPressed = false;
    } 
    }

    @Override
    public void mouseEntered(MouseEvent me) {
       
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
      
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //Movement for the character wuith arrows
       int key = ke.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            left = true;
        }else if(key == KeyEvent.VK_RIGHT){
            right = true;
        }else if(key == KeyEvent.VK_SPACE){
            jump = true;
        }
        //If the space bar is held it starts the game once released
        if(key == KeyEvent.VK_SPACE){
            level = 1;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //Making the movement for the arrows
          int key = ke.getKeyCode();
        if(key == KeyEvent.VK_LEFT){
            left = false;
        }else if(key == KeyEvent.VK_RIGHT){
            right = false;
        }else if(key == KeyEvent.VK_SPACE){
            jump = false;
        }
        //On lvl 1 if the space bar is released the game starts
        if(key == KeyEvent.VK_SPACE){
            level = 1;
        }
            
    }
    
}
