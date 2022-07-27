package com.app.projectstartup;

public class simpleClass {

    String name,address,city,area,email;
    Long mobile,pincode;
    int type;



    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public Long getMobile() {
        return mobile;
    }

    public Long getPincode() {
        return pincode;
    }

    public int getType() {
        return type;
    }

    public simpleClass(String name, String address, String city, String area, Long mobile, Long pincode, int type,String email) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.area = area;
        this.mobile = mobile;
        this.pincode = pincode;
        this.type = type;
        this.email=email;
    }
}
