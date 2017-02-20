package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import facade.BoletimChamadaFacade;
import facade.BoletimChamadaFacadeImpl;
import model.BoletimChamada;
import util.Lookup;

@ManagedBean
@ViewScoped
public class BoletimChamadaBean {

	private BoletimChamada boletimChamada;

	private List<BoletimChamada> listaTemporaria;

	private BoletimChamadaFacade boletimChamadaFacade;

	private BoletimChamada boletimChamadaSelecionada;

	@ManagedProperty(value = "#{boletinsChamadasBean}")
	private BoletinsChamadasBean boletinsChamadasBean;

	public BoletimChamadaBean() {
		System.out.println("BoletimChamadaBean");
		boletimChamada = new BoletimChamada();
		boletimChamadaFacade = (BoletimChamadaFacade) Lookup.doLookup(BoletimChamadaFacadeImpl.class,
				BoletimChamadaFacade.class);

		listaTemporaria = new ArrayList<BoletimChamada>();

		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		boletimChamadaSelecionada = (BoletimChamada) session.getAttribute("boletimChamadaSelecionada");
	}

	@PostConstruct
	public void init() {
		boletinsChamadasBean.setListaDefinitiva(boletimChamadaFacade.recuperarTodos());
	}

	public void setBoletinsChamadasBean(BoletinsChamadasBean boletinsChamadasBean) {
		this.boletinsChamadasBean = boletinsChamadasBean;
	}

	public BoletinsChamadasBean getBoletinsChamadasBean() {
		return boletinsChamadasBean;
	}

	public int getTipo() {
		return boletimChamada.getTipo();
	}

	public void setTipo(int tipo) {
		boletimChamada.setTipo(tipo);
	}

	public String getJornal() {
		return boletimChamada.getJornal();
	}

	public void setJornal(String jornal) {
		boletimChamada.setJornal(jornal);
	}

	public String getPrograma() {
		return boletimChamada.getPrograma();
	}

	public void setPrograma(String programa) {
		boletimChamada.setPrograma(programa);
	}

	public Date getDuracao() {
		return boletimChamada.getDuracao();
	}

	public void setDuracao(Date duracao) {
		boletimChamada.setDuracao(duracao);
	}

	public Date getHorarioPrevisto() {
		return boletimChamada.getHorarioPrevisto();
	}

	public void setHorarioPrevisto(Date horarioPrevisto) {
		boletimChamada.setHorarioPrevisto(horarioPrevisto);
	}

	public List<BoletimChamada> getListaTemporaria() {
		return listaTemporaria;
	}

	public void setListaTemporaria(List<BoletimChamada> listaTemporaria) {
		this.listaTemporaria = listaTemporaria;
	}

	public String incluir() {
		for (BoletimChamada boletimChamada : listaTemporaria) {
			boletimChamadaFacade.salvar(boletimChamada);
		}
		boletinsChamadasBean.setListaDefinitiva(boletimChamadaFacade.recuperarTodos());
		return "index.xhtml?faces-redirect=true";
	}

	public void incluirNaTabela() {
		listaTemporaria.add(boletimChamada);
		boletimChamada = new BoletimChamada();
	}

	public String excluirBoletimChamada() {
		boletimChamadaFacade.excluir(boletimChamadaSelecionada);
		return "index.xhtml?faces-redirect=true";
	}

	public String atualizarBoletimChamada() {
		boletimChamadaFacade.atualizar(boletimChamadaSelecionada);
		return "index.xhtml?faces-redirect=true";
	}

	public void setBoletimChamadaSelecionada(BoletimChamada boletimChamada) {
		boletimChamadaSelecionada = boletimChamada;
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("boletimChamadaSelecionada", boletimChamadaSelecionada);
	}

	public BoletimChamada getBoletimChamadaSelecionada() {
		return boletimChamadaSelecionada;
	}

	public int getTipoBoletimChamadaSelecionada() {
		return boletimChamadaSelecionada.getTipo();
	}

	public void setTipoBoletimChamadaSelecionada(int tipo) {
		boletimChamadaSelecionada.setTipo(tipo);
	}

	public String getJornalBoletimChamadaSelecionada() {
		return boletimChamadaSelecionada.getJornal();
	}

	public void setJornalBoletimChamadaSelecionada(String jornal) {
		boletimChamadaSelecionada.setJornal(jornal);
	}

	public String getProgramaBoletimChamadaSelecionada() {
		return boletimChamadaSelecionada.getPrograma();
	}

	public void setProgramaBoletimChamadaSelecionada(String programa) {
		boletimChamadaSelecionada.setPrograma(programa);
	}

	public Date getDuracaoBoletimChamadaSelecionada() {
		return boletimChamadaSelecionada.getDuracao();
	}

	public void setDuracaoBoletimChamadaSelecionada(Date duracao) {
		boletimChamadaSelecionada.setDuracao(duracao);
	}

	public Date getHorarioPrevistoBoletimChamadaSelecionada() {
		return boletimChamadaSelecionada.getHorarioPrevisto();
	}

	public void setHorarioPrevistoBoletimChamadaSelecionada(Date horarioPrevisto) {
		boletimChamadaSelecionada.setHorarioPrevisto(horarioPrevisto);
	}

}
