package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

/**
 * LivreAddServlet makes the interaction between the services and the interface
 */
public class LivreAddServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * Makes the book creation
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/livre_add")){

            // Submit to the .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/livre_add.jsp");
            dispatcher.forward(request, response);
        }
    }   
    
    /**
     * Makes the id relation and call livre_details
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LivreService livreService = LivreServiceImpl.getInstance();
        
        try {
            if (request.getParameter("titre") == null){
                throw new ServletException("Can't create because title is empty");
            }else{
                int id = livreService.create(request.getParameter("titre"), request.getParameter("auteur"), request.getParameter("isbn"));
                request.setAttribute("id", id);

                response.sendRedirect(request.getContextPath() + "/livre_details?id=" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}