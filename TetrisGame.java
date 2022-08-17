/*
    Spawns the brick and get the rows and positions for the spawned brick
    Staci Hamlett
    March 24, 2022
 */
import java.util.*;
import java.awt.event.*;
import java.io.*;

public class TetrisGame 
{

    TetrisBrick fallingBrick;

    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    private int[][] background;
    private int state = 1;
    private int score = 0;
    Random randGen;

    public TetrisGame(int rs, int cs) 
    {
        rows = rs;
        cols = cs;
        randGen = new Random();
        background = new int[rows][cols];
        spawnBrick();
        File leaderboard = new File("Leaderboard.dat");
        if(!leaderboard.exists())
        {
            makeAnewLeaderboard();
        }
        
    }

    public String toString() {
        String save = score+"\n"+" " + background.length + " " + background[0].length + "\n";
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                save += background[row][col] + " ";
            }
            save += "\n";
        }
        save += "\n" + fallingBrick.getClass();
        return save;

    }

    public void initBoard() 
    {
        for (int row = 0; row < rows; row++) 
        {
            for (int col = 0; col < cols; col++) 
            {
                background[row][col] = 0;
            }
        }
    }

    public int fetchBoardPosition(int row, int col) 
    {
        return background[row][col];
    }
    public void NewGame() 
    {
        initBoard();
        spawnBrick();
        score = 0;
        state = 1;
    }

    private void spawnBrick() 
    {
        int brickDecide = randGen.nextInt(numBrickTypes);
        for(int col = 0; col < cols; col++)
        {
            if (fetchBoardPosition(0,col) !=0)
            {
                state = 0;
            }
        }
        if(state == 1)
        {
            switch (brickDecide) 
            {
                case 6:
                    fallingBrick = new JayBrick();
                    fallingBrick.initPosition(cols / 2, rows / 2);
                    break;
                case 5:
                    fallingBrick = new LongBrick();
                    fallingBrick.initPosition(cols / 2, rows / 2);
                    break;
                case 4:
                    fallingBrick = new ElBrick();
                    fallingBrick.initPosition(cols / 2, rows / 2);
                    break;
                case 3:
                    fallingBrick = new ZeeBrick();
                    fallingBrick.initPosition(cols / 2, rows / 2);

                    break;
                case 2:
                    fallingBrick = new EssBrick();
                    fallingBrick.initPosition(cols / 2, rows / 2);
                    break;
                case 1:
                    fallingBrick = new StackBrick();
                    fallingBrick.initPosition(cols / 2, rows / 2);
                    break;
                case 0:
                    fallingBrick = new SquareBrick();
                    fallingBrick.initPosition(cols / 2, rows / 2);
                    break;

            }
            if (validateMove() != 0)
            {
                spawnBrick();
                
            }
        }
    }

    public void makeMove(int keycode) {
        if (keycode == KeyEvent.VK_U)
        {
            fallingBrick.moveUp();
            if (validateMove() == 1) {
                fallingBrick.moveDown();
            }
        }
        if (keycode == KeyEvent.VK_D) 
        {
            fallingBrick.moveDown();

            if (validateMove() != 0) 
            {
                fallingBrick.moveUp();
                transferColor();
                
                int lastRow = fallingBrick.getMinimumRow();
                int amtOfrowsGone = 0;
                
                for (int currentRow = fallingBrick.getMaxRow(); currentRow >= lastRow; currentRow--)
                {
                    if (rowHasSpace(currentRow)== false)
                    {
                        copyAllRows (currentRow);
                        currentRow ++;
                        lastRow --;
                        amtOfrowsGone +=1;
                    }
                }
                
                if (amtOfrowsGone == 1)
                {
                    score += 100;
                }
                if (amtOfrowsGone == 2)
                {
                    score += 300;                    
                }
                if (amtOfrowsGone == 3)
                {
                    score += 600;
                }
                if (amtOfrowsGone == 4)
                {
                    score += 1200;
                }
                    
                    spawnBrick(); 
            }     
        }
        if (keycode == KeyEvent.VK_RIGHT) {

            fallingBrick.moveRight();
            if (validateMove() != 0) {
                fallingBrick.moveLeft();

            }
        }
        if (keycode == KeyEvent.VK_LEFT) {

            fallingBrick.moveLeft();
            if (validateMove() != 0) {
                fallingBrick.moveRight();
            }
        }
        if (keycode == KeyEvent.VK_UP) {
            fallingBrick.rotate();
            if (validateMove() != 0) {
                fallingBrick.unrotate();
            }
        }
        if (keycode == KeyEvent.VK_N) 
        {
            NewGame();
        }

    }

    private int validateMove() {
        for (int seg = 0; seg < getNumSegs(); seg++) 
        {
            if (getSegCol(seg) < 0 || getSegCol(seg) >= cols
                    || getSegRow(seg) < 0 || getSegRow(seg) >= rows) {
                return 1;

            }
            if (fetchBoardPosition(getSegRow(seg), getSegCol(seg)) != 0) {
                return 2;
            }
        }

        return 0;
    }
    private boolean rowHasSpace(int rowNumber)
    {
        int filledSpace = 0;
        for (int col = 0; col < cols; col ++)
        {
           if(fetchBoardPosition(rowNumber,col) != 0)
           {
               filledSpace += 1;
           }
        }
        if (filledSpace == cols)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private void copyRow(int rowNumber)
    {
        for (int col = 0; col < cols; col ++)
        {
            background[rowNumber][col] = background[rowNumber - 1][col];
        }
    }
    private void copyAllRows(int rowNumber)
    {
        for (int row = rows;rowNumber > 0; rowNumber -- )
        {
            copyRow(rowNumber);
        }
        
        
    }
    
        private void transferColor() {
        for (int seg = 0; seg < getNumSegs(); seg++) {
            {
                background[getSegRow(seg)][getSegCol(seg)] = fallingBrick.colorNum;
            }

        }
    }

    public void saveToFile(String saveFile) 
    {

        File connection = new File(saveFile);
        if (connection.exists() && !connection.canWrite()) {
            System.err.print("Unable to write to file");
        }
        try
        {
            FileWriter writer = new FileWriter(connection);

            writer.write(toString());
            writer.write(fallingBrick.toString());
            writer.close();

        } catch (IOException ioe) 
        {
            System.err.print("Trouble writing to file");
        }
    }

    public void retrieveFromFile(String saveFile) {
        try {

            File connection = new File(saveFile);
            Scanner scan = new Scanner(connection);

            score = scan.nextInt();
            int rows = scan.nextInt();
            int cols = scan.nextInt();
            background = new int[rows][cols];

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    background[row][col] = scan.nextInt();
                }
            }
            scan.next();
            String brickType = scan.next();

            if (brickType.equals("ElBrick")) {
                fallingBrick = new ElBrick();
            } else if (brickType.equals("EssBrick")) {
                fallingBrick = new EssBrick();
            } else if (brickType.equals("JayBrick")) {
                fallingBrick = new JayBrick();
            } else if (brickType.equals("LongBrick")) {
                fallingBrick = new LongBrick();
            } else if (brickType.equals("SquareBrick")) {
                fallingBrick = new SquareBrick();
            } else if (brickType.equals("StackBrick")) {
                fallingBrick = new StackBrick();
            } else if (brickType.equals("ZeeBrick")) {
                fallingBrick = new ZeeBrick();
            }

            int brickRows = scan.nextInt();
            int brickCols = scan.nextInt();
            for (int brickRow = 0; brickRow < brickRows; brickRow++) {
                for (int brickCol = 0; brickCol < brickCols; brickCol++) 
                {
                    fallingBrick.position[brickRow][brickCol] = scan.nextInt();
                }

            }

        } catch (IOException ioe) 
        {
            System.err.print("Error Reading the file");
        }
    }
    
    public String makeAnewLeaderboard()
    {
        String leaderboard = "Leaderboard.dat";
        try
        {
            FileWriter writer = new FileWriter(leaderboard);
            writer.close();
            
        }
        catch(IOException ioe)
        {
            System.err.println("Trouble Writing to File!!!!!!!!!!!!!");
        }
        return leaderboard;
    }
    
    public void addScore()
    {
        try
        {
            File leaderboard = new File("Leaderboard.dat");
            if (!leaderboard.exists()&& !leaderboard.canWrite())
            {
                String errMessage = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n"
                                   +"Trouble opening to write file+\n"
                                   +" This Program is Ending\n"
                                   +"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
                System.out.println(errMessage);
                System.exit(0);            
                
            }
            
            FileWriter addScore = new FileWriter(leaderboard, true);
            addScore.append("\n"+getGameScore());
            addScore.close();
        }
        catch(IOException ioe)
        {
            System.err.println("Trouble Writing to File!!!!!!!!!!!!!");
        }
        
    }
    public String organizeScore()
    {
        ArrayList <Integer> scores = new ArrayList();
        String displayLeaderboard = "";
        try
        {
            File leaderboard = new File ("LeaderBoard.dat");
            Scanner scan = new Scanner(leaderboard);
            while (scan.hasNext())
            {
                
                scores.add(scan.nextInt());
            }
           
        } 
        catch(IOException ioe)
        {
            System.err.println("Trouble Reading From File!!!!!!!!!!!!!");
        }
        int spaceAvailable = 10;
        for(int dex = 0; dex < scores.size(); dex = 0)
        {
            int highScore = scores.get(dex);
            while (dex < scores.size()-1)
            {
                int secondScore = scores.get(dex + 1);
                if (highScore <= secondScore)
                {
                    highScore = secondScore;
                }
                dex++;
            }
            spaceAvailable -= 1;
            if(spaceAvailable >= 0)
            {
                displayLeaderboard += highScore+"\n";
                scores.remove(scores.indexOf(highScore));
            }
            else
            {
                scores.remove(dex);
            }
            
            
        }
        return displayLeaderboard;
    }
    
    
    public int getNumSegs() {
        return fallingBrick.numSegments;
    }

    public int fallingBrickColor() {
        return fallingBrick.getColorNumber();
    }

    public int getRow() {

        return rows;

    }

    public int getCol() {
        return cols;
    }

    public int getSegRow(int Segs) {

        return fallingBrick.position[Segs][0];

    }

    public int getSegCol(int Segs) {
        return fallingBrick.position[Segs][1];
    }
    
    public int getGameState()
    {
        return state;
    }
    public int getGameScore()
    {
        return score;
    }


}
