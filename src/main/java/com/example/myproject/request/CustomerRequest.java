package com.example.myproject.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String name;
    private String email;
    private String old_password;
    private String password;
    private String re_password;
    private String address;
    private String phone;

    public CustomerRequest(String name, String email, String password, String re_password, String address, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.re_password = re_password;
        this.address = address;
        this.phone = phone;
    }
}
