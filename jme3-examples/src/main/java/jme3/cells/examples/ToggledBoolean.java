package jme3.cells.examples;

public class ToggledBoolean {

    private boolean value;

    public ToggledBoolean() {
        this(false);
    }

    public ToggledBoolean(boolean initialValue) {
        this.value = initialValue;
    }

    public boolean toggle() {
        return (value = !value);
    }
}
