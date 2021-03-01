package com.apd.tema2.factory;

import com.apd.tema2.Main;
import com.apd.tema2.entities.Pedestrians;
import com.apd.tema2.entities.ReaderHandler;
import com.apd.tema2.intersections.*;
import java.util.concurrent.*;
 

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Returneaza sub forma unor clase anonime implementari pentru metoda de citire din fisier.
 */
public class ReaderHandlerFactory {

    public static ReaderHandler getHandler(String handlerType) {
        // simple semaphore intersection
        // max random N cars roundabout (s time to exit each of them)
        // roundabout with exactly one car from each lane simultaneously
        // roundabout with exactly X cars from each lane simultaneously
        // roundabout with at most X cars from each lane simultaneously
        // entering a road without any priority
        // crosswalk activated on at least a number of people (s time to finish all of them)
        // road in maintenance - 1 lane 2 ways, X cars at a time
        // road in maintenance - N lanes 2 ways, X cars at a time
        // railroad blockage for T seconds for all the cars
        // unmarked intersection
        // cars racing
        return switch (handlerType) {
            case "simple_semaphore" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) {
                    // Exemplu de utilizare:
                    Main.intersection = IntersectionFactory.getIntersection("simple_semaphore");
                }
            };
            case "simple_n_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    // To parse input line use:
                    String[] line = br.readLine().split(" ");
                    int maxCars = Integer.parseInt(line[0]);
                    int maxTime = Integer.parseInt(line[1]);

                    Main.intersection = IntersectionFactory.getIntersection("simple_n_roundabout");
                    ((SimpleNRoundaboutIntersection)Main.intersection).setCars(maxCars);
                    ((SimpleNRoundaboutIntersection)Main.intersection).setTime(maxTime);
                    ((SimpleNRoundaboutIntersection)Main.intersection).setSemaphore(maxCars); 
                }
            };
            case "simple_strict_1_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] line = br.readLine().split(" ");
                    int lanes = Integer.parseInt(line[0]);
                    int maxTime = Integer.parseInt(line[1]);


                    Main.intersection = IntersectionFactory.getIntersection("simple_strict_1_car_roundabout");
                    ((SimpleStrict1CarRoundaboutIntersection)Main.intersection).setLanes(lanes);
                    ((SimpleStrict1CarRoundaboutIntersection)Main.intersection).setTime(maxTime);
                    ((SimpleStrict1CarRoundaboutIntersection)Main.intersection).setSemaphores(lanes); 

                }
            };
            case "simple_strict_x_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {

                }
            };
            case "simple_max_x_car_roundabout" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    String[] line = br.readLine().split(" ");
                    int lanes = Integer.parseInt(line[0]);
                    int maxTime = Integer.parseInt(line[1]);
                    int x = Integer.parseInt(line[2]);

                    Main.intersection = IntersectionFactory.getIntersection("simple_max_x_car_roundabout");
                    ((SimpleMaxXCarRoundaboutIntersection)Main.intersection).setLanes(lanes);
                    ((SimpleMaxXCarRoundaboutIntersection)Main.intersection).setTime(maxTime);
                    ((SimpleMaxXCarRoundaboutIntersection)Main.intersection).setSemaphores(lanes, x); 
                }
            };
            case "priority_intersection" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    
                }
            };
            case "crosswalk" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    
                }
            };
            case "simple_maintenance" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    
                }
            };
            case "complex_maintenance" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    
                }
            };
            case "railroad" -> new ReaderHandler() {
                @Override
                public void handle(final String handlerType, final BufferedReader br) throws IOException {
                    Main.intersection = IntersectionFactory.getIntersection("railroad");
                    ((RailroadIntersection)Main.intersection).setBarrier(Main.carsNo);
                }
            };
            default -> null;
        };
    }

}
