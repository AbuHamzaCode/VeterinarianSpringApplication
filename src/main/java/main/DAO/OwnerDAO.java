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

/**
 *  OwnerDAO - class for work with repository CRUD methods
 */

@Service
@Transactional
public class OwnerDAO {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    public List<User> getOwners() {
        List<User> users = userRepository.findAll();
        users.removeIf(user -> user.getId().compareTo(1L) == 0);
        return users;
    }

    public List<PetResponse> getPets(){
        List<Pet> pets = petRepository.findAll();
        List<PetResponse> petResponses = new ArrayList<>();
        for (Pet pet : pets){
            petResponses.add(new PetResponse(
                    pet.getId(),
                    pet.getBreed(),
                    pet.getName(),
                    pet.getGender(),
                    pet.getAge(),
                    pet.getDescription(),
                    pet.getUser().getFullName()
            ));
        }
        return petResponses;
    }

    public User getOwnerById(long id) {
        if (userRepository.existsById(id)) {
            return userRepository.getById(id);
        }
        return null;
    }

    public boolean deleteAllPets(String fullName){
       List<Pet> pets = petRepository.findAll();
       if (pets.isEmpty()){
           return false;
       }
       for (Pet pet : pets){
           if (pet.getUser().getFullName().contains(fullName)){
               petRepository.delete(pet);
           }
       }
       return true;
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

    public Pet updatePet(long id, Pet newPet, String ownerName) {
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

    public Pet getPetById(long id, String fullName){
        if (petRepository.existsById(id)){
            Pet pet = petRepository.getById(id);
            if (pet.getUser().getFullName().contains(fullName)){
                return pet;
            }
        }
        return null;
    }

}
