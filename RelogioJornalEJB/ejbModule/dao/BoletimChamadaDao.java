package dao;

import javax.ejb.Stateless;

import model.BoletimChamada;

@Stateless
public class BoletimChamadaDao extends GenericDao<BoletimChamada> {

	public BoletimChamadaDao() {
		super(BoletimChamada.class);
	}

}
