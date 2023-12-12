package alotra.milktea.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String username;
	
	private String password;
	
	@Column(length = 60)
	private String email;
	
	@Column(length=6)
	private String code;

	private boolean isEnable;

	@ManyToOne
	@JoinColumn(name="roleID")
	private Role role;

	@OneToOne(mappedBy = "user")
	private Customer customer;
}
