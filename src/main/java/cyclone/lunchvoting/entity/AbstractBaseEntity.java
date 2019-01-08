package cyclone.lunchvoting.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Base class to derive entity classes from.
 * https://github.com/spring-projects/spring-data-book/blob/master/jpa/src/main/java/com/oreilly/springdata/jpa/core/AbstractEntity.java
 */

@MappedSuperclass
@Data
@NoArgsConstructor
abstract class AbstractBaseEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO) // Sequence "HIBERNATE_SEQUENCE" not found
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    AbstractBaseEntity(Integer id) {
        this.id = id;
    }

//    @JsonIgnore
//    public boolean isNew() {
//        return id == null;
//    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractBaseEntity that = (AbstractBaseEntity)obj;

        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
