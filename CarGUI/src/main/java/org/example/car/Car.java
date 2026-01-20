package org.example.car;

import java.util.ArrayList;
import java.util.List;

public class Car extends Thread {
    // Components
    private final Engine engine;
    private final Gearbox gearbox;
    private final Clutch clutch;

    // Identification data
    private final String plateNumber;
    private final String model;
    private final String manufacturer;
    private final int maxSpeed;
    private final int weight;

    // Position
    private double xPosition = 0.0; // Starting X position
    private double yPosition = 0.0; // Starting Y position

    // Mouse target
    private double xTarget = 0.0;
    private double yTarget = 0.0;

    // Thread logic
    private volatile boolean isRunning = true;
    private boolean isEngineOn = false;

    // Observers
    private final List<Observer> observers = new ArrayList<>();


    // Full Constructor (Dependency Injection)
    public Car(Engine engine, Gearbox gearbox, Clutch clutch, String plateNumber,
               String manufacturer, String model, int maxSpeed, int weight) {

        this.engine = engine;
        this.gearbox = gearbox;
        this.clutch = clutch;

        this.plateNumber = plateNumber;
        this.manufacturer = manufacturer;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.weight = weight;

        this.xPosition = 0;
        this.yPosition = 0;
        this.xTarget = 0;
        this.yTarget = 0;

        this.isEngineOn = false;
        this.isRunning = true; // Thread ready to work

    }

    // LOGIC
    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(16);

                if (isEngineOn) {
                    calculateMovement();
                }

                notifyObservers();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void calculateMovement() {
        double dx = xTarget - xPosition;
        double dy = yTarget - yPosition;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // If we are near target, stop
        if (distance < 5) return;

        // With higher rpms, faster it goes
        double speedFactor = (engine.getRpm() * 0.002);

        if (speedFactor > 10) speedFactor = 10;
        if (speedFactor < 0) speedFactor = 0;

        // Vector normalization
        double moveX = (dx / distance) * speedFactor;
        double moveY = (dy / distance) * speedFactor;

        // Position update
        this.xPosition += moveX;
        this.yPosition += moveY;

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
            this.engine.start();
            this.isEngineOn = true;
            // If thread is not already working, start it
            if (!this.isAlive()) {
                this.start();
            }
        }
    }

    public void stopEngine() {
        this.engine.stop();
        this.isEngineOn = false;
    }

    // Stop thread when app is closed
    public void killThread() {
        this.isRunning = false;
    }

    // Observer
    public void addObserver(Observer o) {
        observers.add(o);
    }

    private void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }

    // Getters
    public double getXPosition() { return xPosition; }
    public double getYPosition() { return yPosition; }
    public Engine getEngine() { return engine; }
    public Gearbox getGearbox() { return gearbox; }
    public Clutch getClutch() { return clutch; }
    public boolean isOn() { return isEngineOn; }
    public String getModel() { return model; }
    public String getManufacturer() { return manufacturer; }
    public String getPlateNumber() { return plateNumber; }
    public int getWeight() { return weight; }
    public int getSpeed() { return (int)(engine.getRpm() * 0.05); } // Simplified speed for display
    public int getCurrentGear() { return gearbox.getCurrentGear(); }
    public boolean isClutchPressed() { return clutch.isPressed(); }

    @Override
    public String toString() { return manufacturer + " " + model; }

    // Delegations to components (for controller compatibility)
    public void shiftUp() { gearbox.shiftUp(); }
    public void shiftDown() { gearbox.shiftDown(); }
    public void brake() { engine.brake(); }

}


