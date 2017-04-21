package util;

public enum JornalEnum {

	OPCAO_1("Jornal da Eptv 1� Edi��o"), OPCAO_2("Jornal da Eptv 2� Edi��o"), OPCAO_3("Eptv Cidade"), OPCAO_4(
			"Bom Dia Cidade");

	private String nomeJornal;

	JornalEnum(String nomeJornal) {
		this.setNomeJornal(nomeJornal);
	}

	public String getNomeJornal() {
		return nomeJornal;
	}

	public void setNomeJornal(String nomeJornal) {
		this.nomeJornal = nomeJornal;
	}

}
