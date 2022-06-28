package ru.itis.m_social_test.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @NotBlank(message = "Поле username не может быть пустым")
    @Pattern(regexp = "^[a-zA-Z]*$",message = "Username может быть только на латинском")
    private String username;

    private String name;


}
