package br.com.views;

import java.time.LocalDate;
import java.util.Scanner;

import org.hibernate.ObjectNotFoundException;

import application.localstorage.LocalStorage;
import br.com.controllers.SessionController;
import br.com.controllers.UserController;
import br.com.controllers.exceptions.InvalidInputDataException;
import br.com.controllers.exceptions.ObjectAlreadyExistsException;
import br.com.entities.Phone;
import br.com.entities.User;
import br.com.entities.enums.Profile;
import br.com.utils.DateFormat;

public class UserView {

	public UserView(Profile profile) {
		mainView(profile);
	}

	public static void mainView(Profile profile) {
 
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Perfil: " + Profile.toEnum(LocalStorage.user.getProfile()));

			System.out.println("--------" + profileToStringTranslated(profile) + "<Principal>--------");
			System.out.println("[1]-Cadastrar");
			System.out.println("[2]-Alterar");
			System.out.println("[3]-Excluir");
			System.out.println("[4]-Buscar");
			System.out.println("[0]-Voltar");
			System.out.print("Escolha uma opção: ");
			int op = sc.nextInt();

			System.out.println();

			switch (op) {
			case 1:
				if(!SessionController.isAdmin()) {
					System.out.println("\nSeu perfil não tem permissão para acessar esse módulo@@@@");
					break;
				}
				UserView.registerView(sc, profile);
				break;
			case 2:
				if(profile == Profile.STUDENT && SessionController.isInstructor()) {
					System.out.println("\nSeu perfil não tem permissão para acessar esse módulo@@@@");
					break;
				}
				UserView.updateView(sc, profile);
				break;
			case 3:
				if(!SessionController.isAdmin()) {
					System.out.println("\nSeu perfil não tem permissão para acessar esse módulo@@@@");
					break;
				}
				UserView.deleteView(sc, profile);
				break;
			case 4:
				UserView.findView(sc, profile);
				break;
			default:
				break;
			}

			if (op == 0) {
				break;
			}

		} while (true);

		
	}

	public static String profileToStringTranslated(Profile profile) {
		String profileString;
		if (profile == Profile.ADMIN) {
			profileString = "Administrador";
		} else if (profile == Profile.INSTRUCTOR) {
			profileString = "Instrutor";
		} else {
			profileString = "Aluno";
		}
		return profileString;
	}

	private static void registerView(Scanner sc, Profile profile) {

		UserController userController = new UserController();

		int op = -1;
		do {
			sc.nextLine();
			System.out.println("--------" + profileToStringTranslated(profile) + "<Cadastrar>--------");
			System.out.print("Nome: ");
			String name = sc.nextLine();
			System.out.print("CPF: ");
			String cpf = sc.next();
			System.out.print("SEXO [1]-MASCULINO [2]-FEMININO: ");
			int input = sc.nextInt();
			String sex = input == 1 ? "MASCULINO" : "FEMININO";
			System.out.print("Email: ");
			String email = sc.next();
			System.out.print("Senha: ");
			String password = sc.next();
			sc.nextLine();
			System.out.print("Endereço completo: ");
			String address = sc.nextLine();

			LocalDate birthDate = null;

			do {
				System.out.print("Data de Nascimento(dd/MM/AAAA): ");
				birthDate = DateFormat.stringToLocalDate(sc.next());
			} while (birthDate == null);

			User user = new User(name, cpf, sex, email, password, address, birthDate, profile);

			// Cadastrando telefones do usuário
			do {
				System.out.print("Telefone: ");
				String number = sc.next();
				user.addPhone(new Phone(number, user));

				System.out.print("Deseja cadastrar mais numeros telefonicos?\n[1]-SIM [2]-NÃO: ");
				int i = sc.nextInt();

				if (i != 1) {
					break;
				}
			} while (true);

			System.out.print("\nDeseja salvar esse cadastro?\n[1]-SIM [2]-NÃO: ");
			op = sc.nextInt();

			if (op == 1) {

				do {
					try {
						userController.create(user);
						System.out.println("Cadastrado com sucesso!");
						break;
					} catch (ObjectAlreadyExistsException e) {
						System.out.println("Erro:" + e.getMessage());
						System.out.print("Insira outro cpf:");
						user.setCpf(sc.next());
					}
				} while (true);
			}

			System.out.println("\n[1]-Efetuar novo cadastro");
			System.out.println("[2]-Voltar ao menu anterior");
			op = sc.nextInt();

		} while (op != 2);
	}

	private static void updateView(Scanner sc, Profile profile) {

		UserController userController = new UserController();

		int op = -1;
		do {
			sc.nextLine();

			User user = null;
			if (SessionController.isStudent() || SessionController.isInstructor()) {
				user = LocalStorage.user;
			} else {
				do {
					System.out.println("--------" + profileToStringTranslated(profile) + "<Alterar>--------");
					System.out.print("Digite o CPF do " + profileToStringTranslated(profile) + ": ");
					String cpf = sc.nextLine();
					try {
						user = userController.findOne(cpf);
						if(Profile.toEnum(LocalStorage.user.getProfile()) != profile) {
							System.out.println("Nenhum " + profileToStringTranslated(profile) + "encontrado com esse cpf");
							continue;
						}
						break;
					} catch (ObjectNotFoundException e) {
						System.out.println("Erro: " + e.getMessage());
					}
				} while (true);
			}

			System.out.println(user);

			System.out.println(
					"Alterando dados do " + profileToStringTranslated(profile) + " <" + user.getName() + ">: ");
			System.out.print("Nome: ");
			user.setName(sc.nextLine());
			System.out.print("SEXO [1]-MASCULINO [2]-FEMININO: ");
			int input = sc.nextInt();
			String sex = input == 1 ? "MASCULINO" : "FEMININO";
			user.setSex(sex);
			System.out.print("Email: ");
			user.setEmail(sc.next());
			System.out.print("Password: ");
			user.setPassword(sc.next());
			sc.nextLine();
			System.out.print("Endereço completo: ");
			user.setAddress(sc.nextLine());

			// Alterando data nasc
			LocalDate birthDate = null;
			do {
				System.out.print("Data de Nascimento(dd/MM/AAAA): ");
				birthDate = DateFormat.stringToLocalDate(sc.next());
			} while (birthDate == null);

			user.setBirthDate(birthDate);

			System.out.print("Deseja alterar telefones?\n[1]-Sim [2]-Não:");
			op = sc.nextInt();
			if (op == 1) {
				do {
					System.out.println("Lista telefonica: ");
					user.getPhonesList().forEach(System.out::println);
					System.out.print("Codigo do telefone que deseja alterar: ");
					int id = sc.nextInt();
					System.out.println("Insira um novo numero: ");
					String newNumber = sc.next();
					for (Phone phone : user.getPhonesList()) {
						if (phone.getId() == id) {
							phone.setNumber(newNumber);
							System.out.println("Alteração feita com sucesso!");
						}
					}
					System.out.print("Deseja alterar mais numeros telefonicos?\n[1]-SIM [2]-NÃO");
					op = sc.nextInt();
				} while (op == 1);
			}

			System.out.println("Novos dados do "+profileToStringTranslated(profile)+": ");
			System.out.println(user);

			System.out.print("\nDeseja salvar alterações?\n[1]-SIM [2]-NÃO: ");
			op = sc.nextInt();

			if (op == 1) {
				do {
					try {
						userController.update(user);
						System.out.println("Atualizado com sucesso!");
						break;
					} catch (ObjectNotFoundException e) {
						System.out.println("Erro: " + e.getMessage());
						break;
					} catch (InvalidInputDataException e) {
						System.out.println("Erro: " + e.getMessage());
						break;
					}
				} while (true);
			}

			System.out.println("\n[1]-Efetuar nova alteração\n[2]-Voltar ao menu anterior");
			System.out.print("Escolha: ");
			op = sc.nextInt();

		} while (op != 2);
	}

	private static void deleteView(Scanner sc, Profile profile) {

		UserController userController = new UserController();

		int op = -1;
		do {
			sc.nextLine();
			System.out.println("--------"+profileToStringTranslated(profile)+" <Excluir>--------");
			System.out.print("Digite o CPF do "+profileToStringTranslated(profile)+": ");
			String cpf = sc.nextLine();
			User user = null;
			try {
				user = userController.findOne(cpf);
			} catch (ObjectNotFoundException e) {
				System.out.println("Erro: " + e.getMessage());
				continue;
			}

			System.out.print("\nDeseja realmente excluir o "+profileToStringTranslated(profile)+" <" + user.getName() + ">?\n[1]-SIM [2]-NÃO: ");
			op = sc.nextInt();

			if (op == 1) {
				userController.delete(cpf);
			}

			System.out.println("\n[1]-Efetuar nova exclusão\n[2]-Voltar ao menu anterior");
			System.out.print("Escolha: ");
			op = sc.nextInt();

		} while (op != 2);
	}

	private static void findView(Scanner sc, Profile profile) {

		UserController userController = new UserController();

		int op = -1;
		do {
			sc.nextLine();
			String cpf;
			System.out.println("--------"+profileToStringTranslated(profile)+" <Buscar>--------");
			if(SessionController.isStudent()) {
				cpf = LocalStorage.user.getCpf();
			} else {
				System.out.print("Digite o CPF do "+profileToStringTranslated(profile)+": ");
				cpf = sc.next();
			}
			try {
				User user = userController.findOne(cpf);
				if(Profile.toEnum(user.getProfile()) != profile) {
					System.out.println("Nenhum " + profileToStringTranslated(profile) + "encontrado com esse cpf");
					continue;
				} else {
					System.out.println(user);
				}
			} catch (ObjectNotFoundException e) {
				System.out.println("Erro: " + e.getMessage());
			}

			System.out.println("\n[1]-Efetuar nova busca\n[2]-Voltar ao menu anterior");
			System.out.print("Escolha: ");
			op = sc.nextInt();

		} while (op != 2);
	}

}