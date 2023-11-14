package alotra.milktea.model;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLoginModel {
	private String usernameOrEmail;
	@Length(min=3,max=60)
	private String password;
}
