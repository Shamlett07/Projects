/*
    Controls the color and starting position of the Long Brick
    Staci Hamlett
    March 24, 2022
*/


public class LongBrick extends TetrisBrick
{
    public LongBrick()
    {
        colorNum = 3;
    }
    public void rotate()
    {
        
        orientation +=1;
        if (orientation == 1)
        {
            position[0][0] = position[0][0] - 1;
            position[2][0] = position[1][0] + 2;
            position[numSegments-1][0] = position[1][0] + 1;
            
            position[0][1] = position[1][1];
            position[2][1] = position[1][1];
            position[numSegments-1][1] = position[1][1];
        }
        else if (orientation == 2)
        {
            position[0][0] = position[1][0];
            position[2][0] = position[1][0];
            position[numSegments-1][0] = position[1][0];
            
            position[0][1] = position[1][1] + 1;
            position[2][1] = position[1][1] - 1;
            position[numSegments-1][1] = position[1][1] - 2;
            orientation = 0;
            
        }
        
    }
    public void unrotate()
    {
        for (int rotations = 0; rotations < 1; rotations ++)
        {
            rotate();
        }
    }
    
    public void initPosition(int x_center, int y_center)
    {
        int Xcen = x_center-3;
       
            for(int Seg = 0; Seg < numSegments; Seg++ )
            {
                for(int dex = 0; dex < 2;dex++)
                {
                    if (dex == 0)
                    {
                        position [Seg][dex] = 0;
                    }
                    else
                    {
                        position [Seg][dex] = Xcen +=1;
                    }
                }
                
            }
            
           
        
    }
}
