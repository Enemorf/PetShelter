package com.jteam.GroupProject.model.owners;

import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "owners")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "animal_type",
        discriminatorType = DiscriminatorType.STRING)
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long telegramId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ownerId")
    private List<TrialPeriod> trialPeriodList;

    public Owner(Long telegramId, String firstName, String lastName, String phone, List<TrialPeriod> trialPeriodList) {
        this.telegramId = telegramId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.trialPeriodList = trialPeriodList;
    }

    public Owner(User user) {
        this.setTelegramId(user.getTelegramId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPhone(user.getPhone());
    }
}
