package main.restControllers;

import main.DAO.OwnerDAO;
import main.entities.User;
import main.entities.Pet;
import main.payload.request.OwnerRequest;
import main.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for user role
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/in")
public class InController {

    @Autowired
    private OwnerDAO ownerDAO;

    @GetMapping("/user/pets")
    public ResponseEntity<?> getPets(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Pet> pets = ownerDAO.getPetsByOwnerName(userDetails.getFullName());
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }


    @GetMapping("/user/pet")
    public ResponseEntity<?> getPetByName(@RequestParam("name") String name, Authentication authentication) {
        if (name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Pet> pets = ownerDAO.getPetsByOwnerName(userDetails.getFullName());

        if (pets == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        Pet pet = pets.stream().filter(p -> p.getName().contains(name)).findFirst().get();
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }


    @PostMapping("/user/pet")
    public ResponseEntity<?> addPet(@Valid @RequestBody Pet request, Authentication authentication) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = ownerDAO.getOwnerById(userDetails.getId());
        Pet pet = ownerDAO.addPet(user.getId(), request);
        return new ResponseEntity<>(pet, HttpStatus.CREATED);
    }


    @PutMapping("/user/owner")
    public ResponseEntity<?> updateOwner(@Valid @RequestBody OwnerRequest request, Authentication authentication) {
        if (request == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = ownerDAO.updateOwner(userDetails.getId(), request);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("/user/pet")
    public ResponseEntity<?> updatePet(@RequestParam("id") long id,
                                       @Valid @RequestBody Pet request,
                                       Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Pet pet = ownerDAO.updatePet(id, request, userDetails.getFullName());
        if (pet == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    @DeleteMapping("/user/pet")
    public ResponseEntity<?> deletePetById(@RequestParam("id") long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<Pet> pets = ownerDAO.getPetsByOwnerName(userDetails.getFullName());
        for (Pet pet : pets) {
            if (pet.getId().equals(id)) {
                ownerDAO.deletePetById(id);
                return new ResponseEntity<>(id, HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @DeleteMapping("/user/pets")
    public ResponseEntity<?> deleteAll(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        boolean result = ownerDAO.deleteAllPets(userDetails.getFullName());
        if (result){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
