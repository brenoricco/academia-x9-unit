package br.com.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import application.localstorage.LocalStorage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_TRAINING")
public class Training {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "TB_TRAINING_EXERCISE", 
				joinColumns = {	@JoinColumn(name = "TRAINING_ID") },
				inverseJoinColumns = { @JoinColumn(name = "EXERCISE_ID") })
	private List<Exercise> exercises = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column
	private LocalDate createdAt;
		
	@Column(name = "author_id")
	private Integer author;
	
	@OneToOne(mappedBy = "training")
	private TrainingFrequency trainingFrequency;
	
	public Training(Integer id, User user) {
		this.id = id;
		this.user = user;
		this.author = LocalStorage.user.getId();
		this.createdAt = LocalDate.now();
	}
	
	@Override
	public String toString() {
		
		String exercisesList = "\n";
		
		for (Exercise e : exercises) {
			exercisesList = exercisesList + "   Codigo: "+ e.getId() + "   Titulo: " + e.getTitle() + "   Grupo: " + e.getGroup() + "\n";
		}
		
		return "Codigo do treino: " + id + "    Aluno: " + user.getName() +
				"\nLista de Exercicios: " + exercisesList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Training other = (Training) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
