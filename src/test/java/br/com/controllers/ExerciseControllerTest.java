package br.com.controllers;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import br.com.controllers.exceptions.InvalidInputDataException;
import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.entities.Exercise;
import br.com.entities.enums.MuscleGroup;

class ExerciseControllerTest {
	ExerciseController exerciseController;

	Exercise e1 = null;
	Exercise e2 = null;
	Exercise e3 = null;

	@BeforeEach
	void init() {
		System.out.println("\n\nTeste Session Controller: \n=======INICIA METODO=========");
		exerciseController = new ExerciseController();
		e1 = new Exercise("Crossover", MuscleGroup.PEITO);
		e2 = new Exercise("Supino Reto", MuscleGroup.PEITO);
		e3 = new Exercise("Supine Declinado", MuscleGroup.PEITO);
	}

	@Nested
	@DisplayName("Cenario001 - Testa ExerciseController create")
	class testeExerciseControllerCreate {
		@Test
		@DisplayName("CT001.01 - Teste metodo create com titulo nulo")
		void create01() {
			e1.setTitle(null);
			assertThrows(ObjectNotFoundException.class, () -> {
				exerciseController.create(e1);
			}); 			
		}
		
		@Test
		@DisplayName("CT001.02 - Teste metodo create novo Exercicio")
		void create02() {
			exerciseController.create(new Exercise("Rosca alternada", MuscleGroup.BRACO));
			Exercise exercise = exerciseController.findOneByTitle("Rosca alternada");
			assertEquals("Rosca alternada".toUpperCase(), exercise.getTitle());
			if(exercise != null) {
				exerciseController.delete(exercise.getId());
			}
		}
		
	}
	
	@Nested
	@DisplayName("Cenario002 - Testa ExerciseController findOne")
	class testeExerciseControllerFindOne {
		@Test
		@DisplayName("CT002.01 - Teste metodo findOne com id nulo")
		void findOne01() {
			
			assertThrows(InvalidInputDataException.class, () -> {
				exerciseController.findOne(null);
			});
		}
		
		@Test
		@DisplayName("CT002.02 - Teste metodo findOne com id inexistente")
		void findOne02() {
			assertThrows(ObjectNotFoundException.class, () -> {
				exerciseController.findOne(999999999);
			});
		}
		
		@Test
		@DisplayName("CT002.03 - Teste metodo findOne com id existente")
		void findOne03() {
			Exercise expected = exerciseController.findAll().get(1);
			
			Exercise actual = exerciseController.findOne(expected.getId());
					
			assertEquals(expected, actual);
		}
	}
	
	@Nested
	@DisplayName("Cenario003 - Testa ExerciseController update")
	class testeExerciseControllerUpdate {
		@Test
		@DisplayName("CT003.01 - Teste metodo update com id nulo")
		void update01() {
			
			Exercise e = exerciseController.findAll().get(1);
			e.setId(null);
			assertThrows(ObjectNotFoundException.class, () -> {
				exerciseController.update(e);
			});
		}
		
		@Test
		@DisplayName("CT003.02 - Teste metodo update com exercicio null")
		void update02() {
			
			assertThrows(ObjectNotFoundException.class, () -> {
				exerciseController.update(null);
			});
		}
		
		@Test
		@DisplayName("CT003.03 - Teste metodo update valido")
		void update03() {
			Exercise expected = exerciseController.findAll().get(1);
			expected.setTitle("Teste");
			exerciseController.update(expected);
			
			Exercise actual = exerciseController.findOne(expected.getId());
			
			assertEquals(expected, actual);
			
		}
	}
	
	@Nested
	@DisplayName("Cenario004 - Testa ExerciseController delete")
	class testeExerciseControllerDelete {
		@Test
		@DisplayName("CT004.01 - Teste metodo delete id null")
		void delete01() {
					
			assertThrows(InvalidInputDataException.class, () -> {
				exerciseController.delete(null);
			});
		}
		
		@Test
		@DisplayName("CT004.02 - Teste metodo delete id inexistente")
		void delete02() {
					
			assertThrows(ObjectNotFoundException.class, () -> {
				exerciseController.delete(9999999);
			});
		}
	}
	
	@AfterEach
	void end() {
		System.out.println("========FIM DO METODO=========");
	}

}
