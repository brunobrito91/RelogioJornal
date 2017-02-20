package converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.orderlist.OrderList;

import model.BoletimChamada;

@FacesConverter(value = "boletimChamadaConverter")
public class BoletimChamadaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg1 instanceof OrderList) {
			OrderList orderList = (OrderList) arg1;
			@SuppressWarnings("unchecked")
			List<BoletimChamada> boletimChamadas = (List<BoletimChamada>) orderList.getValue();
			for (BoletimChamada boletimChamada : boletimChamadas) {
				if (boletimChamada.getId() == Integer.valueOf(arg2)) {
					return boletimChamada;
				}
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 instanceof BoletimChamada) {
			BoletimChamada boletimChamada = (BoletimChamada) arg2;
			return String.valueOf(boletimChamada.getId());
		}

		return null;
	}

}
