package iyaad.azzam.container;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WebContainer {
    public int port;
    final private String configFileName;
    public Map<String, HttpServlet> handlers = new HashMap<>();

    public WebContainer(int port, String configFileName){
        this.port = port;
        this.configFileName = configFileName;
    }
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            Thread socketHandler = new SocketHandler(socket, handlers);
            socketHandler.start();
        }
    }
    private void loadProperties() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(configFileName);
        if (input == null)
            throw new RuntimeException("Unable to find file: " + configFileName);

        Properties properties = new Properties();
        properties.load(input);
        properties.forEach((key, value) ->{
            HttpServlet httpServlet = getServletInstance((String)value);
            assert httpServlet != null;
            httpServlet.init();
            handlers.put((String)key, httpServlet);
        });
    }

    private HttpServlet getServletInstance(String className){
        try {
            return (HttpServlet) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args) throws IOException {
        WebContainer webContainer = new WebContainer(8888, "config.properties");
        webContainer.loadProperties();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                webContainer.handlers.forEach((urlPattern, HTTPServlet)->HTTPServlet.destroy());
            }
        });
        /**
         * webContainer.handlers.forEach((url, httpServlet) ->{
         *   System.out.println(url);
         *  httpServlet.doGet();
         * });
         **/
        webContainer.start();
    }
}
