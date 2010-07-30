package ch.utils.eclipse.ast;

public interface IResolver
{
    /**
     * String representation of the ASTNode element.
     *  
     * @return Could return Null if resolver can't resolve value.
     */
    String resolveAsString();
}
