/*
 * APD - Tema 1
 * Octombrie 2020
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <pthread.h>
#include <string.h>

pthread_barrier_t barrier;

char *in_filename_julia;
char *in_filename_mandelbrot;
char *out_filename_julia;
char *out_filename_mandelbrot;


// structura pentru un numar complex
typedef struct _complex {
	double a;
	double b;
} complex;

// structura pentru parametrii unei rulari
typedef struct _params {
	int is_julia, iterations;
	double x_min, x_max, y_min, y_max, resolution;
	complex c_julia;
} params;

params par;
int width, height;
int **result;
int NUM_THREADS;

// citeste argumentele programului
void get_args(int argc, char **argv)
{
	if (argc < 5) {
		printf("Numar insuficient de parametri:\n\t"
				"./tema1 fisier_intrare_julia fisier_iesire_julia "
				"fisier_intrare_mandelbrot fisier_iesire_mandelbrot\n");
		exit(1);
	}

	in_filename_julia = argv[1];
	out_filename_julia = argv[2];
	in_filename_mandelbrot = argv[3];
	out_filename_mandelbrot = argv[4];
	NUM_THREADS = atoi(argv[5]);
}

// citeste fisierul de intrare
void read_input_file(char *in_filename, params* par)
{
	FILE *file = fopen(in_filename, "r");
	if (file == NULL) {
		printf("Eroare la deschiderea fisierului de intrare!\n");
		exit(1);
	}

	fscanf(file, "%d", &par->is_julia);
	fscanf(file, "%lf %lf %lf %lf",
			&par->x_min, &par->x_max, &par->y_min, &par->y_max);
	fscanf(file, "%lf", &par->resolution);
	fscanf(file, "%d", &par->iterations);

	if (par->is_julia) {
		fscanf(file, "%lf %lf", &par->c_julia.a, &par->c_julia.b);
	}

	fclose(file);
}

// scrie rezultatul in fisierul de iesire
void write_output_file(char *out_filename, int **result, int width, int height)
{
	int i, j;

	FILE *file = fopen(out_filename, "w");
	if (file == NULL) {
		printf("Eroare la deschiderea fisierului de iesire!\n");
		return;
	}

	fprintf(file, "P2\n%d %d\n255\n", width, height);
	for (i = 0; i < height; i++) {
		for (j = 0; j < width; j++) {
			fprintf(file, "%d ", result[i][j]);
		}
		fprintf(file, "\n");
	}

	fclose(file);
}

// aloca memorie pentru rezultat
int **allocate_memory(int width, int height)
{
	int **result;
	int i;

	result = malloc(height * sizeof(int*));
	if (result == NULL) {
		printf("Eroare la malloc!\n");
		exit(1);
	}

	for (i = 0; i < height; i++) {
		result[i] = malloc(width * sizeof(int));
		if (result[i] == NULL) {
			printf("Eroare la malloc!\n");
			exit(1);
		}
	}

	return result;
}

// elibereaza memoria alocata
void free_memory(int **result, int height)
{
	int i;

	for (i = 0; i < height; i++) {
		free(result[i]);
	}
	free(result);
}

// returneaza minimul dintre dou numere
int min(int a, int b){
	if (a < b)
		return a;
	return b;
}

// ruleaza algoritmul Julia
void run_julia(params *par, int **result, int width, int height, int thread_id)
{
	int w, h, i;

	int start_width = thread_id * width / NUM_THREADS;
    int end_width = min((thread_id + 1) * width / NUM_THREADS, width);

	// impart executia primului for la cele NUM_THREADS thread-uri
	for (w = start_width; w < end_width; w++) {
		for (h = 0; h < height; h++) {
			int step = 0;
			complex z = { .a = w * par->resolution + par->x_min,
							.b = h * par->resolution + par->y_min };

			while (sqrt(pow(z.a, 2.0) + pow(z.b, 2.0)) < 2.0 && step < par->iterations) {
				complex z_aux = { .a = z.a, .b = z.b };

				z.a = pow(z_aux.a, 2) - pow(z_aux.b, 2) + par->c_julia.a;
				z.b = 2 * z_aux.a * z_aux.b + par->c_julia.b;

				step++;
			}

			result[h][w] = step % 256;
		}
	}

	// dupa paralelizarea algoritmului, paralelizez si tranfomarea rezultatului
	pthread_barrier_wait(&barrier);

	int start_height = thread_id * (height / 2) / NUM_THREADS;
    int end_height = min((thread_id + 1) * (height / 2) / NUM_THREADS, (height / 2));

	// transforma rezultatul din coordonate matematice in coordonate ecran
	for (i = start_height; i < end_height; i++) {
		int *aux = result[i];
		result[i] = result[height - i - 1];
		result[height - i - 1] = aux;
	}
}

// ruleaza algoritmul Mandelbrot
void run_mandelbrot(params *par, int **result, int width, int height, int thread_id)
{
	int w, h, i;

	int start = thread_id * width / NUM_THREADS;
    int end = min((thread_id + 1) * width / NUM_THREADS, width);

    // impart executia primului for la cele NUM_THREADS thread-uri
	for (w = start; w < end; w++) {
		for (h = 0; h < height; h++) {
			complex c = { .a = w * par->resolution + par->x_min,
							.b = h * par->resolution + par->y_min };
			complex z = { .a = 0, .b = 0 };
			int step = 0;

			while (sqrt(pow(z.a, 2.0) + pow(z.b, 2.0)) < 2.0 && step < par->iterations) {
				complex z_aux = { .a = z.a, .b = z.b };

				z.a = pow(z_aux.a, 2.0) - pow(z_aux.b, 2.0) + c.a;
				z.b = 2.0 * z_aux.a * z_aux.b + c.b;

				step++;
			}

			result[h][w] = step % 256;
		}
	}

	pthread_barrier_wait(&barrier);
	// dupa paralelizarea algoritmului, paralelizez si tranfomarea rezultatului
	int start_height = thread_id * (height / 2) / NUM_THREADS;
    int end_height = min((thread_id + 1) * (height / 2) / NUM_THREADS, (height / 2));

	// transforma rezultatul din coordonate matematice in coordonate ecran
	for (i = start_height; i < end_height; i++) {
		int *aux = result[i];
		result[i] = result[height - i - 1];
		result[height - i - 1] = aux;
	}
}

// se apeleaza citirea din fisier, se face initializarea variabilelor width, height si result
void read_and_allocate(char* filename) {
	//se citesc parametrii de intrare
	if (strcmp(filename, in_filename_julia) == 0) {
		read_input_file(in_filename_julia, &par);
	} else if (strcmp(filename, in_filename_mandelbrot) == 0) {
		read_input_file(in_filename_mandelbrot, &par);
	}

	width = (par.x_max - par.x_min) / par.resolution;
	height = (par.y_max - par.y_min) / par.resolution;

	//se aloca tabloul cu rezultatul
	result = allocate_memory(width, height);
}

// se apeleaza scrierea in fisier, precum si functia de eliberare a memoriei
void write_and_free(char *filename){
	// se elibereaza memoria alocata
	if (strcmp(filename, out_filename_julia) == 0) {
		write_output_file(out_filename_julia, result, width, height);
	} else if (strcmp(filename, out_filename_mandelbrot) == 0) {
		write_output_file(out_filename_mandelbrot, result, width, height);
	}

	// se elibereaza memoria alocata
	free_memory(result, height);
}

void *thread_function(void *arg) {
	int thread_id = *(int *)arg;

	if(thread_id == 0) {
		read_and_allocate(in_filename_julia);
	}

	pthread_barrier_wait(&barrier);
	//se ruleaza algoritmul Julia
	run_julia(&par, result, width, height, thread_id);
	pthread_barrier_wait(&barrier);

	if(thread_id == 0) {
		write_and_free(out_filename_julia);
		read_and_allocate(in_filename_mandelbrot);
	}

	pthread_barrier_wait(&barrier);	
	// se ruleaza algoritmul Mandelbrot
	run_mandelbrot(&par, result, width, height, thread_id);
	pthread_barrier_wait(&barrier);	

	if(thread_id == 0) {
		write_and_free(out_filename_mandelbrot);
	}

	pthread_exit(NULL);
}

int main(int argc, char *argv[])
{

	// se citesc argumentele programului
	get_args(argc, argv);
	pthread_t tid[NUM_THREADS];
	int thread_id[NUM_THREADS];
	int i, r;

	// se initializeaza bariera
	r = pthread_barrier_init(&barrier, NULL, NUM_THREADS);
	if (r) {
		printf("Eroarea la initializarea barierei\n");
		exit(-1);
	}

	// se creeaza thread-urile
	for (i = 0; i < NUM_THREADS; i++) {
		thread_id[i] = i;
		r = pthread_create(&tid[i], NULL, thread_function, &thread_id[i]);
		if (r) {
            printf("Eroare la crearea thread-ului %d\n", thread_id[i]);
            exit(-1);
        }
	}

	// se asteapta terminarea thread-urilor secundare
	for (i = 0; i < NUM_THREADS; i++) {
		 r = pthread_join(tid[i], NULL);
		if (r) {
            printf("Eroare la asteptarea thread-ului %d\n", thread_id[i]);
            exit(-1);
        }
	}
	
	// distrugerea barierei
	r = pthread_barrier_destroy(&barrier);
	if (r) {
		printf("Eroare la distrugerea barierei\n");
		exit(-1);
	}

	return 0;
}
