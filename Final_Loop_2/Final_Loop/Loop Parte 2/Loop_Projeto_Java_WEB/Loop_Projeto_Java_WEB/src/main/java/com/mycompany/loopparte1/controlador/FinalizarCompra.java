/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.loopparte1.controlador;

import com.mycompany.loopparte1.modelo.dao.DesenvolvedoraDAO;
import com.mycompany.loopparte1.modelo.dao.GameDAO;
import com.mycompany.loopparte1.modelo.dao.MetodoPagamentoDAO;
import com.mycompany.loopparte1.modelo.dao.PedidoDAO;
import com.mycompany.loopparte1.modelo.dao.Pedido_ItensDAO;
import com.mycompany.loopparte1.modelo.dao.UsuarioDAO;
import com.mycompany.loopparte1.modelo.entidade.Desenvolvedora;
import com.mycompany.loopparte1.modelo.entidade.Game;
import com.mycompany.loopparte1.modelo.entidade.MetodoPagamento;
import com.mycompany.loopparte1.modelo.entidade.Pedido;
import com.mycompany.loopparte1.modelo.entidade.Pedido_Itens;
import com.mycompany.loopparte1.modelo.entidade.Usuario;
import com.mycompany.loopparte1.servico.ConverteData;
import com.mycompany.loopparte1.servico.WebConstantes;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;



/**
 *
 * @author João Henrique
 */
@WebServlet("/FinalizarCompra")
public class FinalizarCompra extends HttpServlet {

    private PedidoDAO pedidoDAO = new PedidoDAO();
    private Pedido_ItensDAO pedidoItensDAO = new Pedido_ItensDAO();
    private GameDAO gameDAO = new GameDAO();
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private MetodoPagamentoDAO pagamentoDAO = new MetodoPagamentoDAO();
    String user="";
    private ConverteData converte = new ConverteData();
    LocalDate dataAtual = LocalDate.now();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pega o usuário logado da sessão
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("user");
        
        user =  request.getParameter("user");
        

        // Captura o ID do jogo selecionado e a quantidade
        int gameId = Integer.parseInt(request.getParameter("game"));
        int quantidade = Integer.parseInt(request.getParameter("quantidade"));
        Calendar calendar = Calendar.getInstance();
        Date data_pedido = (Date) calendar.getTime();
        
        // Busca o jogo selecionado
        Game game = gameDAO.buscarPorId(gameId);


        // Calcula o valor total da compra
        double valorTotal = game.getPreco() * quantidade;

        // Verifica se o usuário tem saldo suficiente
        if (usuario.getPontos()>= valorTotal) {
            
            // Cria o pedido
            Pedido pedido = new Pedido();
            pedido.getUser_id().setIdUsuario(Integer.valueOf(user));
            pedido.getPagamento_id().setIdPaga(1);
            pedido.setData_pedido(converte.converteCalendario(data_pedido));
            pedidoDAO.salvar(pedido); // Insere o pedido no banco

            // Insere o item do pedido
            Pedido_Itens pedidoItens = new Pedido_Itens();
            pedidoItens.getPedido_id().setIdPedido(pedido.getIdPedido());
            pedidoItens.getGame_id().setIdGame(gameId);
            pedidoItens.setQuantidade(quantidade);
            pedidoItens.setValor(valorTotal);
            pedidoItensDAO.salvar(pedidoItens); // Insere o item do pedido

            // Desconta o valor do saldo do usuário
            usuario.setPontos(usuario.getPontos() - valorTotal);
            usuarioDAO.atualizarSaldo(usuario); // Atualiza o saldo no banco

            // Atualiza a sessão com o novo saldo
            session.setAttribute("user", usuario);

            // Redireciona para uma página de confirmação ou resumo do pedido
            response.sendRedirect("resumoPedido.jsp");
        } else {
            // Caso o saldo seja insuficiente, redirecione para uma página de erro
            request.setAttribute("mensagemErro", "Saldo insuficiente para completar a compra.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        }
    }
}

