package alotra.milktea.service;

import java.util.List;


import alotra.milktea.entity.User;

public interface IUserService {
	boolean register(User user);
	List<User> findAll();
	User findOne(String username);
	boolean vetifyUserCode(User user);
	boolean login(String username, String password);
}
