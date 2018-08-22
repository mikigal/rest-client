package pl.mikigal.restclient.data;

import pl.mikigal.restclient.enums.ArgumentType;

public class Argument {
    private final ArgumentType type;
    private final String name;
    private final Object value;

    public Argument(ArgumentType type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public ArgumentType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
