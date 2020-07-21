package main.model;

public class Student {
    private String name;
    private int id;
    private String class_name;

    public Student(){

    }

    public Student(String name, int id, String class_name) {
        this.name = name;
        this.id = id;
        this.class_name = class_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
