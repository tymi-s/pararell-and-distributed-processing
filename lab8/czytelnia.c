#include<stdlib.h>
#include<stdio.h>
#include<unistd.h>
#include<pthread.h>

#include"czytelnia.h"


/*** Implementacja procedur interfejsu ***/

int my_read_lock_lock(cz_t* cz_p){

  pthread_mutex_lock(&cz_p->mutex);
  
  // JEŻELI( liczba_pisz > 0 lub ~empty( pisarze ) ) wait( czytelnicy );
  while(cz_p->l_p > 0 || cz_p->pisarze_czekajacy > 0) {
    cz_p->czytelnicy_czekajacy++;
    pthread_cond_wait(&cz_p->czytelnicy, &cz_p->mutex);
    cz_p->czytelnicy_czekajacy--;
  }
  
  cz_p->l_c++;
  pthread_cond_signal(&cz_p->czytelnicy);  // kolejni czytelnicy
  
  pthread_mutex_unlock(&cz_p->mutex);
  return 0;
  
}


int my_read_lock_unlock(cz_t* cz_p){
   
    pthread_mutex_lock(&cz_p->mutex);
  
  cz_p->l_c--;
  
  // JEŻELI( liczba_czyt = 0 ) signal( pisarze );
  if(cz_p->l_c == 0) {
    pthread_cond_signal(&cz_p->pisarze);
  }
  
  pthread_mutex_unlock(&cz_p->mutex);
  return 0;

}


int my_write_lock_lock(cz_t* cz_p){
  
  pthread_mutex_lock(&cz_p->mutex);  // 1. Zamknij mutex na początku
  
  // JEŻELI( liczba_czyt+liczba_pisz > 0 ) wait( pisarze );
  while(cz_p->l_c + cz_p->l_p > 0) {
    cz_p->pisarze_czekajacy++;
    pthread_cond_wait(&cz_p->pisarze, &cz_p->mutex);
    cz_p->pisarze_czekajacy--;
  }
  
  cz_p->l_p++;
  
  pthread_mutex_unlock(&cz_p->mutex);  // 2. Zwolnij mutex na końcu
  return 0;
}


int my_write_lock_unlock(cz_t* cz_p){
    
  pthread_mutex_lock(&cz_p->mutex);
  
  cz_p->l_p--;
  
  // JEŻELI( ~empty( czytelnicy ) ) signal( czytelnicy );
  // WPP signal( pisarze )
  if(cz_p->czytelnicy_czekajacy > 0) {  // zastąpienie empty()
    pthread_cond_signal(&cz_p->czytelnicy);
  } else {
    pthread_cond_signal(&cz_p->pisarze);
  }
  
  pthread_mutex_unlock(&cz_p->mutex);
  return 0;
  
}

void inicjuj(cz_t* cz_p){

  cz_p->l_p = 0;
  cz_p->l_c = 0;
  cz_p->czytelnicy_czekajacy = 0;
  cz_p->pisarze_czekajacy = 0;
  
  pthread_mutex_init(&cz_p->mutex, NULL);
  pthread_cond_init(&cz_p->czytelnicy, NULL);
  pthread_cond_init(&cz_p->pisarze, NULL);  

}

void czytam(cz_t* cz_p){

// wypisanie wartości zmiennych kontrolujących działanie: liczby czytelników i pisarzy
  printf("\t\t\t\t\tczytam:  l_c %d, l_p %d\n", cz_p->l_c, cz_p->l_p); 
// sprawdzenie warunku poprawności i ewentualny exit
// warunek:
  if( cz_p->l_p>1 || (cz_p->l_p==1 && cz_p->l_c>0) || cz_p->l_p<0 || cz_p->l_c<0 ) {
    printf("Blad: ....\n");
    //exit(0);
  }

    usleep(rand()%3000000);
}

void pisze(cz_t* cz_p){

// wypisanie wartości zmiennych kontrolujących działanie: liczby czytelników i pisarzy
  printf("\t\t\t\t\tpisze:   l_c %d, l_p %d\n", cz_p->l_c, cz_p->l_p); 
// sprawdzenie warunku poprawności i ewentualny exit
  if( cz_p->l_p>1 || (cz_p->l_p==1 && cz_p->l_c>0) || cz_p->l_p<0 || cz_p->l_c<0 ) {
    printf("Blad: ....\n");
    //exit(0);
  }

    usleep(rand()%3000000);
}


