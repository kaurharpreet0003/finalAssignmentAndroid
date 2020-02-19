package com.example.phonebookassignment;

public class PersonClass {
    int id;
    String first_name, last_name, address;
    int phone;

//    public PersonClass(int id, String first_name, String last_name, String address, int phone) {
//        this.id = id;
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.address = address;
//        this.phone = phone;
//    }

    public PersonClass(String string, String string1, int anInt, String string2) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPhone() {
        return phone;
    }

}
