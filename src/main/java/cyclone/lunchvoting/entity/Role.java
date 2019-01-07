package cyclone.lunchvoting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role extends AbstractNamedEntity implements GrantedAuthority {

    @Column(name = "description")
    @Transient // not fetched until explicitly called getDescription()
    private String description;

    @Override
    public String getAuthority() {
        return super.getName();
    }
}