#include<stdlib.h>
#include<stdio.h>
#include<pthread.h>
#include <unistd.h>

int zmienna_wspolna=0;
#define ILOSC 10
#define WYMIAR 1000
#define ROZMIAR WYMIAR*WYMIAR
double a[ROZMIAR],b[ROZMIAR],c[ROZMIAR];

typedef struct{
    int a;
    double b;
}struktura;
double czasozajmowacz(){
  int i, j, k;
  int n=WYMIAR;
  for(i=0;i<ROZMIAR;i++) a[i]=1.0*i;
  for(i=0;i<ROZMIAR;i++) b[i]=1.0*(ROZMIAR-i);
  for(i=0;i<n;i++){
    for(j=0;j<n;j++){
      c[i+n*j]=0.0;
      for(k=0;k<n;k++){
        c[i+n*j] += a[i+n*k]*b[k+n*j];
      }
    }
  }
  return(c[ROZMIAR-1]);
}

void * zadanie_watku (void * arg_wsk)
{
	struktura kopia = *((struktura *)arg_wsk);
        printf("\nZmienna a kopii w  funkcji watku: %d",kopia.a);
	printf("\nZmienna b struktury w  funkcji watku: %f",kopia.b);

	struktura * oryginal = (struktura*)arg_wsk;
	printf("\nZmienna a oryginału w  funkcji watku: %d",oryginal->a);
	printf("\nZmienna b oryginału w  funkcji watku: %f",oryginal->b);
	
	oryginal->a=69;
        return NULL;
}


int main(){
        pthread_t t;
        pthread_attr_t attr;
        void *wynik;
        
        struktura s; 
        s.a = 67;
        s.b=2.81;


        pthread_create(&t,NULL,zadanie_watku,&s);
	pthread_join(t, &wynik);   
      
	printf("\nPO FUNKCJI WATKU WARTOSC a STRUKTURY: %d\n",s.a);

	return 0;
}
