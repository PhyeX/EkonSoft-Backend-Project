package io.project.ekonsoft.Controller;

import io.project.ekonsoft.Models.*;
import io.project.ekonsoft.Repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("getAll/active")
    public ResponseEntity<List<Project>> getAllActives() {
        List<Project> projects = null;
        try {
            projects = projectRepository.findAll().stream().filter(Project::isActive).collect(Collectors.toList());
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }
        catch (Exception e){

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("getAll/passive")
    public ResponseEntity<List<Project>> getAllPassives() {
        List<Project> projects = null;
        try {
            projects = projectRepository.findAll().stream().filter( i -> !i.isActive() ).collect(Collectors.toList());
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

    @PutMapping("update/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid Project newProject,@PathVariable int id) {

        try {
            Project x = projectRepository.findById(id)
                    .map(project -> {
                        project.setName(newProject.getName());
                        project.setPlatforms(newProject.getPlatforms());
                        project.setDeadline(newProject.getDeadline());
                        project.setTechnologies(newProject.getTechnologies());
                        project.setDetails(newProject.getDetails());
                        project.getDomains().get(0).setDomainRegistration(newProject.getDomains().get(0).getDomainRegistration());
                        project.setActive(newProject.isActive());
                        // Mail Update
                        Mail mail = project.getMailDetails().get(0);
                        mail.setDescription(newProject.getMailDetails().get(0).getDescription());
                        mail.setServerRegistration(newProject.getMailDetails().get(0).getServerRegistration());
                        // Related Person Update
                        RelatedPerson relatedPerson = project.getRelatedPersons().get(0);
                        relatedPerson.setName(newProject.getRelatedPersons().get(0).getName());
                        relatedPerson.setPhone(newProject.getRelatedPersons().get(0).getPhone());
                        // Server Update
                        Server server = project.getServers().get(0);
                        Server newServer = newProject.getServers().get(0);
                        server.setAddress(newServer.getAddress());
                        server.setIp(newServer.getIp());
                        server.setUsername(newServer.getUsername());
                        server.setPanelAddress(newServer.getPanelAddress());
                        server.setPassword(newServer.getPassword());
                        // Webpanel Update
                        WebPanel webpanel = project.getWebPanels().get(0);
                        webpanel.setPassword(newProject.getWebPanels().get(0).getUsername());
                        webpanel.setUsername(newProject.getWebPanels().get(0).getPassword());
                        // Customer Update
                        Customer customer = project.getCustomers().get(0);
                        customer.setAddress(newProject.getCustomers().get(0).getAddress());
                        customer.setName(newProject.getCustomers().get(0).getName());


                        return projectRepository.save(project);
                    }).orElseGet(() -> {
                        newProject.setId(id);
                        return projectRepository.save(newProject);
                    });

                return new ResponseEntity<>("Successful", HttpStatus.OK);
            }
        catch (Exception e){
            return new ResponseEntity<>("Unsuccessful", HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("changeActivity/{id}")
    public ResponseEntity<String> updateActivity(@PathVariable int id) {
        try {
            Project project = projectRepository.findById(id).orElse(null);
            if( project != null ) {
                project.setActive(!project.isActive());
                projectRepository.saveAndFlush(project);
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