package org.example.car;

public class Gearbox extends Component {
    private int currentGear;
    private final int maxGears;

    // Additional properties (hardcoded aesthetics)
    private String componentName;
    private double price;
    private double weight;

    public Gearbox(String manufacturer, String model, int maxGears, String componentName, double price, double weight) {
        super(manufacturer, model);
        this.maxGears = maxGears;
        this.componentName = componentName;
        this.price = price;
        this.weight = weight;
        this.currentGear = 0; // Start at neutral (0)
    }

    public void shiftUp() {
        if (this.currentGear == -1) {
            this.currentGear = 0;
            System.out.println("Neutral (N)");
        } else if (this.currentGear < this.maxGears) {
            this.currentGear++;
            System.out.println("Gear: " + this.currentGear);
        } else {
            System.out.println("Max gear reached!");
        }
    }

    public void shiftDown() {
        if (this.currentGear == 0) {
            // From Neutral to Reverse
            this.currentGear = -1;
            System.out.println("Reverse (R)");
        } else if (this.currentGear > 0) {
            // Normal downshift
            this.currentGear--;
            if (this.currentGear == 0) {
                System.out.println("Neutral (N)");
            } else {
                System.out.println("Gear: " + this.currentGear);
            }
        }
    }

    // GETTERS
    public String getComponentName() { return this.componentName; }
    public double getPrice() { return this.price; }
    public double getWeight() { return this.weight; }
    public int getCurrentGear() { return this.currentGear; }

}
