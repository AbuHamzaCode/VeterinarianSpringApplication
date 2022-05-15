package main.DAO;

import main.entities.User;
import main.entities.Pet;
import main.payload.request.OwnerRequest;
import main.payload.response.PetResponse;
import main.repository.UserRepository;
import main.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OwnerDAO {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    public List<User> getOwners() {
        return userRepository.findAll();
    }

    public List<PetResponse> getPets(){
        List<Pet> pets = petRepository.findAll();
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : pets){
            petResponses.add(new PetResponse(pet.getId(),
                    pet.getBreed(),
                    pet.getName(),
                    pet.getGender(),
                    pet.getAge(),
                    pet.getDescription(),
                    pet.getUser().getFullName()));
        }
        return petResponses;
    }

    public User getOwnerById(long id) {
        if (userRepository.existsById(id)) {
            return userRepository.getById(id);
        }
        return null;
    }

    public PetResponse addPet(long id, Pet pet) {
        User user = userRepository.getById(id);
        Pet newPet = new Pet(pet.getBreed(),
                pet.getName(),
                pet.getGender(),
                pet.getAge(),
                pet.getDescription(),
                user);
        newPet = petRepository.save(newPet);
        user.addPet(newPet);
        return new PetResponse(newPet.getId(),
                newPet.getBreed(),
                newPet.getName(),
                newPet.getGender(),
                newPet.getAge(),
                newPet.getDescription(),
                user.getFullName());
    }


    public long deleteOwnerById(long id) {
        if (!userRepository.existsById(id)) {
            return 0;
        }
        userRepository.deleteById(id);
        return id;
    }

    public long deletePetById(long id){
        if (!petRepository.existsById(id)){
            return 0;
        }
        petRepository.deleteById(id);
        return id;
    }



    public User updateOwner(long id, OwnerRequest newOwner) {
        User updatedUser = userRepository.getById(id);
        updatedUser.setFullName(newOwner.getFullName());
        updatedUser.setAddress(newOwner.getAddress());
        updatedUser.setPhone(newOwner.getPhone());
        updatedUser.setEmail(newOwner.getEmail());
        return updatedUser;
    }

    public PetResponse updatePet(long id, Pet newPet, String ownerName) {
        if (!petRepository.existsById(id)){
            return null;
        }
        Pet updatedPet = petRepository.getById(id);
        if (!updatedPet.getUser().getFullName().contains(ownerName)){
            return null;
        }
        updatedPet.setName(newPet.getName());
        updatedPet.setBreed(newPet.getBreed());
        updatedPet.setAge(newPet.getAge());
        updatedPet.setGender(newPet.getGender());
        updatedPet.setDescription(newPet.getDescription());
        return new PetResponse(updatedPet.getId(),
                updatedPet.getBreed(),
                updatedPet.getName(),
                updatedPet.getGender(),
                updatedPet.getAge(),
                updatedPet.getDescription(),
                updatedPet.getUser().getFullName());
    }

    public List<PetResponse> getPetsByOwnerName(String fullName) {
        if (fullName == null) {
            return null;
        }
        List<PetResponse> pets = new ArrayList<>();
        for (Pet pet : petRepository.findAll()) {
            if (pet.getUser().getFullName().contains(fullName)) {
                pets.add(new PetResponse(pet.getId(),
                        pet.getBreed(),
                        pet.getName(),
                        pet.getGender(),
                        pet.getAge(),
                        pet.getDescription(),
                        pet.getUser().getFullName()));
            }
        }
        return pets;
    }

}
