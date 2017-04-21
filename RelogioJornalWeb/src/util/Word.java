package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import model.Boletim;
import model.Chamada;
import model.Jornal;

public class Word {

	private final static int EXCLUSAO = 1;

	public static void criarArquivoJornal(Jornal jornal) throws IOException {
		XWPFDocument documento = new XWPFDocument();

		FileOutputStream arquivo = new FileOutputStream(new File("jornal.docx"));

		XWPFParagraph paragrafo = documento.createParagraph();
		XWPFRun run = paragrafo.createRun();

		GregorianCalendar dataDoDia = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/YY");
		run.setText("Ref.: Previsão de horários do dia – " + format.format(dataDoDia.getTime()) + " – "
				+ DiasDaSemana.values()[dataDoDia.get(Calendar.DAY_OF_WEEK) - 1].getDiaDaSemana());

		XWPFTable tabela = documento.createTable(2, 4);
		tabela.setCellMargins(50, 50, 50, 50);

		inserirParagrafoNaColuna(tabela, 0, 0, "Jornal");
		inserirParagrafoNaColuna(tabela, 0, 1, "Horário");
		inserirParagrafoNaColuna(tabela, 0, 2, "Produção");
		inserirParagrafoNaColuna(tabela, 0, 3, "Blocos");

		format.applyPattern("HH:mm:ss");
		inserirParagrafoNaColuna(tabela, 1, 0, jornal.getNome());
		inserirParagrafoNaColuna(tabela, 1, 1, format.format(jornal.getHora()));
		inserirParagrafoNaColuna(tabela, 1, 2, format.format(jornal.getDuracao()));
		inserirParagrafoNaColuna(tabela, 1, 3, String.valueOf(jornal.getBlocos()));

		documento.write(arquivo);
		documento.close();
		arquivo.close();
	}

	private static void inserirParagrafoNaColuna(XWPFTable tabela, int linha, int coluna, String texto, String cor) {
		XWPFParagraph paragrafo;
		XWPFRun run;

		paragrafo = tabela.getRow(linha).getCell(coluna).getParagraphs().get(0);
		paragrafo.setAlignment(ParagraphAlignment.CENTER);

		run = paragrafo.createRun();
		run.setBold(true);
		run.setColor(cor);
		run.setFontFamily("Calibri");
		run.setFontSize(14);
		run.setText(texto);
	}

	private static void inserirParagrafoNaColuna(XWPFTable tabela, int linha, int coluna, String texto) {
		XWPFParagraph paragrafo;
		XWPFRun run;

		paragrafo = tabela.getRow(linha).getCell(coluna).getParagraphs().get(0);
		paragrafo.setAlignment(ParagraphAlignment.CENTER);

		run = paragrafo.createRun();
		run.setBold(true);
		run.setFontFamily("Calibri");
		run.setFontSize(14);
		run.setText(texto);
	}

	public static void criarArquivoBoletim(List<Boletim> boletins, int operacao, Boletim boletim) throws IOException {
		XWPFDocument documento = new XWPFDocument();

		FileOutputStream arquivo = new FileOutputStream(new File("boletins.docx"));

		XWPFParagraph paragrafo = documento.createParagraph();
		XWPFRun run = paragrafo.createRun();

		GregorianCalendar dataDoDia = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/YY");
		run.setText("Ref.: Previsão de horários do dia – " + format.format(dataDoDia.getTime()) + " – "
				+ DiasDaSemana.values()[dataDoDia.get(Calendar.DAY_OF_WEEK) - 1].getDiaDaSemana());

		switch (operacao) {
		case EXCLUSAO: {
			paragrafo = documento.createParagraph();
			run = paragrafo.createRun();
			run.setText("Boletim excluído(Horário: " + boletim.getHorarioPrevistoStr() + " - Duração: "
					+ boletim.getDuracaoStr() + " - Programa: " + boletim.getPrograma() + ")");
			run.setBold(true);
			run.setColor("FF0000");
		}
			break;
		}

		preencherTabelaBoletins(boletins, boletim, documento, format);

		documento.write(arquivo);
		documento.close();
		arquivo.close();
	}

