package bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import facade.CronometroJornalFacade;
import facade.CronometroJornalFacadeImpl;
import model.CronometroJornal;
import util.Lookup;

@ManagedBean
@RequestScoped
public class CronometroJornalBean {
	private CronometroJornal cronometroJornal;
	private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

	private CronometroJornalFacade cronometroJornalFacade;
	private PeriodFormatter periodFormat;

	public CronometroJornalBean() {
		cronometroJornalFacade = (CronometroJornalFacade) Lookup.doLookup(CronometroJornalFacadeImpl.class,
				CronometroJornalFacade.class);
		cronometroJornal = cronometroJornalFacade.recuperar(1);
		if (cronometroJornal == null) {
			cronometroJornal = new CronometroJornal();
		}
		periodFormat = new PeriodFormatterBuilder().printZeroAlways().rejectSignedValues(true).minimumPrintedDigits(2)
				.appendHours().appendSeparator(":").appendMinutes().appendSeparator(":").appendSeconds().toFormatter();
	}

	public String getJornal() {
		return cronometroJornal.getJornal();
	}

	public void setJornal(String jornal) {
		cronometroJornal.setJornal(jornal);
	}

	public Date getHoraJornal() {
		return cronometroJornal.getHoraJornal();
	}

	public void setHoraJornal(Date horaJornal) {
		cronometroJornal.setHoraJornal(horaJornal);
	}

	public String getHoraJornalStr() {
		return format.format(cronometroJornal.getHoraJornal());
	}

	public String getTempoRestanteStr() {
		Calendar now = GregorianCalendar.getInstance();

		LocalTime start = new LocalTime(now.getTimeInMillis());
		LocalTime end = new LocalTime(cronometroJornal.getHoraJornal().getTime());

		Period period = new Period(start, end);

		return period.toString(periodFormat);

	}

	public Date getDuracao() {
		return cronometroJornal.getDuracao();
	}

	public void setDuracao(Date duracao) {
		cronometroJornal.setDuracao(duracao);
	}

	public int getBlocos() {
		return cronometroJornal.getBlocos();
	}

	public void setBlocos(int blocos) {
		cronometroJornal.setBlocos(blocos);
	}

	public void atualizarHoraJornal() {
		cronometroJornalFacade.atualizar(cronometroJornal);
	}
}