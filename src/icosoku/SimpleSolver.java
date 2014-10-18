package icosoku;

/**
 * Created by Georg on 17.10.2014.
 */
public class SimpleSolver {

    public static void main(String[] args) {

        int[] problem1 = {1,3,10,7,5,4,11,6,12,8,9,2};

        IcoSoku rätsel = new IcoSoku(problem1);

        SolveIcoSoku(rätsel, 0);

    }

    private static void SolveIcoSoku(IcoSoku rätsel, int fläche)
    {
        for(int plättchen = 0; plättchen < 20; plättchen++)
        {
            for(int orient = 0; orient < 2; orient++)
            {
                rätsel.setzePlaettchen(fläche, plättchen, orient);
            }
        }



        if(rätsel.pruefeAlles())
        {
            return;
        }


    }

}
