package uo.ri.util;

import uo.ri.model.Mecanico;

public class MechanicBuilder implements Builder<Mecanico> {
	private String dni = Values.newDni();
	private String name = Values.newName();
	private String surname = Values.newSurname();

	@Override
	public Mecanico build() {
		return new Mecanico( dni, name, surname);
	}
	
	public MechanicBuilder withDni(String dni) {
		this.dni = dni;
		return this;
	}

	public MechanicBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public MechanicBuilder withSurname(String surname) {
		this.surname = surname;
		return this;
	}

}
