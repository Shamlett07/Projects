/*
    Deals with aspects of the Bricks that will be similar across all the subclasses
    Staci Hamlett
    March 24, 2022
*/

public abstract class TetrisBrick 
{
    
    protected int numSegments = 4;
    protected int colorNum;
    protected int orientation;
    protected int position [][];
    
    public TetrisBrick()
    {
        position = new int[numSegments][2];
        orientation = 0;
    }
    public String toString()
    {
        String brickPosition = "\n";
        
        brickPosition = "\n"+position.length+" "+position[0].length+"\n";
        for (int row = 0; row < position.length; row ++)
        {
            for (int col = 0; col < position[1].length; col ++)
            {
                brickPosition += position[row][col] + " ";
            }
            brickPosition += "\n";
        }
        brickPosition = brickPosition.substring(0,brickPosition.length()-1);
        return brickPosition;
    }
    public void moveDown()
    {
        for (int SegRow = 0; SegRow < numSegments; SegRow++)
        {
            position[SegRow][0] = position[SegRow][0] + 1;
        }
    }
    public void moveUp()
    {
        for (int SegRow = 0; SegRow < numSegments; SegRow++)
        {
            position[SegRow][0] = position[SegRow][0] - 1;
        }
    }
    public void moveLeft()
    {
        for (int SegCol = 0; SegCol < numSegments; SegCol++)
        {
            position[SegCol][1] = position[SegCol][1] - 1;
        }
    }
    public void moveRight()
    {
        for (int SegCol = 0; SegCol < numSegments; SegCol++)
        {
            position[SegCol][1] = position[SegCol][1] + 1;
        }
        
    }
    
      public int getMaxRow()
    {
        int maxRow = 0;
        for(int seg = 0; seg < numSegments; seg ++)
        {
            if(position[seg][0] > maxRow)
            {
                maxRow = position[seg][0];
            }
        }
        return maxRow;
    }
    public int getMinimumRow()
    {
        int minRow = 20;
        for(int seg = 0; seg < numSegments; seg ++)
        {
          if(position[seg][0] < minRow)
            {
                minRow = position[seg][0];
            }
        }
        return minRow;
    }
    public abstract void rotate();
    public abstract void unrotate();
    public int getColorNumber()
    {
        return colorNum;
    }
    public abstract void initPosition(int x_center, int y_center);
}
