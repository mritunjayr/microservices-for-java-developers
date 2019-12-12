package com.mritun.backend;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

@WebServlet(urlPatterns = {"/api/backend"})
public class BackendHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        String greeting = req.getParameter("greeting");
        ResponseDTO response = new ResponseDTO();
        response.setGreeting(greeting + " from cluster backend");
        response.setTime(System.currentTimeMillis());
        response.setIp(getIp());
        PrintWriter writer = resp.getWriter();
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, response);
    }

    private String getIp() {
        String hostname = null;
        try {
            hostname = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostname = "unknown";
        }
        return hostname;
    }
}
