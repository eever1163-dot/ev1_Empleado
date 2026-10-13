package entity;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Empleado {
	private int idEmpleado;
	private String nombre;
	private String apellido;
	private String documento;
	private String correo;
	private String telefono;
	private String cargo;
	private double salario;
	private LocalDate fechaContratacion;
	private boolean activo;
	private Categoria categoria;
}
