package main.restControllers;

import main.DAO.OwnerDAO;
import main.entities.User;
import main.entities.Pet;
import main.payload.response.PetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private OwnerDAO ownerDAO;

    //Worked
    @GetMapping("/users")
    public ResponseEntity<?> getOwners(){
        List<User> users = ownerDAO.getOwners();
        if (users.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/pets")
    public ResponseEntity<?> getPets(){
        List<PetResponse> pets = ownerDAO.getPets();
        if (pets.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    //Worked
    @DeleteMapping("/owner")
    public ResponseEntity<?> deleteOwnerById(@RequestParam("id") long id){
        long result = ownerDAO.deleteOwnerById(id);
        if (result == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Worked
    @DeleteMapping("/pet")
    public ResponseEntity<?> deletePetById(@RequestParam("id") long id){
        long result = ownerDAO.deletePetById(id);
        if (result == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //Worked
    @PostMapping("/pet")
    public ResponseEntity<?> addPet(@RequestParam("id") long ownerId, @Valid @RequestBody Pet request){
        if (ownerDAO.getOwnerById(ownerId) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        PetResponse pet = ownerDAO.addPet(ownerId, request);
        return new ResponseEntity<>(pet, HttpStatus.CREATED);
    }



}
