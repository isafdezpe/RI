package uo.ri.persistence.jpa;

import uo.ri.business.repository.ContractCategoryRepository;
import uo.ri.model.ContractCategory;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class ContractCategoryJpaRepository extends
		BaseRepository<ContractCategory> implements ContractCategoryRepository {

	@Override
	public ContractCategory findByName(String name) {
		return Jpa.getManager()
				.createNamedQuery("ContractCategory.findByName",
						ContractCategory.class)
				.setParameter(1, name)
				.getResultList().stream()
				.findFirst()
				.orElse(null);
	}

}
