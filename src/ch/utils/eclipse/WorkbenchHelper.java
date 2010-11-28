/**
 * Copyright (C) 2009 andrey
 */
package ch.utils.eclipse;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.internal.ui.javaeditor.JavaEditor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import ch.utils.eclipse.ast.ASTHelper;
import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;

/**
 * @author andrey
 */
public class WorkbenchHelper
{
    static ILog log = Logger.getLogger(WorkbenchHelper.class);

    /**
     * Find project by the editor part.
     * 
     * @param editorPart
     * @return
     */
    public static IProject getProject(IEditorPart editorPart)
    {
        return ((IFileEditorInput) editorPart.getEditorInput()).getFile()
                .getProject();
    }

    /**
     * Provide access to the active editor.
     * 
     * @return
     */
    public static IEditorPart getCurrentEditor()
    {
        return getCurrentWindow().getActivePage().getActiveEditor();
    }
    
    /**
     * Find caret position at the current editor.
     * @return Caret offset.
     */
    public static int getCurrentOffset()
    {
        IEditorPart editor = getCurrentEditor();
       
        StyledText st = (StyledText) editor.getAdapter(Control.class);
        
        //editor.getEditorInput().get
        
        return st.getCaretOffset();
    }
    
    /**
     * Find caret position at the current Java editor.
     * @return Caret offset or -1 if can't find.
     */
    public static int getCurrentOffsetJDT()
    {
        JavaEditor editor = null;
        try
        {
            editor = (JavaEditor)getCurrentEditor();
        } catch (ClassCastException e)
        {
            log.error(e.getMessage());
            return -1;
        }
        
        ISourceViewer sourceViewer = editor.getViewer();
        StyledText styledText = sourceViewer.getTextWidget();
        
        if (sourceViewer instanceof ITextViewerExtension5)
        {
            ITextViewerExtension5 extension = (ITextViewerExtension5) sourceViewer;
            return extension.widgetOffset2ModelOffset(styledText
                    .getCaretOffset());
        } else
        {
            int offset = sourceViewer.getVisibleRegion().getOffset();
            return offset + styledText.getCaretOffset();
        }
    }

    /**
     * Provide access to the active document.
     * 
     * @return
     */
    public static IDocument getCurrentDocument()
    {
        ITextEditor editor = (ITextEditor)getCurrentEditor();
        
        return getDocumentByEditor(editor); 
    }
    
    /**
     * Provide access to the active document.
     * 
     * @return
     */
    public static IDocument getDocumentByEditor(ITextEditor editor)
    {
        IDocumentProvider provider = editor.getDocumentProvider();

        return provider.getDocument(editor.getEditorInput()); 
    }
    
    /**
     * Provide access to the window selection service.
     * 
     * @return
     */
    public static ISelectionService getCurrentSelectionService()
    {
        return getCurrentWindow().getSelectionService();
    }
    
    /**
     * Current workbench window.
     * 
     * @return
     */
    public static IWorkbenchWindow getCurrentWindow()
    {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    }
    
    /**
     * Create and add new file to the project.
     * If folder is not exists in project create it.
     * 
     * @param prj
     * @param folder
     * @param fileName
     * @param text
     * @throws HelperException
     */
    public static void addFileToProject(IProject prj, String folder,
            String fileName, String text) throws HelperException
    {
        try
        {
            // open if necessary
            if (prj.exists())
            {
                if (!prj.isOpen())
                {
                    prj.open(null);
                }

                // check the folder
                IFolder srcFolder = prj.getFolder(folder);
                if (!srcFolder.exists())
                {
                    srcFolder.create(false, true, null);
                }
                
                // get file from project
                IFile newFile = srcFolder.getFile(fileName);
                
                if (newFile.exists())
                {
                    // set new sources
                    newFile.setContents(new ByteArrayInputStream(text.getBytes()),
                            false, false, null);                    
                }
                else
                {
                    // create new file
                    newFile.create(new ByteArrayInputStream(text.getBytes()),
                            false, null);
                }
            }

        } catch (CoreException e)
        {
            throw new HelperException("Can't add new file to the project", e);
        }
    }
}