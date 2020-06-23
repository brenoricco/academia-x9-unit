package br.com.views;

import java.util.Scanner;

import br.com.controllers.ExerciseController;
import br.com.controllers.exceptions.ObjectNotFoundException;
import br.com.entities.Exercise;
import br.com.entities.enums.MuscleGroup;

public class ExerciseView {

	public ExerciseView() {
		ExerciseView.mainView();
	}

	private static void mainView() {

		Scanner sc = new Scanner(System.in);

		int op = -1;
		do {
			System.out.println("--------Exercicios <Principal>--------");
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
				ExerciseView.registerView(sc);
				break;
			case 2:
				ExerciseView.updateView(sc);
				break;
			case 3:
				ExerciseView.deleteView(sc);
				break;
			case 4:
				ExerciseView.findView(sc);
				break;
			default:
				System.out.println("Opcao Invalida!");
				break;
			}

		} while (op != 0);

	}

	private static void registerView(Scanner sc) {

		ExerciseController exerciseController = new ExerciseController();

		int op = -1;
		do {
			sc.nextLine();
			System.out.println("--------Exercicios <Cadastrar>--------");
			System.out.print("Titulo: ");
			String title = sc.nextLine();
			System.out.println("Grupo: ");
			System.out.print("[1]-BRAÇO \n[2]-PERNA \n[3]-COSTA \n[4]-OMBRO \n[5]-PEITO");
			System.out.print("Opção: ");
			op = sc.nextInt();
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

			System.out.print("\nDeseja salvar esse cadastro?\n[1]-SIM [2]-NÃO: ");
			op = sc.nextInt();

			if (op == 1) {
				exerciseController.create(new Exercise(title, group));
			}

			System.out.println("\n[1]-Efetuar novo cadastro");
			System.out.println("[2]-Voltar ao menu anterior");
			op = sc.nextInt();

		} while (op != 2);
	}

	private static void updateView(Scanner sc) {

		ExerciseController exerciseController = new ExerciseController();

		int op = -1;
		do {
			sc.nextLine();
			System.out.println("--------Exercicio <Alterar>--------\n");

			exerciseController.findAll().forEach(System.out::println);

			System.out.print("\nDigite o codigo do exercicio: ");
			Integer id = sc.nextInt();
			sc.nextLine();
			Exercise exercise = exerciseController.findOne(id);

			if (exercise == null) {
				continue;
			}

			System.out.println(exercise);

			System.out.println("Alterando dados do exercicio <" + exercise.getTitle() + ">: ");
			System.out.print("Titulo: ");
			exercise.setTitle(sc.nextLine());
			System.out.println("Grupo: ");
			System.out.print("[1]-BRAÇO \n[2]-PERNA \n[3]-COSTA \n[4]-OMBRO \n[5]-PEITO");
			op = sc.nextInt();
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
			exercise.setGroup(group);

			System.out.println("Novos dados do aluno: ");
			System.out.println(exercise);

			System.out.print("\nDeseja salvar alterações?\n[1]-SIM [2]-NÃO: ");
			op = sc.nextInt();

			if (op == 1) {
				exerciseController.update(exercise);
			}

			System.out.println("\n[1]-Efetuar nova alteração\n[2]-Voltar ao menu anterior");
			System.out.print("Escolha: ");
			op = sc.nextInt();

		} while (op != 2);
	}

	private static void deleteView(Scanner sc) {

		ExerciseController exerciseController = new ExerciseController();

		int op = -1;
		do {
			sc.nextLine();
			System.out.println("--------Exercicio <Excluir>--------");

			exerciseController.findAll().forEach(System.out::println);

			System.out.print("\nDigite codigo do exercicio: ");
			Integer id = sc.nextInt();

			Exercise exercise = exerciseController.findOne(id);

			if (exercise == null) {
				continue;
			}

			System.out.print(
					"\nDeseja realmente excluir o Exercicio <" + exercise.getTitle() + ">?\n[1]-SIM [2]-NÃO: ");
			op = sc.nextInt();

			if (op == 1) {
				if(!exercise.getTrainings().isEmpty()) {
					System.out.println("\n@@ Você não pode deletar um exercicio que possui treinos relacionados a ele. @@");
				} else {
					exerciseController.delete(id);
					System.out.println("Deletado com sucesso!");
				}
			}

			System.out.println("\n[1]-Efetuar nova exclusão\n[2]-Voltar ao menu anterior");
			System.out.print("Escolha: ");
			op = sc.nextInt();

		} while (op != 2);
	}

	private static void findView(Scanner sc) {

		ExerciseController exerciseController = new ExerciseController();

		int op = -1;
		do {
			sc.nextLine();
			System.out.println("--------Exercicio <Buscar>--------");
			Exercise exercise = null;

			System.out.println("[1]-Buscar pelo codigo \n[2]-Buscar pelo titulo \n[0]-Voltar");
			System.out.print("Opção desejada:");
			op = sc.nextInt();
			
			try {
				if (op == 1) {
					System.out.print("Digite o codigo do exercicio: ");
					Integer id = sc.nextInt();

					exercise = exerciseController.findOne(id);
				} else if (op == 2) {
					sc.nextLine();
					System.out.print("Digite o titulo do exercicio: ");
					String title = sc.nextLine();

					exercise = exerciseController.findOneByTitle(title);
				}else if(op == 0) {
					break;
				}else {
					System.out.println("Opção invalida.");
				}
			} catch (ObjectNotFoundException e) {
				System.out.println(e.getMessage());
			}

			if (exercise != null) {
				System.out.println("\nExercicio encontrado: ");
				System.out.println(exercise);
			}

			System.out.println("\n[1]-Efetuar nova busca\n[2]-Voltar ao menu anterior");
			System.out.print("Escolha: ");
			op = sc.nextInt();

		} while (op != 2);
	}

}