package br.com.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "TB_PHONE")
public class Phone implements Serializable {

	private static final long serialVersionUID = -8656686260120201644L;

	@Id
	@Column(name = "ID_PHONE")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String number;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USER")
	private User user;

	public Phone(String number, User user) {
		this.number = number;
		this.user = user;
	}

	public String toString() {

		return "Codigo: " + id + "   Numero: " + number ;
	}
}
