package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;
import com.ensta.librarymanager.models.Abonnement;

/**
 * MembreAddServlet makes the interaction between the services and the interface
 */
public class MembreAddServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * Makes the member creation
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/membre_add")){

            // Submit gathered information th the appropriate .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_add.jsp");
            dispatcher.forward(request, response);
        }
    }   
    

    /**
     * Makes the id relation and call membre_details
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();
        
        try {
            if (request.getParameter("nom") == null || request.getParameter("nom") == "" || request.getParameter("prenom") == null  || request.getParameter("prenom") == "" ){
                throw new ServletException("Can't create because any name field is empty");
            }else{
                int id = membreService.create(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("adresse"), request.getParameter("email"), request.getParameter("telephone"), Abonnement.BASIC);
                request.setAttribute("id", id);
                response.sendRedirect(request.getContextPath() + "/membre_details?id=" + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}