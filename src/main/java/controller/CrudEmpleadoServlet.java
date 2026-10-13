package controller;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.Categoria;
import entity.Empleado;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import model.EmpleadoModel;

@WebServlet("/crudEmpleadoAlias")
public class CrudEmpleadoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String metodo = req.getParameter("metodo");
		switch (metodo) {
		case "listaPorNombre": 		listaPorNombre(req, res); break;
		case "registra": 			registra(req, res); break;
		case "actualiza": 			actualiza(req, res); break;
		case "eliminacionFisica":	eliminacionFisica(req, res); break;
		case "eliminacionLogica":	eliminacionLogica(req, res); break;
		default:
			res.getWriter().write("{\"mensajeSalida\":\"Método no encontrado\"}");
		}
	}

	public void listaPorNombre(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		//1 Recibir el parametro del nombre
		String nombre = req.getParameter("nombre");

		//2 Crear un objeto EmpleadoModel
		EmpleadoModel model = new EmpleadoModel();
		List<Empleado> lista = model.listaEmpleadoPorNombre(nombre);

		//3 Enviar la lista de empleados al cliente en JSON
		enviarJson(res, lista);
	}

	public void registra(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		//1 Recibir los datos del formulario del JSP
		String nombre = req.getParameter("nombre");
		String apellido = req.getParameter("apellido");
		String documento = req.getParameter("documento");
		String correo = req.getParameter("correo");
		String telefono = req.getParameter("telefono");
		String cargo = req.getParameter("cargo");
		String categoria = req.getParameter("categoria");
		String salario = req.getParameter("salario");
		String fechaContratacion = req.getParameter("fecContratacion");

		//2 Crear un objeto Categoria y Empleado
		Categoria objCategoria = new Categoria();
		objCategoria.setIdCategoria(Integer.parseInt(categoria));

		Empleado objEmpleado = new Empleado();
		objEmpleado.setNombre(nombre);
		objEmpleado.setApellido(apellido);
		objEmpleado.setDocumento(documento);
		objEmpleado.setCorreo(correo);
		objEmpleado.setTelefono(telefono);
		objEmpleado.setCargo(cargo);
		objEmpleado.setCategoria(objCategoria);
		objEmpleado.setSalario(Double.parseDouble(salario));
		objEmpleado.setFechaContratacion(java.time.LocalDate.parse(fechaContratacion));
		objEmpleado.setActivo(true); // true = activo, false = inactivo

		//3 Crear un objeto EmpleadoModel
		EmpleadoModel model = new EmpleadoModel();
		model.insertaEmpleado(objEmpleado);
		List<Empleado> lista = model.listaEmpleadoPorNombre("");

		enviarJson(res, lista);
	}

	public void actualiza(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		//1 Recibir los datos del formulario del JSP
		String idEmpleado = req.getParameter("id");
		String nombre = req.getParameter("nombre");
		String apellido = req.getParameter("apellido");
		String documento = req.getParameter("documento");
		String correo = req.getParameter("correo");
		String telefono = req.getParameter("telefono");
		String cargo = req.getParameter("cargo");
		String categoria = req.getParameter("categoria");
		String salario = req.getParameter("salario");
		String fechaContratacion = req.getParameter("fecContratacion");
		String activo = req.getParameter("activo");

		//2 Crear un objeto Categoria y Empleado
		Categoria objCategoria = new Categoria();
		objCategoria.setIdCategoria(Integer.parseInt(categoria));

		Empleado objEmpleado = new Empleado();
		objEmpleado.setIdEmpleado(Integer.parseInt(idEmpleado));
		objEmpleado.setNombre(nombre);
		objEmpleado.setApellido(apellido);
		objEmpleado.setDocumento(documento);
		objEmpleado.setCorreo(correo);
		objEmpleado.setTelefono(telefono);
		objEmpleado.setCargo(cargo);
		objEmpleado.setCategoria(objCategoria);
		objEmpleado.setSalario(Double.parseDouble(salario));
		objEmpleado.setFechaContratacion(java.time.LocalDate.parse(fechaContratacion));
		objEmpleado.setActivo(activo.equals("1")); // 1 = activo, 0 = inactivo

		//3 Crear un objeto EmpleadoModel
		EmpleadoModel model = new EmpleadoModel();
		model.actualizarEmpleado(objEmpleado);
		List<Empleado> lista = model.listaEmpleadoPorNombre("");

		enviarJson(res, lista);
	}

	public void eliminacionFisica(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String idEmpleado = req.getParameter("idEmpleado");
		EmpleadoModel model = new EmpleadoModel();
		model.eliminarEmpleado(Integer.parseInt(idEmpleado));

		List<Empleado> lista = model.listaEmpleadoPorNombre("");

		enviarJson(res, lista);
	}

	public void eliminacionLogica(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		String idEmpleado = req.getParameter("idEmpleado");
		EmpleadoModel model = new EmpleadoModel();
		Empleado obj = model.buscarPorId(Integer.parseInt(idEmpleado));
		obj.setActivo(!obj.isActivo());
		model.actualizarEmpleado(obj);

		List<Empleado> lista = model.listaEmpleadoPorNombre("");

		enviarJson(res, lista);
	}

	//Construir el JSON mediante Gson modo pretty print y enviarlo al cliente
	private void enviarJson(ServletResponse res, List<Empleado> lista) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonSalida = gson.toJson(lista);

		System.out.println("Respuesta JSON: " + jsonSalida);

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(jsonSalida);
	}

}
