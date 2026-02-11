package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/igra/start")
public class IgraStart extends HttpServlet {

	public static List<Igra> listaIgara = new ArrayList<>();
	public static int brojIgara = 0;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Igra novaIgra = new Igra();
		brojIgara++;
		novaIgra.id = listaIgara.size();
		listaIgara.add(novaIgra);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/igra/play?id=" + novaIgra.id);
		//req.getRequestDispatcher("/igra/play?id=" + novaIgra.id).forward(req, resp);
	}
}
