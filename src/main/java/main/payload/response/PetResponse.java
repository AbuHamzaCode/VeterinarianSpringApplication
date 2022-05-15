package main.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PetResponse {

    private Long id;
    private String breed;
    private String name;
    private String gender;
    private Integer age;
    private String description;
    private String ownerName;
}
