package Gossip_exercises.controller;

import Gossip_exercises.model.UserService;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * --------ch4----------
 * (1)确保登陆用户才能新添信息，检查HttpSession中是否右"login"属性
 * (2)字数合法，保存到txt，成功后重定向到会员页
 * (3)否则转发会员页，以显示字数应为140字内提示
 * (4)简化流程，若无信息直接请求此servlet，也直接重定向到会员页
 */

//ch5：重构前
//@WebServlet(name = "Gossip_exercises.controller.Message",urlPatterns = "/message.do")

//重构后：使用初始参数
@WebServlet(
        urlPatterns={"/message.do"},
        initParams={
                @WebInitParam(name = "SUCCESS_VIEW", value = "member.view"),
                @WebInitParam(name = "ERROR_VIEW", value = "member.view"),
            }
)
public class Message extends HttpServlet {
    /*ch5：重构前
    private final String USERS = "C:\\Users\\54234\\Documents\\GitHub\\NEU_JavaWebProjet\\users";
    private final String LOGIN_VIEW = "index.html";
    private final String SUCCESS_VIEW = "member.view";
    private final String ERROR_VIEW = "member.view";
    */

    //重构后:已在标注中声明，使用init()加载，从config获取
    private String LOGIN_VIEW;
    private String SUCCESS_VIEW;
    private String ERROR_VIEW;
    @Override
    public void init() throws ServletException {
        LOGIN_VIEW = getServletConfig().getInitParameter("LOGIN_VIEW");
        SUCCESS_VIEW = getServletConfig().getInitParameter("SUCCESS_VIEW");
        ERROR_VIEW = getServletConfig().getInitParameter("ERROR_VIEW");
    }
    
    
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response) 
                             throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        UserService userService = (UserService) getServletContext().getAttribute("userService");

        //(1):确保无login属性，则重定向到登录页
        if(request.getSession().getAttribute("login")==null){
            response.sendRedirect(LOGIN_VIEW);
            return;
        }
        String blabla =request.getParameter("blabla");
        //(2),(3):用户留言符合格式，保存；否则转发到会员页
        if(blabla!=null &&blabla.length() != 0){
            if(blabla.length()<140){
                String username =
                        (String) request.getSession().getAttribute("login");
                userService.addMessage(username,blabla);
                response.sendRedirect(SUCCESS_VIEW);
            }else{
                request.getRequestDispatcher(ERROR_VIEW).forward(request,response);
            }
        }else{
            //(4):无信息则重定向到会员网页
            response.sendRedirect(ERROR_VIEW);
        }
        /*
         * 实作步骤1到4
         *
         */
	}

	/* ---------------------ch5：Servlet进阶API 重构，该功能将由UserService提供----------------------

    private void addMessage(String username, String blabla) throws IOException {
        //信息保存到.txt，以日期做文件名
        String file = USERS + "/" + username + "/" + new Date().getTime() + ".txt";
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(blabla);
        writer.close();
    }

    */
}
