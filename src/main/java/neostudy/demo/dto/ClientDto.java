package neostudy.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    @NotBlank(message = "name must not be empty")
    private String name;

    @Email(message = "email isn't correct")
    @NotBlank(message = "email can't be null")
    private String email;
}