	private static void preencherTabelaBoletins(List<Boletim> boletins, Boletim boletim, XWPFDocument documento,
			SimpleDateFormat format) {
		if (boletins.size() > 0) {
			XWPFTable tabela = documento.createTable(boletins.size() + 1, 3);
			tabela.setCellMargins(50, 50, 50, 50);

			inserirParagrafoNaColuna(tabela, 0, 0, "Horário");
			inserirParagrafoNaColuna(tabela, 0, 1, "Duração");
			inserirParagrafoNaColuna(tabela, 0, 2, "Programa");

			format.applyPattern("HH:mm:ss");

			for (int i = 0; i < boletins.size(); i++) {
				String color = "000000";

				if ((boletim != null) && (boletins.get(i).getId() == boletim.getId())) {
					color = "FF0000";
				}
				inserirParagrafoNaColuna(tabela, i + 1, 0, boletins.get(i).getHorarioPrevistoStr(), color);
				inserirParagrafoNaColuna(tabela, i + 1, 1, boletins.get(i).getDuracaoStr(), color);
				inserirParagrafoNaColuna(tabela, i + 1, 2, boletins.get(i).getPrograma(), color);
			}
		}
	}

	public static void criarArquivoChamada(List<Chamada> chamadas, int operacao, Chamada chamada) throws IOException {
		XWPFDocument documento = new XWPFDocument();

		FileOutputStream arquivo = new FileOutputStream(new File("chamadas.docx"));

		XWPFParagraph paragrafo = documento.createParagraph();
		XWPFRun run = paragrafo.createRun();

		GregorianCalendar dataDoDia = new GregorianCalendar();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/YY");
		run.setText("Ref.: Previsão de horários do dia – " + format.format(dataDoDia.getTime()) + " – "
				+ DiasDaSemana.values()[dataDoDia.get(Calendar.DAY_OF_WEEK) - 1].getDiaDaSemana());

		switch (operacao) {
		case EXCLUSAO: {
			paragrafo = documento.createParagraph();
			run = paragrafo.createRun();
			run.setText("Chamada excluída(Jornal: " + chamada.getNomeJornal() + " - Horário: "
					+ chamada.getHorarioPrevistoStr() + " - Duração: " + chamada.getDuracaoStr() + " - Programa: "
					+ chamada.getPrograma() + ")");
			run.setBold(true);
			run.setColor("FF0000");
		}
			break;
		}

		preencherTabelaChamadas(chamadas, chamada, documento, format);

		documento.write(arquivo);
		documento.close();
		arquivo.close();
	}

	private static void preencherTabelaChamadas(List<Chamada> chamadas, Chamada chamada, XWPFDocument documento,
			SimpleDateFormat format) {
		if (chamadas.size() > 0) {
			XWPFTable tabela = documento.createTable(chamadas.size() + 1, 4);
			tabela.setCellMargins(50, 50, 50, 50);

			inserirParagrafoNaColuna(tabela, 0, 0, "Jornal");
			inserirParagrafoNaColuna(tabela, 0, 1, "Horário");
			inserirParagrafoNaColuna(tabela, 0, 2, "Duração");
			inserirParagrafoNaColuna(tabela, 0, 3, "Programa");

			format.applyPattern("HH:mm:ss");

			for (int i = 0; i < chamadas.size(); i++) {
				String color = "000000";

				if ((chamada != null) && (chamadas.get(i).getId() == chamada.getId())) {
					color = "FF0000";
				}
				inserirParagrafoNaColuna(tabela, i + 1, 0, chamadas.get(i).getNomeJornal(), color);
				inserirParagrafoNaColuna(tabela, i + 1, 1, chamadas.get(i).getHorarioPrevistoStr(), color);
				inserirParagrafoNaColuna(tabela, i + 1, 2, chamadas.get(i).getDuracaoStr(), color);
				inserirParagrafoNaColuna(tabela, i + 1, 3, chamadas.get(i).getPrograma(), color);
			}
		}
	}

}
