package br.com.views;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import application.localstorage.LocalStorage;
import br.com.controllers.SessionController;
import br.com.controllers.TrainingFrequencyController;
import br.com.controllers.UserController;
import br.com.controllers.UserTrainingController;
import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.entities.TrainingFrequency;
import br.com.entities.User;

public class UserTrainingView {

	public UserTrainingView() {
		UserTrainingView.mainView();
	}

	private static void mainView() {
		Scanner sc = new Scanner(System.in);
		int op = -1;
		do {
			System.out.println("--------Controle de Treinos--------");
			System.out.println("[1]-Imprimir treino do dia");
			System.out.println("[2]-Imprimir plano de treino");
			System.out.println("[3]-Relatorio treinos realizados por periodo");
			System.out.println("[0]-Voltar");
			System.out.print("Escolha uma opção: ");
			op = sc.nextInt();

			System.out.println();

			switch (op) {
			case 1:
				UserTrainingView.findTrainingOfDay(sc);
				break;
			case 2:
				UserTrainingView.findAllTrainingsView(sc);
				break;
			case 3:
				UserTrainingView.trainingReportByPeriod(sc);
				break;	
			default:
				System.out.println("Opção invalida!");
				break;
			}

		} while (op != 0);

	}

	private static void trainingReportByPeriod(Scanner sc) {
		
		TrainingFrequencyController trainingFrequencyController = new TrainingFrequencyController();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		
		do {
			System.out.println("--- Relatorio de treinos por periodo ---");
			
			System.out.println("\nRelatório de horários com maior fluxo de alunos na academia: ");
			System.out.println("Informe um periodo: ");
			System.out.print("De (dd/MM/yyyy HH:mm): ");
			String startDateStr = sc.nextLine();
			LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
			System.out.print("Ate (dd/MM/yyyy HH:mm): ");
			String endDateStr = sc.nextLine();
			LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);
			
			if(SessionController.isStudent()) {
				List<TrainingFrequency> list =  LocalStorage.user.getTrainings().stream()
					.filter(t -> t.getDate().isAfter(startDate) && 
								t.getDate().isBefore(endDate))
					.collect(Collectors.toList());
				
				if(list.isEmpty()) {
					System.out.println("\nNenhum treino realizado nesse periodo");
				} else {
					System.out.println("\nTreinos Realizados no Periodo de " + startDate + " a " + endDate + ": ");
					list.forEach(t -> System.out.println(t.getTraining()));
				}
			} else {
				trainingFrequencyController.findAll().forEach(System.out::println);
				
				List<TrainingFrequency> list = trainingFrequencyController.findAll().stream()
					.filter(t -> t.getDate().isAfter(startDate) && 
								t.getDate().isBefore(endDate))
					.collect(Collectors.toList());
				
				if(list.isEmpty()) {
					System.out.println("\nNenhum treino realizado nesse periodo");
				} else {
					System.out.println("\nTreinos Realizados no Periodo de " + startDate + " a " + endDate + ": ");
					list.forEach(t -> System.out.println(t.getTraining()));
				}
			}
			
			System.out.print("Deseja realizar mais alguma busca?\n [1]-SIM [2]-NAO: ");
			int input = sc.nextInt();
			
			if(input == 2) break;
			
		} while(true);
		
	}

	private static void findTrainingOfDay(Scanner sc) {

		UserController userController = new UserController();
		UserTrainingController userTrainingController = new UserTrainingController();

		User user = null;
		if (SessionController.isStudent()) {
			user = LocalStorage.user;
		} else {
			do {
				sc.nextLine();
				System.out.println("Insira o cpf do usuario para imprimir o treino do dia: ");
				try {
					String cpf = sc.next();
					user = userController.findOne(cpf);
					if (user.getTrainingPlan() == null) {
						System.out.println("O usuário do cpf informado não possui nenhum plano de treino");
					} else {
						break;
					}
				} catch (ObjectNotFoundException e) {
					System.out.println(e.getMessage());
				}
			} while (true);
		}
		
		if(user.getTrainingPlan().isEmpty()) {
			System.out.println("Nenhum treino cadastrado.");
		} else {
			System.out.println("\n------- Treino do dia ---------");
			System.out.println(userTrainingController.findUserDayTraining(user));
		}
	}

	private static void findAllTrainingsView(Scanner sc) {

		UserController userController = new UserController();

		User user = null;
		if (SessionController.isStudent()) {
			user = LocalStorage.user;
		} else {
			do {
				sc.nextLine();
				System.out.println("Insira o cpf do usuario para buscar seu plano de treino: ");
				try {
					String cpf = sc.next();
					user = userController.findOne(cpf);
					if (user.getTrainingPlan() == null) {
						System.out.println("O usuário do cpf informado não possui nenhum plano de treino");
					} else {
						break;
					}
				} catch (ObjectNotFoundException e) {
					System.out.println(e.getMessage());
				}
			} while (true);
		}
		
		if(user.getTrainingPlan().isEmpty()) {
			System.out.println("Plano de treino não cadastrado.");
		} else {
			System.out.println("\n------- Plano De Treino ---------");
			user.getTrainingPlan().forEach(System.out::println);
			System.out.println("\n              QUANTIDADE DE TREINOS: " + user.getTrainingPlan().size());
		}
	}

}