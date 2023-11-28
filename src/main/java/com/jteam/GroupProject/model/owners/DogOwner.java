package com.jteam.GroupProject.model.owners;

import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.model.animal.Dog;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("dog")
public class DogOwner extends Owner {

    @OneToMany(mappedBy = "ownerId", fetch = FetchType.EAGER)
    private List<Dog> dogList;

    public DogOwner(Long telegramId, String firstName, String lastName, String phone, List<Dog> dogList, List<TrialPeriod> trialPeriodList) {
        super(telegramId, firstName, lastName, phone, trialPeriodList);
        this.dogList = dogList;
    }

    public DogOwner(Long id, Long telegramId, String firstName, String lastName, String phone, List<TrialPeriod> trialPeriodList, List<Dog> dogList) {
        super(id, telegramId, firstName, lastName, phone, trialPeriodList);
        this.dogList = dogList;
    }

    public DogOwner(User user) {
        super(user);
    }
}
