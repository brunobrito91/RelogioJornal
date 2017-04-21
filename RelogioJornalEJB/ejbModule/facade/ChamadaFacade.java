package facade;

import java.util.List;

import javax.ejb.Remote;

import model.Chamada;

@Remote
public interface ChamadaFacade {

	public abstract void salvar(Chamada chamada);

	public abstract Chamada recuperar(int id);

	public abstract List<Chamada> recuperarTodos();

	public abstract void excluir(Chamada chamada);

	public abstract void atualizar(Chamada chamada);

}
