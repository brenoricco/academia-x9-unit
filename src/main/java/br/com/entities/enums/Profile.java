package br.com.entities.enums;

public enum Profile {
	ADMIN(1),
	INSTRUCTOR(2),
	STUDENT(3);

	private int cod;

	private Profile(int cod) {
		this.cod = cod;
	}

	public int getCod() {
		return cod;
	}

	public static Profile toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (Profile x : Profile.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
