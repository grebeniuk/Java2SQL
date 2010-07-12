package ch.javaformatters.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

public class NodesValueResolver
{
    /**
     * Determinates node type and returns appropriate resolver for it.
     * 
     * @param node ASTNode instance.
     * @return Node values resolver.
     */
    public static IResolver getResolver(ASTNode node) throws ResolverException
    {
        if (node.getNodeType() == ASTNode.STRING_LITERAL)
        {
            return new StringLiteralResolver(node);
        } 
        else if (node instanceof Name)
        {
            return new NameResolver(node);
        }
        else if (node instanceof Expression)
        {
            return new ConstantResolver(node);
        }
        
        throw new ResolverException("Can't find resolver for such node type. Type = " + node.getNodeType());
    }
}
