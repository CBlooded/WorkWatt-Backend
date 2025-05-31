package org.workwattbackend.user.activationHost;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "activation_host")
public class ActivationHostEntity {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
}
