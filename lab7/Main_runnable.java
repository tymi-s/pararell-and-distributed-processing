import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.ArrayList;

class Main_runnable {
    public static void main(String[] args) {

        int liczba_watkow = 4;
        int liczba_zadan = 16;
        double a = 0;
        double b = Math.PI;
        double szerokosc_podprzedzialu = (b - a) / liczba_zadan;
        double dx = 0.001;

        // Pula wątków:
        // taka pula wątków powoduje że mamy 4 zadania przydzielanie równolegle do 4 wątków. Kolejne zadania
        // są przydzielane po ukończerniu obecnych przez wszystkie 4 wątki
        ExecutorService executor = Executors.newFixedThreadPool(liczba_watkow);


        Calka_runnable[] tasks = new Calka_runnable[liczba_zadan];


        for (int i = 0; i < liczba_zadan; i++) {
            double a_local = a + i * szerokosc_podprzedzialu;
            double b_local = a_local + szerokosc_podprzedzialu;

            tasks[i] = new Calka_runnable(a_local, b_local, dx);
            executor.execute(tasks[i]);
        }

        executor.shutdown();


        while (!executor.isTerminated()) {

        }


        double suma = 0.0;
        for (int i = 0; i < liczba_zadan; i++) {
            suma += tasks[i].getWynik();
        }

        System.out.println("\n\nWynik całkowania: " + suma);
        System.out.println("Oczekiwany wynik: 2.0");
    }
}