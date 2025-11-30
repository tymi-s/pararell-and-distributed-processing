import java.util.Scanner;


class Histogram_test {
    
    public static void main(String[] args) {

	Scanner scanner = new Scanner(System.in);
	
	System.out.println("Set image size: n (#rows), m(#kolumns)");
	int n = scanner.nextInt();
	int m = scanner.nextInt();
	int[] tab = new int[94];
	Obraz_v2 obraz_1 = new Obraz_v2(n, m);
	
	/////////////////////////////////////////////////////////////////////////////////obliczenia sekwencyjne
	obraz_1.calculate_histogram();
	System.out.println("Obliczenia sekwencyjne");
	obraz_1.print_histogram();

	////////////////////////////////////////////////////////////////////////////////////////// Oblicz równolegle
        System.out.println("\nObliczenia rownlegle:");
	System.out.println("\nPodaj liczbe threads'ow: ");
        int num_threads = scanner.nextInt();
	int znaki_na_watek = 94/num_threads; // blok dla każdego watku
	int reszta = 94% num_threads;
        
        Thread[] threads = new Thread[num_threads];//tablica threadsow
        
        int start = 0;
        for(int i=0; i<num_threads; i++) {
            int end = start + znaki_na_watek - 1;
            if(i < reszta) end++;
            
            Watek_v2 zadanie = new Watek_v2(obraz_1, start, end, i+1);
            threads[i] = new Thread(zadanie);  // tworzenie thread'a z runnable :D
            threads[i].start();
            
            start = end + 1;
        }
        
        // Czekaj na zakończenie
        for(int i=0; i<num_threads; i++) {
            try {
                threads[i].join();
		for(int i=0; i<94; i++){
			tab[i]=zadanie[i];	
		}
		 
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }        
        // Porównaj wyniki
        System.out.println("\n=== PORÓWNANIE ===");
        obraz_1.compare_histograms();

    }

}

