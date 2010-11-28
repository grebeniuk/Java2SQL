package ch.utils.eclipse.ast;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.NodeFinder;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

import sun.util.BuddhistCalendar;
import ch.utils.eclipse.WorkbenchHelper;
import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

public class ASTHelper
{
    static ILog log = Logger.getLogger(ASTHelper.class);

    /**
     * Create ASTParser for the current editor contents. 
     * ASTParser returned by this method should be inited by ASTParser.createAST() method.
     * @return ASTParser instance.
     */
    public static ASTParser getASTParser()
    {
        ITextEditor editor = (ITextEditor) WorkbenchHelper.getCurrentEditor();
        IEditorInput input = editor.getEditorInput();
        IJavaElement element = JavaUI.getEditorInputJavaElement(input);
        ICompilationUnit source = (ICompilationUnit) element;
        
        return getASTParser(source);
    }
    
    /**
     * Create ASTParser object for specified CompiliationUnit.
     * ASTParser returned by this method should be inited by ASTParser.createAST() method.
     * @param source CompiliationUnit instance.
     * @return ASTParser.
     */
    public static ASTParser getASTParser(ICompilationUnit source)
    {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setResolveBindings(true);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(source);
                
        return parser;
    }

    /**
     * Finds AST node, that begins at given position
     * @param unit
     * @param position
     * @return ASTNode object
     */
    public static ASTNode findNodeByOffset(ASTNode root, final int boundaryOffset)
    {

        class Visitor extends ASTVisitor
        {
            ASTNode result = null;

            public ASTNode getResult()
            {
                return result;
            }

            public boolean visit(InfixExpression node)
            {
                log.debug("node pos " + node.getStartPosition() +  " node = " + node);
                if (node.getStartPosition() == boundaryOffset)
                    result = node;
                return false;
            };

        }

        log.debug("offset = " + boundaryOffset);
        Visitor visitor = new Visitor();
        root.accept(visitor);

        return visitor.getResult();
    }
    

    /**
     * Find node by offset ant then goes through all parents to find the one with specified type.
     * @param node Root ASTNode.
     * @param boundaryOffset Offset position.
     * @param nodeTypes<int[]> Types for target node.
     * @param nodeParntTypes<int[]> Types for target's node parent.
     * @return
     */
    public static ASTNode findNodeTypeByOffset(ASTNode node, int boundaryOffset, int[] nodeTypes, int[] nodeParentTypes)
    {   
        ASTNode res = NodeFinder.perform(node, boundaryOffset, 0);
        
        log.debug("infix = " + ASTNode.INFIX_EXPRESSION);
        log.debug("offset:" + boundaryOffset);
        log.debug("res:" + res.getClass() + ", infix " + res.getNodeType());
        
        while (res != null)
        { 
            if (isInArray(nodeTypes, res.getNodeType()) && 
                    res.getParent() != null && 
                    isInArray(nodeParentTypes, res.getParent().getNodeType()))
            {
                return res;
            }
            
            log.debug("res:" + res.getClass() + ", infix " + res.getNodeType());
            
            res = res.getParent();
        }
        
        return res;
    }
    
    /**
     * Check is element exists in array.
     * @return true If key is in array or array is empty.
     */
    private static boolean isInArray(int[] arr, int key)
    {
        if (arr.length == 0)
        {
            return true;
        }
        
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == key)
            {
                return true;
            }
        }
        
        return false;
    }
    /**
     * Looking for variable declaration.
     * 
     * @param binding Variable binding.
     * @param node Some node.
     * @return
     */
    public static VariableDeclaration findDeclaration(IBinding binding, CompilationUnit root)
    {
        ASTNode declaration = root.findDeclaringNode(binding.getKey());
        
        if (declaration instanceof VariableDeclaration)
        {
            return (VariableDeclaration)declaration;
        }            

        return null;
    }
    
    /**
     * Find compilation unit by node.
     * 
     * @param node
     * @return Null if can't resolve CompilationUnit by specified node.
     */
    public static CompilationUnit getCompilationUnit(ASTNode node)
    {   
        ASTNode root = node.getRoot();
        if (root != null && root instanceof CompilationUnit)
        {
            return (CompilationUnit)root;
        }
        
        return null;
    }

    /**
     * Find CompiliationUnit for specified variable binding.  
     * @param vb Variable binding.
     * @return Compilation unit instance.
     */
    public static ICompilationUnit getCompilationUnit(IBinding vb)
    {
        IJavaElement je = vb.getJavaElement().getParent();
        
        while (je != null && !(je instanceof ICompilationUnit))
        {
            je = je.getParent();
        }
       
        return (ICompilationUnit)je;
    }
}