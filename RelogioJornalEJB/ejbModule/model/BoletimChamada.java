package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BoletimChamada implements Serializable {
	private static final long serialVersionUID = -156204982844159431L;

	@Id
	@GeneratedValue
	private int id;

	private int tipo;
	private String jornal;
	private Date duracao;
	private Date horarioPrevisto;
	private String programa;

	public BoletimChamada() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getDuracaoStr() {
		SimpleDateFormat format = new SimpleDateFormat("mm:ss");
		return format.format(duracao);
	}

	public Date getDuracao() {
		return duracao;
	}

	public void setDuracao(Date duracao) {
		this.duracao = duracao;
	}

	public String getHorarioPrevistoStr() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(horarioPrevisto);
	}

	public Date getHorarioPrevisto() {
		return horarioPrevisto;
	}

	public void setHorarioPrevisto(Date horarioPrevisto) {
		this.horarioPrevisto = horarioPrevisto;
	}

	public String getJornal() {
		return jornal;
	}

	public void setJornal(String jornal) {
		this.jornal = jornal;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
		result = prime * result + ((horarioPrevisto == null) ? 0 : horarioPrevisto.hashCode());
		result = prime * result + id;
		result = prime * result + ((jornal == null) ? 0 : jornal.hashCode());
		result = prime * result + ((programa == null) ? 0 : programa.hashCode());
		result = prime * result + tipo;
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
		BoletimChamada other = (BoletimChamada) obj;
		if (duracao != other.duracao)
			return false;
		if (horarioPrevisto == null) {
			if (other.horarioPrevisto != null)
				return false;
		} else if (!horarioPrevisto.equals(other.horarioPrevisto))
			return false;
		if (id != other.id)
			return false;
		if (jornal == null) {
			if (other.jornal != null)
				return false;
		} else if (!jornal.equals(other.jornal))
			return false;
		if (programa == null) {
			if (other.programa != null)
				return false;
		} else if (!programa.equals(other.programa))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BoletimChamada [id=" + id + ", tipo=" + tipo + ", jornal=" + jornal + ", duracao=" + duracao
				+ ", horarioPrevisto=" + horarioPrevisto + ", programa=" + programa + "]";
	}

}
