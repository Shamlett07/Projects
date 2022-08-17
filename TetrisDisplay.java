/*
    Displays the current status of the game. It shows the board and the brick the game assigned
    Staci Hamlett
    March 24, 2022
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TetrisDisplay extends JPanel
{
    private TetrisGame game;
    
    private int start_x = 100;
    private int start_y = 100;
    private int cell_size = 20;
    private int wallWid = 10;
    private int speed = 900;
    
    private boolean pause = false;
    
    Timer timer;
    
    private Color[] colors = {Color.white,Color.black, Color.yellow,Color.orange,Color.red,
                                  Color.pink,Color.magenta,Color.green,Color.blue};
    
   public TetrisDisplay(TetrisGame gam)
    {
        game = gam;

        
        this.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent ke)
            {
                translateKey(ke);
            }
        
        }); 
        timer = new Timer (speed, new ActionListener()
        {
            public void actionPerformed (ActionEvent ae)
            {
                timer.start();
                cycleMove();
                
                
                if (pause == true)
                {
                    timer.stop();
                    
                }
            }
            
        });
          timer.start();
          
           this.setFocusable(true);
           this.setFocusTraversalKeysEnabled(true);
    }
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        
        drawWell(g);
        drawBackground(g);
        drawBrick(g);
        drawScore(g);
        if (game.getGameState() == 0)
        {
            drawGameOver(g);
            
        }
        
        
    }
    
    private void drawScore (Graphics g)
    {
        
        int bannerHei = 50;
        int bannerWid = 200;
        
        int fontSize = 25;
        g.setColor(colors[0]);
        g.fillRect(0, 0, bannerWid, bannerHei);
        
        g.setColor(colors[1]);
        Font largeFont = new Font("Arial",1,fontSize);
        g.setFont(largeFont);
        g.drawString("Score: "+game.getGameScore(),bannerWid/4, bannerHei/2);
    }
    private void drawGameOver (Graphics g)
    {
        int bannerHei = 70;
        int bannerWid = 320;
        
        int xCordinate = 100;
        int yCordinate = 200;
        
        int fontSize = 40;
        g.setColor(Color.white);
        g.fillRect(start_x - cell_size*2, start_y/2+cell_size*(game.getRow()/2), bannerWid, bannerHei);
        g.setColor(colors[1]);
        g.drawRect(start_x - cell_size*2, start_y/2+cell_size*(game.getRow()/2), bannerWid, bannerHei);
        
        g.setColor(Color.blue);
        Font largeFont = new Font ("Arial", 0, fontSize);
        g.setFont(largeFont);
        g.drawString("Game Over", start_x+cell_size, start_y+cell_size*game.getRow()/2);
    }
    private void drawWell(Graphics g)
    {
         
         g.setColor(colors[0]);
         g.fillRect(start_x, start_y,cell_size*game.getCol(),
                    cell_size*game.getRow());

         g.setColor(colors[1]);
         g.fillRect(start_x-wallWid, start_y, wallWid,
                        game.getRow() * cell_size);
         
         g.fillRect(start_x - wallWid,start_y + cell_size*game.getRow(),
                  cell_size*game.getCol()+2*wallWid, wallWid);
         
         g.fillRect(start_x + cell_size*game.getCol(), start_y,
                 wallWid, cell_size*game.getRow());
         
         g.drawLine(start_x - wallWid + 1, start_y,  
                    start_x + cell_size * game.getCol() + wallWid - 1, start_y);
         
        
     }

     private void drawBackground (Graphics g)
     {
        for (int row = 0; row < game.getRow(); row++)
        {
            for (int col = 0; col < game.getCol(); col++)
            {
                if (game.fetchBoardPosition(row, col) != 0)
                {
                    g.setColor(colors[game.fetchBoardPosition(row, col)]);
                    g.fillRect(start_x + cell_size * col, start_y + cell_size * row
                              , cell_size, cell_size);
                    g.setColor(colors[1]);
                    g.drawRect(start_x + cell_size * col, start_y + cell_size * row
                               , cell_size, cell_size);

                 }

             }

         }

    }
     
     private void drawBrick(Graphics g)
     {  
        
        for(int numSeg = 0; numSeg < game.getNumSegs(); numSeg ++)
        {   
                
                g.setColor(colors[game.fallingBrickColor()]);
                g.fillRect(start_x + cell_size * game.getSegCol(numSeg), start_y + cell_size * game.getSegRow(numSeg)
                        , cell_size, cell_size);
                g.setColor(colors[1]);
                g.drawRect(start_x + cell_size * game.getSegCol(numSeg), start_y + cell_size * game.getSegRow(numSeg)
                        , cell_size, cell_size);
            
            repaint();
        }

     }
     private void translateKey(KeyEvent ke)
     {
        int code = ke.getKeyCode();
         switch (code)
         {
            case KeyEvent.VK_RIGHT:
                game.makeMove(KeyEvent.VK_RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                game.makeMove(KeyEvent.VK_LEFT);
                break;
            case KeyEvent.VK_U:
                game.makeMove(KeyEvent.VK_U);
                break;
            case KeyEvent.VK_D:
                game.makeMove(KeyEvent.VK_D);
                break;    
             case KeyEvent.VK_UP:
                game.makeMove(KeyEvent.VK_UP);
               break;
            case KeyEvent.VK_N:
                game.makeMove(KeyEvent.VK_N);
                break;
            case KeyEvent.VK_SPACE:
                if(pause == false)
                {
                    pause = true;
                }
                else 
                {
                    pause = false;
                    timer.restart();
                }
                break;
            case KeyEvent.VK_S:
                game.saveToFile("Tetris_Save.dat");
                break;
            case KeyEvent.VK_R:
                game.retrieveFromFile("Tetris_Save.dat");
                break;
            case KeyEvent.VK_C:
                
                break;
         }   
     }
     private void cycleMove()
     {
         game.makeMove(KeyEvent.VK_D);
         repaint();
     }
}
