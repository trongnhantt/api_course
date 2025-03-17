package com.example.course.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "administrator")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Administrator {
    @Id
    @Column(name = "administrator_id", columnDefinition = "BIGINT")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long administratorId;

    @JsonIgnore
    @OneToOne(mappedBy = "administrator")
    private User user;

}
