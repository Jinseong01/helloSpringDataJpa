package hw2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="roles")
@Getter
@Setter
@NoArgsConstructor
public class Role
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String rolename;

    @ManyToMany(mappedBy="roles")
    private List<Member> members;

    public Role(String rolename) {
        this.rolename = rolename;
    }
}
