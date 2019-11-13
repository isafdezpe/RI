package uo.ri.persistence;

import java.sql.Connection;

public interface SQLGateway {

	/**
	 * Establece la conexión con la base de datos
	 * @param c conexión
	 */
	void setConnection(Connection c);
}
