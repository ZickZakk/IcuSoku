package icosoku;

import java.util.HashSet;

/**
 * Created by Georg on 17.10.2014.
 */
public class IcoSokuSolver
{
    public static boolean SolveIcoSoku(IcoSoku icoSoku, int fläche)
    {
        if (fläche > 19)
        {
            return icoSoku.pruefeAlles();
        }

        // Mekre alle geprüften Plättchen
        boolean[] schonGeprüft = new boolean[20];

        for (int plättchen = 0; plättchen < 20; plättchen++)
        {
            if (icoSoku.istPlaettchenVerfuegbar(plättchen))
            {
                schonGeprüft[plättchen] = true;

                // Doppelte Plättchen müssen nicht nochmal gerpüft werden
                if (doppeltesPlättchen(plättchen, schonGeprüft))
                {
                    continue;
                }

                // Symmetrische Plättchen müssen nicht gedreht werden
                int maxOrient = 3;
                if (plättchenSymmetrisch(icoSoku, plättchen))
                {
                    maxOrient = 1;
                }

                for (int orient = 0; orient < maxOrient; orient++)
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

    public static boolean SolveIcoSoku(IcoSoku icoSoku, int flächeId, Integer[] sortierteFlächen)
    {
        if (flächeId > 19)
        {
            return icoSoku.pruefeAlles();
        }

        int fläche = sortierteFlächen[flächeId];

        // Merke alle geprüften Plättchen
        boolean[] schonGeprüft = new boolean[20];

        for (int plättchen = 0; plättchen < 20; plättchen++)
        {
            if (icoSoku.istPlaettchenVerfuegbar(plättchen))
            {
                schonGeprüft[plättchen] = true;

                // Doppelte Plättchen müssen nicht nochmal gerpüft werden
                if (doppeltesPlättchen(plättchen, schonGeprüft))
                {
                    continue;
                }

                // Symmetrische Plättchen müssen nicht gedreht werden
                int maxOrient = 3;
                if (plättchenSymmetrisch(icoSoku, plättchen))
                {
                    maxOrient = 1;
                }

                for (int orient = 0; orient < maxOrient; orient++)
                {
                    icoSoku.setzePlaettchen(fläche, plättchen, orient);

                    if (flaecheKorrekt(icoSoku, fläche))
                    {
                        if (SolveIcoSoku(icoSoku, flächeId + 1, sortierteFlächen))
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

    private static boolean doppeltesPlättchen(int plättchen, boolean[] schonGeprüft)
    {
        switch (plättchen)
        {
            // Plättchen 5=6=7
            case 5:
                return schonGeprüft[6] || schonGeprüft[7];
            case 6:
                return schonGeprüft[5] || schonGeprüft[7];
            case 7:
                return schonGeprüft[5] || schonGeprüft[6];
            // Plättchen 8=9=10
            case 8:
                return schonGeprüft[9] || schonGeprüft[10];
            case 9:
                return schonGeprüft[8] || schonGeprüft[10];
            case 10:
                return schonGeprüft[8] || schonGeprüft[9];
            // Plättchen 14=15
            case 14:
                return schonGeprüft[15];
            case 15:
                return schonGeprüft[14];
            // Plättchen 16=17
            case 16:
                return schonGeprüft[17];
            case 17:
                return schonGeprüft[16];
            default:
                return false;
        }
    }

    private static boolean plättchenSymmetrisch(IcoSoku icoSoku, int plättchen)
    {
        Integer A = icoSoku.zahlAmPlaettchen(plättchen, 0);
        Integer B = icoSoku.zahlAmPlaettchen(plättchen, 1);
        Integer C = icoSoku.zahlAmPlaettchen(plättchen, 2);

        return A.equals(B) && B.equals(C) && A.equals(C);
    }

    private static boolean flaecheKorrekt(IcoSoku icoSoku, int fläche)
    {
        int[] eckenToCheck = icoSoku.getEckenToFlaeche(fläche);

        for (int i = 0; i < eckenToCheck.length; i++)
        {
            int eckeToCheck = eckenToCheck[i];
            int plättchenZahl = icoSoku.anzahlPlaettchenAnEcke(eckeToCheck);

            int punkteDifferenz = icoSoku.sollWertAnEcke(eckeToCheck) - icoSoku.istWertAnEcke(eckeToCheck);
            if(punkteDifferenz < 0)
            {
                return false;
            }

            if (punkteDifferenz > ((5 - plättchenZahl) * 3))
            {
                return false;
            }
        }

        return true;
    }

}
