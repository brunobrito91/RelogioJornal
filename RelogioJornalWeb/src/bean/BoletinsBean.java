package bean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import facade.BoletimFacade;
import facade.BoletimFacadeImpl;
import model.Boletim;
import util.Lookup;

@ManagedBean(name = "boletinsBean")
@ApplicationScoped
public class BoletinsBean {

	private List<Boletim> listaDefinitiva;
	private BoletimFacade boletimChamadaFacade;
	private Comparator<Boletim> comparator;

	public BoletinsBean() {
		System.out.println("BoletinsBean");
		boletimChamadaFacade = (BoletimFacade) Lookup.doLookup(BoletimFacadeImpl.class, BoletimFacade.class);
		listaDefinitiva = boletimChamadaFacade.recuperarTodos();

		comparator = criarComparador();
	}

	public List<Boletim> getListaDefinitiva() {
		Collections.sort(listaDefinitiva, comparator);
		return listaDefinitiva;
	}

	private Comparator<Boletim> criarComparador() {
		Comparator<Boletim> comparator = new Comparator<Boletim>() {

			@Override
			public int compare(Boletim o1, Boletim o2) {
				if (o1.getHorarioPrevisto().before(o2.getHorarioPrevisto())) {
					return -1;
				}
				if (o1.getHorarioPrevisto().after(o2.getHorarioPrevisto())) {
					return 1;
				}
				return 0;
			}
		};
		return comparator;
	}

	public void setListaDefinitiva(List<Boletim> listaDefinitiva) {
		this.listaDefinitiva = listaDefinitiva;
	}
}
