/*
    Controls the color and starting position of the Stack Brick
    Staci Hamlett
    March 24, 2022
*/


public class StackBrick extends TetrisBrick
{
    public StackBrick()
    {
        colorNum = 8;
    }
    public void rotate()
    {
        orientation += 1;
        
        if (orientation == 1)
        {
            position[0][0] = position[2][0];
            position[1][0] = position[2][0] - 1;
            position[numSegments-1][0] = position[2][0] + 1;
            
            position[0][1] = position[2][1] + 1; 
            position[1][1] = position[2][1];
            position[numSegments-1][1] = position[2][1];
            
        }
        else if (orientation == 2)
        {
            position[0][0] = position[2][0] + 1;
            position[1][0] = position[2][0];
            position[numSegments-1][0] = position[2][0];
            
            position[0][1] = position[2][1]; 
            position[1][1] = position[2][1] + 1;
            position[numSegments-1][1] = position[2][1] - 1;

        }
        else if (orientation == 3)
        {
            position[0][0] = position[2][0];
            position[1][0] = position[2][0] + 1;
            position[numSegments-1][0] = position[2][0] - 1;
            
            position[0][1] = position[2][1] - 1; 
            position[1][1] = position[2][1];
            position[numSegments-1][1] = position[2][1];
        }
        else
        {
           position[0][0] = position[2][0] - 1;
            position[1][0] = position[2][0];
            position[numSegments-1][0] = position[2][0];
            
            position[0][1] = position[2][1]; 
            position[1][1] = position[2][1] - 1;
            position[numSegments-1][1] = position[2][1] + 1;
            
            orientation = 0;

        }
        
        
    }
    public void unrotate()
    {
        int rotationLimit = 3;
        for (int rotations = 0; rotations < rotationLimit; rotations ++)
        {
            rotate();
        }
        
    }
    public void initPosition(int x_center, int y_center)
    {
        int Xcen = x_center - 2 ;
        int Ycen = y_center * 0;
        int incr = 0;
        
        for(int Seg = 0; Seg < numSegments; Seg++ )
            {
                if (Seg == 0)
                {
                    position[Seg][0] = 0;
                    position[Seg][1] = Xcen + 1;
                }
                else if (Seg != 0)
                {
                    position[Seg][0] = Ycen + 1;
                    position[Seg][1] = Xcen + incr ;
                    incr++;
                }
            }

    }
}
