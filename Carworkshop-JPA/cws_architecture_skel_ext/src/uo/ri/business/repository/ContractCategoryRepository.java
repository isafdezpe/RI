package uo.ri.business.repository;

import uo.ri.model.ContractCategory;

public interface ContractCategoryRepository
		extends Repository<ContractCategory> {

	/**
	 * 
	 * @param name
	 * @return contract category identified by name
	 */
	ContractCategory findByName(String name);

}
