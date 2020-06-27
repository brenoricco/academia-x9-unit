package br.com.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import br.com.controllers.exceptions.InvalidInputDataException;

class SessionControllerTest {
	SessionController sessionController;

	@BeforeEach
	void init() {
		System.out.println("\n\nTeste Session Controller: \n=======INICIA METODO=========");
		sessionController = new SessionController();
	}

	@Nested
	@DisplayName("Cenario001 - Testa SessionController validateLogin")
	class testeUserControllerCreate {
		@Test 
		@DisplayName("CT001.01 - Teste metodo validateLogin com senha invalida")
		void validateLogin01() {
			
			assertFalse(sessionController.validateLogin("111111", "senhainvalida"));
		}
		
		@Test
		@DisplayName("CT001.02 - Teste metodo validateLogin com dados validos")
		void validateLogin02() {
			
			assertTrue(sessionController.validateLogin("111111", "111111"));
		}
		
		@Test
		@DisplayName("CT001.03 - Teste metodo validateLogin com cpf ou senha nulo")
		void validateLogin03() {
			
			assertThrows(InvalidInputDataException.class, () -> {
				sessionController.validateLogin(null, "111111");
				sessionController.validateLogin("111111", null);
			});
		}
	}

	@AfterEach
	void end() {
		System.out.println("========FIM DO METODO=========");
	}

}
