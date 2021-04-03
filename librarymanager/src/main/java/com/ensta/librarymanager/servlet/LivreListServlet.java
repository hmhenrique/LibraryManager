package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

/**
 * LivreListServlet makes the interaction between the services and the interface
 */
public class LivreListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * allows to show a list of books
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/livre_list")){
            LivreService livreService = LivreServiceImpl.getInstance();
            try {
                request.setAttribute("listLivre", livreService.getList());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Submit to the .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_list.jsp");
            dispatcher.forward(request, response);
        }
    }
}