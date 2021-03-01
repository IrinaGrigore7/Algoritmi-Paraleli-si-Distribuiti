package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.*;
import java.io.*; 
import java.util.*; 

public class RailroadIntersection implements Intersection {

    CyclicBarrier barrier;
    Queue<Integer> carQueue = new LinkedList<Integer>(); 
    Queue<Integer> laneQueue = new LinkedList<Integer>(); 


    public void setBarrier(int numThreads){
    	this.barrier = new CyclicBarrier(numThreads);
    }

    public CyclicBarrier getBarrier(){
    	return this.barrier;
    }

    public synchronized void printQueue(){
    	System.out.println("Car " + carQueue.poll() +
                         " from side number " + laneQueue.poll() + " has started driving");

    }

    public synchronized void addQueue(int carNumber, int lane){
    	System.out.println("Car " + carNumber + " from side number " + lane + " has stopped by the railroad");
    	carQueue.add(carNumber);
    	laneQueue.add(lane);
    }

}
