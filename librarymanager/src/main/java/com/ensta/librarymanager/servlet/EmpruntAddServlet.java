package com.ensta.librarymanager.servlet;

import java.io.IOException;

import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;


/**
 * DashboardServlet makes the interaction between the services and the interface
 */
public class EmpruntAddServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * allows you to display a loan addition form, based on two <select> type fields which should not
     * contain respectively only books available for loan and members who can make a new loan 
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        
        if (action.equals("/emprunt_add")){
            MembreService membreService = MembreServiceImpl.getInstance();
            LivreService livreService = LivreServiceImpl.getInstance();
            try {
                request.setAttribute("listMembreDispo", membreService.getListMembreEmpruntPossible());
                request.setAttribute("listLivreDispo", livreService.getListDispo());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Submit to the .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_add.jsp");
            dispatcher.forward(request, response);
        }
    }
    

    /**
     * has the role of processing the form for creating a new loan from the data retrieved via
     * the previous form.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        MembreService membreService = MembreServiceImpl.getInstance();
        LivreService livreService = LivreServiceImpl.getInstance();

        try {
            if (request.getParameter("idMembre") == null || request.getParameter("idLivre") == null){
                throw new ServletException("Can't add a new loan, there is no book, or member in the request.");
            } else{
                empruntService.create(Integer.parseInt(request.getParameter("idMembre")), Integer.parseInt(request.getParameter("idLivre")), LocalDate.now());
						
				request.setAttribute("listEmprunt", empruntService.getListCurrent());
            }

            request.setAttribute("listMembreDispo", membreService.getListMembreEmpruntPossible());
            request.setAttribute("listLivreDispo", livreService.getListDispo());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreurMessage", e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/emprunt_list");
    }
}