package main.DAO;

import main.entities.User;
import main.entities.Pet;
import main.repository.PetRepository;

import java.util.List;

public class PetDAO {


    private PetRepository petRepository;

    public Pet getByName(String name){
        List<Pet> list = petRepository.findAll();
        for (Pet pet : list){
            if (pet.getName().contains(name)){
                return pet;
            }
        }
        return null;
    }

    public User getOwner(Pet pet){
        if (pet != null && petRepository.existsById(pet.getId())){
            return petRepository.getById(pet.getId()).getUser();
        }
        return null;
    }


}
