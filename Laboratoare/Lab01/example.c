#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

long cores;
#define NUM_THREADS 2

void *f1(void *arg) {
  	long id = (long)arg;
  	for (int i = 0; i < 100; i++)
  		printf("Hello World din thread-ul %ld, indice %d!\n", id, i);
  	pthread_exit(NULL);
}

void *f2(void *arg) {
  	long id = (long)arg;
  	printf("Goodbye din thread-ul %ld\n", id);
  	pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
	cores = sysconf(_SC_NPROCESSORS_CONF);
	pthread_t threads[NUM_THREADS];
  	int r;
  	long id;
  	void *status;

  	//for (id = 0; id < NUM_THREADS; id++) {
  	id = 0;
	r = pthread_create(&threads[id], NULL, f1, (void *)id);
	if (r) {
	  		printf("Eroare la crearea thread-ului %ld\n", id);
	  		exit(-1);
		}

	id = 1;
	r = pthread_create(&threads[id], NULL, f2, (void *)id);
	if (r) {
	  	printf("Eroare la crearea thread-ului %ld\n", id);
	  	exit(-1);
	}

  	for (id = 0; id < NUM_THREADS; id++) {
		r = pthread_join(threads[id], &status);

		if (r) {
	  		printf("Eroare la asteptarea thread-ului %ld\n", id);
	  		exit(-1);
		}
  	}

  	pthread_exit(NULL);
}
