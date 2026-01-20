package org.example.car;

public class Engine extends Component {
    // Constants
    private final int idleRpm = 800;

    // Engine properties
    private final int maxRpm;
    private int currentRpm;

    // Additional properties
    private final String componentName;
    private double price;
    private double weight;

    public Engine(String manufacturer, String model, int maxRpm, String componentName, double price, double weight) {
        super(manufacturer, model);
        this.maxRpm = maxRpm;
        this.componentName = componentName;
        this.price = price;
        this.weight = weight;
        this.currentRpm = 0;
    }

    public void start() {
        if (this.currentRpm == 0) {
            // If engine was off, set to idle RPM
            this.currentRpm = idleRpm;
        }
    }

    public void stop() {
        this.currentRpm = 0;
    }

    // Accelerate (Gas)
    public void increaseRpm(int amount) {
        if (this.currentRpm == 0) {
            System.out.println("Engine is off!");
            return;
        }

        if (this.currentRpm + amount <= this.maxRpm) {
            this.currentRpm += amount;
        } else {
            // Set at max RPM
            this.currentRpm = this.maxRpm;
            System.out.println("Rev limiter hit!");
        }
    }

    // Decelerate (Let go of gas)
    public void decreaseRpm(int amount) {
        if (this.currentRpm - amount >= this.idleRpm) {
            this.currentRpm -= amount;
        } else {
            // Drop to IDLE instead of stalling (unless already off)
            if (this.currentRpm > 0) {
                this.currentRpm = this.idleRpm;
            }
        }
    }

    // Braking logic
    public void brake() {
        if (this.currentRpm > 0) {
            this.currentRpm -= 20; // Braking load on engine
            // If RPM drops too low, engine stalls (becomes 0)
            if (this.currentRpm < 0) {
                this.currentRpm = 0;
            }
        }
    }

    // SETTER
    public void setRpm(int setRpm) {
        if (setRpm < 0) this.currentRpm = 0;
        else this.currentRpm = Math.min(setRpm, maxRpm);
    }

    // GETTER
    public int getRpm() { return this.currentRpm; }
    public String getComponentName() { return this.componentName; }
    public double getPrice() { return this.price; }
    public double getWeight() { return this.weight; }
}
