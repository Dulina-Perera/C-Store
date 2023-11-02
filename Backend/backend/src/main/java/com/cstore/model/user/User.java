package com.cstore.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user", schema = "cstore")
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 15)
    private Role role;
}
