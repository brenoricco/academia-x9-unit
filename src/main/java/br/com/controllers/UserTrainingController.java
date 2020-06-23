package br.com.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.dao.UserDAO;
import br.com.entities.Training;
import br.com.entities.TrainingFrequency;
import br.com.entities.User;

public class UserTrainingController {

	UserDAO userDao = new UserDAO();
	TrainingFrequencyController frequencyController = new TrainingFrequencyController();

	public Training findUserDayTraining(User user) {
		Training training = null;

		if (user == null || user.getTrainingPlan() == null)
			return null;

		if (user.getLastTraining() == null) {
			user.setLastTraining(-1);
		}

		if (user.getLastTrainingDate() != null && user.getLastTrainingDate().equals(LocalDate.now())) {
			return user.getTrainingPlan().get(user.getLastTraining());
		}

		if (user.getLastTraining() == user.getTrainingPlan().size() - 1) {
			user.setLastTraining(-1);
		}

		int trainingOfDay = user.getLastTraining() + 1;
		training = user.getTrainingPlan().get(trainingOfDay);
		user.setLastTraining(trainingOfDay);
		user.setLastTrainingDate(LocalDate.now());

		TrainingFrequency trainingFrequency = new TrainingFrequency(null, user, LocalDateTime.now(), training);
		frequencyController.save(trainingFrequency);
		user.getTrainings().add(trainingFrequency);
		userDao.save(user);

		return training;
	}
	
	public Map<String, Integer> findSchedulesWithTheMostStudents(LocalDateTime startDate, LocalDateTime endDate) {
		
		List<TrainingFrequency> list = frequencyController.findAll()
				.stream().filter(t -> t.getDate().isAfter(startDate) &&
									  t.getDate().isBefore(endDate)).collect(Collectors.toList());
		
		Map<String, Integer> numberOfPeoplePerHour = new HashMap<>();
		
		//Hours AM
		int TwelveToOne_AM = 0, OneToTwo_AM = 0, TwoToThree_AM = 0, ThreeToFour_AM = 0, FourToFive_AM = 0, FiveToSix_AM = 0, SixToSeven_AM = 0, 
				SevenToEight_AM = 0, EightToNine_AM = 0, NineToTen_AM = 0, TenToEleven_AM = 0, ElevenToTwelve_AM = 0;
		
		//Hours PM
		int TwelveToOne_PM = 0, OneToTwo_PM = 0, TwoToThree_PM = 0, ThreeToFour_PM = 0, FourToFive_PM = 0, FiveToSix_PM = 0, SixToSeven_PM = 0, 
			SevenToEight_PM = 0, EightToNine_PM = 0, NineToTen_PM = 0, TenToEleven_PM = 0, ElevenToTwelve_PM = 0;
		
		for (TrainingFrequency t : list) {
			int hour = t.getDate().getHour();
			switch (hour) {
			//Hours AM
			case 0:
				TwelveToOne_AM++;
				numberOfPeoplePerHour.put("00:00 as 01:00", TwelveToOne_AM);
				break;
			case 1:
				OneToTwo_AM++;
				numberOfPeoplePerHour.put("01:00 as 02:00", OneToTwo_AM);
				break;
			case 2:
				TwoToThree_AM++;
				numberOfPeoplePerHour.put("02:00 as 03:00", TwoToThree_AM);
				break;
			case 3:
				ThreeToFour_AM++;
				numberOfPeoplePerHour.put("03:00 as 04:00", ThreeToFour_AM);
				break;
			case 4:
				FourToFive_AM++;
				numberOfPeoplePerHour.put("04:00 as 05:00", FourToFive_AM);
				break;
			case 5:
				FiveToSix_AM++;
				numberOfPeoplePerHour.put("05:00 as 06:00", FiveToSix_AM);
				break;
			case 6:
				SixToSeven_AM++;
				numberOfPeoplePerHour.put("06:00 as 07:00", SixToSeven_AM);
				break;
			case 7:
				SevenToEight_AM++;
				numberOfPeoplePerHour.put("07:00 as 08:00", SevenToEight_AM);
				break;
			case 8:
				EightToNine_AM++;
				numberOfPeoplePerHour.put("08:00 as 09:00", EightToNine_AM);
				break;
			case 9:
				NineToTen_AM++;
				numberOfPeoplePerHour.put("09:00 as 10:00", NineToTen_AM);
				break;
			case 10:
				TenToEleven_AM++;
				numberOfPeoplePerHour.put("10:00 as 11:00", TenToEleven_AM);
				break;
			case 11:
				ElevenToTwelve_AM++;
				numberOfPeoplePerHour.put("11:00 as 12:00", ElevenToTwelve_AM);
				break;
			//Hours PM	
			case 12:
				TwelveToOne_PM++;
				numberOfPeoplePerHour.put("12:00 as 13:00", TwelveToOne_PM);
				break;
			case 13:
				OneToTwo_PM++;
				numberOfPeoplePerHour.put("13:00 as 14:00", OneToTwo_PM);
				break;
			case 14:
				TwoToThree_PM++;
				numberOfPeoplePerHour.put("14:00 as 15:00", TwoToThree_PM);
				break;
			case 15:
				ThreeToFour_PM++;
				numberOfPeoplePerHour.put("15:00 as 16:00", ThreeToFour_PM);
				break;
			case 16:
				FourToFive_PM++;
				numberOfPeoplePerHour.put("16:00 as 17:00", FourToFive_PM);
				break;
			case 17:
				FiveToSix_PM++;
				numberOfPeoplePerHour.put("17:00 as 18:00", FiveToSix_PM);
				break;
			case 18:
				SixToSeven_PM++;
				numberOfPeoplePerHour.put("18:00 as 19:00", SixToSeven_PM);
				break;
			case 19:
				SevenToEight_PM++;
				numberOfPeoplePerHour.put("19:00 as 20:00", SevenToEight_PM);
				break;
			case 20:
				EightToNine_PM++;
				numberOfPeoplePerHour.put("20:00 as 21:00", EightToNine_PM);
				break;
			case 21:
				NineToTen_PM++;
				numberOfPeoplePerHour.put("21:00 as 22:00", NineToTen_PM);
				break;
			case 22:
				TenToEleven_PM++;
				numberOfPeoplePerHour.put("22:00 as 23:00", TenToEleven_PM);
				break;
			case 23:
				ElevenToTwelve_PM++;
				numberOfPeoplePerHour.put("23:00 as 24:00", ElevenToTwelve_PM);
				break;	
			default:
				break;
			}
		}
		
		return numberOfPeoplePerHour;
	}

}
