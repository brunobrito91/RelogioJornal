package facade;

import java.util.List;

import javax.ejb.Remote;

import model.Boletim;

@Remote
public interface BoletimFacade {

	public abstract void salvar(Boletim boletim);

	public abstract Boletim recuperar(int id);

	public abstract List<Boletim> recuperarTodos();

	public abstract void excluir(Boletim boletim);

	public abstract void atualizar(Boletim boletim);

}
