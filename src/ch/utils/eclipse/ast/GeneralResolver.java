package ch.utils.eclipse.ast;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Name;
import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

public class GeneralResolver implements IResolver
{
    ILog log = Logger.getLogger(GeneralResolver.class);
    protected ASTNode node;
    
    public GeneralResolver(ASTNode node)
    {
        this.node = node;
    }
    
    public String resolveAsString()
    {
        return null;
    }
    
    /**
     * Wrap string into java comment strings.
     * @param str Source string.
     * @return Wrapped string.
     */
    protected String wrapIntoComment(String str)
    {
        return "/*" + str + "*/";
    }
    
    /**
     * Format value for pretty output.
     * @param value Variable value. Value could be Null, in this case it will be replaced with the string representation.
     * @param node Node that contains formatting value.
     * @return Formatted string.
     */
    protected String getFormattedVariable(String value, Name node)
    {
        value = value == null ? ResolverConstants.UNKNOW_VAL : value;
        
        return value + " " + wrapIntoComment(node.getFullyQualifiedName());
    }
    
    /**
     * Resolve string value from a List of ASTNodes.
     * @param args Arguments list.
     * @return String representation.
     */
    protected String resolveArgumentsAsString(List<ASTNode> args)
    {
        if (args == null || args.size() == 0)
        {
            log.warn("There are no arguments");
            return null;
        }
        
        try
        {
            ASTNode n = args.get(0);
            return NodesValueResolver.getResolver(n).resolveAsString();
        } catch (ResolverException e)
        {
            log.warn("Can't get resolver.", e);
            return null;
        }
    }
}
