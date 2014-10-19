package icosoku;

/**
 * Created by Georg on 17.10.2014.
 */
public class SimpleSolver
{

    public static void main(String[] args)
    {

        int[] problem1 = {1, 3, 10, 7, 5, 4, 11, 6, 12, 8, 9, 2};

        IcoSoku icoSoku = new IcoSoku(problem1);

        if (SolveIcoSoku(icoSoku, 0))
        {
            System.out.println("Rätsel gelöst!");
        }
        else
        {
            System.out.println("Rätsel konnte nicht gelöst werden!");
        }

    }

    private static boolean SolveIcoSoku(IcoSoku icoSoku, int fläche)
    {
        if (fläche > 19)
        {
            return true;
        }

        for (int plättchen = 0; plättchen < 20; plättchen++)
        {
            if (icoSoku.istPlaettchenVerfuegbar(plättchen))
            {
                for (int orient = 0; orient < 2; orient++)
                {
                    icoSoku.setzePlaettchen(fläche, plättchen, orient);

                    int[] eckenToCheck = icoSoku.getEckenToFlaeche(fläche);
                    boolean alleEckenRichtig = true;

                    for (int i = 0; i < eckenToCheck.length; i++)
                    {
                        int eckeToCheck = eckenToCheck[i];
                        alleEckenRichtig = alleEckenRichtig && (icoSoku.istWertAnEcke(eckeToCheck) <= icoSoku.sollWertAnEcke(eckeToCheck));
                    }

                    if (alleEckenRichtig)
                    {
                        if (SolveIcoSoku(icoSoku, fläche + 1))
                        {
                            return true;
                        }
                    }

                    icoSoku.entfernePlaettchen(fläche);
                }
            }
        }

        return false;
    }

}
