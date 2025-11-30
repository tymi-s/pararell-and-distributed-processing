import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

public class MainSort {
    public static void main(String[] args) {

        // Rozmiar tablicy do sortowania
        int size = 10_000_000;


        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int)(Math.random() * 10000);
        }

        System.out.println("=== SORTOWANIE PRZEZ SCALANIE Z FORKJOINPOOL ===");
        System.out.println("Rozmiar tablicy: " + size);


        // liczba watkow === liczba procesorów
        // taka pula wątków pozwala na szybsze wykonanie zadan
        // gdy jeden z watkow ma podzadanie o dłuższym czasie wykonania niż inne to gdy inne czekają
        // to wtedy kradną część podzadania i pomagają jer wykonać
        ForkJoinPool pool = new ForkJoinPool();

        System.out.println("Liczba wątków w puli: " + pool.getParallelism());

        // Pomiar czasu
        long start = System.nanoTime();

        // sortowanie
        DivideTask task = new DivideTask(array);
        int[] sorted = pool.invoke(task);

        long end = System.nanoTime();
        double timeSec = (end - start) / 1_000_000_000.0;

        System.out.println("Czas sortowania: " + timeSec + " s");

        //sprawdzenie
        boolean isSorted = true;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] < sorted[i-1]) {
                isSorted = false;
                break;
            }
        }
        System.out.println("Poprawnie posortowane: " + isSorted);


        System.out.println("\nPierwszych 10 elementów: " + Arrays.toString(Arrays.copyOfRange(sorted, 0, 10)));
        System.out.println("Ostatnich 10 elementów: " + Arrays.toString(Arrays.copyOfRange(sorted, sorted.length - 10, sorted.length)));

        pool.shutdown();
    }
}