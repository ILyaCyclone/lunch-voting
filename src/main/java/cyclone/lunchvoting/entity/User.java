package cyclone.lunchvoting.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractNamedEntity {
    @Column(name = "email", nullable = false, unique = true)
    @Size(max = 100)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 5, max = 100)
    @JsonIgnore
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles"
            , joinColumns = @JoinColumn(name = "id_user")
            , inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")//, cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("date")
    private List<Vote> votes = new ArrayList<>();

    public User(Integer id, String name, String email, String password) {
        this(id, name, email, password, null);
    }

    public User(Integer id, String name, String email, String password, Collection<Role> roles) {
        super(id, name);
        Assert.hasText(email, "User email can't be empty");
        Assert.hasText(password, "User password can't be empty");
        this.email = email;
        this.password = password;
        this.roles = roles != null ? new HashSet(roles) : Collections.emptySet();
    }
}
