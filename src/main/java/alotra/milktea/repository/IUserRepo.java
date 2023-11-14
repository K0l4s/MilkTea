package alotra.milktea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import alotra.milktea.entity.User;

@Repository
public interface IUserRepo extends JpaRepository<User, String>{

	User findByUsername(String username);
	
	boolean findUserByEmailAndCode(String email, String code);
	
	@Query("SELECT u FROM User u WHERE (u.email = :email OR u.username = :email) AND u.password = :password")
	boolean login(@Param("email") String emailOrUsername,@Param("password") String password);
}
