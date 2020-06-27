package br.com.controllers;

import java.util.List;

import br.com.controllers.exceptions.ConstraintViolationException;
import br.com.controllers.exceptions.InvalidInputDataException;
import br.com.controllers.exceptions.ObjectAlreadyExistsException;
import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.dao.UserDAO;
import br.com.entities.User;

public class UserController {

	UserDAO userDAO = new UserDAO();
 
	public void create(User user) {

		if (user == null || user.getCpf() == null) {
			throw new ObjectNotFoundException("Insira os dados do usuário corretamente para poder efetuar o cadastro.");
		}

		User UserExists = userDAO.findOne(user.getCpf());

		if (UserExists != null) {
			throw new ObjectAlreadyExistsException("Já existe um usuário cadastrado com esse CPF.");
		}

		userDAO.save(user);
	}

	public List<User> findAll() {
		return userDAO.findAll();
	}

	public User findOne(String cpf) {
		if (cpf == null) {
			throw new InvalidInputDataException(
					"Insira os dados do usuário corretamente para poder efetuar o cadastro.");
		}

		User user = userDAO.findOne(cpf);

		if (user == null) {
			throw new ObjectNotFoundException("Nenhum usuário encontrado com esse cpf.");
		}

		return user;
	}

	public void update(User user) {
		if (user == null || user.getCpf() == null) {
			throw new InvalidInputDataException(
					"Insira os dados do usuário corretamente para poder efetuar o cadastro.");
		}

		User UserExists = userDAO.findOne(user.getCpf());

		if (UserExists == null) {
			throw new ObjectNotFoundException("Não existe um usuário cadastrado com esse CPF.");
		}

		userDAO.save(user);
	}

	public void delete(String cpf) {

		if (cpf == null) {
			throw new InvalidInputDataException(
					"Insira os dados do usuário corretamente para poder efetuar o cadastro.");
		}

		User user = userDAO.findOne(cpf);

		if (user == null) {
			throw new ObjectNotFoundException("Nenhum usuário encontrado com esse cpf.");
		}
		
		if (!(user.getTrainingPlan().isEmpty() || user.getTrainings().isEmpty())) {
			throw new ConstraintViolationException("Não e possivel deletar um usuário que possui entidades relacionadas a ele");
		}

		userDAO.remove(cpf);
	}
}
