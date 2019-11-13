package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Averia;

public interface AveriaRepository extends Repository<Averia> {

	/**
	 * @param idsAveria, lista de los id de avería a recuperar
	 * @return lista con averias cuyo id aparece en idsAveria, o lista vacía si
	 *         no hay ninguna
	 */
	List<Averia> findByIds(List<Long> idsAveria);

	/**
	 * @param dni
	 * @return lista con averias no facturadas de un cliente identificado por su
	 *         dni o lista vacía si no hay ninguna
	 */
	List<Averia> findNoFacturadasByDni(String dni);
}