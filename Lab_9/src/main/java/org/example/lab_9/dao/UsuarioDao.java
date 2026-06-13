package org.example.lab_9.dao;

import org.example.lab_9.beans.Usuario;
import org.example.lab_9.db.ConexionDB;
import org.example.lab_9.db.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    public Usuario login(String correo, String password) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND pass = ?";
        String hashed = PasswordUtil.hashPassword(password);
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, hashed);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuarios(rs.getInt("idusuarios"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setDni(rs.getString("dni"));
                    u.setCorreo(rs.getString("correo"));
                    return u;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = ConexionDB.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuarios(rs.getInt("idusuarios"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setDni(rs.getString("dni"));
                u.setCorreo(rs.getString("correo"));
                lista.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean registrarUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios (nombre, apellido, pass, dni, correo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, PasswordUtil.hashPassword(u.getPass()));
            ps.setString(4, u.getDni());
            ps.setString(5, u.getCorreo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean existeDni(String dni) {
        String sql = "SELECT 1 FROM usuarios WHERE dni = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { return false; }
    }

    public boolean existeCorreo(String correo) {
        String sql = "SELECT 1 FROM usuarios WHERE correo = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { return false; }
    }
}
