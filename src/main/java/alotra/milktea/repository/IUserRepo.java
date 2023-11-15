package alotra.milktea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import alotra.milktea.entity.User;

@Repository
public interface IUserRepo extends JpaRepository<User, String>{

	User findUserByUsername(String username);
	
//	@Query("SELECT u FROM User u WHERE (u.username = :username AND u.code = :code)")
	List<User> findUserByUsernameAndCode(@Param("username") String username,@Param("code") String code);
	
//	@Query("SELECT u FROM User u WHERE u.username = :email AND u.password = :password")
	List<User> findUserByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
}
