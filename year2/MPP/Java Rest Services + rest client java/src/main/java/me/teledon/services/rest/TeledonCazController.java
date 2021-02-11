package me.teledon.services.rest;

import me.teledon.model.CazCaritabil;
import me.teledon.repository.CazuriDBRepository;
import me.teledon.repository.RepositoryExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/teledon/cazuri")
public class TeledonCazController {

    @Autowired
    private CazuriDBRepository cazuriDBRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<CazCaritabil> getAll() {return cazuriDBRepository.findAll();}

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(method = RequestMethod.POST)
    public CazCaritabil create(@RequestBody CazCaritabil caz){
        System.out.println("in create");
        System.out.println(caz.getDescription());
        System.out.println(caz);
        cazuriDBRepository.save(caz);
        return caz;

    }

    @RequestMapping(method = RequestMethod.PUT)
    public CazCaritabil update(@RequestBody CazCaritabil caz) {
        System.out.println("Update caz " + caz.getDescription());
        cazuriDBRepository.update(caz);
        return caz;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        System.out.println("delete caz " + id);
        try {
            cazuriDBRepository.delete(id);
            return new ResponseEntity<CazCaritabil>(HttpStatus.OK);
        }catch (RepositoryExeption ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){
        System.out.println("in get by id");
        CazCaritabil caz= cazuriDBRepository.get(id);
        System.out.println("se returneaza " + caz.getDescription());
        if (caz==null)
            return new ResponseEntity<String>("Caz not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<CazCaritabil>(caz, HttpStatus.OK);
    }


    @ExceptionHandler(RepositoryExeption.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String teledonError(RepositoryExeption ex){return ex.getMessage();}
}
