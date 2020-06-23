package br.com.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.entities.enums.MuscleGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_EXERCISE")
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "MUSCLE_GROUP")
	private Integer group;
	
	@ManyToMany(mappedBy = "exercises",cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private List<Training> trainings = new ArrayList<>();
	
	public Exercise(String title, MuscleGroup group) {
		this.title = title;
		this.group = group.getCod();
	}
	
	public MuscleGroup getGroup() {
		return MuscleGroup.toEnum(group);
	}
	
	public void setGroup(MuscleGroup group) {
		this.group = group.getCod();
	}

	@Override
	public String toString() {
		return "Codigo: " + id + "  Titulo: " + title + "  Grupo: " + getGroup();
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
		Exercise other = (Exercise) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
