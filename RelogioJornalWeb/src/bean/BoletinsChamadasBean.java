package bean;

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import facade.BoletimChamadaFacade;
import facade.BoletimChamadaFacadeImpl;
import model.BoletimChamada;
import util.Lookup;

@ManagedBean(name = "boletinsChamadasBean")
@ApplicationScoped
public class BoletinsChamadasBean {
	private List<BoletimChamada> listaDefinitiva;
	private BoletimChamadaFacade boletimChamadaFacade;

	public BoletinsChamadasBean() {
		System.out.println("BoletinsChamadasBean");
		boletimChamadaFacade = (BoletimChamadaFacade) Lookup.doLookup(BoletimChamadaFacadeImpl.class,
				BoletimChamadaFacade.class);
		listaDefinitiva = boletimChamadaFacade.recuperarTodos();
	}

	public List<BoletimChamada> getListaDefinitiva() {
		return listaDefinitiva;
	}

	public void setListaDefinitiva(List<BoletimChamada> listaDefinitiva) {
		this.listaDefinitiva = listaDefinitiva;
	}
}
