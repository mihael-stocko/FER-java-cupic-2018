package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/igra/play")
public class IgraPlay extends HttpServlet {

	String poruka;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if("true".equals(req.getParameter("debug"))) {
			req.setAttribute("secretNumber", IgraStart.listaIgara.get(Integer.parseInt(req.getParameter("id"))).zamisljeniBroj);
		} else {
			req.setAttribute("secretNumber", null);
		}
		
		
		String s = IgraStart.listaIgara.get(Integer.parseInt(req.getParameter("id"))).poruka;
		req.setAttribute("poruka", s);
		req.setAttribute("id", req.getParameter("id"));
		req.setAttribute("brojPokusaja", 8-IgraStart.listaIgara.get(Integer.parseInt(req.getParameter("id"))).brojPokusaja);
		
		req.getRequestDispatcher("/WEB-INF/igra.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		
		IgraStart.listaIgara.get(id).brojPokusaja--;
		
		if(IgraStart.listaIgara.get(id).brojPokusaja == 0) {
			IgraStart.brojIgara--;
			req.setAttribute("tajniBroj", IgraStart.listaIgara.get(id).zamisljeniBroj);
			req.getRequestDispatcher("/WEB-INF/fail.jsp").forward(req, resp);
			return;
		}
		
		Igra i = IgraStart.listaIgara.get(id);
		int pokusaj = 0;
		try {
			pokusaj = Integer.parseInt(req.getParameter("broj"));
			if(pokusaj < 1 || pokusaj > 100) throw new Exception(); 
			
			if(pokusaj == IgraStart.listaIgara.get(id).zamisljeniBroj) {
				IgraStart.brojIgara--;
				req.getRequestDispatcher("/WEB-INF/bravo.jsp").forward(req, resp);
				return;
			}
			
			if(pokusaj < IgraStart.listaIgara.get(id).zamisljeniBroj) {
				i.poruka = "Broj je veci.";
				
			} else {
				i.poruka = "Broj je manji.";
			}
		}catch(Exception e) {
			i.poruka = "Greška";
			i.brojPokusaja++;
		}
		
		req.setAttribute("id", req.getParameter("id"));
		req.setAttribute("secretNumber", IgraStart.listaIgara.get(Integer.parseInt(req.getParameter("id"))).zamisljeniBroj);
		req.setAttribute("brojPokusaja", IgraStart.listaIgara.get(Integer.parseInt(req.getParameter("id"))).brojPokusaja);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/igra/play?id=" + id);
		//req.getRequestDispatcher("/WEB-INF/igra.jsp").forward(req, resp);
	}
}
