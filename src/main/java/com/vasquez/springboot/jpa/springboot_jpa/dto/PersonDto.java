package com.vasquez.springboot.jpa.springboot_jpa.dto;

public class PersonDto {
    private String name;
    private String lastname;
    
    public PersonDto(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "[name=" + name + ", lastname=" + lastname + "]";
    }

    
}
