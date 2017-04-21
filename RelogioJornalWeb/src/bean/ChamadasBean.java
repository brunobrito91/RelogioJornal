package bean;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import facade.ChamadaFacade;
import facade.ChamadaFacadeImpl;
import model.Chamada;
import util.Lookup;

@ManagedBean(name = "chamadasBean")
@ApplicationScoped
public class ChamadasBean {

	private List<Chamada> listaDefinitiva;
	private ChamadaFacade chamadaFacade;
	private Comparator<Chamada> comparator;

	public ChamadasBean() {
		System.out.println("ChamadasBean");
		chamadaFacade = (ChamadaFacade) Lookup.doLookup(ChamadaFacadeImpl.class, ChamadaFacade.class);
		listaDefinitiva = chamadaFacade.recuperarTodos();

		comparator = criarComparador();
	}

	public List<Chamada> getListaDefinitiva() {
		Collections.sort(listaDefinitiva, comparator);
		return listaDefinitiva;
	}

	private Comparator<Chamada> criarComparador() {
		Comparator<Chamada> comparator = new Comparator<Chamada>() {

			@Override
			public int compare(Chamada o1, Chamada o2) {
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

	public void setListaDefinitiva(List<Chamada> listaDefinitiva) {
		this.listaDefinitiva = listaDefinitiva;
	}
}
