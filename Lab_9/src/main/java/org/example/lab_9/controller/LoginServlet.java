package org.example.lab_9.controller;

import org.example.lab_9.beans.Usuario;
import org.example.lab_9.dao.UsuarioDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet", ""})


public class LoginServlet extends HttpServlet {
    private UsuarioDao usuarioDao = new UsuarioDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            request.getSession().invalidate();
            response.sendRedirect("LoginServlet");
            return;
        }
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String pass = request.getParameter("pass");

        Usuario u = usuarioDao.login(correo, pass);
        if (u != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioSesion", u);
            response.sendRedirect("UsuariosServlet");
        } else {
            request.setAttribute("error", "Credenciales incorrectas");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }

}
