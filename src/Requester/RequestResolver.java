package Requester;
import CommandResolvers.HashCommandResolver;
import CommandResolvers.IdleCommandResolver;
import CommandResolvers.ListCommandResolver;
import CommandResolvers.WrongCommandResolver;
import IResolver.IResolver;
import Threaded.Threaded;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestResolver extends Threaded {
    private Socket client;
    private ServerSocket server;
    private BufferedReader input;
    private BufferedWriter output;

    public RequestResolver(ServerSocket server, Socket client) {
        this.client = client;
        this.server = server;

        try {
            this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            this.output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String request = "";
            String line = "";

            while (!(line = input.readLine()).equals("---end.")) {
                System.out.println(String.format("Received command: %s", line));
                request += line;
            }

            if (request.startsWith("list"))
                sendResult(new ListCommandResolver());

            else if (request.startsWith("hash"))
                sendResult(new HashCommandResolver(), request.substring(5));

            else if (request.startsWith("idle"))
                sendResult(new IdleCommandResolver(), request.substring(5));

            else if (request.startsWith("stop server")) {
                server.close();

            } else
                sendResult(new WrongCommandResolver(), request);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendResult(IResolver resolver){
        this.sendResult(resolver, "");
    }

    private void sendResult(IResolver resolver, String value){
        String result = resolver.resolve(value);

        try {
            output.write(result + "\n");
            output.write("---end.");
            output.flush();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
