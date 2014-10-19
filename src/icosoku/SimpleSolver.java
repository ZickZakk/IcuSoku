package icosoku;

import java.util.HashSet;

/**
 * Created by Georg on 17.10.2014.
 */
public class SimpleSolver
{

    public static void main(String[] args)
    {

        int[] problem1 = {1, 3, 10, 7, 5, 4, 11, 6, 12, 8, 9, 2};

        IcoSoku icoSoku = new IcoSoku(problem1);

        HashSet<Integer> sortierteFlächen = new HashSet<Integer>();

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

                    if (flaecheKorrekt(icoSoku, fläche))
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

    private static boolean flaecheKorrekt(IcoSoku icoSoku, int fläche)
    {
        int[] eckenToCheck = icoSoku.getEckenToFlaeche(fläche);
        boolean alleEckenRichtig = true;

        for (int i = 0; i < eckenToCheck.length; i++)
        {
            int eckeToCheck = eckenToCheck[i];
            if(icoSoku.anzahlPlaettchenAnEcke(eckeToCheck) == 5)
            {
                // Wenn schon alle Flächen zur Ecke belegt sind, muss die Zahl gleich sein
                alleEckenRichtig = alleEckenRichtig && (icoSoku.sollWertAnEcke(eckeToCheck) == icoSoku.istWertAnEcke(eckeToCheck));
            }
            else
            {
                // Sonst muss kleiner gleich sein
                alleEckenRichtig = alleEckenRichtig && (icoSoku.istWertAnEcke(eckeToCheck) <= icoSoku.sollWertAnEcke(eckeToCheck));
            }
        }
        return alleEckenRichtig;
    }

}
