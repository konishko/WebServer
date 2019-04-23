package Client;

import Dispatcher.ThreadDispatcher;
import Threaded.Threaded;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class WebClient extends Threaded {
    private String ip;
    private Integer port;
    private String name;

    public WebClient(String ip, Integer port){
        new WebClient(ip, port, "Client");
    }

    public WebClient(String ip, Integer port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;

        ThreadDispatcher.getInstance().add(this, name);
    }

    public void run() {
        try(Socket client = new Socket(ip, port);) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            try {
                while(true) {
                    String request = reader.readLine();
                    out.write(request + "\r\n");
                    if(request.equals("---end."))
                        break;
                }
                out.flush();

                String line = "";

                while(!((line = in.readLine()).equals("---end.")))
                    System.out.println(line);
            }
            finally {
                System.out.println(String.format("%s stopped work", this.name));
                in.close();
                out.close();
            }
        }
        catch(SocketException ex){
            System.out.println("\nServer is unreachable.");
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        WebClient wc = new WebClient("localhost", 4765);
    }
}
