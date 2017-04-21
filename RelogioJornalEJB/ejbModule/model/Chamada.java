package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Chamada implements Serializable {
	private static final long serialVersionUID = 8508768150965649782L;

	@Id
	@GeneratedValue
	private int id;

	private String programa;
	private Date duracao;
	private Date horarioPrevisto;
	private String nomeJornal;

	public Chamada() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 15);

		duracao = calendar.getTime();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getDuracaoStr() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
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

	public String getNomeJornal() {
		return nomeJornal;
	}

	public void setNomeJornal(String nomeJornal) {
		this.nomeJornal = nomeJornal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
		result = prime * result + ((horarioPrevisto == null) ? 0 : horarioPrevisto.hashCode());
		result = prime * result + id;
		result = prime * result + ((nomeJornal == null) ? 0 : nomeJornal.hashCode());
		result = prime * result + ((programa == null) ? 0 : programa.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chamada other = (Chamada) obj;
		if (duracao == null) {
			if (other.duracao != null)
				return false;
		} else if (!duracao.equals(other.duracao))
			return false;
		if (horarioPrevisto == null) {
			if (other.horarioPrevisto != null)
				return false;
		} else if (!horarioPrevisto.equals(other.horarioPrevisto))
			return false;
		if (id != other.id)
			return false;
		if (nomeJornal == null) {
			if (other.nomeJornal != null)
				return false;
		} else if (!nomeJornal.equals(other.nomeJornal))
			return false;
		if (programa == null) {
			if (other.programa != null)
				return false;
		} else if (!programa.equals(other.programa))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Chamada [id=" + id + ", programa=" + programa + ", duracao=" + duracao + ", horarioPrevisto="
				+ horarioPrevisto + ", nomeJornal=" + nomeJornal + "]";
	}

}
