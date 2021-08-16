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
public class RelatedPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "related_id")
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String phone;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id")
    private Project project;
}
