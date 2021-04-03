package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;
import com.ensta.librarymanager.models.Livre;

/**
 * LivreDetailsServlet makes the interaction between the services and the interface
 */
public class LivreDetailsServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * allows you to display the information of a book, in a form with pre-filled fields. This form
     * allows to update the information of the book. Below the form is displayed the information relating to the loan
     * current book (if applicable).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/livre_details")){
            LivreService livreService = LivreServiceImpl.getInstance();
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();

            try {
                request.setAttribute("actuelEmprunts", empruntService.getListCurrentByLivre(Integer.parseInt(request.getParameter("id"))));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                request.setAttribute("livre", livreService.getById(Integer.parseInt(request.getParameter("id"))));
            } catch (Exception e) {
                new ServletException("It's not possible to get the chosen book", e);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_details.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * role of processing the book information update form. In the event of a problem in the
     * process, you will need to throw a ServletException.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        try {
            if (request.getParameter("titre") == "" || request.getParameter("titre") == null){
                throw new ServletException("The title is empty.");
            } else{
                Livre livreAdd = livreService.getById(Integer.parseInt(request.getParameter("id")));
                livreAdd.setAuteur(request.getParameter("auteur"));
                livreAdd.setTitre(request.getParameter("titre"));
                livreAdd.setISBN(request.getParameter("isbn"));
                livreService.update(livreAdd);
                request.setAttribute("id", livreAdd.getId());
                request.setAttribute("actuelEmprunts", empruntService.getListCurrentByLivre(livreAdd.getId()));
        
                response.sendRedirect(request.getContextPath() + "/livre_details?id=" + livreAdd.getId());
            }
        } catch (Exception e) {
            new ServletException("Error in updating. The title is empty.", e);
            request.setAttribute("erreurMessage", "Title is empty.");
        }
    }
}