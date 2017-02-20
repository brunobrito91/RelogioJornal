package facade;

import java.util.List;

import javax.ejb.Remote;

import model.CronometroJornal;

@Remote
public interface CronometroJornalFacade {
	public abstract void salvar(CronometroJornal cronometroJornal);

	public abstract void atualizar(CronometroJornal cronometroJornal);
	public abstract CronometroJornal recuperar(int id);

	public abstract List<CronometroJornal> recuperarTodos();

}
