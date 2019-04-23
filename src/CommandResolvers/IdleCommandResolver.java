package CommandResolvers;
import IResolver.IResolver;

public class IdleCommandResolver implements IResolver {
    public String resolve(String time){
        try {
            Long sleepTime = Long.valueOf(time);
            Thread.sleep(sleepTime);

            return String.format("Slept for %s milliseconds", sleepTime);
        }

        catch(InterruptedException ex){
            return "Interrupted.";
        }
    }
}
