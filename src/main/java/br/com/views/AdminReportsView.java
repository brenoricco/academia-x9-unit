package br.com.views;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.controllers.ExerciseController;
import br.com.controllers.UserController;
import br.com.controllers.UserTrainingController;
import br.com.entities.Exercise;
import br.com.entities.User;
import br.com.entities.enums.MuscleGroup;
import br.com.entities.enums.Profile;

public class AdminReportsView {

	public AdminReportsView() {
		AdminReportsView.mainView();
	}

	private static void mainView() {

		Scanner sc = new Scanner(System.in);

		int op = -1;
		do {
			System.out.println("--------Relatorios<Principal>--------");
			System.out.println("[1]-Relatorio de Alunos");
			System.out.println("[2]-Relatorio de Instrutores");
			System.out.println("[3]-Relatorio de Exercicios");
			System.out.println("[4]-Relatorio de Horarios Com Maior Fluxo de Alunos");
			System.out.println("[0]-Voltar");
			System.out.print("Escolha uma opção: ");
			op = sc.nextInt();

			System.out.println();

			switch (op) {
			case 1:
				AdminReportsView.studentsReportView(sc);
				break;
			case 2:
				AdminReportsView.instructorsReportView(sc);
				break;
			case 3:
				AdminReportsView.exercisesReportView(sc);
				break;
			case 4:
				AdminReportsView.frequencyReportView(sc);
				break;
			default:
				System.out.println("Opção invalida.");
				break;
			}

		} while (op != 0);

	}

	private static void frequencyReportView(Scanner sc) {
		UserTrainingController userTrainingController = new UserTrainingController();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		do {
			sc.nextLine();
			System.out.println("\nRelatório de horários com maior fluxo de alunos na academia: ");
			System.out.println("Informe um periodo: ");
			System.out.print("De (dd/MM/yyyy HH:mm): ");
			String startDateStr = sc.nextLine();
			LocalDateTime startDate = LocalDateTime.parse(startDateStr, formatter);
			System.out.print("Ate (dd/MM/yyyy HH:mm): ");
			String endDateStr = sc.nextLine();
			LocalDateTime endDate = LocalDateTime.parse(endDateStr, formatter);

			Map<String, Integer> list = userTrainingController.findSchedulesWithTheMostStudents(startDate, endDate);

			if (list.isEmpty()) {
				System.out.println("Nenhum registro encontrado nesse periodo.");
			} else {
				System.out.println("\nRelatório de fluxo no periodo de " + startDateStr + " ate " + endDateStr + "\n");
				System.out.println("    Horário     |   Quantidade de Alunos");
				list.forEach((k, v) -> System.out.println(k + "  |           " + v));
			}

			System.out.println("Deseja gerar outro relatório? [1]-SIM [2]-NAO");
			int input = sc.nextInt();

			if (input == 2)
				break;

		} while (true);
	}

	private static void exercisesReportView(Scanner sc) {

		ExerciseController exerciseController = new ExerciseController();

		do {
			System.out.println("---- Relatorio de Exercicios ----");
			System.out.println("[1]-Geral");
			System.out.println("[2]-Por Grupo");
			System.out.println("[0]-Voltar");
			System.out.print("Opção: ");
			int input = sc.nextInt();

			if (input == 0)
				break;

			switch (input) {
			case 1:
				List<Exercise> list = exerciseController.findAll();
				if (list.isEmpty()) {
					System.out.println("Nenhum exercicio cadastrado");
					break;
				}
				System.out.println("\n----- RELATORIO GERAL DE EXERCICIOS ------");
				list.forEach(System.out::println);
				System.out.println("\n                    TOTAL DE EXERCICIOS: " + list.size() + "\n");
				break;
			case 2:
				reportOfExercisesByGroup(sc);
				break;
			default:
				System.out.println("Opção invalida.");
				break;
			}
		} while (true);
	}

	private static void reportOfExercisesByGroup(Scanner sc) {
		ExerciseController exerciseController = new ExerciseController();

		do {
			System.out.println("Informe o grupo para buscar os exercicios:");
			System.out.println("[1]-BRAÇO \n[2]-PERNA \n[3]-COSTA \n[4]-OMBRO \n[5]-PEITO");
			System.out.print("Opcao: ");
			int op = sc.nextInt();
			MuscleGroup group = null;

			switch (op) {
			case 1:
				group = MuscleGroup.BRACO;
				break;
			case 2:
				group = MuscleGroup.PERNA;
				break;
			case 3:
				group = MuscleGroup.COSTA;
				break;
			case 4:
				group = MuscleGroup.OMBRO;
				break;
			case 5:
				group = MuscleGroup.PEITO;
				break;
			default:
				System.out.println("Opção invalida");
				break;
			}

			List<Exercise> list = new ArrayList<>();
			for (Exercise e : exerciseController.findAll()) {
				if (e.getGroup().equals(group)) {
					list.add(e);
				}
			}

			if (list.isEmpty()) {
				System.out.println("Nenhum exercicio cadastrado nesse grupo.");
			} else {
				System.out.println("RELATORIO DE EXERCICIOS DO GRUPO: " + group.toString());
				list.forEach(System.out::println);
			}

			System.out.print("Deseja efetuar mais buscas?\n[1]-SIM [2]-NAO: ");
			int input = sc.nextInt();

			if (input == 2) {
				break;
			}
		} while (true);
	}

