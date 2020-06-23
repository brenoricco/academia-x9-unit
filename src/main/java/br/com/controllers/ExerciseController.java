package br.com.controllers;

import java.util.List;

import br.com.controllers.exceptions.ObjectAlreadyExistsException;
import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.dao.ExerciseDAO;
import br.com.entities.Exercise;

public class ExerciseController {

	ExerciseDAO exerciseDAO = new ExerciseDAO();

	public void create(Exercise exercise) {

		if (exercise == null) {
			throw new ObjectNotFoundException("Insira os dados do exercicio corretamente para poder efetuar o cadastro.");
		}

		Exercise gymExerciseExists = exerciseDAO.findOne(exercise.getId());

		if (gymExerciseExists != null) {
			throw new ObjectAlreadyExistsException("Já existe um exercicio cadastrado com esse titulo.");
		}
		
		exercise.setTitle(exercise.getTitle().toUpperCase());
		exerciseDAO.save(exercise);
	}

	public List<Exercise> findAll() {
		return exerciseDAO.findAll();
	}

	public Exercise findOne(Integer id) {
		Exercise GymExercise = exerciseDAO.findOne(id);

		if (GymExercise == null) {
			throw new ObjectNotFoundException("Nenhum exercicio encontrado com esse codigo.");
		}

		return GymExercise;
	}
	
	public Exercise findOneByTitle(String title) {
		Exercise GymExercise = exerciseDAO.findOneByTitle(title.toUpperCase());

		if (GymExercise == null) {
			throw new ObjectNotFoundException("Nenhum exercicio encontrado com esse titulo.");
		}

		return GymExercise;
	}

	public void update(Exercise exercise) {
		if (exercise == null) {
			throw new ObjectNotFoundException(
					"Insira os dados do exercicio corretamente para poder efetuar a alteração.");
		}

		Exercise exerciseExists = exerciseDAO.findOne(exercise.getId());

		if (exerciseExists == null) {
			throw new ObjectNotFoundException("Não existe um exercicio cadastrado com esse titulo.");
		}

		exerciseDAO.save(exercise);
	}

	public void delete(Integer id) {
		exerciseDAO.remove(id);
	}
}
