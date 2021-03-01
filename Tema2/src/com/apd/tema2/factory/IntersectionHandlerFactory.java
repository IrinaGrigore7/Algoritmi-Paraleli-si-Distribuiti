package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.*;
import com.apd.tema2.intersections.*;
import com.apd.tema2.utils.Constants;
import java.util.concurrent.*;


import static java.lang.Thread.sleep;

/**
 * Clasa Factory ce returneaza implementari ale InterfaceHandler sub forma unor
 * clase anonime.
 */
public class IntersectionHandlerFactory {

    public static IntersectionHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of
        // them)
        // road in maintenance - 2 ways 1 lane each, X cars at a time
        // road in maintenance - 1 way, M out of N lanes are blocked, X cars at a time
        // railroad blockage for s seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    try{
                        // fiecare thread este pus sa astepte la semafor 
                        System.out.println("Car " +  car.getId() + " has reached the semaphore, now waiting...");
                        Thread.sleep(car.getWaitingTime());
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }  
                    // dupa ce timpul de asteptare a trecut, este afisat urmatorul mesaj
                    System.out.println("Car " + car.getId() + " has waited enough, now driving...");  
                }
            };
            case "simple_n_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    try{
                        // intra doar n masini in intersectie
                        System.out.println("Car " +  car.getId() + " has reached the roundabout, now waiting...");
                       ((SimpleNRoundaboutIntersection)Main.intersection).getSemaphore().acquire();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }  
                    try{
                        // asteapta sa iasa din interectie
                        System.out.println("Car " +  car.getId() + " has entered the roundabout");
                        Thread.sleep(((SimpleNRoundaboutIntersection)Main.intersection).getTime());
                       
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }  
                    // au iesit din intersectie
                    System.out.println("Car " + car.getId() + " has exited the roundabout after " +  
                        (((SimpleNRoundaboutIntersection)Main.intersection).getTime() / 1000) + " seconds"); 
                    ((SimpleNRoundaboutIntersection)Main.intersection).getSemaphore().release();
                    
                }
            };
            case "simple_strict_1_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    try{
                        // in intersectie intra o singura masina de pe fiecare directie de mers
                        System.out.println("Car " +  car.getId() + " has reached the roundabout");
                       ((SimpleStrict1CarRoundaboutIntersection)Main.intersection).getSemaphore(car.getStartDirection()).acquire();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }  
                    try{
                        // asteapta sa iasa din intersectie
                        System.out.println("Car " +  car.getId() + " has entered the roundabout from lane " + car.getStartDirection());
                        Thread.sleep(((SimpleStrict1CarRoundaboutIntersection)Main.intersection).getTime());
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }  
                    // ies din intersectie
                    System.out.println("Car " + car.getId() + " has exited the roundabout after " +  
                        (((SimpleStrict1CarRoundaboutIntersection)Main.intersection).getTime() / 1000) + " seconds"); 
                    ((SimpleStrict1CarRoundaboutIntersection)Main.intersection).getSemaphore(car.getStartDirection()).release();
                }
            };
            case "simple_strict_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {

                }
            };
            case "simple_max_x_car_roundabout" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance
                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    try{
                        // in intersectie pot intra maxim n  masini de pe fiecare directie de mers
                        System.out.println("Car " +  car.getId() + " has reached the roundabout from lane " + car.getStartDirection());
                       ((SimpleMaxXCarRoundaboutIntersection)Main.intersection).getSemaphore(car.getStartDirection()).acquire();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }  

                    try{
                        // asteapta sa iasa din intersectie
                        System.out.println("Car " +  car.getId() + " has entered the roundabout from lane " + car.getStartDirection());
                        Thread.sleep(((SimpleMaxXCarRoundaboutIntersection)Main.intersection).getTime());
                       
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }  
                    // ies din intersecti
                    System.out.println("Car " + car.getId() + " has exited the roundabout after " +  (((SimpleMaxXCarRoundaboutIntersection)Main.intersection).getTime() / 1000) + " seconds"); 
                    ((SimpleMaxXCarRoundaboutIntersection)Main.intersection).getSemaphore(car.getStartDirection()).release();
                    
                }
            };
            case "priority_intersection" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    // Get your Intersection instance

                    try {
                        sleep(car.getWaitingTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } // NU MODIFICATI

                    // Continuati de aici
                }
            };
            case "crosswalk" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };
            case "simple_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };
            case "complex_maintenance" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    
                }
            };
            case "railroad" -> new IntersectionHandler() {
                @Override
                public void handle(Car car) {
                    try {
                        // toate masinile care ajung asteapta la beriera
                        ((RailroadIntersection)Main.intersection).addQueue(car.getId(), car.getStartDirection());
                        ((RailroadIntersection)Main.intersection).getBarrier().await();
                    } catch (InterruptedException | BrokenBarrierException e ) {
                        e.printStackTrace();
                    } 
                    // un thread anunta trecerea trenului
                    if(car.getId() == 0) {
                        System.out.println("The train has passed, cars can now proceed");
                    }
                    // se realizeaza trecerea masinilor in rdinea in care au ajuns la bariera
                    ((RailroadIntersection)Main.intersection).printQueue();
                }
                   
            };
            default -> null;
        };
    }
}
