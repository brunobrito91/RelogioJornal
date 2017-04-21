package bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import email.Email;
import facade.EmailFacade;
import facade.EmailFacadeImpl;
import facade.JornalFacade;
import facade.JornalFacadeImpl;
import model.Jornal;
import util.Lookup;
import util.Word;

@ManagedBean
@RequestScoped
public class JornalBean {
	private static final int TEMPO_ALERTA = 10;
	private Jornal jornal;
	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	private JornalFacade jornalFacade;
	private EmailFacade _emailFacadeImpl;

	private PeriodFormatter periodFormat;

	public JornalBean() {
		jornalFacade = (JornalFacade) Lookup.doLookup(JornalFacadeImpl.class, JornalFacade.class);
		_emailFacadeImpl = (EmailFacade) Lookup.doLookup(EmailFacadeImpl.class, EmailFacade.class);

		List<Jornal> listaJornais = jornalFacade.recuperarTodos();
		if (listaJornais.size() > 0) {
			listaJornais = filtrar(listaJornais);
		}
		if (listaJornais.size() > 0) {
			Collections.sort(listaJornais, criarComparador());

			jornal = listaJornais.get(0);
		} else {
			jornal = new Jornal();
		}

		periodFormat = new PeriodFormatterBuilder().printZeroAlways().rejectSignedValues(true).minimumPrintedDigits(2)
				.appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").appendSeconds().toFormatter();
	}

	private ArrayList<Jornal> filtrar(List<Jornal> lista) {
		ArrayList<Jornal> result = new ArrayList<Jornal>();
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		for (Jornal jornal : lista) {
			calendar1.setTime(jornal.getHora());
			calendar2.setTime(jornal.getDuracao());

			calendar1.add(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));

			if (calendar1.after(Calendar.getInstance())) {
				result.add(jornal);
			}
		}
		return result;
	}

	private Comparator<Jornal> criarComparador() {
		Comparator<Jornal> comparator = new Comparator<Jornal>() {

			@Override
			public int compare(Jornal o1, Jornal o2) {
				if (o1.getHora().before(o2.getHora())) {
					return -1;
				}
				if (o1.getHora().after(o2.getHora())) {
					return 1;
				}
				return 0;
			}
		};
		return comparator;
	}

	public List<Jornal> getJornais() {
		return jornalFacade.recuperarTodos();
	}

	public Date getHoraJornal() {
		return jornal.getHora();
	}

	public String getHoraJornalStr() {
		if (jornal.getId() == 0) {
			return "Indefinido";
		}
		return format.format(jornal.getHora());
	}

	public String getjornal() {
		if (jornal.getId() == 0) {
			return "Indefinido";
		}
		return jornal.getNome();
	}

	public String getTempoRestanteStr() {
		Calendar calendar = GregorianCalendar.getInstance();

		LocalTime start = new LocalTime(calendar.getTimeInMillis());
		LocalTime end = new LocalTime(jornal.getHora().getTime());
		calendar.setTime(jornal.getDuracao());

		Period period;
		if (start.isBefore(end)) {
			period = new Period(start, end);
		} else {
			if (!start.isAfter(end.plusMinutes(calendar.get(Calendar.MINUTE)))) {
				return "NO AR";
			}
			period = new Period(end, end);
		}

		return period.toString(periodFormat);

	}

	public void atualizarHoraJornal() {
		jornalFacade.atualizar(jornal);
		enviarEmail("Cronograma jornal - " + jornal.getNome(), "Cronograma jornal - " + jornal.getNome());
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

				Email email = new Email("Programação", "Programação", emails, assunto,
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

	public String getBackgroundColor() {
		Calendar calendar = GregorianCalendar.getInstance();

		LocalTime start = new LocalTime(calendar.getTimeInMillis());
		LocalTime end = new LocalTime(jornal.getHora().getTime());

		Period period;
		if (start.isBefore(end)) {
			period = new Period(start, end);
			if ((period.getHours() == 0) && (period.getMinutes() < TEMPO_ALERTA)) {
				return "red";
			}
		}
		return "white";
	}

	public String getColor() {
		Calendar calendar = GregorianCalendar.getInstance();

		LocalTime start = new LocalTime(calendar.getTimeInMillis());
		LocalTime end = new LocalTime(jornal.getHora().getTime());

		Period period;
		if (start.isBefore(end)) {
			period = new Period(start, end);
			if ((period.getHours() == 0) && (period.getMinutes() < TEMPO_ALERTA)) {
				return "white";
			}
		}
		return "red";
	}
}