package Client;

import Dispatcher.ThreadDispatcher;
import Threaded.Threaded;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class IdleClient extends Threaded {
    private String ip;
    private Integer port;
    private String name;

    public IdleClient(String ip, Integer port){
        new IdleClient(ip, port, "Idler");
    }

    public IdleClient(String ip, Integer port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;

        ThreadDispatcher.getInstance().add(this, name);
    }
    public void run() {
        try(Socket client = new Socket(ip, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            Random rnd = new Random();
            Integer sleepTime = 100 * rnd.nextInt(1000);

            try {
                out.write("idle " + sleepTime + "\r\n");
                out.write("---end.\r\n");
                out.flush();

                String line = new String();

                while(!((line = in.readLine()).equals("---end.")))
                    System.out.println(line);
            }
            finally {
                client.close();
                System.out.println(String.format("%s stopped work", this.name));
                in.close();
                out.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        new IdleClient("localhost", 4765);
    }
}
