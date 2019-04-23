package CommandResolvers;

import IResolver.IResolver;

public class WrongCommandResolver implements IResolver {
    public String resolve(String command){
        return String.format("This server doesn`t understand this command: %s", command);
    }
}
