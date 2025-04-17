package pl.cyrkoniowa.centrumdiety.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegistrationDto {

	@NotNull(message = "Pole login jest wymagane")
	@Size(min = 1, message = "Pole login jest wymagane")
	private String userName;

	@NotNull(message = "Pole hasło jest wymagane")
	@Size(min = 1, message = "Pole hasło jest wymagane")
	private String password;

	@NotNull(message = "Pole imię jest wymagane")
	@Size(min = 1, message = "Pole imię jest wymagane")
	private String firstName;

	@NotNull(message = "Pole nazwisko jest wymagane")
	@Size(min = 1, message = "Pole nazwisko jest wymagane")
	private String lastName;

	@NotNull(message = "Pole email jest wymagane")
	@Size(min = 1, message = "Pole email jest wymagane")
	@Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Niepoprawny adres email")
	private String email;

	public UserRegistrationDto() {

	}

}
