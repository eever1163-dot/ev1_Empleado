package controller;

import java.io.IOException;

import entity.Categoria;
import entity.Empleado;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.EmpleadoModel;

@WebServlet("/registraEmpleadoAlias")
public class RegistraEmpleadoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1 Recibir los datos del formulario del JSP
		String nombre = req.getParameter("nombre");
		String apellido = req.getParameter("apellido");
		String documento = req.getParameter("documento");
		String correo = req.getParameter("correo");
		String telefono = req.getParameter("telefono");
		String cargo = req.getParameter("cargo");
		String categoria = req.getParameter("categoria");
		String salario = req.getParameter("salario");
		String fechaContratacion = req.getParameter("fecContratacion");

		System.out.println("Datos recibidos: " + nombre + " - " + apellido + " - " + documento + " - " + correo
				+ " - " + telefono + " - " + cargo + " - " + categoria + " - " + salario + " - " + fechaContratacion);

		// 2 Crear un objeto Categoria
		Categoria objCategoria = new Categoria();
		objCategoria.setIdCategoria(Integer.parseInt(categoria));

		// 3 Crear un objeto Empleado
		Empleado empleado = new Empleado();
		empleado.setNombre(nombre);
		empleado.setApellido(apellido);
		empleado.setDocumento(documento);
		empleado.setCorreo(correo);
		empleado.setTelefono(telefono);
		empleado.setCargo(cargo);
		empleado.setCategoria(objCategoria);
		empleado.setSalario(Double.parseDouble(salario));
		empleado.setFechaContratacion(java.time.LocalDate.parse(fechaContratacion));
		empleado.setActivo(true);

		// 4 Crear un objeto EmpleadoModel
		EmpleadoModel model = new EmpleadoModel();
		int salida = model.insertaEmpleado(empleado);

		String mensajeSalida = (salida > 0) ? "Empleado registrado correctamente (OK)" : "Error al registrar el empleado";

		// 5 Enviar una respuesta al cliente en JSON al jquery
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write("{\"mensajeSalida\":\"" + mensajeSalida + "\"}");
	}
}
