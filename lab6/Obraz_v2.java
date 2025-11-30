import  java.util.Random;


class Obraz_v2 {
    
    private int[] hist_parallel;    
    private int size_n;
    private int size_m;
    private char[][] tab;
    private char[] tab_symb;
    private int[] histogram;
    
    public Obraz_v2(int n, int m) {
	
	this.size_n = n;
	this.size_m = m;
	tab = new char[n][m];
	tab_symb = new char[94];
	hist_parallel = new int[94];
	final Random random = new Random();
	
	
	for(int k=0;k<94;k++) {
	    tab_symb[k] = (char)(k+33); 
	}

	for(int i=0;i<n;i++) {
	    for(int j=0;j<m;j++) {	
		tab[i][j] = tab_symb[random.nextInt(94)];  // ascii 33-127
		//tab[i][j] = (char)(random.nextInt(94)+33);  // ascii 33-127
		System.out.print(tab[i][j]+" ");
	    }
	    System.out.print("\n");
	}
	System.out.print("\n\n"); 
	
	histogram = new int[94];
   	clear_histogram();
	clear_histogram_parallel();
    }
    
    public void clear_histogram(){

	for(int i=0;i<94;i++) histogram[i]=0;

    }
    public void clear_histogram_parallel() {
    for(int i=0; i<94; i++) hist_parallel[i] = 0;
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////zliczanie znaków
    //jeden watek pokolei sprawdza ilosc wystapien każdego znaku po koleji
    public void calculate_histogram(){

	  for(int i=0;i<size_n;i++) {
	    for(int j=0;j<size_m;j++) {

		
		  for(int k=0;k<94;k++) {
		    if(tab[i][j] == tab_symb[k]) histogram[k]++;
		    //if(tab[i][j] == (char)(k+33)) histogram[k]++;	    
		  }

	    }
	  }

    }
   
    //jeden watek liczy ilosc wystapien znakow dla danego bloku znakow - mniej watkow ale wiecej operacji wykonuje kazdy z nich
    public synchronized void calculate_histogram_parallel_block(int start_znak, int end_znak) {
        for(int i=0; i<size_n; i++) {
            for(int j=0; j<size_m; j++) {
                for(int k=start_znak; k<=end_znak; k++) {  // Tylko dla znaków z bloku
                    if(tab[i][j] == tab_symb[k]) {
                        hist_parallel[k]++;
                    }
                }
            }
        }
    }    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////porownanie
    public void compare_histograms() {
    boolean same = true;
    for(int i=0; i<94; i++) {
        if(histogram[i] != hist_parallel[i]) {
            same = false;
            
        }
    }
    	if(same) {
        System.out.println("\n Histogramy sa identyczne - obliczenia poprawne!");
        }
	else {
        System.out.println("\n Histogramy sie roznia!");
    	}
    }

    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////wypisywanie
    public synchronized void print_histogram_parallel_block(int start_znak, int end_znak, int thread_id) {
    for(int k=start_znak; k<=end_znak; k++) {
        System.out.print("Wątek " + thread_id + ": " + tab_symb[k] + " " + hist_parallel[k] + " ");
            for(int i=0; i<hist_parallel[k]; i++) {
                System.out.print("=");
            }
            System.out.println();
        
    }
}

    public void print_histogram(){
	
	for(int i=0;i<94;i++) {
	    System.out.print(tab_symb[i]+" "+histogram[i]+"\n");	    
	    //System.out.print((char)(i+33)+" "+histogram[i]+"\n");	    
	}

    }

}
