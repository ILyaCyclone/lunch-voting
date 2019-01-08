package cyclone.lunchvoting.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role extends AbstractNamedEntity implements GrantedAuthority {

    @Column(name = "description")
    @Transient // not fetched until explicitly called getDescription()
    private String description;

    @Override
    public String getAuthority() {
        return super.getName();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + super.getId()
                + ", name='" + super.getName() + '\'' +
                '}';
    }
}