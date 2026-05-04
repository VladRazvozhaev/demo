package neostudy.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "name can't be null")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "email can't be null")
    @Column(nullable = false, unique = true)
    private String email;
}
