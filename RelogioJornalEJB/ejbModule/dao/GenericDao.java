package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDao<T> {
	private final String UNIT_NAME = "RelogioJornalPU";

	@PersistenceContext(unitName = UNIT_NAME)
	public EntityManager em;

	private Class<T> entityClass;

	public GenericDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void salvar(T entity) {
		em.persist(entity);
	}
	
	public void atualizar(T entity) {
		em.merge(entity);
	}

	public T recuperar(int id) {
		return em.find(entityClass, id);
	}
	
	public void excluir(Object id, Class<T> classe) {
		T entityToBeRemoved = em.getReference(classe, id);

		em.remove(entityToBeRemoved);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> recuperarTodos() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

}
