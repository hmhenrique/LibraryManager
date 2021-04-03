package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;
import com.ensta.librarymanager.models.Membre;
import com.ensta.librarymanager.models.Abonnement;

/**
 * MembreDetailsServlet makes the interaction between the services and the interface
 */
public class MembreDetailsServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * allows you to display a member's information, in a form with pre-filled fields. This form
     * allows you to update the member's information. Below the form is displayed the information relating to the loan
     * member's current (if applicable).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/membre_details")){
            MembreService membreService = MembreServiceImpl.getInstance();
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();

            try {
                request.setAttribute("actuelEmprunts", empruntService.getListCurrentByMembre(Integer.parseInt(request.getParameter("id"))));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            try {
                request.setAttribute("membre", membreService.getById(Integer.parseInt(request.getParameter("id"))));
            } catch (Exception e) {
                new ServletException("It's not possible to get the chosen member", e);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_details.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * role of processing the member's information update form. 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreService membreService = MembreServiceImpl.getInstance();
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        try {
            if (request.getParameter("prenom") == "" || request.getParameter("prenom") == null || request.getParameter("nom") == "" || request.getParameter("nom") == null){
                throw new ServletException("Any name field are empty");
            } else{
                Membre membreAdd = membreService.getById(Integer.parseInt(request.getParameter("id")));

                membreAdd.setPrenom(request.getParameter("prenom"));
                membreAdd.setNom(request.getParameter("nom"));
                membreAdd.setEmail(request.getParameter("email"));
                membreAdd.setTelephone(request.getParameter("telephone"));
                if (request.getParameter("abonnement").equals("BASIC")){
                    membreAdd.setAbonnement(Abonnement.BASIC);
                } else if (request.getParameter("abonnement").equals("PREMIUM")){
                    membreAdd.setAbonnement(Abonnement.PREMIUM);
                } else if (request.getParameter("abonnement").equals("VIP")){
                    membreAdd.setAbonnement(Abonnement.VIP);
                } 
                membreService.update(membreAdd);
                request.setAttribute("id", membreAdd.getId());
                request.setAttribute("actuelEmprunts", empruntService.getListCurrentByMembre(membreAdd.getId()));
                
                response.sendRedirect(request.getContextPath() + "/membre_details?id=" + membreAdd.getId());
            }
        } catch (Exception e) {
            new ServletException("Error in updating. The name is empty.", e);
            request.setAttribute("erreurMessage", "Name is empty");
        }

    }
    
}