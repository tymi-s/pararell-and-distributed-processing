class Watek extends Thread{

	private Obraz_v1 obraz;
	private char znak;
	private int thread_id;
	
	public Watek(Obraz_v1 obraz, char znak, int thread_id){
		this.obraz = obraz;
		this.znak = znak;
		this.thread_id = thread_id;

	}
	
	//run() to metoda uruchamiana przez metode start() na obiekcie tej klasy. oznacza ona że to co jest w run będzie wykonywane przez osobny wątek czyli będą dwa wątki w programie main i start()
	@Override
	public void run(){
		
		obraz.calculate_histogram_parallel(znak);
		obraz.print_histogram_parallel(znak, thread_id);
		
	}
}
