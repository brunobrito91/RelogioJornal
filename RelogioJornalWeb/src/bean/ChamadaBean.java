package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import email.Email;
import facade.ChamadaFacade;
import facade.ChamadaFacadeImpl;
import facade.EmailFacade;
import facade.EmailFacadeImpl;
import model.Chamada;
import util.Lookup;
import util.Word;

@ManagedBean
@ViewScoped
public class ChamadaBean {

	private final int INCLUSAO = 0;
	private final int EXCLUSAO = 1;
	private final int ATUALIZACAO = 2;

	private Chamada chamada;

	private List<Chamada> listaTemporaria;

	private ChamadaFacade chamadaFacade;
	private EmailFacade _emailFacadeImpl;

	private Chamada chamadaSelecionada;

	private Calendar calendar = GregorianCalendar.getInstance();
	// private static final int DOZE_HORAS = 12;
	private final int DEZ_MINUTOS = 10;

	@ManagedProperty(value = "#{chamadasBean}")
	private ChamadasBean chamadasBean;

	public ChamadaBean() {
		System.out.println("ChamadaBean");
		chamada = new Chamada();
		chamadaFacade = (ChamadaFacade) Lookup.doLookup(ChamadaFacadeImpl.class, ChamadaFacade.class);
		_emailFacadeImpl = (EmailFacade) Lookup.doLookup(EmailFacadeImpl.class, EmailFacade.class);

		listaTemporaria = new ArrayList<Chamada>();

		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		chamadaSelecionada = (Chamada) session.getAttribute("chamadaSelecionada");
	}

	@PostConstruct
	public void init() {
		chamadasBean.setListaDefinitiva(chamadaFacade.recuperarTodos());
	}

	public void setChamadasBean(ChamadasBean chamadasBean) {
		this.chamadasBean = chamadasBean;
	}

	public ChamadasBean getChamadasBean() {
		return chamadasBean;
	}

	public String getNomeJornal() {
		return chamada.getNomeJornal();
	}

	public void setNomeJornal(String nomeJornal) {
		chamada.setNomeJornal(nomeJornal);
	}

	public String getPrograma() {
		return chamada.getPrograma();
	}

	public void setPrograma(String programa) {
		chamada.setPrograma(programa);
	}

	public Date getDuracao() {
		return chamada.getDuracao();
	}

	public void setDuracao(Date duracao) {
		chamada.setDuracao(duracao);
	}

	public Date getHorarioPrevisto() {
		return chamada.getHorarioPrevisto();
	}

	public void setHorarioPrevisto(Date horarioPrevisto) {
		Calendar calendarAux = GregorianCalendar.getInstance();
		Calendar calendar = GregorianCalendar.getInstance();

		calendar.setTime(horarioPrevisto);
		calendar.set(Calendar.DAY_OF_MONTH, calendarAux.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, calendarAux.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendarAux.get(Calendar.YEAR));

		chamada.setHorarioPrevisto(calendar.getTime());
	}

	public List<Chamada> getListaTemporaria() {
		return listaTemporaria;
	}

	public void setListaTemporaria(List<Chamada> listaTemporaria) {
		this.listaTemporaria = listaTemporaria;
	}

	public String incluir() {
		for (Chamada chamada : listaTemporaria) {
			chamadaFacade.salvar(chamada);
		}
		chamadasBean.setListaDefinitiva(chamadaFacade.recuperarTodos());
		enviarEmail(INCLUSAO, null);
		exibirMensagemSucesso("Inclusão realizada com sucesso!");
		listaTemporaria.clear();
		return "";
	}

	private void exibirMensagemSucesso(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Sucesso", mensagem));
	}

