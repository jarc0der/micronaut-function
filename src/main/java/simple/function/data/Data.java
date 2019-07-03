package simple.function.data;

import io.micronaut.core.annotation.Introspected;

import java.io.Serializable;

@Introspected
public class Data implements Serializable {

    private String name;

    private long timestamp;

    private Role role;

    public Data() { }

    public Data(String name, long timestamp, Role role) {
        this.name = name;
        this.timestamp = timestamp;
        this.role = role;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

