package org.example.lab_9.dao;

import org.example.lab_9.beans.Usuario;
import org.example.lab_9.db.ConexionDB;
import org.example.lab_9.beans.Transaccion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDao {
    public List<Transaccion> listarPorUsuario(int idUsuario) {
        List<Transaccion> lista = new ArrayList<>();
        String sql = "SELECT t.*, u.nombre, u.apellido FROM transacciones t " +
                "INNER JOIN usuarios u ON t.usuarios_idusuarios = u.idusuarios " +
                "WHERE t.usuarios_idusuarios = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaccion t = new Transaccion();
                    t.setIdtransacciones(rs.getInt("idtransacciones"));
                    t.setMonto(rs.getDouble("monto"));
                    t.setDescripcion(rs.getString("descripcion"));
                    t.setTitulo(rs.getString("titulo"));
                    t.setFecha(rs.getDate("fecha"));
                    t.setTipo(rs.getString("tipo"));

                    Usuario u = new Usuario();
                    u.setIdUsuarios(rs.getInt("usuarios_idusuarios"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    t.setUsuario(u);

                    lista.add(t);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean registrarTransaccion(Transaccion t) {
        String sql = "INSERT INTO transacciones (monto, descripcion, titulo, fecha, usuarios_idusuarios, tipo) VALUES (?, ?, ?, CURDATE(), ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, t.getMonto());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getTitulo());
            ps.setInt(4, t.getUsuario().getIdUsuarios());
            ps.setString(5, t.getTipo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean eliminarTransaccion(int idTransaccion, int idUsuarioSesion) {
        String sql = "DELETE FROM transacciones WHERE idtransacciones = ? AND usuarios_idusuarios = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTransaccion);
            ps.setInt(2, idUsuarioSesion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
}
