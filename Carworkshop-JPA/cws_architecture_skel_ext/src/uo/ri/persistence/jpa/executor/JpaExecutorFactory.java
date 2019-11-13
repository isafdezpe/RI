package uo.ri.persistence.jpa.executor;

import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.ComandExecutorFactory;

public class JpaExecutorFactory implements ComandExecutorFactory {

	@Override
	public CommandExecutor forExecutor() {
		return new JpaCommandExecutor();
	}

}
