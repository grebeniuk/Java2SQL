package ch.utils.eclipse.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

/**
 * Resolve values from Class instance creation nodes.
 * <b>Example:</b> BigInteger.valueOf(30402L); 
 * 
 * @author Andrey Grebeniuk
 */
public class MethodInvocationResolver extends GeneralResolver
{
    ILog log = Logger.getLogger(MethodInvocationResolver.class);

    public MethodInvocationResolver(ASTNode node)
    {
        super(node);
    }

    public String resolveAsString()
    {
        MethodInvocation c = (MethodInvocation)node;
        @SuppressWarnings("unchecked")
        List<ASTNode> args = c.arguments();
        
        return resolveArgumentsAsString(args);
    }
}
