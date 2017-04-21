package dao;

import javax.ejb.Stateless;
import model.Boletim;

@Stateless
public class BoletimDao extends GenericDao<Boletim> {

	public BoletimDao() {
		super(Boletim.class);
	}

}
