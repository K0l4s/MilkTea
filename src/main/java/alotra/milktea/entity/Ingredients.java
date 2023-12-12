package alotra.milktea.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="ingredients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredients implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;

	@Column(nullable = false)
	private short status;
}
