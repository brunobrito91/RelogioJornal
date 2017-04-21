package facade;

import java.util.List;

import javax.ejb.Remote;

import model.Jornal;

@Remote
public interface JornalFacade {
	public abstract void salvar(Jornal jornal);

	public abstract void atualizar(Jornal jornal);

	public abstract Jornal recuperar(int id);

	public abstract List<Jornal> recuperarTodos();

}
