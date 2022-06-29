package ru.itis.m_social_test.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Поле email не может быть пустым")
    @Email(message = "Email должен быть валидным")
    private String email;

    @Column
    @NotBlank(message = "Поле username не может быть пустым")
    @Pattern(regexp = "^[a-zA-Z]*$", message = "Username может быть только на латинском")
    private String username;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "mov_id")
    )
    private List<Movie> movieList;
}