	private void exibirMensagemErro(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagem));
	}

	private void enviarEmail(int operacao, Chamada chamada) {
		try {

			String mensagem = "";
			switch (operacao) {
			case INCLUSAO: {
				mensagem = "Inclusão de chamadas";
				Word.criarArquivoChamada(chamadasBean.getListaDefinitiva(), operacao, null);
			}
				break;
			case EXCLUSAO: {
				mensagem = "Exclusão de chamada";
				Word.criarArquivoChamada(chamadasBean.getListaDefinitiva(), operacao, chamada);
			}
				break;
			case ATUALIZACAO: {
				mensagem = "Atualização de chamada";
				Word.criarArquivoChamada(chamadasBean.getListaDefinitiva(), operacao, chamada);
			}
				break;

			}

			String caminhoAnexo = "chamadas.docx";

			Set<String> emails = new HashSet<>();
			List<model.Email> emailsAddress = _emailFacadeImpl.findAll();

			if (emailsAddress.size() > 0) {
				for (model.Email email : emailsAddress) {
					emails.add(email.getEmail());
				}

				Email email = new Email("coordenacao.sca@eptv.com.br", "Coordenação", emails, "Chamadas",
						mensagem + "\nMensagem enviada pelo sistema, clique no link a seguir para visualizar as alterações online: \nhttp://101.100.21.85:8080/RelogioJornalWeb/index.xhtml",
						caminhoAnexo);
				email.start();
			} else {
				exibirMensagemErro("Não foi possível enviar o e-mail. Não há e-mails cadastrados");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void incluirNaTabela() {
		listaTemporaria.add(chamada);
		chamada = new Chamada();
	}

	public String excluirChamada() {
		chamadaFacade.excluir(chamadaSelecionada);
		chamadasBean.setListaDefinitiva(chamadaFacade.recuperarTodos());
		enviarEmail(EXCLUSAO, chamadaSelecionada);
		return "index.xhtml?faces-redirect=true";
	}

	public String atualizarChamada() {
		chamadaFacade.atualizar(chamadaSelecionada);
		chamadasBean.setListaDefinitiva(chamadaFacade.recuperarTodos());
		enviarEmail(ATUALIZACAO, chamadaSelecionada);
		exibirMensagemSucesso("Atualização realizada com sucesso!");
		return "";
	}

	public void setChamadaSelecionada(Chamada chamada) {
		chamadaSelecionada = chamada;
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("chamadaSelecionada", chamadaSelecionada);
	}

	public Chamada getChamadaSelecionada() {
		return chamadaSelecionada;
	}

	public String getNomeJornalChamadaSelecionada() {
		return chamadaSelecionada.getNomeJornal();
	}

	public void setNomeJornalChamadaSelecionada(String nomeJornal) {
		chamadaSelecionada.setNomeJornal(nomeJornal);
	}

	public String getProgramaChamadaSelecionada() {
		return chamadaSelecionada.getPrograma();
	}

	public void setProgramaChamadaSelecionada(String programa) {
		chamadaSelecionada.setPrograma(programa);
	}

	public Date getDuracaoChamadaSelecionada() {
		return chamadaSelecionada.getDuracao();
	}

	public void setDuracaoChamadaSelecionada(Date duracao) {
		chamadaSelecionada.setDuracao(duracao);
	}

	public Date getHorarioPrevistoChamadaSelecionada() {
		return chamadaSelecionada.getHorarioPrevisto();
	}

	public void setHorarioPrevistoChamadaSelecionada(Date horarioPrevisto) {
		Calendar calendarAux = GregorianCalendar.getInstance();
		Calendar calendar = GregorianCalendar.getInstance();

		calendar.setTime(horarioPrevisto);
		calendar.set(Calendar.DAY_OF_MONTH, calendarAux.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, calendarAux.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendarAux.get(Calendar.YEAR));

		chamadaSelecionada.setHorarioPrevisto(calendar.getTime());
	}

	public void chamadaListener() {
		Date now = new Date();
		for (Chamada chamada : chamadasBean.getListaDefinitiva()) {
			calendar.setTime(chamada.getHorarioPrevisto());
			// calendar.add(Calendar.HOUR, DOZE_HORAS);
			calendar.add(Calendar.MINUTE, DEZ_MINUTOS);
			if (now.after(calendar.getTime())) {
				chamadaFacade.excluir(chamada);
				chamadasBean.setListaDefinitiva(chamadaFacade.recuperarTodos());
			}
		}

	}
}
