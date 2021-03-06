package cyclone.lunchvoting.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractNamedEntity extends AbstractBaseEntity {
    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
//    @SafeHtml // false positive on word "new"
    private String name;

    AbstractNamedEntity(Integer id, String name) {
        super(id);
        Assert.hasText(name, "Name must not empty!");
        this.name = name;
    }
}
