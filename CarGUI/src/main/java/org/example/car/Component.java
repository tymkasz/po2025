package org.example.car;

/**
 * Abstract base class for all car parts (e.g., Engine, Gearbox).
 * Represents shared identification properties like manufacturer and model.
 */
public abstract class Component {
    private final String manufacturer;
    private final String model;

    /**
     * Base constructor.
     *
     * @param manufacturer The name of the component's manufacturer.
     * @param model        The name of the component's model.
     */
    public Component(String manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }
    public String getModel() {
        return model;
    }
    @Override
    public String toString() { return manufacturer + " " + model; }
}
