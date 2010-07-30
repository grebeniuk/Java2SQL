package ch.utils.eclipse.ast;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

public class ClassInstanceCreateionResolver extends GeneralResolver implements IResolver
{
    ILog log = Logger.getLogger(ClassInstanceCreateionResolver.class);

    public ClassInstanceCreateionResolver(ASTNode node)
    {
        super(node);
    }

    public String resolveAsString()
    {
        String res = null;
        ClassInstanceCreation c = (ClassInstanceCreation)node;
        List<?> args = c.arguments();
        
        if (args == null || args.size() == 0)
        {
            log.warn("There are no arguments");
            return null;
        }
        
        try
        {
            ASTNode n = (ASTNode)args.get(0);
            res = NodesValueResolver.getResolver(n).resolveAsString();
        } catch (ResolverException e)
        {
            log.warn("Can't get binding resolver.", e);
            return null;
        }
        
        return res;
    }
}
