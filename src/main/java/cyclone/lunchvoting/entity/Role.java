package cyclone.lunchvoting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role extends AbstractNamedEntity {

    @Column(name = "description")
    @Transient // not fetched until explicitly called getDescription()
    private String description;
}