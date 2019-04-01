package mainecoins.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SingInDTO {

    @Email
    private String email;
    @NotBlank
    private String password;
}
