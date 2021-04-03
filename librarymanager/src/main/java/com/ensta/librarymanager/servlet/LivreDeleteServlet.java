package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

/**
 * LivreDeleteServlet makes the interaction between the services and the interface
 */
public class LivreDeleteServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * used to display a form for deleting a book, based on a <select> type field
     * containing current loans.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/livre_delete")){
            LivreService livreService = LivreServiceImpl.getInstance();
            int id = -1;
            if (request.getParameter("id") != null){
                id = Integer.parseInt(request.getParameter("id"));
            }

            try {
                if (id != -1){
                    request.setAttribute("livre", livreService.getById(id));
                    request.setAttribute("id", id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Submit to the .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_delete.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * role of processing the deletion form of a book from the data retrieved via the
     * previous form.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();

        try {
            livreService.delete(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/livre_list");
    }
}