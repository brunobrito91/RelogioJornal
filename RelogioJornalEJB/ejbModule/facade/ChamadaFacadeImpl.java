package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.ChamadaDao;
import model.Chamada;

@Stateless
public class ChamadaFacadeImpl implements ChamadaFacade {

	@EJB
	private ChamadaDao chamadaDao;

	@Override
	public void salvar(Chamada chamada) {
		chamadaDao.salvar(chamada);
	}

	@Override
	public Chamada recuperar(int id) {
		return chamadaDao.recuperar(id);
	}

	@Override
	public List<Chamada> recuperarTodos() {
		return chamadaDao.recuperarTodos();
	}

	@Override
	public void excluir(Chamada chamada) {
		chamadaDao.excluir(chamada.getId(), Chamada.class);
	}

	@Override
	public void atualizar(Chamada chamada) {
		chamadaDao.atualizar(chamada);
	}

}
