package ch.chviews.views;

import java.io.IOException;

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
    }

    private void fillLocalToolBar(IToolBarManager manager)
    {
        manager.add(aRefersh);
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
        aRefersh.setText("Referesh");
        aRefersh.setToolTipText("Click to refresh the text.");
        aRefersh.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
                .getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
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