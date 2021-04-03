package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

/**
 * DashboardServlet makes the interaction between the services and the front page
 */
public class DashboardServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * Show the system dashboard and the values of members, books and loans
     */
    @Override
    protected void doGet (final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final String action = request.getServletPath();

        if (action.equals("/dashboard")) {
            final MembreService membreService = MembreServiceImpl.getInstance();
            final LivreService livreService = LivreServiceImpl.getInstance();
            final EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            
            try {
                request.setAttribute("numberOfMembres", membreService.count());
                request.setAttribute("numberOfLivres", livreService.count());
                request.setAttribute("numberOfEmprunts", empruntService.count());
                request.setAttribute("currentEmprunts", empruntService.getListCurrent());
            } catch (final Exception e) {
                e.printStackTrace();
            }

            // Submit to the .jsp:
            final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
            dispatcher.forward(request, response);
            
        }
    }    
}