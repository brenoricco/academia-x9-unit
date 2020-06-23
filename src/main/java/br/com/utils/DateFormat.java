package br.com.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormat {
	
	public static LocalDate stringToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		LocalDate formatedDate = null;
		
		try {
			
			formatedDate = LocalDate.parse(date, formatter);
			
		} catch (DateTimeParseException e) {
			
			System.out.println("Erro ao formatar a data: " + e.getMessage() + ", favor inserir uma data válida como no exemplo: '01/01/2001'");
		}
		
		return formatedDate;
	}
}
