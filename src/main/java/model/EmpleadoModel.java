package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
			String sql = "INSERT INTO empleado (nombre, apellido, documento, correo, telefono, cargo, salario, fecha_contratacion, activo) VALUES (?,?,?,?,?,?,?,?,?)";

			//3 Crear el PreparedStatement
			ps = cn.prepareStatement(sql);
			ps.setString(1, obj.getNombre());
			ps.setString(2, obj.getApellido());
			ps.setString(3, obj.getDocumento());
			ps.setString(4, obj.getCorreo());
			ps.setString(5, obj.getTelefono());
			ps.setString(6, obj.getCargo());
			ps.setDouble(7, obj.getSalario());
			ps.setDate(8, java.sql.Date.valueOf(obj.getFechaContratacion()));
			ps.setBoolean(9, obj.isActivo());

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

			//2 se prepara la sentencia SQL
			String sql = "SELECT * FROM empleado WHERE nombre LIKE ?";
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

}
