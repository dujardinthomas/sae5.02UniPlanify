import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Compteur")
public class Compteur extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String CALENDAR_TEMPLATE = "<html><head><title>Compteur</title></head><body>%s</body></html>";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Map<String, Integer> counters = (Map<String, Integer>) session.getAttribute("counters");

        if (counters == null) {
            counters = new HashMap<>();
            session.setAttribute("counters", counters);
        }

        String day = request.getParameter("day");
        if (day != null) {
            Integer counter = counters.get(day);
            if (counter == null) {
                counter = 1;
            } else {
                counter++;
            }
            counters.put(day, counter);
        }

        PrintWriter out = response.getWriter();
        String calendarHtml = generateCalendar(counters);
        out.println(String.format(CALENDAR_TEMPLATE, calendarHtml));
    }

    private String generateCalendar(Map<String, Integer> counters) {
        // Generate your calendar HTML here, including counters for each day.
        // You can use a loop to create the calendar grid and insert counters where needed.
        // For simplicity, I'm providing a basic example:

        StringBuilder html = new StringBuilder("<table border='1'><tr><th>Day</th><th>Counter</th></tr>");

        for (int day = 1; day <= 31; day++) {
            String dayStr = String.valueOf(day);
            Integer counter = counters.get(dayStr);
            counter = (counter == null) ? 0 : counter;
            html.append("<tr><td><a href='Compteur?day=").append(dayStr).append("'>").append(dayStr).append("</a></td><td>").append(counter).append("</td></tr>");
        }

        html.append("</table>");
        return html.toString();
    }
}
