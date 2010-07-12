package ch.javaformatters.ast;

import org.eclipse.jdt.core.dom.ASTNode;

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
}
