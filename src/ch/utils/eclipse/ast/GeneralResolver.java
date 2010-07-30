package ch.utils.eclipse.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Name;

public class GeneralResolver implements IResolver
{
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
}
