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
import facade.BoletimFacade;
import facade.BoletimFacadeImpl;
import facade.EmailFacade;
import facade.EmailFacadeImpl;
import model.Boletim;
import util.Lookup;
import util.Word;

@ManagedBean
@ViewScoped
public class BoletimBean {
	private final int INCLUSAO = 0;
	private final int EXCLUSAO = 1;
	private final int ATUALIZACAO = 2;

	private Boletim boletim;

	private List<Boletim> listaTemporaria;

	private BoletimFacade boletimFacade;
	private EmailFacade _emailFacadeImpl;

	private Boletim boletimSelecionado;

	private Calendar calendar = GregorianCalendar.getInstance();
	// private static final int DOZE_HORAS = 12;
	private static final int DEZ_MINUTOS = 10;

	@ManagedProperty(value = "#{boletinsBean}")
	private BoletinsBean boletinsBean;

	public BoletimBean() {
		System.out.println("BoletimBean");
		boletim = new Boletim();
		boletimFacade = (BoletimFacade) Lookup.doLookup(BoletimFacadeImpl.class, BoletimFacade.class);
		_emailFacadeImpl = (EmailFacade) Lookup.doLookup(EmailFacadeImpl.class, EmailFacade.class);

		listaTemporaria = new ArrayList<Boletim>();

		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		boletimSelecionado = (Boletim) session.getAttribute("boletimSelecionado");
	}

	@PostConstruct
	public void init() {
		boletinsBean.setListaDefinitiva(boletimFacade.recuperarTodos());
	}

	public void setBoletinsBean(BoletinsBean boletinsBean) {
		this.boletinsBean = boletinsBean;
	}

	public BoletinsBean getBoletinsBean() {
		return boletinsBean;
	}

	public String getPrograma() {
		return boletim.getPrograma();
	}

	public void setPrograma(String programa) {
		boletim.setPrograma(programa);
	}

	public Date getDuracao() {
		return boletim.getDuracao();
	}

	public void setDuracao(Date duracao) {
		boletim.setDuracao(duracao);
	}

	public Date getHorarioPrevisto() {
		return boletim.getHorarioPrevisto();
	}

	public void setHorarioPrevisto(Date horarioPrevisto) {
		Calendar calendarAux = GregorianCalendar.getInstance();
		Calendar calendar = GregorianCalendar.getInstance();

		calendar.setTime(horarioPrevisto);
		calendar.set(Calendar.DAY_OF_MONTH, calendarAux.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, calendarAux.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendarAux.get(Calendar.YEAR));

		boletim.setHorarioPrevisto(calendar.getTime());
	}

	public List<Boletim> getListaTemporaria() {
		return listaTemporaria;
	}

	public void setListaTemporaria(List<Boletim> listaTemporaria) {
		this.listaTemporaria = listaTemporaria;
	}

	public String incluir() {
		for (Boletim boletim : listaTemporaria) {
			boletimFacade.salvar(boletim);
		}
		boletinsBean.setListaDefinitiva(boletimFacade.recuperarTodos());
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

	private void enviarEmail(int operacao, Boletim boletim) {
		try {
			String mensagem = "";
			switch (operacao) {
			case INCLUSAO: {
				mensagem = "Inclusão de boletins";
				Word.criarArquivoBoletim(boletinsBean.getListaDefinitiva(), operacao, null);
			}
				break;
			case EXCLUSAO: {
				mensagem = "Exclusão de boletim";
				Word.criarArquivoBoletim(boletinsBean.getListaDefinitiva(), operacao, boletim);
			}
				break;
			case ATUALIZACAO: {
				mensagem = "Atualização de boletim";
				Word.criarArquivoBoletim(boletinsBean.getListaDefinitiva(), operacao, boletim);
			}
				break;

			}

			String caminhoAnexo = "boletins.docx";

			Set<String> emails = new HashSet<>();
			List<model.Email> emailsAddress = _emailFacadeImpl.findAll();

			if (emailsAddress.size() > 0) {
				for (model.Email email : emailsAddress) {
					emails.add(email.getEmail());
				}

				Email email = new Email("coordenacao.sca@eptv.com.br", "Coordenação", emails, "Boletins",
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
		listaTemporaria.add(boletim);
		boletim = new Boletim();
	}

	public String excluirBoletim() {
		boletimFacade.excluir(boletimSelecionado);
		boletinsBean.setListaDefinitiva(boletimFacade.recuperarTodos());
		enviarEmail(EXCLUSAO, boletimSelecionado);
		return "index.xhtml?faces-redirect=true";
	}

	public String atualizarBoletim() {
		boletimFacade.atualizar(boletimSelecionado);
		boletinsBean.setListaDefinitiva(boletimFacade.recuperarTodos());
		enviarEmail(ATUALIZACAO, boletimSelecionado);
		exibirMensagemSucesso("Atualização realizada com sucesso!");
		return "";
	}

	public void setBoletimSelecionado(Boletim boletim) {
		boletimSelecionado = boletim;
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.setAttribute("boletimSelecionado", boletimSelecionado);
	}

	public Boletim getBoletimSelecionado() {
		return boletimSelecionado;
	}

	public String getProgramaBoletimSelecionado() {
		return boletimSelecionado.getPrograma();
	}

	public void setProgramaBoletimSelecionado(String programa) {
		boletimSelecionado.setPrograma(programa);
	}

	public Date getDuracaoBoletimSelecionado() {
		return boletimSelecionado.getDuracao();
	}

	public void setDuracaoBoletimSelecionado(Date duracao) {
		boletimSelecionado.setDuracao(duracao);
	}

	public Date getHorarioPrevistoBoletimSelecionado() {
		return boletimSelecionado.getHorarioPrevisto();
	}

	public void setHorarioPrevistoBoletimSelecionado(Date horarioPrevisto) {
		Calendar calendarAux = GregorianCalendar.getInstance();
		Calendar calendar = GregorianCalendar.getInstance();

		calendar.setTime(horarioPrevisto);
		calendar.set(Calendar.DAY_OF_MONTH, calendarAux.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, calendarAux.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendarAux.get(Calendar.YEAR));

		boletimSelecionado.setHorarioPrevisto(calendar.getTime());
	}

	public void boletimListener() {
		Date now = new Date();
		for (Boletim boletim : boletinsBean.getListaDefinitiva()) {
			calendar.setTime(boletim.getHorarioPrevisto());
			// calendar.add(Calendar.HOUR, DOZE_HORAS);
			calendar.add(Calendar.MINUTE, DEZ_MINUTOS);
			if (now.after(calendar.getTime())) {
				boletimFacade.excluir(boletim);
				boletinsBean.setListaDefinitiva(boletimFacade.recuperarTodos());
			}
		}

	}
}
