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
			throw new ObjectNotFoundException("Insira os dados do usu�rio corretamente para poder efetuar o cadastro.");
		}

		User UserExists = userDAO.findOne(user.getCpf());

		if (UserExists != null) {
			throw new ObjectAlreadyExistsException("J� existe um usu�rio cadastrado com esse CPF.");
		}

		userDAO.save(user);
	}

	public List<User> findAll() {
		return userDAO.findAll();
	}

	public User findOne(String cpf) {
		if (cpf == null) {
			throw new InvalidInputDataException(
					"Insira os dados do usu�rio corretamente para poder efetuar o cadastro.");
		}

		User user = userDAO.findOne(cpf);

		if (user == null) {
			throw new ObjectNotFoundException("Nenhum usu�rio encontrado com esse cpf.");
		}

		return user;
	}

	public void update(User user) {
		if (user == null || user.getCpf() == null) {
			throw new InvalidInputDataException(
					"Insira os dados do usu�rio corretamente para poder efetuar o cadastro.");
		}

		User UserExists = userDAO.findOne(user.getCpf());

		if (UserExists == null) {
			throw new ObjectNotFoundException("N�o existe um usu�rio cadastrado com esse CPF.");
		}

		userDAO.save(user);
	}

	public void delete(String cpf) {

		if (cpf == null) {
			throw new InvalidInputDataException(
					"Insira os dados do usu�rio corretamente para poder efetuar o cadastro.");
		}

		User user = userDAO.findOne(cpf);

		if (user == null) {
			throw new ObjectNotFoundException("Nenhum usu�rio encontrado com esse cpf.");
		}
		
		if (!(user.getTrainingPlan().isEmpty() || user.getTrainings().isEmpty())) {
			throw new ConstraintViolationException("N�o e possivel deletar um usu�rio que possui entidades relacionadas a ele");
		}

		userDAO.remove(cpf);
	}
}
