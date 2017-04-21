package dao;

import javax.ejb.Stateless;
import model.Chamada;

@Stateless
public class ChamadaDao extends GenericDao<Chamada> {

	public ChamadaDao() {
		super(Chamada.class);
	}

}
