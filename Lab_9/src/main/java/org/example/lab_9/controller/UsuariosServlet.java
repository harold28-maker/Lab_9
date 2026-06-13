package org.example.lab_9.controller;

import org.example.lab_9.beans.Usuario;
import org.example.lab_9.dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet("/UsuariosServlet")
public class UsuariosServlet extends HttpServlet {
    private UsuarioDao usuarioDao = new UsuarioDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "listar" : request.getParameter("action");

        if (action.equals("listar")) {
            request.setAttribute("listaUsuarios", usuarioDao.listarUsuarios());
            request.getRequestDispatcher("/views/usuarios/lista.jsp").forward(request, response);
        } else if (action.equals("nuevo")) {
            request.getRequestDispatcher("/views/usuarios/nuevo.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String dni = request.getParameter("dni");
        String correo = request.getParameter("correo");
        String pass = request.getParameter("pass");

        // Validaciones del Servidor exigidas por la rúbrica
        String error = null;
        if(nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty()) {
            error = "Nombre y apellido son obligatorios.";
        } else if(dni == null || !dni.matches("\\d{8}")) {
            error = "El DNI debe tener 8 dígitos numéricos.";
        } else if(usuarioDao.existeDni(dni)) {
            error = "El DNI ya se encuentra registrado.";
        } else if(usuarioDao.existeCorreo(correo)) {
            error = "El Correo ya se encuentra registrado.";
        } else if(pass == null || pass.length() < 8 || !pass.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).+$")) {
            error = "La contraseña debe tener mínimo 8 caracteres con letras y números.";
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/views/usuarios/nuevo.jsp").forward(request, response);
        } else {
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setDni(dni);
            u.setCorreo(correo);
            u.setPass(pass);
            usuarioDao.registrarUsuario(u);
            response.sendRedirect("UsuariosServlet");
        }
    }
}
