package com.kruk.car;

public class Clutch extends Component {
    private boolean isPressed;
    private final String componentName;
    private final double price;
    private final double weight ;

    public Clutch(String manufacturer, String model, String componentName, double price, double weight) {
        // super() -> Calls parent constructor (Component)
        super(manufacturer, model);
        this.isPressed = false;
        this.componentName = componentName;
        this.price = price;
        this.weight = weight;
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
        return isPressed;
    }

}
