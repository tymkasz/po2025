package org.example.car;

public class Engine extends Component {
    // Engine properties
    private final int maxRpm;
    private int currentRpm;
    private final int power;

    // Additional properties
    private final String componentName;
    private double price;
    private double weight;

    public Engine(String manufacturer, String model, int maxRpm, int power, String componentName, double price, double weight) {
        super(manufacturer, model);
        this.maxRpm = maxRpm;
        this.power= power;
        this.componentName = componentName;
        this.price = price;
        this.weight = weight;
        this.currentRpm = 0;
    }

    public void start() {
        if (this.currentRpm == 0) currentRpm = 800;
    }

    public void stop() {
        currentRpm = 0;
    }

    // Accelerate (Gas)
    public void increaseRpm(int amount) {
        if (currentRpm > 0 && currentRpm + amount <= maxRpm) currentRpm += amount;
        else if (currentRpm > 0) currentRpm = maxRpm;
    }

    // Decelerate (Let go of gas)
    public void decreaseRpm(int amount) {
        if (currentRpm - amount >= 800) currentRpm -= amount;
        else if (currentRpm > 0) currentRpm = 800;
    }

    // Braking logic
    public void brake() {
        if (currentRpm > 0) currentRpm = Math.max(0, currentRpm - 50);
    }

    // GETTER
    public int getRpm() { return currentRpm; }
    public int getPower() { return power; }
    public String getComponentName() { return componentName; }
    public double getPrice() { return price; }
    public double getWeight() { return weight; }
}
