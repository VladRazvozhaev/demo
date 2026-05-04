package neostudy.demo.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDto {
    private UUID id;
    private String name;
    private String email;
}
