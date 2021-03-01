package com.apd.tema2.intersections;

import com.apd.tema2.entities.Intersection;
import java.util.concurrent.*; 

public class SimpleNRoundaboutIntersection implements Intersection {
	int maxTime;
	int maxCars;
	Semaphore semaphore;

	public SimpleNRoundaboutIntersection(){

	}

	public SimpleNRoundaboutIntersection(int maxTime, int maxCars){
		this.maxTime = maxTime;
		this.maxCars = maxCars;
	}

	public int getTime(){
		return this.maxTime;
	}

	public int getCars(){
		return this.maxCars;
	}

	public Semaphore getSemaphore(){
		return this.semaphore;
	}

	public void setTime(int time){
		this.maxTime = time;
	}

	public void setCars(int cars){
		this.maxCars = cars;
	}

	public void setSemaphore(int value){
		this.semaphore = new Semaphore(value);
	}

    // Define your variables here.
}
