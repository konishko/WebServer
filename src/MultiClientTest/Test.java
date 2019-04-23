package MultiClientTest;

import Client.IdleClient;
import Dispatcher.ThreadDispatcher;
import Monitor.MonitorWindow;
import Server.WebServer;

public class Test {
    public static void main(String[] args){
        ThreadDispatcher td = ThreadDispatcher.getInstance();

        WebServer ws = new WebServer(4675);

        MonitorWindow mw = new MonitorWindow(td.getMonitor());

        td.add(mw, "GUI");

        for(int i = 0; i < 30; i++){
            IdleClient wc = new IdleClient("localhost", 4675, String.format("Idler-%s", i));
        }
    }
}
