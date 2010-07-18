package ch.javaformatters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import ch.utils.eclipse.WorkbenchHelper;
import ch.utils.eclipse.ast.ASTHelper;
import ch.utils.eclipse.ast.NodesValueResolver;
import ch.utils.eclipse.ast.ResolverException;
import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

public class SQLASTFormatter implements IFormatter
{
    ILog log = Logger.getLogger(SQLASTFormatter.class);

    public String getFormatedString(int offset, int lenght) throws IOException
    {
        log.debug(offset + ", " + lenght);
        
        ASTParser parser = ASTHelper.getASTParser();
        ASTNode root = parser.createAST(null);
        ASTNode n = ASTHelper.findNodeByOffset(root, offset);

        if (n == null || n.getNodeType() != ASTNode.INFIX_EXPRESSION)
        {
            throw new IOException("Please select valid Java string expression");
        }

        List<Expression> nodes = new ArrayList<Expression>();
        InfixExpression ie = (InfixExpression) n;

        if (ie.getLeftOperand() != null)
        {
            nodes.add(ie.getLeftOperand());
        }

        if (ie.getRightOperand() != null)
        {
            nodes.add(ie.getRightOperand());
        }

        List extOper = ie.extendedOperands();
        if (extOper != null && !extOper.isEmpty())
        {
            nodes.addAll(extOper);
        }

        StringBuffer buf = new StringBuffer();
        String res = null;
        for (Expression expr : nodes)
        {
            try
            {
                res = NodesValueResolver.getResolver(expr).resolveAsString();
            } catch (ResolverException e)
            {
                log.warn("Error while parsing expression.", e);
            }
            if (res != null)
            {
                buf.append(res);
            }
        }

        return buf.toString();
    }
}
