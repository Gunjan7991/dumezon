package com.example.myproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //to create all the getters and setters
@AllArgsConstructor //to create over-rided constructor
@NoArgsConstructor  //to create default constructor

@Entity //denotes that the model is used to create a table
public class Customer {
    @Id //used to indicate the below is Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //used to tell the system to auto generate this part.
    private Integer customerId;
    @Column(nullable = false, length = 45)
    //nullable = False : means the field cannot be empty
    //length = Int : means that max amount character can be that Int
    private String customerName;
    @Column(nullable = false)
    private String customerAddress;
    @Column(nullable = false, length = 45, unique = true) // Unique: make sure the field remains unique in the table. repeated entry is not permitted.
    private String customerEmail;
    @Column(nullable = false, length = 60)
    private String customerPassword;
    @Column(nullable = false, length = 14)
    private String customerPhone;

}
