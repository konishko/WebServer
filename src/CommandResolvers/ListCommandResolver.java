package CommandResolvers;

import IResolver.IResolver;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ListCommandResolver implements IResolver {
    public String resolve(String value){
        String[] pathes = new File(".").list();
        return Arrays.stream(pathes).collect(Collectors.joining("\n"));
    }
}
