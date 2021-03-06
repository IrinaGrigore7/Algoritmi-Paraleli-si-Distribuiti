package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.*; 

public class SimpleMaxXCarRoundaboutIntersection implements Intersection {
	int maxTime;
	int lanes;
	Semaphore[] semaphores;
	
	public SimpleMaxXCarRoundaboutIntersection(){

	}

	public int getTime(){
		return this.maxTime;
	}

	public int getLanes(){
		return this.lanes;
	}

	public Semaphore getSemaphore(int value) {
		return this.semaphores[value];
	}

	public void setTime(int time){
		this.maxTime = time;
	}

	public void setLanes(int lanes){
		this.lanes = lanes;
	}

	public void setSemaphores(int value, int x){
		this.semaphores = new Semaphore[value];
		for (int i = 0; i < value; ++i) {
			this.semaphores[i] = new Semaphore(x);
		}
	}

    // Define your variables here.
}
