package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CronometroJornal implements Serializable {
	private static final long serialVersionUID = 883000341128941241L;

	@Id
	private int id = 1;
	private String jornal;
	private Date horaJornal;
	private Date duracao;
	private int blocos;

	public CronometroJornal() {
		horaJornal = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getHoraJornal() {
		return horaJornal;
	}

	public void setHoraJornal(Date horaJornal) {
		this.horaJornal = horaJornal;
	}

	public Date getDuracao() {
		return duracao;
	}

	public void setDuracao(Date duracao) {
		this.duracao = duracao;
	}

	public int getBlocos() {
		return blocos;
	}

	public void setBlocos(int blocos) {
		this.blocos = blocos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blocos;
		result = prime * result + ((duracao == null) ? 0 : duracao.hashCode());
		result = prime * result + ((horaJornal == null) ? 0 : horaJornal.hashCode());
		result = prime * result + id;
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
		CronometroJornal other = (CronometroJornal) obj;
		if (blocos != other.blocos)
			return false;
		if (duracao != other.duracao)
			return false;
		if (horaJornal == null) {
			if (other.horaJornal != null)
				return false;
		} else if (!horaJornal.equals(other.horaJornal))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CronometroJornal [id=" + id + ", horaJornal=" + horaJornal + ", duracao=" + duracao + ", blocos="
				+ blocos + "]";
	}

	public String getJornal() {
		return jornal;
	}

	public void setJornal(String jornal) {
		this.jornal = jornal;
	}

}
