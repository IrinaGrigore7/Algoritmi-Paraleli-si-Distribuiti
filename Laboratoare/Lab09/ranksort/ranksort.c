#include<mpi.h>
#include<stdio.h>
#include<stdlib.h>
#include<math.h>

#define N 8
#define MASTER 0

void compareVectors(int * a, int * b) {
	// DO NOT MODIFY
	int i;
	for(i = 0; i < N; i++) {
		if(a[i]!=b[i]) {
			printf("Sorted incorrectly\n");
			return;
		}
	}
	printf("Sorted correctly\n");
}

void displayVector(int * v) {
	// DO NOT MODIFY
	int i;
	int displayWidth = 2 + log10(v[N-1]);
	for(i = 0; i < N; i++) {
		printf("%d ", v[i]);
	}
	printf("\n");
}

int cmp(const void *a, const void *b) {
	// DO NOT MODIFY
	int A = *(int*)a;
	int B = *(int*)b;
	return A-B;
}

int min (int a, int b) {
	if(a < b)
		return a;
	else 
		return b;
}
 
int main(int argc, char * argv[]) {
	int rank, i, j;
	int nProcesses;
	MPI_Init(&argc, &argv);
	int pos[N];
	int sorted = 0;
	int *v = (int*)malloc(sizeof(int)*N);
	int *vSorted = (int*)malloc(sizeof(int)*N);
	int *vQSort = (int*)malloc(sizeof(int)*N);

	for (i = 0; i < N; i++)
		pos[i] = 0;

	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &nProcesses);
	printf("Hello from %i/%i\n", rank, nProcesses);

    if (rank == MASTER) {
        // generate random vector
        v[0] = 5;
        v[1] = 1;
        v[2] = 3;
        v[3] = 2;
        v[4] = 4;
        v[5] = 6;
        v[6] = 7;
        v[7] = 0;

      	// send the vector to all processes
        for (int i = 0; i < nProcesses - 1; i++)
    		MPI_Send(v, N, MPI_INT,i + 1, 1, MPI_COMM_WORLD);
    }

    

	if(rank == 0) {
		// DO NOT MODIFY
		displayVector(v);

		// make copy to check it against qsort
		// DO NOT MODIFY
		for(i = 0; i < N; i++)
			vQSort[i] = v[i];
		qsort(vQSort, N, sizeof(int), cmp);

		// sort the vector v
		
        // recv the new pozitions
        for (int i = 0; i < nProcesses - 1; i++) {
        	int start = i *  N / (nProcesses - 1);
        	int end = min((i +  1) * N / (nProcesses - 1), N);

        	for (int j = start; j < end; j++) {
        		MPI_Status status;
				MPI_Recv(&pos[j], 1, MPI_INT, i + 1, 1, MPI_COMM_WORLD, &status);
        	}
        }
        for (int i = 0; i < N; i++){
        	vSorted[pos[i]] = v[i];
        }
		displayVector(vSorted);
		compareVectors(vSorted, vQSort);
	} else {
		// compute the positions
		int start = (rank - 1) * N / (nProcesses - 1);
		int end = min(rank * N / (nProcesses - 1), N);
		MPI_Status status;
		MPI_Recv(v, N, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		for (int i = start; i < end; i++) {
			for (int j = 0; j < N; j++) {
				if (v[j] < v[i])
					pos[i]++;
			}
		}

		// send the new positions to process MASTER
		for (int i = start; i < end; i++){
			MPI_Send(&pos[i], 1, MPI_INT, 0, 1, MPI_COMM_WORLD);
		}
        
        
	}

	MPI_Finalize();
	return 0;
}
