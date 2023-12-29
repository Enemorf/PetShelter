package com.jteam.GroupProject.model.animal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dog")
public class Dog extends Animal{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    private Boolean isHealthy;

    @Column
    private Boolean vaccinated;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "shelter_id")
    private Long shelterId;

    public Dog(String name, Integer age, Boolean isHealthy, Boolean vaccinated, Long shelterId) {
        this.name = name;
        this.age = age;
        this.isHealthy = isHealthy;
        this.vaccinated = vaccinated;
        this.shelterId = shelterId;
    }

    @Override
    public String toString() {
        return "Имя: " + name +
                ", Возраст: " + age +
                ", Состояние здоровья: " + (isHealthy ? "здоров" : "инвалид") +
                ", Вакцинация: " + (vaccinated ? "привит" : "не привит");
    }
}
