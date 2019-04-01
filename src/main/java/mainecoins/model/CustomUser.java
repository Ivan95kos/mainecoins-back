package mainecoins.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Document("CustomUser")
public class CustomUser {

    @Field("Name")
    @NotBlank(message = "Name can not be empty")
    private String name;

    @Field("Age")
    @Min(value = 18, message = "Your age should be greater than or equal to 18")
    private int age;

    @Field("Password")
    @NotBlank(message = "Password can not be empty")
    private String password;

    @Field("Email")
    @Email(message = "Invalid e-mail address")
    private String email;

}
