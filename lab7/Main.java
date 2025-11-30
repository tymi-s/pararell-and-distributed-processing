import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;

class Main {
    public static void main(String[] args) {

        int liczba_watkow = 4;
        int liczba_zadan = 16;
        double a = 0;
        double b = Math.PI;
        double szerokosc_podprzedzialu = (b - a) / liczba_zadan;
        double dx = 0.001;

        //pula wątków:
        ExecutorService executor = Executors.newFixedThreadPool(liczba_watkow);
        List<Future<Double>> futures = new ArrayList<>();

        // Tworzenie i wysyłanie zadań
        for (int i = 0; i < liczba_zadan; i++) {
            double a_local = a + i * szerokosc_podprzedzialu;
            double b_local = a_local + szerokosc_podprzedzialu;

            Calka_callable task = new Calka_callable(a_local, b_local, dx);
            Future<Double> future = executor.submit(task);
            futures.add(future);
        }
        executor.shutdown();//
        //odebranie wyników
        double suma = 0.0;
        try {
            for (Future<Double> future : futures) {
                suma += future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("\n\nWynik całkowania: " + suma);
        System.out.println("Oczekiwany wynik: 2.0");
    }


}