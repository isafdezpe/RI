package uo.ri.persistence.jpa.util;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseRepository<T> {

	public void add(T t) {
		Jpa.getManager().persist(t);
	}

	public void remove(T t) {
		Jpa.getManager().remove(t);
	}

	public T findById(Long id) {
		return Jpa.getManager().find(type, id);
	}

	public List<T> findAll() {
		String entity = type.getName();
		String query = "select o from " + entity + " o";

		return Jpa.getManager().createQuery(query, type).getResultList();
	}

	/**
	 * As find() and the query "select x from X x" needs the type of the entity
	 * here there is a reflective way of getting it
	 */
	private Class<T> type;

	public BaseRepository() {
		this.type = hackTheTypeOfGenericParameter();
	}

	/**
	 * This is a hack to recover the runtime reflective type of <T>
	 */
	@SuppressWarnings("unchecked")
	private Class<T> hackTheTypeOfGenericParameter() {
		ParameterizedType superType = (ParameterizedType) getClass()
				.getGenericSuperclass();
		return (Class<T>) superType.getActualTypeArguments()[0];
	}

}
