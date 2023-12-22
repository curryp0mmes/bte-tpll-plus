package com.github.curryp0mmes.tpll;

import com.github.curryp0mmes.tpll.config.ModConfigs;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ExternalListener {
    HttpServer server;
    int port;

    public ExternalListener() {

        for (int portTrial = 7780; portTrial <= 7788; portTrial++) {
            try {
                server = HttpServer.create(new InetSocketAddress("localhost", portTrial), 0);
                System.out.println("Successfully started tpll listener on PORT " + portTrial);
                this.port = portTrial;
                break;
            } catch (IOException e) {
                if(portTrial == 7788) {
                    System.err.println("Could not create a tpll listener: " + e.getMessage());
                    return;
                }
            }
        }


        server.createContext("/", new MyHttpHandler());
        server.start();
    }

    private static class MyHttpHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            boolean success = false;
            if("GET".equals(httpExchange.getRequestMethod())) {
                success = handleGetRequest(httpExchange);
            }
            httpExchange.sendResponseHeaders(success ? 200 : 400, -1);
        }

        private boolean handleGetRequest(HttpExchange httpExchange) {
            String request = httpExchange.getRequestURI().toString().substring(1);
            //request contains the full string after /
            System.out.println("New request via WEBSERVER: " + request);

            try {
                if(!ModConfigs.AUTOTPLLACTIVATED) return true;


                String clipboardContent = request;

                //check if string contains numbers, so we only run if we actually got coordinates
                if(!clipboardContent.matches(".*\\d.*")) return false;
                if(!clipboardContent.matches("\\s*[0-9]*\\.[0-9]+.*[0-9]*\\.[0-9]+.*")) return false;

                BtetpllplusClient.tpllqueue.add(clipboardContent);

                MinecraftClient.getInstance().inGameHud.setOverlayMessage(Text.of("§6Added Coordinates To Queue"), false);
                if(!BtetpllplusClient.tpllThread.isAlive()) {
                    BtetpllplusClient.tpllThread = new CoordinateQueue();
                    BtetpllplusClient.tpllThread.start();
                }


            } catch (Exception ignored) {
            }


            return true;
        }
    }
}
