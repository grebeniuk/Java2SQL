package ch.utils.eclipse.ast;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodInvocation;
import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

/**
 * Resolve values from Method Invocation nodes.<br>
 * <b>Example:</b><br> 
 * BigInteger.valueOf(30402L); or LV_ID_IN_SERVICE.toString();
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
        MethodInvocation mi = (MethodInvocation)node;
        
        /* 
         * If the method is toString(), we should resolve the expression value
         * For example LV_ID_IN_SERVICE.toString(). In this case LV_ID_IN_SERVICE is an expresttion.
         */
        if ("toString".equals(mi.getName().getIdentifier()))
        {
            try
            {
                return NodesValueResolver.getResolver(mi.getExpression()).resolveAsString();
            } catch (ResolverException e)
            {
                log.warn("Can't get resolver.", e);
                return null;
            }
        }
        /*
         * Else, resolve arguments.
         */
        else
        {
            @SuppressWarnings("unchecked")
            List<ASTNode> args = mi.arguments();
            
            return resolveArgumentsAsString(args);
        }
    }
}