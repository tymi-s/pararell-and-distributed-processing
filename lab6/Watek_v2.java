class Watek_v2 implements Runnable{

	private Obraz_v2 obraz;
	private int start_znak; // pierwszy znak dla watku
	private int end_znak;  // onstatni znak dla watku
	private int thread_id;
	
	public Watek_v2(Obraz_v2 obraz, int start_znak, int end_znak, int id){
		this.obraz = obraz;
		this.start_znak = start_znak;
		this.end_znak = end_znak;
		this.thread_id = id;

	}
	
	//run() to metoda uruchamiana przez metode start() na obiekcie tej klasy. oznacza ona że to co jest w run będzie wykonywane przez osobny wątek czyli będą dwa wątki w programie main i start()
	@Override
	public void run(){
		
		obraz.calculate_histogram_parallel_block(start_znak,end_znak);
		obraz.print_histogram_parallel_block(start_znak,end_znak, thread_id);
		
	}
}
