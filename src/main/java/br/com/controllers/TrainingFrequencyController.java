package br.com.controllers;

import java.util.List;

import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.dao.TrainingFrequencyDAO;
import br.com.entities.TrainingFrequency;

public class TrainingFrequencyController {

	TrainingFrequencyDAO trainingFrequency = new TrainingFrequencyDAO();

	public void save(TrainingFrequency obj) {
 
		if (obj == null) {
			throw new ObjectNotFoundException("Insira os dados da frequencia de treino corretamente para poder efetuar o cadastro.");
		}
		
		trainingFrequency.save(obj);
	}

	public List<TrainingFrequency> findAll() {
		return trainingFrequency.findAll();
	}

	public TrainingFrequency findOne(Integer id) {
		TrainingFrequency UserTraining = trainingFrequency.findOne(id);

		if (UserTraining == null) {
			throw new ObjectNotFoundException("Nenhuma frequencia de treino encontrada com esse codigo.");
		}

		return UserTraining;
	}

	public void delete(Integer id) {
		trainingFrequency.remove(id);
	}
}
