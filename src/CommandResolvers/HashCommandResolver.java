package CommandResolvers;
import IResolver.IResolver;
import executors.Md5Executor;
import fileWorker.FileWorker;
import interfaceExecutable.IExecutable;

public class HashCommandResolver implements IResolver {
    public String resolve(String path){
        FileWorker fw = new FileWorker(path, false);
        IExecutable executor = new Md5Executor();
        fw.execute(executor);
        return (String)fw.getResult(fw.getInitFile());
    }
}
