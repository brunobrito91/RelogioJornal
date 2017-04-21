package facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dao.EmailDao;
import model.Email;

@Stateless
public class EmailFacadeImpl implements EmailFacade {

	@EJB
	private EmailDao emailDao;

	@Override
	public void save(Email email) {
		emailDao.salvar(email);
	}

	@Override
	public void update(Email email) {
		emailDao.atualizar(email);
	}

	@Override
	public void delete(Email email) {
		emailDao.excluir(email.getEmail(), Email.class);
	}

	@Override
	public Email find(int entityID) {
		return emailDao.recuperar(entityID);
	}

	@Override
	public List<Email> findAll() {
		return emailDao.recuperarTodos();
	}

}
