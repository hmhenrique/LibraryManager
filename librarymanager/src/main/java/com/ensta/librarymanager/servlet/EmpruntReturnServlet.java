package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

/**
 * EmpruntReturnServelet makes the interaction between the services and the interface
 */
public class EmpruntReturnServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * allows you to display a loan return form, based on a <select> type field containing the
     * loan in progress.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if(action.equals("/emprunt_return")){
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            int id = -1;

            if (request.getParameter("id") != null){
                id = Integer.parseInt(request.getParameter("id"));
            } 

            try {
                if (id != -1){
                    request.setAttribute("id", id);
                    request.setAttribute("theEmprunt", empruntService.getListCurrent());
                } else{
                    request.setAttribute("theEmprunt", empruntService.getListCurrent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Submit to the .jsp:
            final RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_return.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * role of processing the loan return form from the data retrieved via the
     * previous form.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();

        try {
            if (request.getParameter("id") == null){
                throw new ServletException("Id number isn't available.");
            } else{
                empruntService.returnBook(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("theEmprunt", empruntService.getListCurrent());
            }
        } catch (Exception e) {
            throw new ServletException("Can't return the book.", e);
        }

        response.sendRedirect(request.getContextPath() + "/emprunt_list");
    }
    
}