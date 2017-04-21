package util;

public enum DiasDaSemana {
	DOMINGO("Domingo"), SEGUNDA("Segunda-Feira"), TERCA("Terça-Feira"), QUARTA("Quarta-Feira"), QUINTA(
			"Quinta-Feira"), SEXTA("Sexta-Feira"), SABADO("Sábado");

	private String diaDaSemana;

	DiasDaSemana(String diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	public String getDiaDaSemana() {
		return diaDaSemana;
	}

}
