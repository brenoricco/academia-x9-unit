package br.com.entities.enums;

public enum MuscleGroup {
	BRACO(1),
	PERNA(2),
	COSTA(3),
	OMBRO(4),
	PEITO(5);

	private int cod;

	private MuscleGroup(int cod) {
		this.cod = cod;
	}

	public int getCod() {
		return cod;
	}

	public static MuscleGroup toEnum(Integer cod) {

		if (cod == null) {
			return null;
		}

		for (MuscleGroup x : MuscleGroup.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
