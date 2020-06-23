package application;

import java.util.Scanner;

import br.com.controllers.SessionController;
import br.com.views.MainView;

public class Program {

	public static void main(String[] args) {
		// Iniciando Sistema.
		Scanner sc = new Scanner(System.in);

		SessionController loginController = new SessionController();

		int op = -1;
		do {
			System.out.println("-------- Login --------");
			System.out.print("CPF:");
			String cpf = sc.next();
			System.out.print("Senha: ");
			String password = sc.next();

			if (loginController.validateLogin(cpf, password)) {
				new MainView();
				break;
			} else {
				System.out.println("Usuario ou senha invalido");
			}

		} while (op != 0);

		System.out.println("\n\n#### ENCERRANDO SISTEMA ####");
		sc.close();
	}

}
