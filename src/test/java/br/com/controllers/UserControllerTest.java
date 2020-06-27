package br.com.controllers;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import br.com.controllers.exceptions.InvalidInputDataException;
import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.entities.Phone;
import br.com.entities.User;
import br.com.entities.enums.Profile;
import br.com.utils.DateFormat;

class UserControllerTest {
	UserController userController;

	User u1 = null;
	User u2 = null;
	User u3 = null;
 
	@BeforeEach
	void init() {
		System.out.println("\n\nTeste Session Controller: \n=======INICIA METODO=========");
		userController = new UserController();
		User u1 = new User("Admin", "000000", "MASCULINO", "admin@gmail.com", "000000", "Centro, 100", DateFormat.stringToLocalDate("20/07/1995"), Profile.ADMIN);
        u1.getPhonesList().add(new Phone("99999999", u1));
        
        u2 = new User("Maria Silva", "111111", "FEMININO", "maria@gmail.com", "111111", "Centro, 100", DateFormat.stringToLocalDate("20/07/1995"), Profile.STUDENT);
        u2.getPhonesList().add(new Phone("9111111", u2));
        u2.setRegisteredAt(LocalDate.parse("2020-02-02"));
        
        User u3 = new User("Jose Silva", "222222", "MASCULINO", "jose@gmail.com", "222222", "Centro, 100", DateFormat.stringToLocalDate("20/07/1995"), Profile.STUDENT);
        u3.getPhonesList().add(new Phone("9111111", u3));
        u3.setRegisteredAt(LocalDate.parse("2020-02-02"));

	}

	@Nested
	@DisplayName("Cenario001 - Testa UserController create")
	class testeUserControllerCreate {
		@Test
		@DisplayName("CT001.01 - Teste metodo create com usuario nulo")
		void create01() {
			User u = null;
			assertThrows(ObjectNotFoundException.class, () -> {
				userController.create(u);
			});
		}
		
		@Test
		@DisplayName("CT001.02 - Teste metodo create com cpf nulo")
		void create02() {
			assertThrows(ObjectNotFoundException.class, () -> {
				u2.setCpf(null);
				userController.create(u2);
			});
		}
		
		@Test
		@DisplayName("CT001.03 - Teste metodo create com cpf existente")
		void create03() {
			assertThrows(ObjectNotFoundException.class, () -> {
				userController.create(u3);
			});
		}
	}
	
	@Nested
	@DisplayName("Cenario002 - Testa UserController findOne")
	class testeUserControllerFindOne {
		@Test
		@DisplayName("CT002.01 - Teste metodo findOne com cpf nulo")
		void findOne01() {
			
			assertThrows(InvalidInputDataException.class, () -> {
				userController.findOne(null);
			});
		}
	 	
		@Test
		@DisplayName("CT002.02 - Teste metodo findOne com cpf inexistente")
		void findOne02() {
			assertThrows(ObjectNotFoundException.class, () -> {
				userController.findOne("cpfinexistente");
			});
		}
		
		@Test
		@DisplayName("CT002.03 - Teste metodo findOne com cpf existente")
		void findOne03() {
			User user = userController.findOne("000000");
			User expected = new User("Admin", "000000", "MASCULINO", "admin@gmail.com", "000000", "Centro, 100", DateFormat.stringToLocalDate("20/07/1995"), Profile.ADMIN);
			expected.setId(1);
			assertEquals(expected, user);
		}
	}
	
	@Nested
	@DisplayName("Cenario003 - Testa UserController update")
	class testeUserControllerUpdate {
		@Test
		@DisplayName("CT003.01 - Teste metodo update com cpf nulo")
		void update01() {
			
			User u = userController.findOne("111111");
			u.setCpf(null);
			
			assertThrows(InvalidInputDataException.class, () -> {
				userController.update(u);
			});
		}
		
		@Test
		@DisplayName("CT003.02 - Teste metodo update com cpf inexistente")
		void update02() {
			User u = userController.findOne("111111");
			u.setCpf("cpfinexistente");
			
			assertThrows(ObjectNotFoundException.class, () -> {
				userController.update(u);
			});
		}
		
		@Test
		@DisplayName("CT003.03 - Teste metodo update com cpf existente")
		void update03() {
			String newEnd = "Novo endereco";
			
			User user = userController.findOne("111111");
			user.setAddress(newEnd);
			userController.update(user);
			User userExpected = userController.findOne("111111");
			
			assertEquals(userExpected.getAddress(), newEnd);
		}
	}
	
	@Nested
	@DisplayName("Cenario004 - Testa UserController delete")
	class testeUserControllerDelete {
		@Test
		@DisplayName("CT004.01 - Teste metodo delete com cpf nulo")
		void delete01() {
					
			assertThrows(InvalidInputDataException.class, () -> {
				userController.delete(null);
			});
		}
		
		@Test
		@DisplayName("CT004.02 - Teste metodo delete com cpf inexistente")
		void delete02() {

			assertThrows(ObjectNotFoundException.class, () -> {
				userController.delete("cpfinexistente");
			});
		}
		
		@Test
		@DisplayName("CT004.03 - Teste metodo delete com cpf existente")
		void delete03() {
			User userTest = new User("Jose Silva", "555555555", "MASCULINO", "jose@gmail.com", "555555555", "Centro, 100", DateFormat.stringToLocalDate("20/07/1995"), Profile.STUDENT);
			
			userController.create(userTest);
			
			userController.delete(userTest.getCpf());
			
			assertThrows(ObjectNotFoundException.class, () -> {
				userController.findOne(userTest.getCpf());
			});
		}
	}

	@AfterEach
	void end() {
		System.out.println("========FIM DO METODO=========");
	}

}
