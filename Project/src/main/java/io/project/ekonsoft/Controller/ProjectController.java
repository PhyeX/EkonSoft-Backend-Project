package io.project.ekonsoft.Controller;

import io.project.ekonsoft.Models.Project;
import io.project.ekonsoft.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/project/")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectRepository projectRepository;


    @PostMapping("save")
     public ResponseEntity<String> save(@RequestBody @Valid Project project) {

        try {
            projectRepository.save(project);
            return new ResponseEntity<>("Successful", HttpStatus.CREATED);
        }
        catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>("Unsuccessful", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Project>> getAll() {
        List<Project> projects = null;
        try {
            projects = projectRepository.findAll();
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }
        catch (Exception e){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        try {
            projectRepository.deleteById(id);
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        }
        catch (Exception e){
            log.info(e.getMessage());
            return new ResponseEntity<>("Unsuccessful", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update")
    public ResponseEntity<String> update(@RequestBody @Valid Project project) {

        try {
            int id = project.getId();
            Project isThereAny = projectRepository.findById(id).orElse(null);
            if( isThereAny != null ) {
                projectRepository.save(project);
                return new ResponseEntity<>("Successful", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Missing project id!", HttpStatus.BAD_REQUEST);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>("Unsuccessful", HttpStatus.BAD_REQUEST);
        }
    }


}