package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;


/**
 * MembreListServlet makes the interaction between the services and the interface
 */
public class MembreListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * allows to show a list of members
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/membre_list")){
            MembreService membreService = MembreServiceImpl.getInstance();
            try {
                request.setAttribute("listMembre", membreService.getList());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Submit to the .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_list.jsp");
            dispatcher.forward(request, response);
        }
    }

    
}