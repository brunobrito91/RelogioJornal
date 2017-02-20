package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.CronometroJornalDao;
import model.CronometroJornal;

@Stateless
public class CronometroJornalFacadeImpl implements CronometroJornalFacade {

	@EJB
	private CronometroJornalDao cronometroJornalDao;

	@Override
	public void salvar(CronometroJornal cronometroJornal) {
		cronometroJornalDao.salvar(cronometroJornal);
	}

	@Override
	public CronometroJornal recuperar(int id) {
		return cronometroJornalDao.recuperar(id);
	}

	@Override
	public List<CronometroJornal> recuperarTodos() {
		return cronometroJornalDao.recuperarTodos();
	}

	@Override
	public void atualizar(CronometroJornal cronometroJornal) {
		cronometroJornalDao.atualizar(cronometroJornal);
	}

}
