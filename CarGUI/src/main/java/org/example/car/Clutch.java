package org.example.car;

public class Clutch extends Component {
    private boolean isPressed;
    // Additional properties (hardcoded for now, or moved to constructor later)
    private final String componentName = "Sport Clutch";
    private final double price = 2500.0;
    private final double weight = 15.0;

    public Clutch(String producent, String model) {
        // super() -> Calls parent constructor (Component)
        super(producent, model);
        this.isPressed = false;
    }

    // LOGIC

    public void press() {
        this.isPressed = true;
        System.out.println("Clutch pressed");
    }

    public void release() {
        this.isPressed = false;
        System.out.println("Clutch released");
    }

    // GETTERS

    public String getComponentName() { return componentName; }
    public double getPrice() { return price; }
    public double getWeight() { return weight; }
    public boolean isPressed() {
        return this.isPressed;
    }

}
