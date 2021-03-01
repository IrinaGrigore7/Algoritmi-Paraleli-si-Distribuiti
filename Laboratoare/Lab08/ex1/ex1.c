#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>

int main (int argc, char *argv[])
{
    int  numtasks, rank;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numtasks);
    MPI_Comm_rank(MPI_COMM_WORLD,&rank);

    int recv_num;
    int random_num;
    int N = argv[1]
    
    if (rank == 0) {
        printf("%d\n", N);

        /*srand(42);
        random_num = rand();
        // Send the number to the next process.
        MPI_Send(&random_num, 1, MPI_INT, 1, 1, MPI_COMM_WORLD);
        printf("Process with rank [%d] sent the number %d.\n", rank, random_num);
         MPI_Status status;
        MPI_Recv(&random_num, 1, MPI_INT, rank - 1, 1, MPI_COMM_WORLD, &status);
        printf("Process with rank [%d], received %d with tag %d.\n",
                rank, random_num, status.MPI_TAG);*/


    } //else {
        // Middle process.
        // Receives the number from the previous process.
        /*MPI_Status status;
        MPI_Recv(&random_num, 1, MPI_INT, rank - 1, 1, MPI_COMM_WORLD, &status);
        printf("Process with rank [%d], received %d with tag %d.\n",
                rank, random_num, status.MPI_TAG);
        // Increments the number.
        random_num += 2;
        // Sends the number to the next process.
        MPI_Send(&random_num, 1, MPI_INT, rank + 1, 1, MPI_COMM_WORLD);
        printf("Process with rank [%d] sent the number %d.\n", rank, random_num);*/

    //}

    MPI_Finalize();

}

