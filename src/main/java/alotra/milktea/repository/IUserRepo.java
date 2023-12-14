package alotra.milktea.repository;

import alotra.milktea.entity.Role;
import alotra.milktea.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepo extends JpaRepository<User, String>{

	User findUserByUsername(String username);
	
//	@Query("SELECT u FROM User u WHERE (u.username = :username AND u.code = :code)")
	List<User> findUserByUsernameAndCode(@Param("username") String username,@Param("code") String code);
	
//	@Query("SELECT u FROM User u WHERE u.username = :email AND u.password = :password")
	List<User> findUserByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	List<User> findUserByEmail(@Param("email") String email);

	@Query("SELECT u FROM User u")
	List<User> findAll(PageRequest pageRequest);
	@Query("SELECT u.role FROM User u WHERE u.username = :username")
	List<Role> findRolesByUsername(String username);
}
