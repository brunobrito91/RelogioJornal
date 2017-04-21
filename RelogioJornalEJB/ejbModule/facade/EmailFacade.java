package facade;

import java.util.List;

import javax.ejb.Remote;

import model.Email;

@Remote
public interface EmailFacade {
	public abstract void save(Email email);

	public abstract void update(Email email);

	public abstract void delete(Email email);

	public abstract Email find(int entityID);

	public abstract List<Email> findAll();
}
