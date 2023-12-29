package com.jteam.GroupProject.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column
    private Long telegramId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @Column
    private String shelterType;

    @Column
    private String shelterName;

    public User(Long telegramId, String firstName, String lastName, String phone) {
        this.telegramId = telegramId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
}
