/*
    Controls the color and starting position of the Square Brick
    Staci Hamlett
    March 24, 2022
*/
public class SquareBrick extends TetrisBrick
{
    public SquareBrick()
    {
        colorNum = 2;
    }
    public void rotate()
    {
         
    }
    public void unrotate()
    {
        
    }
    public void initPosition(int x_center, int y_center)
    {
        int Xcen = x_center -1;
        int Ycen = y_center * 0;
        int incr = 0;
        
        for(int Seg = 0; Seg < numSegments; Seg++ )
            {
                
                if (Seg < 2)
                {
                    position[Seg][0] = 0;
                    position[Seg][1] = Xcen += incr;
                    incr ++;
                }
                else if (Seg >= 2)
                {
                    incr--;
                    position[Seg][0] = Ycen + 1;
                    position[Seg][1] = Xcen - incr; 
                }

            }
    }
    
}
