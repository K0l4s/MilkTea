package alotra.milktea.service;

import alotra.milktea.entity.User;
import alotra.milktea.model.ResetPasswordModel;
import alotra.milktea.model.SendCodeModel;

import java.util.List;

public interface IUserService {
	boolean register(User user);
	List<User> findAll();

	List<User> findAll(int start, int pageSize);

	Long countAll();

	User findOne(String username);
	boolean vetifyUserCode(User user);
	boolean login(String username, String password);

	User findByEmail(String email);

	boolean sendRequestPassCode(SendCodeModel sendCodeModel);

	boolean resetPassw(ResetPasswordModel resetModel);
}
