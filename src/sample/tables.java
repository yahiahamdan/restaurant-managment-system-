package sample;

public class tables {
    int id;
    String name;
    int capacity;

    public tables(int id, String name,int capacity) {
        this.id = id;
        this.name = name;
        this.capacity=capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
