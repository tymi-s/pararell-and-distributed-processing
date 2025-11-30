import java.util.concurrent.RecursiveTask;
import java.util.Arrays;

class DivideTask extends RecursiveTask<int[]> {

    int[] arrayToDivide;
    private static final int THRESHOLD = 250; // warunek stopu

    public DivideTask(int[] arrayToDivide) {
        this.arrayToDivide = arrayToDivide;
    }

    protected int[] compute() {

        // WARUNEK STOPU - dla małych tablic sortuj sekwencyjnie
        if (arrayToDivide.length <= THRESHOLD) {
            Arrays.sort(arrayToDivide); // sortowanie wbudowane
            return arrayToDivide;
        }


        int mid = arrayToDivide.length / 2;
        int[] left = Arrays.copyOfRange(arrayToDivide, 0, mid);
        int[] right = Arrays.copyOfRange(arrayToDivide, mid, arrayToDivide.length);

        //podzadania
        DivideTask task1 = new DivideTask(left);
        DivideTask task2 = new DivideTask(right);

        //odłożenie do innego wątku
        task1.fork();

        // wykonanie w tym watku
        int[] tab2 = task2.compute();

        //join czeka na wynik task1
        int[] tab1 = task1.join();

        // Scalanie
        int[] scal_tab = new int[arrayToDivide.length];
        scal_tab(tab1, tab2, scal_tab);

        return scal_tab;
    }

    private void scal_tab(
            int[] tab1,
            int[] tab2,
            int[] scal_tab) {

        int i = 0, j = 0, k = 0;

        while ((i < tab1.length) && (j < tab2.length)) {

            if (tab1[i] < tab2[j]) {
                scal_tab[k] = tab1[i++];
            } else {
                scal_tab[k] = tab2[j++];
            }

            k++;
        }

        if (i == tab1.length) {

            for (int a = j; a < tab2.length; a++) {
                scal_tab[k++] = tab2[a];
            }

        } else {

            for (int a = i; a < tab1.length; a++) {
                scal_tab[k++] = tab1[a];
            }

        }
    }

}