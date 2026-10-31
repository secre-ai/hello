
package devops.demo.hello;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HelloServer {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                String response = "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; text-align: center; margin-top: 100px; color: #1e293b; }" +
                    "h1 { font-size: 42px; font-weight: bold; margin-bottom: 10px; color: #1e3a8a; }" +
                    "p.sub { font-size: 18px; color: #475569; margin: 8px 0; }" +
                    "p.cicd { font-size: 20px; font-weight: bold; color: #15803d; margin-top: 30px; }" +
                    "footer { margin-top: 150px; font-size: 14px; color: #94a3b8; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<h1>Hello, DevOps!</h1>" +
                    "<p class='sub'>Welcome to our first DevOps demo application.</p>" +
                    "<p class='sub'>This page is generated to verify the build and deployment pipeline.</p>" +
                    "<p class='cicd'>My First CI/CD pipeline has been successfully built and deployed. It works!</p>" +
                    "<footer>© DevOps Demo | Computer Science - Kate Bunda</footer>" +
                    "</body>" +
                    "</html>";

                byte[] bytes = response.getBytes("UTF-8");
                exchange.sendResponseHeaders(200, bytes.length);

                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
            }
        });

        server.start();
        System.out.println("Server started on http://localhost:8080");
    }
}
