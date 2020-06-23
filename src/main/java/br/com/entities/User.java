package br.com.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.entities.enums.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "TB_USER")
public class User implements Serializable {
	
	private static final long serialVersionUID = 8229190997694268418L;

	@Id
    @Column(name = "ID_USER")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String name;

	@Column
	private String cpf;

	@Column
	private String sex;

	@Column
	private String email;
	
	@Column
	private String password;

	@Column
	private String address;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch=FetchType.EAGER)
	private Set<Phone> phonesList = new HashSet<>();

	@Column
	private LocalDate birthDate;

	@Column
	private LocalDate registeredAt;
	
	@Column
	private Integer profile;
		
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Training> trainingPlan = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<TrainingFrequency> trainings = new HashSet<>();
	
	@Column
	private Integer lastTraining;
	
	@Column
	private LocalDate lastTrainingDate;

	public User(String name, String cpf, String sex, String email, String password, String address, LocalDate birthDate, Profile profile) {
		this.name = name;
		this.cpf = cpf;
		this.sex = sex;
		this.email = email;
		this.password = password;
		this.address = address;
		this.birthDate = birthDate;
		this.registeredAt = LocalDate.now();
		this.profile = profile.getCod();
	}

	public void addPhone(Phone phone) {
		this.phonesList.add(phone);
	}

	public String toString() {
		
		String phones = "\n";
		
		for (Phone p : phonesList) {
			phones = phones + "    " + p.getNumber() + "\n";
		}
		
		return 	"Codigo: " + id +
				"\nNome: " + name + 
				"\nCPF: " + cpf + 
				"\nSexo: " + sex + 
				"\nEmail: " + email + 
				"\nEndereço: " + address +
				"\nData de nascimento: " + birthDate + 
				"\nData de Cadastro: " + registeredAt + 
				"\nTelefones: " + phones;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
