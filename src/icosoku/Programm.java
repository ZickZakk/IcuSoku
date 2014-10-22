package icosoku;

import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 * Created by Georg on 22.10.2014.
 */
public class Programm
{
    public static void main(String[] args)
    {
        int[] problem1 = {1, 3, 10, 7, 5, 4, 11, 6, 12, 8, 9, 2};
        int[] problem2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int[] problem3 = {6, 12, 9, 10, 7, 2, 8, 1, 3, 5, 4, 11};
        int[] problem4 = {12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        ArrayList<int[]> alleProbleme = new ArrayList<int[]>();
        alleProbleme.add(problem1);
        //        alleProbleme.add(problem2);
        //        alleProbleme.add(problem3);
        //        alleProbleme.add(problem4);

        //        long gesamtZeit = solveStandard(alleProbleme);
        //long gesamtZeit = solveWithSortedFlächen(alleProbleme);
        long gesamtZeit = solveWithSortedFlächenAdvanced(alleProbleme);

        System.out.println("Alle " + alleProbleme.size() + " Probleme in " + gesamtZeit + " ms gelöst.");
    }

    private static long solveWithSortedFlächenAdvanced(ArrayList<int[]> alleProbleme)
    {
        long gesamtZeit = 0;

        for (int[] problem : alleProbleme)
        {
            IcoSoku icoSoku = new IcoSoku(problem);

            long now = System.currentTimeMillis();

            Integer[] sortierteFlächen = GenerateSortedFlächenArray(icoSoku);

            if (IcoSokuSolver.SolveIcoSoku(icoSoku, 0, sortierteFlächen))
            {
                long total = System.currentTimeMillis() - now;
                gesamtZeit += total;
            }
            else
            {
                System.out.println("Ein Rätsel konnte nicht gelöst werden!");
                System.exit(0);
            }
        }
        return gesamtZeit;
    }

    private static Integer[] GenerateSortedFlächenArray(IcoSoku icoSoku)
    {
        // Set, dass die Flächen enthält (keine doppelt)
        LinkedHashSet<Integer> vorsortierteFlächen = new LinkedHashSet<Integer>();

        // welche Ecken wurden schon betrachtet?
        boolean[] checkedEcken = new boolean[20];

        //Ecke mit der 1 finden
        for (int ecke = 0; ecke < 12; ecke++)
        {
            if (icoSoku.sollWertAnEcke(ecke) == 1)
            {
                // Standard-Sortierung, weil 1 an erster Ecke?
                if (ecke == 0)
                {
                    return new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
                }

                checkedEcken[ecke] = true;

                int[] flaechen = icoSoku.getFlaechenToEcke(ecke);
                for (int flaeche = 0; flaeche < 5; flaeche++)
                {
                    vorsortierteFlächen.add(flaechen[flaeche]);
                }
                break;
            }
        }

        while (vorsortierteFlächen.size() < 20)
        {
            LinkedHashSet<Integer> temp = new LinkedHashSet<Integer>();
            for (Integer fläche : vorsortierteFlächen)
            {
                int[] eckenToCheck = icoSoku.getEckenToFlaeche(fläche);

                for (int i = 0; i < eckenToCheck.length; i++)
                {
                    int eckeToCheck = eckenToCheck[i];

                    if (!checkedEcken[eckeToCheck])
                    {
                        checkedEcken[eckeToCheck] = true;

                        int[] flaechen = icoSoku.getFlaechenToEcke(eckeToCheck);
                        for (int flaeche = 0; flaeche < 5; flaeche++)
                        {
                            temp.add(flaechen[flaeche]);
                        }
                    }
                }
            }
            vorsortierteFlächen.addAll(temp);
        }

        Integer[] sortierteFlächen = new Integer[20];
        sortierteFlächen = vorsortierteFlächen.toArray(sortierteFlächen);
        return sortierteFlächen;
    }

    private static long solveStandard(ArrayList<int[]> alleProbleme)
    {
        long gesamtZeit = 0;

        for (int[] problem : alleProbleme)
        {
            IcoSoku icoSoku = new IcoSoku(problem);

            long now = System.currentTimeMillis();

            if (IcoSokuSolver.SolveIcoSoku(icoSoku, 0))
            {
                long total = System.currentTimeMillis() - now;
                gesamtZeit += total;
            }
            else
            {
                System.out.println("Ein Rätsel konnte nicht gelöst werden!");
                System.exit(0);
            }
        }
        return gesamtZeit;
    }

    private static long solveWithSortedFlächen(ArrayList<int[]> alleProbleme)
    {
        long gesamtZeit = 0;

        for (int[] problem : alleProbleme)
        {
            IcoSoku icoSoku = new IcoSoku(problem);

            long now = System.currentTimeMillis();

            LinkedHashSet<Integer> vorsortierteFlächen = new LinkedHashSet<Integer>();
            for (int eckenWert = 1; eckenWert < 13; eckenWert++)
            {
                for (int ecke = 0; ecke < 12; ecke++)
                {
                    if (icoSoku.sollWertAnEcke(ecke) == eckenWert)
                    {
                        int[] flaechen = icoSoku.getFlaechenToEcke(ecke);
                        for (int flaeche = 0; flaeche < 5; flaeche++)
                        {
                            vorsortierteFlächen.add(flaechen[flaeche]);
                        }
                        break;
                    }
                }
            }

            Integer[] sortierteFlächen = new Integer[20];
            sortierteFlächen = vorsortierteFlächen.toArray(sortierteFlächen);

            if (IcoSokuSolver.SolveIcoSoku(icoSoku, 0, sortierteFlächen))
            {
                long total = System.currentTimeMillis() - now;
                gesamtZeit += total;
            }
            else
            {
                System.out.println("Ein Rätsel konnte nicht gelöst werden!");
                System.exit(0);
            }
        }
        return gesamtZeit;
    }
}
