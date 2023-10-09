package com.actionsMicroservice.domain.action;

import com.actionsMicroservice.dtos.ActionDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name="actions")
@Table(name="actions")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @Column(unique = true)
    private String formLink;
    private String description;
    @Lob
    private byte[] image;

    public Action(ActionDTO data) {
        this.title = data.title();
        this.description = data.description();
        this.formLink = data.formLink();
        this.image = data.image();
    }
}
