package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.BoletimChamadaDao;
import model.BoletimChamada;

@Stateless
public class BoletimChamadaFacadeImpl implements BoletimChamadaFacade {

	@EJB
	private BoletimChamadaDao boletimChamadaDao;

	@Override
	public void salvar(BoletimChamada boletimChamada) {
		boletimChamadaDao.salvar(boletimChamada);
	}

	@Override
	public BoletimChamada recuperar(int id) {
		return boletimChamadaDao.recuperar(id);
	}

	@Override
	public List<BoletimChamada> recuperarTodos() {
		return boletimChamadaDao.recuperarTodos();
	}

	@Override
	public void excluir(BoletimChamada boletimChamada) {
		boletimChamadaDao.excluir(boletimChamada.getId(), BoletimChamada.class);
	}

	@Override
	public void atualizar(BoletimChamada boletimChamada) {
		boletimChamadaDao.atualizar(boletimChamada);
	}

}
