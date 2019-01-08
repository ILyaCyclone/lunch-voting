package cyclone.lunchvoting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Data
@NoArgsConstructor
abstract class AbstractNamedEntity extends AbstractBaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank
    @SafeHtml
    @Size(min = 2, max = 120)
    private String name;

    AbstractNamedEntity(Integer id, String name) {
        super(id);
        Assert.hasText(name, "Name must not be null or empty!");
        this.name = name;
    }
}
