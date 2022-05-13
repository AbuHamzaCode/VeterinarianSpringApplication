package main.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 30)
    private String username;

    @NotBlank
    private String password;

    @Column(name = "full_name")
    @NotBlank
    @Size(min = 2, max = 150)
    private String fullName;

    @NotBlank
    @Size(max = 200)
    private String address;

    @NotBlank
    @Size(max = 15)
    private String phone;

    @NotBlank
    @Email
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Pet> petList = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username,
                String password,
                String fullName,
                String address,
                String phone,
                String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public void addPet(Pet pet){
        petList.add(pet);
    }
}
