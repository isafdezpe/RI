package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "TCategoriaContratos")
public class ContractCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;
	private double trieniumSalary;
	private double productivityPlus;

	@OneToMany(mappedBy = "categoriaContrato")
	private Set<Contract> contratos = new HashSet<>();

	ContractCategory() {
	}

	public ContractCategory(String name, double trienniumSalary,
			double productivityPlus) {
		super();
		this.name = name;
		this.trieniumSalary = trienniumSalary;
		this.productivityPlus = productivityPlus;
	}

	/**
	 * @return id de la categoria de contrato
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return nombre de la categoria
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el trienio
	 * 
	 * @param triennium
	 */
	public void setTrieniumSalary(double triennium) {
		this.trieniumSalary = triennium;
	}

	/**
	 * @return trienio
	 */
	public double getTrieniumSalary() {
		return trieniumSalary;
	}

	/**
	 * Establece el plus de productividad
	 * 
	 * @param productivity
	 */
	public void setProductivityPlus(double productivity) {
		this.productivityPlus = productivity;
	}

	/**
	 * @return plus de productividad
	 */
	public double getProductivityPlus() {
		return productivityPlus;
	}

	/**
	 * @return contratos pertenecientes a la categoria
	 */
	Set<Contract> _getContracts() {
		return contratos;
	}

	/**
	 * @return copia de los contratos
	 */
	public Set<Contract> getContracts() {
		return new HashSet<>(contratos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContractCategory other = (ContractCategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ContractCategory [id=" + id + ", name=" + name
				+ ", trieniumSalary=" + trieniumSalary + ", productivityPlus="
				+ productivityPlus + ", contratos=" + contratos + "]";
	}
}
