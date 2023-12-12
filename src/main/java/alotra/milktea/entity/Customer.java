package alotra.milktea.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="customer")
public class Customer implements Serializable{
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerID;

    @NotNull
    @Length(min=5,max=50)
    private String customerName;

    @NotNull
    @OneToOne
    @JoinColumn(name="userName")
    private User user;

    @Length(max=10)
    private String phone;
}
