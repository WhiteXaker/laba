package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by omatikaya on 23/12/2016.
 */
public class ResultDeletion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean hasErrors = false;
        String res = "";
        int id = 0;
        try {
            res = req.getParameter("res");
            id = Integer.parseInt(req.getParameter("id"));
        }
        catch (Exception e) {
            hasErrors = true;
        }
        if (res.equals("yes") || res.equals("no")) {
            hasErrors = false;
        } else {
            hasErrors = true;
        }

        if (hasErrors) {
            doSetError(resp);
            return;
        }

        if (res.equals("yes")) {
            MysqlJdbcTemplate jdbcTemplate = new MysqlJdbcTemplate();
            String sql = "Update `order` set order_status = 'deleted' where order_id = " + id;
            try {
                jdbcTemplate.update(sql);
            } catch (SQLException e) {
                hasErrors = true;
            }
        }

        if (hasErrors) {
            doSetError(resp);
            return;
        }


        String reply = "<html><head><meta http-equiv=refresh content=\"1;url=http://localhost:8080/\"></head></html>";
        resp.getOutputStream().write( reply.getBytes("UTF-8") );
        resp.setContentType("text/html; charset=UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setStatus( HttpServletResponse.SC_OK );
    }

    protected void doSetError( HttpServletResponse response) throws IOException {
        String reply = "{\"error\":1}";
        response.getOutputStream().write( reply.getBytes("UTF-8") );
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus( HttpServletResponse.SC_OK );
    }
}
