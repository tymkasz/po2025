package org.example.car;

import java.util.ArrayList;
import java.util.List;

public class Car extends Thread {
    // Components
    private final Engine engine;
    private final Gearbox gearbox;

    private final Position position;

    // Identification data
    private final String plateNumber;
    private final String model;
    private final String manufacturer;
    private final int weight;

    // Mouse target
    private double xTarget = 0.0;
    private double yTarget = 0.0;

    // Thread logic
    private volatile boolean isRunning = true;
    private boolean isEngineOn = false;

    // Observers
    private final List<Observer> observers = new ArrayList<>();


    // Full Constructor (Dependency Injection)
    public Car(Engine engine, Gearbox gearbox, String plateNumber,
               String manufacturer, String model, int weight) {

        this.engine = engine;
        this.gearbox = gearbox;

        this.plateNumber = plateNumber;
        this.manufacturer = manufacturer;
        this.model = model;
        this.weight = weight;

        this.position = new Position(0,0);

    }

    // LOGIC
    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(16);
                if (isEngineOn) { calculateMovement(); }
                notifyObservers();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void calculateMovement() {
        double dx = xTarget - position.getX();
        double dy = yTarget - position.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        // If we are near target, stop
        if (distance < 5) return;

        // With higher rpms, faster it goes
        double speedFactor = (engine.getRpm() * 0.002);
        if (speedFactor > 10) speedFactor = 10;

        // Vector normalization
        double moveX = (dx / distance) * speedFactor;
        double moveY = (dy / distance) * speedFactor;

        // Position update
        position.setX(position.getX() + moveX);
        position.setY(position.getY() + moveY);
    }

    // Method evoked by mouse clicking
    public void driveTo(double x, double y) {
        this.xTarget = x;
        this.yTarget = y;

        // Automatic gas increase
        if (isEngineOn && engine.getRpm() < 2000) {
            engine.increaseRpm(1500);
        }
    }

    // Control
    public void startEngine() {
        if (!isEngineOn) {
            engine.start();
            isEngineOn = true;
            // If thread is not already working, start it
            if (!this.isAlive()) this.start();
        }
    }

    public void stopEngine() {
        engine.stop();
        isEngineOn = false;
    }

    public void killThread() { isRunning = false; }

    // Delegations and getters
    public void shiftUp() {
        try { gearbox.shiftUp(); } catch (CarException e) { System.out.println(e.getMessage()); }
    }
    public void shiftDown() {
        try { gearbox.shiftDown(); } catch (CarException e) { System.out.println(e.getMessage()); }
    }

    // Access to components
    public Engine getEngine() { return engine; }
    public Gearbox getGearbox() { return gearbox; }
    public Clutch getClutch() { return gearbox.getClutch(); } // Delegation to gearbox
    public Position getPosition() { return position; }

    // Informative
    public double getXPosition() { return position.getX(); }
    public double getYPosition() { return position.getY(); }
    public int getSpeed() { return (int)(engine.getRpm() * 0.05); }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public String getPlateNumber() { return plateNumber; }
    public int getWeight() { return weight; }
    public boolean isOn() { return isEngineOn; }

    @Override public String toString() { return manufacturer + " " + model; }

    // Observer
    public void addObserver(Observer o) { observers.add(o); }
    private void notifyObservers() { for (Observer o : observers) o.update(); }

}