	private static void studentsReportView(Scanner sc) {

		do {
			System.out.println("----- Relatorio de Alunos -----");
			System.out.println("[1]-Relatorio Geral");
			System.out.println("[2]-Relatorio de Alunos Aniversariantes do Mes");
			System.out.println("[3]-Relatorio de Alunos Ausentes");
			System.out.println("[4]-Relatorio de Alunos Matriculados Por Periodo:");
			System.out.println("[0]-Voltar");
			System.out.print("Opção: ");
			int input = sc.nextInt();

			if (input == 0)
				break;

			switch (input) {
			case 1:
				List<User> list = findAllUsersByProfile(Profile.STUDENT);
				System.out.println("\n----- RELATORIO GERAL DE ALUNOS ------");
				list.forEach(System.out::println);
				System.out.println("                    TOTAL DE ALUNOS: " + list.size() + "\n");
				break;
			case 2:
				reportOfStudentsBirthday();
				break;
			case 3:
				reportAbsentStudents(sc);
				break;
			case 4:
				reportNewStudents(sc);
				break;

			default:
				System.out.println("Opção invalida.");
				break;
			}

		} while (true);

	}

	private static void reportNewStudents(Scanner sc) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		System.out.println("\nRelatório alunos matriculados por periodo: ");
		System.out.println("Informe um periodo: ");
		System.out.print("De (dd/MM/yyyy HH:mm): ");
		String startDateStr = sc.nextLine();
		LocalDate startDate = LocalDate.parse(startDateStr, formatter);
		System.out.print("Ate (dd/MM/yyyy HH:mm): ");
		String endDateStr = sc.nextLine();
		LocalDate endDate = LocalDate.parse(endDateStr, formatter);

		List<User> list = findAllUsersByProfile(Profile.STUDENT).stream()
				.filter(u -> u.getRegisteredAt().isAfter(startDate) && u.getRegisteredAt().isBefore(endDate)).collect(Collectors.toList());

		if (list.isEmpty()) {
			System.out.println("Nenhum aluno novo nesse periodo.");
		}

		System.out.println("Alunos Novos Periodo de " + startDateStr + " a " + endDateStr + ": ");
		list.forEach(System.out::println);
		System.out.println("                       TOTAL DE ALUNOS: " + list.size());

	}

	private static List<User> findAllUsersByProfile(Profile profile) {
		UserController userController = new UserController();
		List<User> list = userController.findAll().stream().filter(u -> u.getProfile() == profile.getCod())
				.collect(Collectors.toList());
		return list;
	}

	private static void reportAbsentStudents(Scanner sc) {
		do {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			System.out.println("\nRelatório de Ausentes por periodo: ");
			System.out.println("Informe um periodo: ");
			
			System.out.print("De (dd/MM/yyyy HH:mm): ");
			String startDateStr = sc.nextLine();
			LocalDate startDate = LocalDate.parse(startDateStr, formatter);
			
			System.out.print("Ate (dd/MM/yyyy HH:mm): ");
			String endDateStr = sc.nextLine();
			LocalDate endDate = LocalDate.parse(endDateStr, formatter);
			
			List<User> activeStudents = findAllUsersByProfile(Profile.STUDENT).stream()
					.filter(u -> u.getLastTrainingDate().isAfter(startDate) && u.getLastTrainingDate().isBefore(endDate))
					.collect(Collectors.toList());

			if (activeStudents.isEmpty()) {
				System.out.println("Nenhum registro de aluno encontrado nesse periodo");
			} else {
				activeStudents.forEach(System.out::println);
			}

			System.out.print("Deseja efetuar outra busca?\n[1]-SIM [2]-NAO: ");
			int i = sc.nextInt();

			if (i == 2) {
				break;
			}
		} while (true);
	}

	private static void instructorsReportView(Scanner sc) {
		do {
			System.out.println("----- Relatorio de Instrutores -----");
			System.out.println("[1]-Geral");
			System.out.println("[2]-Aniversariantes do Mes");
			System.out.println("[0]-Voltar");
			System.out.print("Opção: ");
			int input = sc.nextInt();

			if (input == 0)
				break;

			switch (input) {
			case 1:
				List<User> list = findAllUsersByProfile(Profile.INSTRUCTOR);
				if (list.isEmpty()) {
					System.out.println("Nenhum instrutor cadastrado.");
					break;
				}
				list.forEach(System.out::println);
				break;
			case 2:
				reportOfInstructorsBirthday();
				break;
			case 3:
				reportAbsentStudents(sc);
				break;

			default:
				System.out.println("Opção invalida.");
				break;
			}
		} while (true);

	}

	private static void reportOfStudentsBirthday() {

		List<User> list = findAllUsersByProfile(Profile.STUDENT).stream()
				.filter(u -> u.getBirthDate().getMonth().equals(LocalDate.now().getMonth()))
				.collect(Collectors.toList());

		if (list.isEmpty()) {
			System.out.println("Nenhum aniveriante neste mês.");
		} else {
			System.out.println("---Alunos Aniversariantes do Mês---");
			list.forEach(System.out::println);
		}

	}

	private static void reportOfInstructorsBirthday() {
		System.out.println("---Instrutores Aniversariantes do Mês---");

		List<User> list = findAllUsersByProfile(Profile.INSTRUCTOR).stream()
				.filter(u -> u.getBirthDate().getMonth().equals(LocalDate.now().getMonth()))
				.collect(Collectors.toList());

		if (list.isEmpty()) {
			System.out.println("Nenhum aniveriante neste mês.");
		}

		list.forEach(System.out::println);
	}
}