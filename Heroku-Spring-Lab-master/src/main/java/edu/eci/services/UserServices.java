package edu.eci.services;

import edu.eci.models.User;
import edu.eci.persistences.repositories.IUserRepository;
import edu.eci.services.contracts.IUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserServices implements IUserServices {

	@Autowired
	@Qualifier("UserMemoryRepository")
	private IUserRepository userRepository;

	@Override
	public List<User> list() {
		return userRepository.findAll();
	}

	@Override
	public User create(User user) {
		if (null == user.getId())
			throw new RuntimeException("Id invalid");
		else if (userRepository.find(user.getId()) != null)
			throw new RuntimeException("The user exists");
		else
			userRepository.save(user);
		return user;
	}

	@Override
	public User get(UUID id) {
		return userRepository.find(id);
	}

	@Override
	public User get(String name) {
		return userRepository.getUserByUserName(name);
	}

	@Override
	public void updateUser(User u) {
		List<User> users = list();
		for (User U : users) {
			if (U.getName().equals(u.getName()) || U.getId().equals(u.getId())) {
				U.setName(u.getName());
				U.setId(u.getId());
			}
		}
	}
	@Override
	public void delete(UUID u) {

		userRepository.remove(Long.parseLong(u.toString()));

	}
}
