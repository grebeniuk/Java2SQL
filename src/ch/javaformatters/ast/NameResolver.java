package ch.javaformatters.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;

public class NameResolver extends ConstantResolver
{

    public NameResolver(ASTNode node)
    {
        super(node);
    }

    /*
     * (non-Javadoc)
     * @see ch.javaformatters.ast.ExpressionResolver#resolveAsString()
     */
    @Override
    public String resolveAsString()
    {        
        Name sn = (Name)node;
        String res = super.resolveAsString();
        
        if (res != null)
        {
            return res + " " + wrapIntoComment(sn.getFullyQualifiedName());
        }
        else
        {
            return "? " + wrapIntoComment(sn.getFullyQualifiedName());
        }
    }
}
