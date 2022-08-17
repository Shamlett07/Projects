/*
    Creates the window that the tetris game will be displayed in. 
    Staci Hamlett
    March 24, 2022
*/
import javax.swing.*;
import java.awt.event.*;
public class TetrisWindow extends JFrame
{
        TetrisGame game;
        TetrisDisplay display;

        private int win_height = 600;
        private int win_width = 600;
        private int game_rows = 20;
        private int game_cols = 12;
        
    public TetrisWindow()
    {
        this.setTitle("Tetris Game");
        this.setSize(win_width,win_height);
        
        game = new TetrisGame(game_rows,game_cols);
        display = new TetrisDisplay(game);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.add(display);
        Menus();
        
        this.setVisible(true);
        
    }
    public void Menus ()
    {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        
        JMenu leaderMenu = new JMenu("Leaderboard");
        menuBar.add(leaderMenu);
        
        JMenu savesMenu = new JMenu("Save");
        menuBar.add(savesMenu);
        
        JMenuItem displayLeader = new JMenuItem("Display Leaderboard");
        JMenuItem deleteLeader = new JMenuItem("Delete All Scores From Leaderboard");
        
        displayLeader.addActionListener( new ActionListener()
        {
                public void actionPerformed(ActionEvent ac)
                {
                    if(game.getGameState()==0)
                    {
                        game.addScore();
                        
                    }
                    JOptionPane.showMessageDialog(null, game.organizeScore(),"Leaderboard",0);
                }
        });
        deleteLeader.addActionListener( new ActionListener()
        {
                public void actionPerformed(ActionEvent ac)
                {
                    game.makeAnewLeaderboard();
                    
                }
        });
        leaderMenu.add(displayLeader);
        leaderMenu.add(deleteLeader);
        
        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener( new ActionListener()
        {
                public void actionPerformed(ActionEvent ac)
                {
                   game.saveToFile("Tetris_Save.dat");
                }
        });
        JMenuItem retrieveGame = new JMenuItem("Retrieve Game");
        retrieveGame.addActionListener( new ActionListener()
        {
                public void actionPerformed(ActionEvent ac)
                {
                    
                   game.retrieveFromFile("Tetris_Save.dat");
                }
        });
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener( new ActionListener()
        {
                public void actionPerformed(ActionEvent ac)
                {
                    game.NewGame();
                }
        });
        menuBar.add(newGame);
        savesMenu.add(saveGame);
        savesMenu.add(retrieveGame);
    }
    
    public static void main(String[] args) 
    {
       TetrisWindow myWin = new TetrisWindow();
        
    }
}
