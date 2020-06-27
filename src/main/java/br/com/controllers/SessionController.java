package br.com.controllers;

import application.localstorage.LocalStorage;
import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.entities.User;
import br.com.entities.enums.Profile;

public class SessionController {

	private UserController userController = new UserController();
 
	public boolean validateLogin(String cpf, String password) {
		
		User user = null;
		
		try {
			user = userController.findOne(cpf);
		}catch (ObjectNotFoundException e) {
			return false;
		}
		
		if (user.getPassword().equals(password)) {
			LocalStorage.user = user;
			return true;
		} 
		
		return false;
	}

	public static boolean isStudent() {
		if (LocalStorage.user.getProfile() == Profile.STUDENT.getCod()) {
			return true;
		}
		return false;
	}

	public static boolean isInstructor() {
		if (LocalStorage.user.getProfile() == Profile.INSTRUCTOR.getCod()) {
			return true;
		}
		return false;
	}

	public static boolean isAdmin() {
		if (LocalStorage.user.getProfile() == Profile.ADMIN.getCod()) {
			return true;
		}
		return false;
	}

}
