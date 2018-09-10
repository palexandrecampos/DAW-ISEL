package pt.isel.daw.G8.Web.Application.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.json.*;
import pt.isel.daw.G8.Web.Application.data.ICostumerRepository;
import pt.isel.daw.G8.Web.Application.model.Costumer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class OIDCFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(OIDCFilter.class);
    private static final String AUTHORIZATION_SERVER_INTROSPECT = "http://35.197.230.96/openid-connect-server-webapp/introspect";
    private static final String CLIENT_ID = "daw";
    private static final String CLIENT_SECRET = "secret";
    private static final String BASE64 = "ZGF3OnNlY3JldA==";

    @Autowired
    private ICostumerRepository repo;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("before");
        HttpServletRequest req = (HttpServletRequest) request;
        String method = req.getMethod();
        if(req.getMethod().equals("OPTIONS"))   {
            chain.doFilter(request, response);
            return;
        }
        String auth = "Basic " + BASE64;
        String token = req.getHeader("Authorization").split(" ")[1];
        URL obj = new URL(AUTHORIZATION_SERVER_INTROSPECT);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Authorization", auth);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("token=" + token);
        wr.flush();
        wr.close();
        con.connect();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder res = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            res.append(inputLine);
        }
        in.close();
        System.out.println(res.toString());
        JSONObject json = new JSONObject(res.toString());
        boolean active = (boolean) json.get("active");
        if(!active) {
            System.out.println("Not available");
        }
        else {
            req.setAttribute("sub", json.get("sub"));
            if(repo.findCostumerBySub((String)req.getAttribute("sub")) == null)
                repo.save(new Costumer((String)req.getAttribute("sub")));
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
