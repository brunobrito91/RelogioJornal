package bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import facade.EmailFacade;
import facade.EmailFacadeImpl;
import model.Email;
import util.Lookup;

@ManagedBean
public class ConfiguracaoEmailBean implements Serializable {
	private static final long serialVersionUID = -3669804690692383682L;

	private Email email, emailSelecionado;
	private EmailFacade _emailFacadeImpl;

	private List<Email> emailsSelecionados;

	public ConfiguracaoEmailBean() {
		System.out.println("ConfiguracaoEmailBean");
	}

	@PostConstruct
	public void init() {
		_emailFacadeImpl = (EmailFacade) Lookup.doLookup(EmailFacadeImpl.class, EmailFacade.class);

		email = new Email();
		emailSelecionado = new Email();

	}

	public String getEmail() {
		return email.getEmail();
	}

	public void setEmail(String email) {
		this.email.setEmail(email);
	}

	public Email getEmailSelecionado() {
		return emailSelecionado;
	}

	public void setEmailSelecionado(Email emailSelecionado) {
		this.emailSelecionado = emailSelecionado;
	}

	public String salvarEmail() {
		if (emailExiste(email)) {
			exibirMensagemErro("E-mail já existe!");
		} else {
			_emailFacadeImpl.save(email);
			exibirMensagemSucesso("E-mail incluso com sucesso!");
		}
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

	public void onRowSelect(SelectEvent event) {
		emailSelecionado = ((Email) event.getObject());
	}

	private boolean emailExiste(Email email) {
		List<Email> emailsCadastrados = getListaDeEmails();
		for (Email emailCadastrado : emailsCadastrados) {
			if (emailCadastrado.getEmail().equals(email.getEmail())) {
				return true;
			}
		}
		return false;
	}

	public List<Email> getListaDeEmails() {
		List<Email> emailsCadastrados = _emailFacadeImpl.findAll();
		return emailsCadastrados;
	}

	public List<Email> getEmailsSelecionados() {
		return emailsSelecionados;
	}

	public void setEmailsSelecionados(List<Email> emailsSelecionados) {
		this.emailsSelecionados = emailsSelecionados;
	}

	public void excluirEmailsSelecionados() {
		for (Email email : emailsSelecionados) {
			_emailFacadeImpl.delete(email);
		}
		exibirMensagemSucesso("E-mails excluídos com sucessos!");
	}

}
