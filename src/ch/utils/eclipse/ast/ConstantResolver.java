package ch.utils.eclipse.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;

public class ConstantResolver extends GeneralResolver
{

    public ConstantResolver(ASTNode node)
    {
        super(node);
    }
    
    /*
     * (non-Javadoc)
     * @see ch.javaformatters.ast.GeneralResolver#resolveAsString()
     */
    @Override
    public String resolveAsString()
    {
        Object res = resolveConstantValue();
        
        return res != null ? res.toString() : null;
    }
    
    /**
     * Resolve compilation time constant value if it is exists.
     * @return Object constant value;
     */
    protected Object resolveConstantValue()
    {
        Expression expr = (Expression)node;
        return expr.resolveConstantExpressionValue();
    }
    
    
    
}