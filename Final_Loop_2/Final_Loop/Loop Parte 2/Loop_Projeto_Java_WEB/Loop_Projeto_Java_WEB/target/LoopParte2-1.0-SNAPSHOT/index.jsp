<%-- 
    Document   : index
    Created on : 14 de ago. de 2024, 20:38:06
    Author     : Quiqu
--%>

<%@page import="java.util.List"%>
<%@page import="com.mycompany.loopparte1.modelo.dao.PedidoDAO" %>
<%@page import="com.mycompany.loopparte1.modelo.dao.Pedido_ItensDAO" %>
<%@page import="com.mycompany.loopparte1.modelo.entidade.Pedido" %>
<%@page import="com.mycompany.loopparte1.modelo.entidade.Pedido_Itens" %>
<%@page import="com.mycompany.loopparte1.modelo.dao.DesenvolvedoraDAO"%>
<%@page import="com.mycompany.loopparte1.modelo.dao.GameDAO"%>
<%@page import="com.mycompany.loopparte1.modelo.entidade.Desenvolvedora"%>
<%@page import="com.mycompany.loopparte1.modelo.entidade.Game"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="menu.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <main>

            <div id="principal">

                <section class="hero">
                    <header class="hero-conteudo">
                        <span class="destaque">novidade</span>
                        <h1 class="titulo">Forza Horizon 5</h1>

                        <h2 class="subtitulo">Conquiste as ruas da europa na mais alta velocidade e adrenalina!</h2>
                        <button class="bannerButton">Adquirir agora</button>
                    </header>
                </section>

            </div>


            <section class="jogosLista">

                <div class="jogosheader">
                    <h3 class="lista-titulo">Games</h3>
                </div>

                <div class="jogo-card-container">

                    <% GameDAO gameDAO = new GameDAO();
                        List<Game> games = gameDAO.buscarTodas();
               for (Game game : games) { %>

                    <div class="jogo-card">

                        <p><%= game.getTitulo() %></p>

                        <form id="cadastroForm" name="cadastroForm" action="${pageContext.request.contextPath}/FinalizarCompra" method="post">
                            <input type="hidden" name="game" value="${game.idGame}" />
                            <p><label>Quantidade:</label><input type="number" name="quantidade" value="${quantidade}" class="inputpequeno" /></p>
                            <button class="botaoComprar" type="submit" value="Comprar">Comprar</button>
                        </form>
                       

                    </div>

                    <% } %>

                </div>

            </section>

        </main>

    </body>
</html>
