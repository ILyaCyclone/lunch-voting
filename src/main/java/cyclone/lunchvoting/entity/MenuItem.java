package cyclone.lunchvoting.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "menu")
@Getter
@Setter
@NoArgsConstructor
public class MenuItem extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurant", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "price", nullable = false)
    @NotNull
    private BigDecimal price;

    @Column(name = "date", nullable = false, columnDefinition = "DEFAULT CURRENT_DATE()")
    @NotNull
    private LocalDate date = LocalDate.now(); // should have default value so that JPA won't insert null

    public MenuItem(Integer id, String name, BigDecimal price, LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + super.getId() +
                ", name=" + super.getName() +
                ", price=" + price +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MenuItem menuItem = (MenuItem) o;
        return
                Objects.equals(super.getId(), menuItem.getId()) &&
                        Objects.equals(super.getName(), menuItem.getName()) &&
                        Objects.equals(price, menuItem.price) &&
                        Objects.equals(date, menuItem.date);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (super.getId() != null ? super.getId().hashCode() : 0);
        result = 31 * result + (super.getName() != null ? super.getName().hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
