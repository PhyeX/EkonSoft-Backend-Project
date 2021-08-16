package io.project.ekonsoft.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_id")
    private int id;

    @NotNull
    private String description;

    @NotNull
    private String address;

    @NotNull
    private String password;

    @NotNull
    private String serverRegistration;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id")
    private Project project;
}
