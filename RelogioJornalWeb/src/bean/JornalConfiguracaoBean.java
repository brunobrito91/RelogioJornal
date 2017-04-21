package bean;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import email.Email;
import facade.EmailFacade;
import facade.EmailFacadeImpl;
import facade.JornalFacade;
import facade.JornalFacadeImpl;
import model.Jornal;
import util.Lookup;
import util.Word;

@ManagedBean
@SessionScoped
public class JornalConfiguracaoBean {

	private JornalFacade jornalFacade;
	private EmailFacade _emailFacadeImpl;
	private Jornal jornal;

	public JornalConfiguracaoBean() {
		System.out.println("JornalConfiguracaoBean");
		jornalFacade = (JornalFacade) Lookup.doLookup(JornalFacadeImpl.class, JornalFacade.class);
		_emailFacadeImpl = (EmailFacade) Lookup.doLookup(EmailFacadeImpl.class, EmailFacade.class);

		List<Jornal> lista = jornalFacade.recuperarTodos();
		if (lista.size() > 0) {
			jornal = lista.get(0);
		} else {
			jornal = new Jornal();
		}
	}

	public Jornal getJornal() {
		return jornal;
	}

	public void setNome(Jornal jornal) {
		this.jornal = jornal;
	}

	public int getId() {
		return jornal.getId();
	}

	public void setId(int id) {
		jornal.setId(id);
	}

	public String getNome() {
		return jornal.getNome();
	}

	public void setNome(String nome) {
		jornal.setNome(nome);
	}

	public Date getHora() {
		return jornal.getHora();
	}

	public void setHora(Date hora) {
		Calendar calendarAux = GregorianCalendar.getInstance();
		Calendar calendar = GregorianCalendar.getInstance();

		calendar.setTime(hora);
		calendar.set(Calendar.DAY_OF_MONTH, calendarAux.get(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.MONTH, calendarAux.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, calendarAux.get(Calendar.YEAR));

		jornal.setHora(calendar.getTime());
	}

	public Date getDuracao() {
		return jornal.getDuracao();
	}

	public void setDuracao(Date duracao) {
		jornal.setDuracao(duracao);
	}

	public int getBlocos() {
		return jornal.getBlocos();
	}

	public void setBlocos(int blocos) {
		jornal.setBlocos(blocos);
	}

	public void jornalChanged(ValueChangeEvent e) {
		int id = (int) e.getNewValue();
		jornal = jornalFacade.recuperar(id);
	}

	public void jornalChanged() {
		jornal = jornalFacade.recuperar(getId());
	}

	public void atualizarHoraJornal() {
		jornalFacade.atualizar(jornal);
		enviarEmail(jornal.getNome(), jornal.getNome());
		exibirMensagemSucesso("Atualização realizada com sucesso!");
	}

	private void exibirMensagemSucesso(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage("Sucesso", mensagem));
	}

	private void exibirMensagemErro(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", mensagem));
	}

	private void enviarEmail(String assunto, String mensagem) {
		try {
			Word.criarArquivoJornal(jornal);
			Set<String> emails = new HashSet<>();
			List<model.Email> emailsAddress = _emailFacadeImpl.findAll();

			if (emailsAddress.size() > 0) {
				for (model.Email email : emailsAddress) {
					emails.add(email.getEmail());
				}
				Email email = new Email("coordenacao.sca@eptv.com.br", "Coordenação", emails, assunto,
						mensagem + "\nMensagem enviada pelo sistema, clique no link a seguir para visualizar as alterações online: \nhttp://101.100.21.85:8080/RelogioJornalWeb/index.xhtml",
						"jornal.docx");
				email.start();
			} else {
				exibirMensagemErro("Não foi possível enviar o e-mail. Não há e-mails cadastrados");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String atualizarNome() {
		jornalFacade.atualizar(jornal);
		exibirMensagemSucesso("Alteração realizada com sucesso!");
		return "";
	}

}