package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.BoletimDao;
import model.Boletim;

@Stateless
public class BoletimFacadeImpl implements BoletimFacade {

	@EJB
	private BoletimDao boletimDao;

	@Override
	public void salvar(Boletim boletim) {
		boletimDao.salvar(boletim);
	}

	@Override
	public Boletim recuperar(int id) {
		return boletimDao.recuperar(id);
	}

	@Override
	public List<Boletim> recuperarTodos() {
		return boletimDao.recuperarTodos();
	}

	@Override
	public void excluir(Boletim boletim) {
		boletimDao.excluir(boletim.getId(), Boletim.class);
	}

	@Override
	public void atualizar(Boletim boletim) {
		boletimDao.atualizar(boletim);
	}

}
