package com.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by omatikaya on 23/12/2016.
 */
public class UserDeletion extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean hasErrors = false;
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        }
        catch (Exception e) {
            hasErrors = false;
        }
        if (hasErrors) {
            doSetError(resp);
            return;
        }

        String reply = "<html>\n" +
                "<body align=center>\n" +
                "<h1>Вы точно хотите удалить покупателя #"  + id + "?</h1>" +
                "<p><a href=/result_user?res=yes&id=" + id + ">ДА</a>   <a href=/result?res=no&id=" + id + ">НЕТ</a>" +
                "</body>\n" +
                "</html>";
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
