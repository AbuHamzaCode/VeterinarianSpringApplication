package main.restControllers;

import main.DAO.OwnerDAO;
import main.entities.User;
import main.entities.Pet;
import main.payload.response.PetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *   REST Controller for admin role
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OwnerDAO ownerDAO;


    @GetMapping("/owners")
    public ResponseEntity<?> getOwners(){
        List<User> users = ownerDAO.getOwners();
        if (users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/pets")
    public ResponseEntity<?> getPets(){
        List<Pet> pets = ownerDAO.getPets();
        if (pets.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
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
        return new ResponseEntity<>(petResponses, HttpStatus.OK);
    }


    @DeleteMapping("/owner")
    public ResponseEntity<?> deleteOwnerById(@RequestParam("id") long id){
        long result = ownerDAO.deleteOwnerById(id);
        if (result == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @DeleteMapping("/pet")
    public ResponseEntity<?> deletePetById(@RequestParam("id") long id){
        long result = ownerDAO.deletePetById(id);
        if (result == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/pet")
    public ResponseEntity<?> addPet(@RequestParam("id") long ownerId, @Valid @RequestBody Pet request){
        if (ownerDAO.getOwnerById(ownerId) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        PetResponse pet = ownerDAO.addPet(ownerId, request);
        return new ResponseEntity<>(pet, HttpStatus.CREATED);
    }



}
