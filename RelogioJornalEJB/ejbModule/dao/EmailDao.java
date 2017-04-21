package dao;

import javax.ejb.Stateless;

import model.Email;

@Stateless
public class EmailDao extends GenericDao<Email> {
	public EmailDao() {
		super(Email.class);
	}

	public void delete(Email email) {
		super.excluir(email.getEmail(), Email.class);
	}
}
