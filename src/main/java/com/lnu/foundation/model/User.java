package com.lnu.foundation.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private
    @NonNull
    String username;
    private String email;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;
    private Double lat;
    @Column(name = "long")
    private Double longitude;


}
