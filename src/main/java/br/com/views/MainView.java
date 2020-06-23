package br.com.views;

import java.util.Scanner;

import application.localstorage.LocalStorage;
import br.com.controllers.SessionController;
import br.com.entities.enums.Profile;

public class MainView {
	
	public MainView() {
		MainView.mainView();
	}

	private static void mainView() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("#### BEM VINDO AO GYM PASS FAKE ####");
		
		do {
			System.out.println("Perfil: " + Profile.toEnum(LocalStorage.user.getProfile()));
			System.out.println("-------- Menu Principal --------");
			System.out.println("[1]-Alunos");
			System.out.println("[2]-Instrutores");
			System.out.println("[3]-Exercicios");
			System.out.println("[4]-Treinos");
			System.out.println("[5]-Controle de Treinos");
			if(SessionController.isAdmin()) {
				System.out.println("[6]-Relatorios Administrativos");
				System.out.println("[7]-Administradores");
			}
			System.out.println("[0]-Sair");
			System.out.print("Escolha uma opção: ");
			int op = sc.nextInt();
			
			switch (op) {
			case 1:
				new UserView(Profile.STUDENT);
				break;
			case 2:
				if(SessionController.isStudent()) {
					System.out.println("\nSeu perfil não tem permissão para acessar esse módulo@@@@");
					break;
				}
				new UserView(Profile.INSTRUCTOR);
				break;
			case 3:
				if(SessionController.isStudent()) {
					System.out.println("\nSeu perfil não tem permissão para acessar esse módulo@@@@");
					break;
				}
				new ExerciseView();
				break;
			case 4:
				if(SessionController.isStudent()) {
					System.out.println("\nSeu perfil não tem permissão para acessar esse módulo@@@@");
					break;
				}
				new TrainingView();
				break;
			case 5:
				new UserTrainingView();
				break;
			case 6:
				if(!SessionController.isAdmin()) {
					System.out.println("\nDigite uma opção valida@@@");
					break;
				}
				new AdminReportsView();
				break;	
			case 7:
				if(!SessionController.isAdmin()) {
					System.out.println("\nDigite uma opção valida@@@");
					break;
				}
				new UserView(Profile.ADMIN);
				break;	
			default:
				break;
			}
			
			if(op == 0) {
				break;
			}
			
		} while(true);
		
		sc.close();
	}
}
