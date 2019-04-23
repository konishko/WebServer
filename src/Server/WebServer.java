package Server;

import Dispatcher.ThreadDispatcher;
import Monitor.MonitorWindow;
import Requester.RequestResolver;
import Threaded.Threaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class WebServer extends Threaded{
    private Integer port;
    private ThreadDispatcher dispatcher;
    private boolean isAlive;

    public WebServer(Integer port){
        this.port = port;
        this.dispatcher = ThreadDispatcher.getInstance();
        this.isAlive = true;

        dispatcher.add(this, "Server");
    }

    public void run(){
        try(ServerSocket server = new ServerSocket(port)){
            try{
                Integer num = 0;
                System.out.println("Server running now!");
                while (true){
                    Socket client = server.accept();
                    System.out.println(String.format("New connection from %s client", num));

                    dispatcher.add(new RequestResolver(server, client), String.format("Resolver-%s", num));
                    num++;
                }
            }

            finally{
                System.out.println("Server closing.");
                server.close();
                dispatcher.kill();
            }

        }
        catch(SocketException ex){
            System.out.println("\nClient reset connection.");
        }

        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void stopServer(){
        this.isAlive = false;
    }

    public static void main(String[] args){
        new WebServer(4765);

    }
}
