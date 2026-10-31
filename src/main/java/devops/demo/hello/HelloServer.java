
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
                    "h1 { font-size: 36px; font-weight: bold; margin-bottom: 20px; color: #1e3a8a; }" +
                    "p { font-size: 20px; color: #475569; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<h1>My First CI/CD pipeline has been successfully built and deployed. It works!</h1>" +
                    "<p>Welcome to my first CI/CD pipeline deployment.</p>" +
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
