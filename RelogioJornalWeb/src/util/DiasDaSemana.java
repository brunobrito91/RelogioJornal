package util;

public enum DiasDaSemana {
	DOMINGO("Domingo"), SEGUNDA("Segunda-Feira"), TERCA("Ter�a-Feira"), QUARTA("Quarta-Feira"), QUINTA(
			"Quinta-Feira"), SEXTA("Sexta-Feira"), SABADO("S�bado");

	private String diaDaSemana;

	DiasDaSemana(String diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	public String getDiaDaSemana() {
		return diaDaSemana;
	}

}
