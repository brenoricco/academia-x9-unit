package br.com.controllers;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.entities.Training;

class TrainingControllerTest {
	TrainingController trainingController;
	UserController userController;
	ExerciseController exerciseController;
	
	@BeforeEach
	void init() {
		System.out.println("\n\nTeste Training Frequency Controller: \n=======INICIA METODO=========");
		trainingController = new TrainingController();
		userController = new UserController();
		exerciseController = new ExerciseController();
	}

	@Nested
	@DisplayName("Cenario001 - Testa TrainingController create")
	class testeTrainingControllerCreate {
		@Test
		@DisplayName("CT001.01 - Teste metodo create objeto null")
		void create01() {
			Training t1 = null;
			
			assertThrows(ObjectNotFoundException.class, () -> {
				trainingController.create(t1);
			});
		
		}
	}
 
	@AfterEach
	void end() {
		System.out.println("========FIM DO METODO=========");
	}

}
