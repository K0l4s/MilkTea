package alotra.milktea.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alotra.milktea.entity.User;
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
//			Kiểm tra trùng lặp username
			List<User> listUser = findAll();
			for(User item:listUser)
				if(item.getUsername().equals(user.getUsername()))
						return false;
//			Nếu không lặp username thì lưu user mới
			userRepo.save(user);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	@Override
	public boolean login(String username, String password) {
//		String username = user.getUsername();
//		String password = user.getPassword();
//		return userRepo.login(username, password);
		List<User> listUser = userRepo.findUserByUsernameAndPassword(username,password);
		if(listUser.isEmpty())
			return false;
		return true;
	}

	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}

	@Override
	public User findOne(String username) {
		return userRepo.findUserByUsername(username);
	}

	@Override
	public boolean vetifyUserCode(User user) {
		String code = user.getCode();
		String username = user.getUsername();
//		boolean flag = userRepo.findUserByUsernameAndCode(username, code);
		List<User> listUser = userRepo.findUserByUsernameAndCode(username, code);
		if(listUser.isEmpty())
			return false;
		User updateUser = findOne(username);
		updateUser.setCode("Vetify");
		userRepo.save(updateUser);
		return true;
	}

}
