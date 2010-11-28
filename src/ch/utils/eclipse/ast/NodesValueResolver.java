package ch.utils.eclipse.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;

public class NodesValueResolver
{
    /**
     * Determinate node type and returns appropriate resolver for it.
     * 
     * @param node ASTNode instance.
     * @return Node values resolver.
     */
    public static IResolver getResolver(ASTNode node) throws ResolverException
    {
        if (node == null)
        {
            throw new ResolverException("Node parameter is null");
        }
        
        if (node.getNodeType() == ASTNode.STRING_LITERAL)
        {
            return new StringLiteralResolver(node);
        } 
        else if (node instanceof Name)
        {
            return new NameResolver(node);
        }
        else if (node instanceof ClassInstanceCreation)
        {
            return new ClassInstanceCreateionResolver(node);
        }
        else if (node instanceof MethodInvocation)
        {
            return new MethodInvocationResolver(node);
        }
        else if (node instanceof Expression)
        {
            return new ConstantResolver(node);
        }
        
        throw new ResolverException("Can't find resolver for such node type. Type = " + node.getNodeType());
    }
    
    /**
     * Determinate binding type and returns appropriate resolver for it.
     * 
     * @param node IBinding instance.
     * @return Node values resolver.
     */
    public static IResolver getResolver(IBinding binding)  throws ResolverException
    {
        if (binding == null)
        {
            throw new ResolverException("Binding parameter is null");
        }
        
        return new BindingResolver(binding);
    }
}