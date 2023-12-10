package alotra.milktea.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	
//	@ManyToOne
//	@JoinColumn(name="roleID")
//	private Role roleID;
	
	private String citizenID;
	private String name;
	private String photo;
	
	@ManyToOne
	@JoinColumn(name="username")
	private User username;
	
	@ManyToOne
	@JoinColumn(name="shopID")
	private Shop shop;
}
