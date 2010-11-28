package ch.chviews.views;

import java.io.IOException;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;

import ch.javaformatters.IFormatter;
import ch.javaformatters.SQLASTFormatter;
import ch.utils.eclipse.WorkbenchHelper;
import ch.utils.eclipse.log.ILog;
import ch.utils.eclipse.log.Logger;
import ch.utils.swt.chDlgs;

public class SQLView extends ViewPart
{

    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "chviews.views.SQLView";
    ILog log = Logger.getLogger(SQLView.class);

    private Text viewer;
    private Action aRefersh;
    private Action aCopy;
    
    private Clipboard clipboard;
   
    /**
     * The constructor.
     */
    public SQLView()
    {
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    public void createPartControl(Composite parent)
    {
        viewer = new Text(parent, SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL);
        viewer.setEditable(false);
        // Cornsilk 255-248-220
        viewer.setBackground(new Color(parent.getDisplay(), 255, 248, 220));
        
        makeActions();
        contributeToActionBars();
        
        clipboard = new Clipboard(parent.getDisplay());
    }

    private void contributeToActionBars()
    {
        IActionBars bars = getViewSite().getActionBars();
        fillLocalPullDown(bars.getMenuManager());
        fillLocalToolBar(bars.getToolBarManager());
    }

    private void fillLocalPullDown(IMenuManager manager)
    {
        manager.add(aRefersh);
        manager.add(aCopy);
    }

    private void fillLocalToolBar(IToolBarManager manager)
    {
        manager.add(aRefersh);
        manager.add(aCopy);
    }

    private void makeActions()
    {
        aRefersh = new Action()
        {
            public void run()
            {
                refresh();
            }
        };
        aRefersh.setText("Refresh");
        aRefersh.setToolTipText("Click to refresh the text.");
        aRefersh.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
        
        aCopy = new Action()
        {
            public void run()
            {
                copy();
            }
        };
        aCopy.setText("Copy");
        aCopy.setToolTipText("Click to copy the text to Clipboard.");
        aCopy.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
    }

	/**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus()
    {
        viewer.setFocus();
    }
    
    private void refresh()
    {
        IFormatter f = new SQLASTFormatter();
        int offset = WorkbenchHelper.getCurrentOffsetJDT();
        
        if (offset <= 0)
        {
            viewer.setText("");
            return;
        }
        
        try
        {
            viewer.setText(f.getFormatedString(offset, 0));
        } catch (IOException e)
        {
            chDlgs.showWarning(e.getMessage());
        }
    }
    
    private void copy() 
    {
        TextTransfer textTransfer = TextTransfer.getInstance();
        clipboard.setContents(new Object[] { viewer.getText() },
            new Transfer[] { textTransfer });
    }
    
    /*
     * @return Position of the first character that isn't equal space. 
     */
    private int getFirstNotSpace(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) != ' ')
            {
                return i;
            }
        }
        
        return -1;
    }
}