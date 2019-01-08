package cyclone.lunchvoting.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "user_votes")
@Getter
@Setter
@NoArgsConstructor
public class Vote extends AbstractBaseEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurant", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Vote(User user, Restaurant restaurant, LocalDate date) {
        super(null);
        this.user = user;
        this.restaurant = restaurant;
        this.date = date;
    }

}
