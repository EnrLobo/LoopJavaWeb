/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loopparte1.servico;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Quiqu
 */
@WebFilter(urlPatterns = {"/*"})
public class AuthFilter implements Filter{
    public AuthFilter(){
    
}
    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException{
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
    
       if(path.startsWith("/public/") || path.equalsIgnoreCase("/login.jsp/") 
               || path.equalsIgnoreCase("/com/mycompany/loopparte1/controlador/UsuarioControlador")
               || path.equalsIgnoreCase("/index.jsp") || path.equalsIgnoreCase("/menu.jsp") || path.equalsIgnoreCase("/CadastroUsuario.jsp")
               || path.equalsIgnoreCase("/login.jsp")|| path.contains("/Assets")
               || path.contains("/Assets")|| path.contains("/CSS/") || path.contains("/js/") || path.contains("/imagens/")){
            chain.doFilter(request, response);
        }else{
            if(httpRequest.getSession().getAttribute("user") == null){
                httpResponse.sendRedirect("/loopparte1/login.jsp");
            }else{
                chain.doFilter(request, response);
            }
        }
    }
    
    @Override
    public void destroy(){
        
    }

   
    
}


