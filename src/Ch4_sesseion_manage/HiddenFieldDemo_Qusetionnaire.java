package Ch4_sesseion_manage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Ch4_sesseion_manage.HiddenFieldDemo_Qusetionnaire",urlPatterns = "/questionnaire")
public class HiddenFieldDemo_Qusetionnaire extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Questionnaire</title>");
        out.println("</head>");
        out.println("<body>");

        String page = request.getParameter("page");//page请求参数就额定显示那一页问卷

        out.println("<form action='questionnaire' method='post'>");
        if (page == null) {
            out.println("问题一：<input type='text' name='p1q1'><br>");
            out.println("问题二：<input type='text' name='p1q2'><br>");
            out.println("<input type='submit' name='page' value='下一页'>");
        } else if ("下一页".equals(page)) {
            /*
             * 实作步骤1
             * */
            String p1q1 = request.getParameter("p1q1");
            String p1q2 = request.getParameter("p1q2");

            out.println("问题三：<input type='text' name='p2q1'><br>");

            //使用隐藏域发送第一页问卷答案
            out.println("<input type='hidden' name='p1q1' value ='"+p1q1+"'>");
            out.println("<input type='hidden' name='p1q2' value ='"+p1q2+"'>");

            out.println("<input type='submit' name='page' value='完成'>");
        } else if ("完成".equals(page)) {
            /*
             * 实作步骤2
             * */
            out.println(request.getParameter("p1q1") + "<br>");
            out.println(request.getParameter("p1q2") + "<br>");
            out.println(request.getParameter("p2q1") + "<br>");
        }
        out.println("</form>");

        out.println("</body>");
        out.println("</html>");
        out.close();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        processRequest(request, response);
    }
}
