package alotra.milktea.service;

import java.util.List;

import alotra.milktea.entity.User;
import alotra.milktea.model.UserLoginModel;

public interface IUserService {
	boolean register(User user);
	boolean login(UserLoginModel user);
	List<User> findAll();
	User findOne(String username);
	boolean vetifyUserCode(User user);
}
