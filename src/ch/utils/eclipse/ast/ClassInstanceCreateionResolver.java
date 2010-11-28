package ch.utils.eclipse.ast;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

/**
 * Resolve values from Class instance creation nodes.
 * <b>Example:</b> new BigInteger("123"); 
 * 
 * @author Andrey Grebeniuk
 */
public class ClassInstanceCreateionResolver extends GeneralResolver
{
    ILog log = Logger.getLogger(ClassInstanceCreateionResolver.class);

    public ClassInstanceCreateionResolver(ASTNode node)
    {
        super(node);
    }

    public String resolveAsString()
    {
        ClassInstanceCreation c = (ClassInstanceCreation)node;
        @SuppressWarnings("unchecked")
        List<ASTNode> args = c.arguments();
        
        return resolveArgumentsAsString(args);
    }
}