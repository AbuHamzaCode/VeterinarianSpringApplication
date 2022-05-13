package main.DAO;

import main.entities.User;
import main.entities.Pet;
import main.payload.request.OwnerRequest;
import main.repository.UserRepository;
import main.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerDAO {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    public List<User> getOwners() {
        return userRepository.findAll();
    }

    public List<Pet> getPets(){
        return petRepository.findAll();
    }

    public User getOwnerById(long id) {
        if (userRepository.existsById(id)) {
            return userRepository.getById(id);
        }
        return null;
    }

    public Pet addPet(long id, Pet pet) {
        User user = userRepository.getById(id);
        Pet newPet = new Pet(pet.getBreed(),
                pet.getName(),
                pet.getGender(),
                pet.getAge(),
                pet.getDescription(),
                user);
        newPet = petRepository.save(newPet);
        user.addPet(newPet);
        return newPet;
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

    public Pet updatePet(long id, Pet newPet) {
        if (!petRepository.existsById(id)){
            return null;
        }
        Pet updatedPet = petRepository.getById(id);
        updatedPet.setName(newPet.getName());
        updatedPet.setBreed(newPet.getBreed());
        updatedPet.setAge(newPet.getAge());
        updatedPet.setGender(newPet.getGender());
        updatedPet.setDescription(newPet.getDescription());
        return updatedPet;
    }

    public List<Pet> getPetsByOwnerName(String fullName) {
        if (fullName == null) {
            return null;
        }
        List<Pet> pets = new ArrayList<>();
        for (Pet pet : petRepository.findAll()) {
            if (pet.getUser().getFullName().contains(fullName)) {
                pets.add(pet);
            }
        }
        return pets;
    }

}
