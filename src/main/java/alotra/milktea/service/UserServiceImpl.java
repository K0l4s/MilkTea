package alotra.milktea.service;

import alotra.milktea.entity.Role;
import alotra.milktea.entity.User;
import alotra.milktea.model.ResetPasswordModel;
import alotra.milktea.model.SendCodeModel;
import alotra.milktea.repository.IRoleRepo;
import alotra.milktea.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService{
	@Autowired
	IUserRepo userRepo;
	@Autowired
	Email email = new Email();
	@Autowired
	IRoleRepo roleRepo;
	
	@Override
//	@Transactional
	public boolean register(User user) {
//		try {
//			Kiểm tra trùng lặp username

			List<User> listUser = findAll();
			for(User item:listUser)
				if(item.getUsername().equals(user.getUsername()))
						return false;
			user.setEnable(false);
			Optional<Role> role = roleRepo.findById(3);
			if(user.getRole() == null)
				user.setRole(role.get());
//			Nếu không lặp username thì lưu user mới
			userRepo.save(user);
			return true;
//		} catch(Exception e) {
//			return false;
//		}
	}

	@Override
	public boolean login(String username, String password) {
//		String username = user.getUsername();
//		String password = user.getPassword();
//		return userRepo.login(username, password);
		try {
			List<User> listUser = userRepo.findUserByUsernameAndPassword(username, password);
			User user = listUser.get(0);
			if (user.isEnable() == false)
				return false;
			if (listUser.isEmpty())
				return false;
			return true;
		} catch(Exception ex){
			return false;
		}
	}

	@Override
	public User findByEmail(String email) {
		List<User> listUser = userRepo.findUserByEmail(email);
		User user = listUser.get(0);
//		return userRepo.findUserByEmail(email);
		return user;
	}

	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}
	@Override
	public List<User> findAll(int start, int pageSize){
		PageRequest pageRequest = PageRequest.of(start,pageSize);
		return userRepo.findAll(pageRequest);
	}
	@Override
	public Long countAll(){
		return userRepo.count();
	}
	@Override
	public User findOne(String username) {
		return userRepo.findUserByUsername(username);
	}

	@Override
	public boolean vetifyUserCode(User user) {
		String code = user.getCode();
		String username = user.getUsername();
		if(user.getCode().isEmpty())
			return false;
//		boolean flag = userRepo.findUserByUsernameAndCode(username, code);
		List<User> listUser = userRepo.findUserByUsernameAndCode(username, code);
		if(listUser.isEmpty())
			return false;
		User updateUser = findOne(username);
//		updateUser.setCode("Vetify");
		updateUser.setCode(null);
		updateUser.setEnable(true);
		userRepo.save(updateUser);
		return true;
	}
	@Override
	public boolean sendRequestPassCode(SendCodeModel sendCodeModel){
		User user = findByEmail(sendCodeModel.getEmail());
		if(user.getUsername() == null)
			return false;
		user.setCode(email.getRandom());
		userRepo.save(user);
		return true;
	}

	@Override
	public boolean resetPassw(ResetPasswordModel resetModel){
		User user = findByEmail(resetModel.getEmail());
		if (user.getUsername() == null
		|| !resetModel.getNewPassword().equals(resetModel.getRepeatPassword())
		|| !user.isEnable())
			return false;
		user.setPassword(resetModel.getNewPassword());
		user.setCode(null);
		userRepo.save(user);
		return true;
	}

	@Override
	public boolean delete(String username){
		try{
			userRepo.deleteById(username);
			return true;
		}catch (Exception ex){
			return false;
		}
	}

	@Override
	public boolean enableAccount(String username){
		try{
			User user = findOne(username);
			if(user.isEnable())
				user.setEnable(false);
			else
				user.setEnable(true);
			userRepo.save(user);
			return true;
		}
		catch (Exception ex){
			return false;
		}
	}
	@Override
	public boolean updateUser(User user){
		try{
			userRepo.save(user);
			return true;
		} catch(Exception exx){
			return false;
		}
	}
}
