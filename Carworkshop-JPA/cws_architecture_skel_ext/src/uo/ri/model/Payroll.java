package uo.ri.model;

import java.time.Month;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.*;

import alb.util.date.Dates;

@Entity
@Table(name = "TNominas")
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date date;
	private double baseSalary;
	private double extraSalary;
	private double productivity;
	private double trienniums;
	private double irpf;
	private double socialSecurity;

	@ManyToOne
	private Contract contrato;

	Payroll() {
	}

	public Payroll(Contract contract, Date date) {
		super();
		if (!date.after(contract.getStartDate()))
			throw new IllegalArgumentException();
		Association.Percibir.link(contract, this);
		this.date = Dates.lastDayOfMonth(Dates.subMonths(date, 1));

		this.baseSalary = contrato.getBaseSalaryPerYear() / 14;

		if (Dates.month(this.date) == Month.JUNE.getValue()
				|| Dates.month(this.date) == Month.DECEMBER.getValue())
			this.extraSalary = contrato.getBaseSalaryPerYear() / 14;
		else
			this.extraSalary = 0;

		long diff = this.date.getTime() - contrato.getStartDate().getTime();
		int years = (int) ((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))
				/ 365);
		this.trienniums = years / 3
				* contrato.getContractCategory().getTrieniumSalary();

		double interventions = 0; // TODO: calcular las intervenciones del
									// último mes con repo

		this.productivity = interventions
				* contrato.getContractCategory().getProductivityPlus();

		this.irpf = getGrossTotal() * contrato.getIrpfPercent();

		this.socialSecurity = contrato.getBaseSalaryPerYear() / 12 * 0.05;
	}

	public Payroll(Contract contract, Date payrollDate, double interventions) {
		this(contract, payrollDate);
		this.productivity = interventions
				* contrato.getContractCategory().getProductivityPlus();
		this.irpf = getGrossTotal() * contrato.getIrpfPercent();
	}

	/**
	 * @return id de la nomina
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return copia de la fecha de generacion de la nomina
	 */
	public Date getDate() {
		return (Date) date.clone();
	}

	/**
	 * @return salario base
	 */
	public double getBaseSalary() {
		return baseSalary;
	}

	/**
	 * @return salario extra
	 */
	public double getExtraSalary() {
		return extraSalary;
	}

	/**
	 * @return plus de productivity
	 */
	public double getProductivity() {
		return productivity;
	}

	/**
	 * @return trienios
	 */
	public double getTriennium() {
		return trienniums;
	}

	/**
	 * @return irpf
	 */
	public double getIrpf() {
		return irpf;
	}

	/**
	 * @return seguridad social
	 */
	public double getSocialSecurity() {
		return socialSecurity;
	}

	/**
	 * Establece el contrato al que pertenece la nomina
	 * 
	 * @param contrato
	 */
	void _setContract(Contract contrato) {
		this.contrato = contrato;
	}

	/**
	 * @return contrato de la nomina
	 */
	public Contract getContract() {
		return contrato;
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
		result = prime * result
				+ ((contrato == null) ? 0 : contrato.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		Payroll other = (Payroll) obj;
		if (contrato == null) {
			if (other.contrato != null)
				return false;
		} else if (!contrato.equals(other.contrato))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
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
		return "Payroll [id=" + id + ", date=" + date + ", baseSalary="
				+ baseSalary + ", extraSalary=" + extraSalary
				+ ", productivity=" + productivity + ", trienniums="
				+ trienniums + ", irpf=" + irpf + ", socialSecurity="
				+ socialSecurity + ", contrato=" + contrato + "]";
	}

	/**
	 * @return salario bruto
	 */
	public double getGrossTotal() {
		return getBaseSalary() + getExtraSalary() + getProductivity()
				+ getTriennium();
	}

	/**
	 * @return descuentos
	 */
	public double getDiscountTotal() {
		return getIrpf() + getSocialSecurity();
	}

	/**
	 * @return salario neto
	 */
	public double getNetTotal() {
		return getGrossTotal() - getDiscountTotal();
	}

}
