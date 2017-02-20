package dao;

import javax.ejb.Stateless;

import model.CronometroJornal;

@Stateless
public class CronometroJornalDao extends GenericDao<CronometroJornal> {

	public CronometroJornalDao() {
		super(CronometroJornal.class);
	}

}
