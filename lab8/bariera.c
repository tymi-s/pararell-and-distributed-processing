#include <pthread.h>
#include <stdio.h>

static pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
static pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
static int liczba_watkow_total = 0;
static int liczba_watkow_czeka = 0;

void bariera_init(int n) {
    liczba_watkow_total = n;
}

void bariera(void) {
    pthread_mutex_lock(&mutex);
    
    liczba_watkow_czeka++;
    
    if (liczba_watkow_czeka == liczba_watkow_total) {
        // Ostatni wątek - budzi wszystkich
        liczba_watkow_czeka = 0;
        pthread_cond_broadcast(&cond);
    } else {
        // Czekaj na pozostałe wątki
        pthread_cond_wait(&cond, &mutex);
    }
    
    pthread_mutex_unlock(&mutex);
}
