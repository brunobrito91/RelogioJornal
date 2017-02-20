package facade;

import java.util.List;

import javax.ejb.Remote;

import model.BoletimChamada;

@Remote
public interface BoletimChamadaFacade {

	public abstract void salvar(BoletimChamada boletimChamada);

	public abstract BoletimChamada recuperar(int id);

	public abstract List<BoletimChamada> recuperarTodos();
	
	public abstract void excluir(BoletimChamada boletimChamada);

	public abstract void atualizar(BoletimChamada boletimChamada);

}
