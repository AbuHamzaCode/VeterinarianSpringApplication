package main.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
@Table(name = "pets")
public class Pet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String breed;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    private String gender;

    @NotNull
    @Positive
    private Integer age;

    @NotBlank
    @Size(max = 250)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public Pet() {
    }

    public Pet(String breed, String name, String gender, Integer age, String description, User user) {
        this.breed = breed;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.description = description;
        this.user = user;
    }
}