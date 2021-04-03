package com.ensta.librarymanager.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ensta.librarymanager.services.*;

/**
 * EmpruntListServlet makes the interaction between the services and the interface
 */
public class EmpruntListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    /**
     * allows you to display the list of loans. By default, it only displays current loans. If the show parameter is specified and is
     * equal to "all", then all borrowings must be displayed
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        
        if (action.equals("/emprunt_list")) {
            EmpruntService empruntService = EmpruntServiceImpl.getInstance();
            
            try {
                if (request.getParameter("show") != null && request.getParameter("show").contains("all")){
                    request.setAttribute("empruntList", empruntService.getList());
                    request.setAttribute("show", "all");
                }else {
                    request.setAttribute("empruntList", empruntService.getListCurrent());
                    request.setAttribute("show", "current");
                }
                    
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Submit to the .jsp:
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/emprunt_list.jsp");
            dispatcher.forward(request, response);

        }
    } 
    
}