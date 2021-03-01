#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

/*
    schelet pentru exercitiul 5
*/
//real    0m7.664s fara thread-uri
//real    0m6.518s cu 2 thread-uri
//real    0m5.869s cu 8 thread-uri

int* arr;
int array_size;

#define NUM_THREADS 8

int min(int a, int b) {
    if (a < b)
        return a;
    else 
        return b;
}

void *f(void *arg) {
    long id = (long)arg;
    int start = id * (double)array_size / NUM_THREADS;
    int end = min((id + 1) * (double)array_size / NUM_THREADS, array_size);
    for (int i = start; i < end; i++) {
        arr[i] += 100;
    }

    pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
    if (argc < 2) {
        perror("Specificati dimensiunea array-ului\n");
        exit(-1);
    }

    array_size = atoi(argv[1]);
    pthread_t threads[NUM_THREADS];
    int r;
    long id;
    void *status;

    arr = malloc(array_size * sizeof(int));
    for (int i = 0; i < array_size; i++) {
        arr[i] = i;
    }

    // for (int i = 0; i < array_size; i++) {
    //     printf("%d", arr[i]);
    //     if (i != array_size - 1) {
    //         printf(" ");
    //     } else {
    //         printf("\n");
    //     }
    // }

    // TODO: aceasta operatie va fi paralelizata
  
    for (id = 0; id < NUM_THREADS; id++) {
        r = pthread_create(&threads[id], NULL, f, (void *)id);
        if (r) {
            printf("Eroare la crearea thread-ului %ld\n", id);
            exit(-1);
        }
    }

    for (id = 0; id < NUM_THREADS; id++) {
            r = pthread_join(threads[id], &status);

            if (r) {
                printf("Eroare la asteptarea thread-ului %ld\n", id);
                exit(-1);
            }
        }

    // for (int i = 0; i < array_size; i++) {
    //     printf("%d", arr[i]);
    //     if (i != array_size - 1) {
    //         printf(" ");
    //     } else {
    //         printf("\n");
    //     }
    // }

  	pthread_exit(NULL);
}
