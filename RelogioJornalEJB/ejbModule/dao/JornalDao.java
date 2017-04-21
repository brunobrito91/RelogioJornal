package dao;

import javax.ejb.Stateless;

import model.Jornal;

@Stateless
public class JornalDao extends GenericDao<Jornal> {

	public JornalDao() {
		super(Jornal.class);
	}

}
