package ch.utils.eclipse.ast;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.VariableDeclaration;

import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

public class BindingResolver implements IResolver
{
    ILog log = Logger.getLogger(BindingResolver.class);

    protected IBinding binding = null;
    
    public BindingResolver(IBinding binding)
    {
        this.binding = binding;
    }

    /*
     * (non-Javadoc)
     * @see ch.utils.eclipse.ast.IResolver#resolveAsString()
     */
    public String resolveAsString()
    {
        String res = null;
        ICompilationUnit cu = null;
        VariableDeclaration vd = null;
        ASTNode node = null;
        
        cu = ASTHelper.getCompilationUnit(binding);        
        if (cu == null)
        {
            log.warn("Can't find compiliation unit by binding.");
            return null;
        }
        
        node = ASTHelper.getASTParser(cu).createAST(null);
        if (cu == null || !(node instanceof CompilationUnit))
        {
            log.warn("Can't create ASTParser by compiliation unit. Compiliation unit = " + node);
            return null;
        }

        vd = ASTHelper.findDeclaration(binding, (CompilationUnit)node);
        if (vd == null)
        {
            log.warn("Can't find Variable declaration.");
            return null;
        }
        
        node = vd.getInitializer();
        if (node == null)
        {
            log.warn("There is no initialazer for provided variable declaration.");
            return null;
        }
        
        try
        {
            res = NodesValueResolver.getResolver(node).resolveAsString();
        } catch (ResolverException e)
        {
            log.warn("Can't get binding resolver.", e);
            return null;
        }
        
        return res;
    }
}
