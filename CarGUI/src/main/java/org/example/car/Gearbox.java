package org.example.car;

public class Gearbox extends Component {
    private int currentGear;
    private final int maxGears;

    private final Clutch clutch;

    // Additional properties (hardcoded aesthetics)
    private String componentName;
    private double price;
    private double weight;

    public Gearbox(String manufacturer, String model, int maxGears, Clutch clutch, String componentName, double price, double weight) {
        super(manufacturer, model);
        this.maxGears = maxGears;
        this.clutch = clutch;
        this.componentName = componentName;
        this.price = price;
        this.weight = weight;
        this.currentGear = 0; // Start at neutral (0)
    }

    public void shiftUp() throws CarException {
        if (!clutch.isPressed()) {
            throw new CarException("Cannot shift: Clutch is not pressed.");
        }
        if (currentGear < maxGears) {
            currentGear++;
        } else {
            throw new CarException("Cannot shift: Max gear reached.");
        }
    }

    public void shiftDown() throws CarException {
        if (!clutch.isPressed()) {
            throw new CarException("Cannot shift: Clutch is not pressed.");
        }
        if (currentGear > -1) {
            currentGear--;
        } else {
            throw new CarException("Cannot shift: Already in reverse.");
        }
    }

    // GETTERS
    public String getComponentName() { return this.componentName; }
    public double getPrice() { return this.price; }
    public double getWeight() { return this.weight; }
    public int getCurrentGear() { return this.currentGear; }
    public Clutch getClutch() { return this.clutch; }

}
