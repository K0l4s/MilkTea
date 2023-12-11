package alotra.milktea.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employee")
public class Employee implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeID;
	
	@ManyToOne
	@JoinColumn(name="roleID")
	private Role roleID;
	
	private String citizenID;
	private String name;
	private String photo;
	
	@ManyToOne
	@JoinColumn(name="username")
	private User username;
	
	@ManyToOne
	@JoinColumn(name="shopID")
	private Shop shop;

	@Column(nullable = false)
	private short status;
}
