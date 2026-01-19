package org.example.car;

public class Car {
    private final Engine engine;
    private final Gearbox gearbox;
    private final Clutch clutch;

    private boolean isEngineOn;
    private final String plateNumber;
    private final String model;
    private final String manufacturer;
    private final int maxSpeed;
    private final int weight;

    private double currentVelocity = 0;
    private double xPosition = -0.0; // Starting X position
    private double yPosition = 0.0;      // Starting Y position

    // Default Constructor for testing
    public Car(String plateNumber, String manufacturer, String model, int maxSpeed, int weight) {
        this(
                new Engine("Toyota", "Boxer", 7000), // Default Engine
                new Gearbox("Aisin", "Manual", 7), // Default Gearbox
                plateNumber,
                manufacturer,
                model,
                maxSpeed,
                weight,
                new Clutch("Exedy", "Stage1") // Default Clutch
        );
    }

    // Full Constructor (Dependency Injection)
    public Car(Engine engine, Gearbox gearbox, String plateNumber,
               String manufacturer, String model, int maxSpeed, int weight, Clutch clutch) {

        this.engine = engine;
        this.gearbox = gearbox;
        this.clutch = clutch;

        this.plateNumber = plateNumber;
        this.manufacturer = manufacturer;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.weight = weight;

        this.isEngineOn = false;

    }

    // LOGIC

    public void start() {
        this.engine.start();
        this.isEngineOn = true;
        System.out.println("Car started: " + this);
    }

    public void stop() {
        this.engine.stop();

        // Downshift to neutral after stopping
        while (this.getCurrentGear() > 0) {
            this.gearbox.shiftDown();
        }

        this.isEngineOn = false;
        System.out.println("Car stopped");
    }

    public void shiftUp() {
        if (clutch.isPressed()) {
            gearbox.shiftUp();

            // Simulate RPM drop after upshifting
            int currentRpm = this.engine.getRpm();
            int afterShiftRpm = (int) (currentRpm * 0.7);

            // Prevent RPM dropping below idle if engine is on
            if (isEngineOn && afterShiftRpm < 800) {
                afterShiftRpm = 800;
            }

            engine.setRpm(afterShiftRpm);
        } else {
            System.out.println("Press clutch to shift!");
        }
    }

    public void shiftDown() {
        if (clutch.isPressed()) {
            int currentSpeed = this.getSpeed(); // Get speed before shifting

            this.gearbox.shiftDown();
            int newGear = gearbox.getCurrentGear();

            // RPM jump on downshift
            if (newGear > 0 && currentSpeed > 0) {
                // Formula: RPM = (Speed * Constant) / Gear
                int newRpm = (currentSpeed * 80) / newGear;

                this.engine.setRpm(newRpm);
                System.out.println("Downshifted: RPM jumps to " + newRpm);
            }
        } else {
            System.out.println("Press clutch to shift!");
        }
    }

    public int getSpeed() {
        if (!this.isEngineOn) return 0;
        if (this.getCurrentGear() == 0) return 0; // Engine doesn't drive wheels on neutral

        // Formula: Speed = (RPM * Gear) / 80
        int calculatedSpeed = (this.engine.getRpm() * this.getCurrentGear()) / 80;

        // Speed limiter (physics won't allow exceeding maxSpeed)
        return Math.min(calculatedSpeed, maxSpeed);
    }

    public void brake() {
        this.engine.brake();
    }

    // GETTERS 

    @Override
    public String toString() {
        return manufacturer + " " + model + " (" + plateNumber + ")";
    }

    public boolean isOn() { return this.isEngineOn; }
    public String getManufacturer() { return this.manufacturer; }
    public String getPlateNumber() { return this.plateNumber; }
    public int getWeight() { return this.weight; }
    public int getCurrentGear() { return this.gearbox.getCurrentGear(); }
    public String getModel() { return this.model; }
    public Engine getEngine() { return this.engine; }
    public Gearbox getGearbox() { return this.gearbox; }
    public boolean isClutchPressed() { return this.clutch.isPressed(); }
    public Clutch getClutch() { return this.clutch; }
    public double getCurrentVelocity() { return currentVelocity; }
    public void setCurrentVelocity(double velocity) { this.currentVelocity = velocity; }
    public double getXPosition() { return xPosition; }
    public void setXPosition(double x) { this.xPosition = x; }
    public double getYPosition() { return yPosition; }
    public void setYPosition(double y) { this.yPosition = y; }

}
