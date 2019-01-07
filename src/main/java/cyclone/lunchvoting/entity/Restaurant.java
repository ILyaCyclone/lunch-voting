package cyclone.lunchvoting.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("name")
    @JsonIgnoreProperties("restaurant")
    private List<MenuItem> menu;

    public Restaurant(String name) {
        this(null, name);
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public List<MenuItem> getMenu() {
        return Collections.unmodifiableList(menu);
    }


    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + super.getId() +
                ", name=" + super.getName()+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Restaurant restaurant = (Restaurant) o;
        return
                Objects.equals(super.getId(), restaurant.getId()) &&
                        Objects.equals(super.getName(), restaurant.getName());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (super.getId() != null ? super.getId().hashCode() : 0);
        result = 31 * result + (super.getName() != null ? super.getName().hashCode() : 0);
        return result;
    }


}
