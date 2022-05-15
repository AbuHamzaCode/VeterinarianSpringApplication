package main.restControllers;

import main.DAO.OwnerDAO;
import main.entities.User;
import main.entities.Pet;
import main.payload.request.OwnerRequest;
import main.payload.response.PetResponse;
import main.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/in")
public class InController {

    @Autowired
    private OwnerDAO ownerDAO;

    //Worked
    @GetMapping("/user/pets")
    public ResponseEntity<?> getPetsByOwnerName(@RequestParam("name") String fullName, Authentication authentication){
        if (fullName == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<PetResponse> pets = ownerDAO.getPetsByOwnerName(userDetails.getFullName());
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    //Worked
    @GetMapping("/user/pet")
    public ResponseEntity<?> getPetByName(@RequestParam("name") String name, Authentication authentication){
        if (name == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<PetResponse> pets = ownerDAO.getPetsByOwnerName(userDetails.getFullName());

        if (pets == null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        PetResponse pet = pets.stream().filter(p -> p.getName().contains(name)).findFirst().get();
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }

    //Worked
    @PostMapping("/user/pet")
    public ResponseEntity<?> addPet(@Valid @RequestBody Pet request, Authentication authentication){
        if (request == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = ownerDAO.getOwnerById(userDetails.getId());
        PetResponse pet = ownerDAO.addPet(user.getId(), request);
        return new ResponseEntity<>(pet, HttpStatus.CREATED);
    }

    //Worked
    @PutMapping("/user/owner")
    public ResponseEntity<?> updateOwner(@Valid @RequestBody OwnerRequest request, Authentication authentication){
        if (request == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = ownerDAO.updateOwner(userDetails.getId(), request);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Worked
    @PutMapping("/user/pet")
    public ResponseEntity<?> updatePet(@RequestParam("id") long id,
                                       @Valid @RequestBody Pet request,
                                       Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        PetResponse pet = ownerDAO.updatePet(id, request, userDetails.getFullName());
        if (pet == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>(pet, HttpStatus.OK);
    }
}
