pthreads_detach_kill:pthreads_detach_kill.o
	gcc pthreads_detach_kill.o -o pthreads_detach_kill

pthreads_detach_kill.o:pthreads_detach_kill.c
	gcc -c pthreads_detach_kill.c

pthreads_sam:pthreads_sam.o
	gcc pthreads_sam.o -o pthreads_sam

pthreads_sam.o:pthread_sam.c
	gcc -c pthreads_sam.c


clean:
	rm -rf *.o pthreads_detach_kill pthreads_sam
