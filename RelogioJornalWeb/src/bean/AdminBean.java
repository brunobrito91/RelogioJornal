package bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@ViewScoped
public class AdminBean {

	private boolean admin = false;

	public AdminBean() {
		System.out.println("AdminBean");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
		HttpSession session = (HttpSession) request.getSession();

		if (session.getAttribute("admin") != null) {
			System.out.println("admin");
			admin = (boolean) session.getAttribute("admin");
		}
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
}
