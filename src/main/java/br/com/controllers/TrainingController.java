package br.com.controllers;

import java.util.List;

import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.dao.TrainingDAO;
import br.com.entities.Training;

public class TrainingController {

	TrainingDAO trainingDAO = new TrainingDAO();

	public void create(Training training) {

		if (training == null) {
			throw new ObjectNotFoundException("Insira os dados do exercicio corretamente para poder efetuar o cadastro.");
		}
		
		trainingDAO.save(training);
	}

	public List<Training> findAll() {
		return trainingDAO.findAll();
	}

	public Training findOne(Integer id) {
		Training training = trainingDAO.findOne(id);

		if (training == null) {
			throw new ObjectNotFoundException("Nenhum treino encontrado com esse codigo.");
		}

		return training;
	}

	public void update(Training training) {
		if (training == null) {
			throw new ObjectNotFoundException(
					"Insira os dados do treino corretamente para poder efetuar a alteração.");
		}
 
		Training TrainingPlanExists = trainingDAO.findOne(training.getId());

		if (TrainingPlanExists == null) {
			throw new ObjectNotFoundException("Não existe um treino cadastrado com esse id.");
		}

		trainingDAO.save(training);
	}

	public void delete(Integer id) {
		trainingDAO.remove(id);
	}
}
