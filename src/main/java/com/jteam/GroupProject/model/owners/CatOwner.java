package com.jteam.GroupProject.model.owners;

import com.jteam.GroupProject.model.TrialPeriod;
import com.jteam.GroupProject.model.User;
import com.jteam.GroupProject.model.animal.Cat;
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
@DiscriminatorValue("cat")
public class CatOwner extends Owner {

    @OneToMany(mappedBy = "ownerId", fetch = FetchType.EAGER)
    private List<Cat> catList;

    public CatOwner(Long telegramId, String firstName, String lastName, String phone, List<Cat> catList, List<TrialPeriod> trialPeriodList) {
        super(telegramId, firstName, lastName, phone, trialPeriodList);
        this.catList = catList;
    }

    public CatOwner(Long id, Long telegramId, String firstName, String lastName, String phone, List<TrialPeriod> trialPeriodList, List<Cat> catList) {
        super(id, telegramId, firstName, lastName, phone, trialPeriodList);
        this.catList = catList;
    }

    public CatOwner(User user) {
        super(user);
    }
}
