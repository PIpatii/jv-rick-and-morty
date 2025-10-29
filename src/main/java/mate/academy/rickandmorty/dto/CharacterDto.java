package mate.academy.rickandmorty.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDto {
    private Long id;
    private Long externalId;
    private String name;
    private String status;
    private String gender;
}
