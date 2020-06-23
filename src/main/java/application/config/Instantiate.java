package application.config;

import java.time.LocalDate;

import br.com.dao.ExerciseDAO;
import br.com.dao.UserDAO;
import br.com.entities.Exercise;
import br.com.entities.Phone;
import br.com.entities.User;
import br.com.entities.enums.MuscleGroup;
import br.com.entities.enums.Profile;
import br.com.utils.DateFormat;

public class Instantiate {
	
	public static void main(String[] args) {
		
		UserDAO userDao = new UserDAO();
        User admin = new User("Admin", "000000", "MASCULINO", "admin@gmail.com", "000000", "Centro, 100", DateFormat.stringToLocalDate("20/07/1995"), Profile.ADMIN);
        admin.getPhonesList().add(new Phone("99999999", admin));
        userDao.save(admin);
        
        User user01 = new User("Maria Silva", "111111", "FEMININO", "maria@gmail.com", "111111", "Centro, 100", DateFormat.stringToLocalDate("20/07/1995"), Profile.STUDENT);
        user01.getPhonesList().add(new Phone("9111111", user01));
        user01.setRegisteredAt(LocalDate.parse("2020-02-02"));
        userDao.save(user01);
        
        User user02 = new User("Jose Silva", "222222", "MASCULINO", "jose@gmail.com", "222222", "Centro, 100", DateFormat.stringToLocalDate("20/07/1995"), Profile.STUDENT);
        user02.getPhonesList().add(new Phone("9111111", user02));
        user02.setRegisteredAt(LocalDate.parse("2020-02-02"));
        userDao.save(user02);
		
		ExerciseDAO exerciseDao = new ExerciseDAO();
		Exercise exe1 = new Exercise("Rosca Direta", MuscleGroup.BRACO);
		Exercise exe2 = new Exercise("Rosca inversa", MuscleGroup.BRACO);
		Exercise exe3 = new Exercise("Agachamento Livre", MuscleGroup.PERNA);
		Exercise exe4 = new Exercise("Passada Frontal", MuscleGroup.PERNA);
		
		exerciseDao.save(exe1);
		exerciseDao.save(exe2);
		exerciseDao.save(exe3);
		exerciseDao.save(exe4);
		
	}
}
