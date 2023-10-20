package com.actionsMicroservice.domain.action;

import com.actionsMicroservice.dtos.ActionDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity(name="actions")
@Table(name="actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80)
    private String title;

    @Column(unique = true, length = 128)
    private String formLink;

    @Column(length = 4096)
    private String description;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB", length = 2621440)
    private String image;

    @Enumerated(EnumType.STRING)
    private ActionStatus status;

    @Column(columnDefinition = "TIMESTAMP")
    private Instant timestamp = Instant.now();

    @Column(length = 10)
    private Boolean isDeleted = Boolean.FALSE;

    public Action(ActionDTO data) {
        this.title = data.title();
        this.description = data.description();
        this.formLink = data.formLink();
        this.image = data.image();
        this.status = data.status();
    }
}
