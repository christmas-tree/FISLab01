package com.fis.humans.tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandler {
    public static void handle(HttpServletRequest request, HttpServletResponse response, int errorCode, String errorMessage) throws ServletException, IOException {
        response.setStatus(errorCode);
        request.setAttribute("errorCode", errorCode);
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/error.jsp").forward(request, response);
    }
}