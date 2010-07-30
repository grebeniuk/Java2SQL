package ch.utils.eclipse.ast;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.StringLiteral;

public class StringLiteralResolver extends GeneralResolver implements IResolver
{   
    public StringLiteralResolver(ASTNode node)
    {
        super(node);
    }

    public String resolveAsString()
    {
        StringLiteral sl = (StringLiteral)node;
        
        return sl.getLiteralValue();
    }

}
