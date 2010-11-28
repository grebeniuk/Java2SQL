package ch.utils.eclipse.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.Name;
import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

public class NameResolver extends ConstantResolver
{ 
    ILog log = Logger.getLogger(NameResolver.class);

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
        Name n = (Name)node;
        String res = super.resolveAsString();
        
        if (res != null)
        {
            return getFormattedVariable(res, n);
        }
        else
        {
            res = resolveAsStringBinding(n);
            return getFormattedVariable(res, n);
        }
    }

    /**
     * 
     * @param n
     * @return Null if value can't be resolved.
     */
    private String resolveAsStringBinding(Name n)
    {
        String res = null;
        IBinding binding = n.resolveBinding();
        
        if (binding == null)
        {
            return null;
        }
        
        try
        {
            res = NodesValueResolver.getResolver(binding).resolveAsString();
        } catch (ResolverException e)
        {
            log.warn("Can't get binding resolver.", e);
            return null;
        }
        
        return res;
    }
}