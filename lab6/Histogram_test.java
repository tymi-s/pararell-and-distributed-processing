import java.util.Scanner;


class Histogram_test {
    
    public static void main(String[] args) {

	Scanner scanner = new Scanner(System.in);
	
	System.out.println("Set image size: n (#rows), m(#kolumns)");
	int n = scanner.nextInt();
	int m = scanner.nextInt();
	Obraz_v1 obraz_1 = new Obraz_v1(n, m);
	
	/////////////////////////////////////////////////////////////////////////////////obliczenia sekwencyjne
	obraz_1.calculate_histogram();
	System.out.println("Obliczenia sekwencyjne");
	obraz_1.print_histogram();

	////////////////////////////////////////////////////////////////////////////////////////// Oblicz równolegle
        System.out.println("\nObliczenia rownlegle:");
	System.out.println("\nSet number of characters to process (max 94):");
        int num_chars = scanner.nextInt();
        
        Watek[] threads = new Watek[num_chars];
        
        // tworzenie i uruchamianie watkow
        for(int i=0; i<num_chars; i++) {
            char znak = (char)(i+33); 
            threads[i] = new Watek(obraz_1, znak, i+1);
            threads[i].start();
        }
        
        // Czekaj na zakończenie
        for(int i=0; i<num_chars; i++) {
            try {
                threads[i].join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // Porównanie
        System.out.println("\nPORÓWNANIE");
        obraz_1.compare_histograms();

    }

}

