package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.JornalDao;
import model.Jornal;

@Stateless
public class JornalFacadeImpl implements JornalFacade {

	@EJB
	private JornalDao jornalDao;

	@Override
	public void salvar(Jornal jornal) {
		jornalDao.salvar(jornal);
	}

	@Override
	public void atualizar(Jornal jornal) {
		jornalDao.atualizar(jornal);
	}

	@Override
	public Jornal recuperar(int id) {
		return jornalDao.recuperar(id);
	}

	@Override
	public List<Jornal> recuperarTodos() {
		return jornalDao.recuperarTodos();
	}

}
