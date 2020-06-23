package br.com.views;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.hibernate.ObjectNotFoundException;

import br.com.controllers.ExerciseController;
import br.com.controllers.TrainingController;
import br.com.controllers.UserController;
import br.com.entities.Exercise;
import br.com.entities.Training;
import br.com.entities.User;

public class TrainingView {

	public TrainingView() {
		TrainingView.mainView();
	}

	private static void mainView() {

		Scanner sc = new Scanner(System.in);

		int op = -1;
		do {
			System.out.println("--------Treino<Principal>--------");
			System.out.println("[1]-Cadastrar");
			System.out.println("[2]-Alterar");
			System.out.println("[3]-Excluir");
			System.out.println("[4]-Buscar");
			System.out.println("[0]-Voltar");
			System.out.print("Escolha uma opção: ");
			op = sc.nextInt();

			System.out.println();

			switch (op) {
			case 1:
				TrainingView.registerView(sc);
				break;
			case 2:
				TrainingView.updateView(sc);
				break;
			case 3:
				TrainingView.deleteView(sc);
				break;
			case 4:
				TrainingView.findView(sc);
				break;
			default:
				break;
			}

		} while (op != 0);

	}

	private static void registerView(Scanner sc) {

		UserController userController = new UserController();
		ExerciseController exerciseController = new ExerciseController();
		TrainingController trainingController = new TrainingController();

		do {
			try {
				System.out.print("Insira o cpf do aluno: ");
				String cpf = sc.next();
				User user = userController.findOne(cpf);

				Training training = new Training(null, user);
				trainingController.create(training);
				do {
					List<Exercise> list = exerciseController.findAll();
					System.out.println("\n----- EXERCICIOS DISPONIVEIS ------");
					list.forEach(System.out::println);
					System.out.println("Qtd exercicios adicionados: " + training.getExercises().size());
					System.out.print("Informe o codigo do exercicio que deseja adicionar ou '0' para sair: ");
					int input = sc.nextInt();

					if (input == 0) {
						user.getTrainingPlan().add(training);
						break;
					}
					try {
						Exercise exercise = list.stream().filter(e -> e.getId() == input).findFirst().get();
						exercise.getTrainings().add(training);
						exerciseController.update(exercise);
						training.getExercises().add(exercise);
						System.out.println("\nEXERCICIOS ADICIONADOS:");
						training.getExercises().forEach(System.out::println);
					} catch (NoSuchElementException e) {
						System.out.println("Nenhum exercicio com o codigo informado.");
					}

				} while (true);

				trainingController.update(training);
				userController.update(user);

				System.out.println("Treino cadastrado com sucesso!!!");

				System.out.print("Deseja cadatrar mais treinos?\n[1]-SIM  [2]-NÃO:");
				int input = sc.nextInt();

				if (input == 2) {
					break;
				}

			} catch (ObjectNotFoundException e) {
				System.out.println(e.getMessage());
			}
		} while (true);

	}

	private static void updateView(Scanner sc) {

		TrainingController trainingController = new TrainingController();
		ExerciseController exerciseController = new ExerciseController();

		int op = -1;
		do {
			sc.nextLine();
			System.out.println("-------Treino <Alterar>--------\n");

			trainingController.findAll().forEach(System.out::println);

			System.out.print("\nDigite o codigo do treino: ");
			Integer id = sc.nextInt();
			sc.nextLine();
			Training training = trainingController.findOne(id);

			System.out.println(training);

			System.out.println("Alterando Exercicios do Treino <" + training.getId() + ">: ");

			training.getExercises().forEach(System.out::println);

			do {
				System.out.println("[1]-Adicionar um novo exercicio\n[2]-Remover um exercicio");
				System.out.print("Opcao Desejada: ");
				int input = sc.nextInt();

				if (input == 1) {

					System.out.print("Insira o codigo do exercicio que deseja adicionar: ");
					int cod = sc.nextInt();
					Exercise exercise = exerciseController.findOne(cod);
					training.getExercises().add(exercise);

				} else if (input == 2) {

					System.out.print("Insira o codigo do exercicio que deseja Remover: ");
					int cod = sc.nextInt();
					training.getExercises().removeIf(e -> e.getId() == cod);

				} else {
					System.out.println("Opção invalida@@@");
				}

				System.out.println("Novo Plano de Treino");
				System.out.println(training);

				System.out.println("Deseja fazer mais alguma alteração?[1]-Sim  [2]-Não: ");
				int cod = sc.nextInt();

				if (cod == 2)
					break;

			} while (true);

			System.out.print("\nDeseja salvar alterações?\n[1]-SIM [2]-NÃO: ");
			op = sc.nextInt();

			if (op == 1) {
				trainingController.update(training);
			}

			System.out.println("\n[1]-Efetuar nova alteração\n[2]-Voltar ao menu anterior");
			System.out.print("Escolha: ");
			op = sc.nextInt();

		} while (op != 2);
	}

	private static void deleteView(Scanner sc) {

		TrainingController trainingController = new TrainingController();

		int op = -1;
		do {
			sc.nextLine();
			System.out.println("--------Treino <Excluir>--------");

			System.out.print("\nDigite o codigo do treino: ");
			int cod = sc.nextInt();

			Training training = null;
			try {
				training = trainingController.findOne(cod);
			} catch (ObjectNotFoundException e) {
				System.out.println(e.getMessage());
			}

			training.getExercises().forEach(System.out::println);

			System.out.print("Deseja realmente excluir o treino?\n[1]-SIM  [2]-NAO: ");
			int input = sc.nextInt();

			if (input == 1) {
				trainingController.delete(cod);
				System.out.println("Excluido com sucesso!");
			}

			System.out.println("\n[1]-Efetuar nova exclusão\n[2]-Voltar ao menu anterior");
			System.out.print("Escolha: ");
			op = sc.nextInt();

		} while (op != 2);
	}

	private static void findView(Scanner sc) {
		TrainingController trainingController = new TrainingController();
		
		System.out.println("---- BUSCAR TREINO ----");
		
		while(true) {
			try {
				System.out.print("Informe o codigo do treino: ");
				int cod = sc.nextInt();
				Training training = trainingController.findOne(cod);
				System.out.println(training);
				break;
			}catch (ObjectNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}

}