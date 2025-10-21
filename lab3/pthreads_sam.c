#include<stdlib.h>
#include<stdio.h>
#include<pthread.h>
#include <unistd.h>

int zmienna_wspolna=0;
#define ILOSC 10
#define WYMIAR 1000
#define ROZMIAR WYMIAR*WYMIAR
double a[ROZMIAR],b[ROZMIAR],c[ROZMIAR];

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
	int a = *(int *)arg_wsk;
	printf("\nZmienna w funkcji watku: %d",a);
	return NULL;
}
// watki działają  w różnej kolejności co zależy od doboru czasu dla nich przez procesor
// aby nadać kolejność wykonywania wątków można użyć pthread_setschedparam
int main(){
	pthread_t tid[ILOSC], tid2[ILOSC];
	pthread_attr_t attr;
	void *wynik;
	int id[ILOSC];
	int i;
	int p = ILOSC;
	
	for (int j=0; j<p;j++){
		id[j]=j;
	} 
	for(i=0; i <ILOSC;i++){
		
		pthread_create(&tid[i],NULL,zadanie_watku,&id[i]);
	}
	for(i=0; i<ILOSC;i++){
		
		pthread_join(tid[i], &wynik); 	
	}
}


