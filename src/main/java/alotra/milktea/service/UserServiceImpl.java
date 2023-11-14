package alotra.milktea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alotra.milktea.entity.User;
import alotra.milktea.model.UserLoginModel;
import alotra.milktea.repository.IUserRepo;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements IUserService{
	@Autowired
	IUserRepo userRepo;
	
	@Override
	@Transactional
	public boolean register(User user) {
		try {
			userRepo.save(user);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean login(UserLoginModel user) {
		String emailOrUsername = user.getUsernameOrEmail();
		String password = user.getPassword();
//		List<User> listUser = userRepo.login(emailOrUsername, password);
//		if(listUser.isEmpty())
//			return false;
//		return true;
		return userRepo.login(emailOrUsername, password);
	}

	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}

	@Override
	public User findOne(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public boolean vetifyUserCode(User user) {
		String code = user.getCode();
		String email = user.getEmail();
		boolean flag = userRepo.findUserByEmailAndCode(email, code);
		return flag;
	}

}
