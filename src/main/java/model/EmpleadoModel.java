package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.Categoria;
import entity.Empleado;
import util.MySqlDBConexion;

public class EmpleadoModel {

	public int insertaEmpleado(Empleado obj) {
		int salida = -1;
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			//1 Crear la conexion a la BD
			cn = MySqlDBConexion.getConexion();

			//2 Crear el SQL de insercion
			String sql = "INSERT INTO empleado (nombre, apellido, documento, correo, telefono, cargo, id_categoria, salario, fecha_contratacion, activo) VALUES (?,?,?,?,?,?,?,?,?,?)";

			//3 Crear el PreparedStatement
			ps = cn.prepareStatement(sql);
			ps.setString(1, obj.getNombre());
			ps.setString(2, obj.getApellido());
			ps.setString(3, obj.getDocumento());
			ps.setString(4, obj.getCorreo());
			ps.setString(5, obj.getTelefono());
			ps.setString(6, obj.getCargo());
			ps.setInt(7, obj.getCategoria().getIdCategoria());
			ps.setDouble(8, obj.getSalario());
			ps.setDate(9, java.sql.Date.valueOf(obj.getFechaContratacion()));
			ps.setBoolean(10, obj.isActivo());

			System.out.println("SQL: " + ps);

			//4 Ejecutar el SQL
			salida = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (cn != null)
					cn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return salida;
	}

	public List<Empleado> listaEmpleadoPorNombre(String nombre) {
		ArrayList<Empleado> salida = new ArrayList<Empleado>();

		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			//1 se crea conexion
			conn = MySqlDBConexion.getConexion();

			//2 se prepara la sentencia SQL (JOIN con categoria para traer la descripcion)
			String sql = "SELECT e.*, c.descripcion AS categoria_descripcion FROM empleado e "
					+ "INNER JOIN categoria c ON e.id_categoria = c.id_categoria "
					+ "WHERE e.nombre LIKE ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%" + nombre + "%");

			//3 se ejecuta la consulta
			rs = pstm.executeQuery();

			while (rs.next()) {
				Empleado obj = new Empleado();
				obj.setIdEmpleado(rs.getInt("id_empleado"));
				obj.setNombre(rs.getString("nombre"));
				obj.setApellido(rs.getString("apellido"));
				obj.setDocumento(rs.getString("documento"));
				obj.setCorreo(rs.getString("correo"));
				obj.setTelefono(rs.getString("telefono"));
				obj.setCargo(rs.getString("cargo"));
				obj.setSalario(rs.getDouble("salario"));
				obj.setFechaContratacion(rs.getDate("fecha_contratacion").toLocalDate());
				obj.setActivo(rs.getBoolean("activo"));

				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getInt("id_categoria"));
				categoria.setDescripcion(rs.getString("categoria_descripcion"));
				obj.setCategoria(categoria);

				salida.add(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return salida;
	}

	//Actualizar empleado
	public int actualizarEmpleado(Empleado obj) {
		int salida = -1;
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "update empleado set nombre=?, apellido=?, documento=?, correo=?, telefono=?, cargo=?, id_categoria=?, salario=?, fecha_contratacion=?, activo=? where id_empleado=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getNombre());
			pstm.setString(2, obj.getApellido());
			pstm.setString(3, obj.getDocumento());
			pstm.setString(4, obj.getCorreo());
			pstm.setString(5, obj.getTelefono());
			pstm.setString(6, obj.getCargo());
			pstm.setInt(7, obj.getCategoria().getIdCategoria());
			pstm.setDouble(8, obj.getSalario());
			pstm.setDate(9, java.sql.Date.valueOf(obj.getFechaContratacion()));
			pstm.setBoolean(10, obj.isActivo());
			pstm.setInt(11, obj.getIdEmpleado());
			System.out.println("SQL ==> " + pstm);
			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return salida;
	}

	//eliminar empleado fisicamente
	public int eliminarEmpleado(int idEmpleado) {
		int salida = -1;
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "delete from empleado where id_empleado=?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idEmpleado);
			System.out.println("SQL ==> " + pstm);
			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return salida;
	}

	//Busca por ID
	public Empleado buscarPorId(int idEmpleado) {
		Empleado obj = null;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = MySqlDBConexion.getConexion();
			String sql = "SELECT e.*, c.descripcion AS categoria_descripcion FROM empleado e "
					+ "INNER JOIN categoria c ON e.id_categoria = c.id_categoria "
					+ "WHERE e.id_empleado = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idEmpleado);
			System.out.println("SQL ==> " + pstm);
			rs = pstm.executeQuery();
			if (rs.next()) {
				obj = new Empleado();
				obj.setIdEmpleado(rs.getInt("id_empleado"));
				obj.setNombre(rs.getString("nombre"));
				obj.setApellido(rs.getString("apellido"));
				obj.setDocumento(rs.getString("documento"));
				obj.setCorreo(rs.getString("correo"));
				obj.setTelefono(rs.getString("telefono"));
				obj.setCargo(rs.getString("cargo"));
				obj.setSalario(rs.getDouble("salario"));
				obj.setFechaContratacion(rs.getDate("fecha_contratacion").toLocalDate());
				obj.setActivo(rs.getBoolean("activo"));

				Categoria categoria = new Categoria();
				categoria.setIdCategoria(rs.getInt("id_categoria"));
				categoria.setDescripcion(rs.getString("categoria_descripcion"));
				obj.setCategoria(categoria);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstm != null)
					pstm.close();
				if (conn != null)
					conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return obj;
	}

}
