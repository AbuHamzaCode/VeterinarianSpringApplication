package main.payload.response;

public class PetResponse {
    private Long id;
    private String breed;
    private String name;
    private String gender;
    private String age;
    private String description;
    private String ownerName;

    public PetResponse(Long id,
                       String breed,
                       String name,
                       String gender,
                       String age,
                       String description,
                       String ownerName) {
        this.id = id;
        this.breed = breed;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.description = description;
        this.ownerName = ownerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
