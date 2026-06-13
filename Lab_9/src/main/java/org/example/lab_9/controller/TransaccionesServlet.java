package org.example.lab_9.controller;

import org.example.lab_9.beans.Usuario;
import org.example.lab_9.beans.Transaccion;
import org.example.lab_9.dao.TransaccionDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/TransaccionesServlet")
public class TransaccionesServlet extends HttpServlet {
    private TransaccionDao transaccionDao = new TransaccionDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "listar" : request.getParameter("action");
        Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuarioSesion");

        if (action.equals("listar")) {
            request.setAttribute("listaTransacciones", transaccionDao.listarPorUsuario(usuarioSesion.getIdUsuarios()));
            request.getRequestDispatcher("/views/transacciones/lista.jsp").forward(request, response);
        } else if (action.equals("nuevo")) {
            request.getRequestDispatcher("/views/transacciones/nuevo.jsp").forward(request, response);
        } else if (action.equals("borrar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            transaccionDao.eliminarTransaccion(id, usuarioSesion.getIdUsuarios());
            response.sendRedirect("TransaccionesServlet");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titulo = request.getParameter("titulo");
        String tipo = request.getParameter("tipo");
        String descripcion = request.getParameter("descripcion");
        String montoStr = request.getParameter("monto");
        Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuarioSesion");

        double monto = 0;
        String error = null;

        try {
            monto = Double.parseDouble(montoStr);
            if (monto <= 0) error = "El monto debe ser mayor a 0.";
        } catch (Exception e) {
            error = "El monto debe ser un número válido.";
        }

        if (titulo == null || titulo.isEmpty() || tipo == null || tipo.isEmpty()) {
            error = "Título y Tipo son campos requeridos.";
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/views/transacciones/nuevo.jsp").forward(request, response);
        } else {
            Transaccion t = new Transaccion();
            t.setTitulo(titulo);
            t.setTipo(tipo);
            t.setDescripcion(descripcion);
            t.setMonto(monto);
            t.setUsuario(usuarioSesion);

            transaccionDao.registrarTransaccion(t);
            response.sendRedirect("TransaccionesServlet");
        }
    }
}
