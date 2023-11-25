package com.jteam.GroupProject.model.shelters;

import com.jteam.GroupProject.model.animal.Dog;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dog_shelter")
public class DogShelter {
    /**
     * id для приюта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Название приюта
     */
    @Column
    private String name;
    /**
     * Адрес и схема проезда
     */
    @Column
    private String location;
    /**
     * Расписание
     */
    @Column
    private String timetable;
    /**
     * О приюте
     */
    @Column(name = "about_me")
    private String aboutMe;
    /**
     * Список животных в приюте
     */
    @OneToMany(mappedBy = "shelterId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Dog> list;
    /**
     * Способ связи с охраной
     */
    @Column
    private String security;
    /**
     * Рекомендации о технике безопасности на территории приюта
     */
    @Column(name = "safety_advice")
    private String safetyAdvice;

    /**
     * Конструктор для POST в БД (Без id, без List)
     */
    public DogShelter(Long idShelter, String name, String location, String timetable, String aboutMe, String security, String safetyAdvice) {
        this.id = idShelter;
        this.name = name;
        this.location = location;
        this.timetable = timetable;
        this.aboutMe = aboutMe;
        this.security = security;
        this.safetyAdvice = safetyAdvice;
    }

    /**
     * Конструктор для PUT в БД (c id, без List)
     */
    public DogShelter(String name, String location, String timetable, String aboutMe, String security, String safetyAdvice) {
        this.name = name;
        this.location = location;
        this.timetable = timetable;
        this.aboutMe = aboutMe;
        this.security = security;
        this.safetyAdvice = safetyAdvice;
    }
}
