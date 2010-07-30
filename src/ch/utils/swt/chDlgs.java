/**
 * Copyright (C) 2008 Andrey Grebeniuk
 */
package ch.utils.swt;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Include static methods for showing some type of the dialogs.
 * 
 * @author Andrey Grebeniuk
 */

public class chDlgs
{
    /**
     * Display an error message.
     * 
     * @param message
     *            String with text for displaying.
     */
    public static void showError(final String message)
    {
        PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable()
        {
            public void run()
            {
                IWorkbenchWindow window = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow();
                if (window != null)
                {
                    MessageDialog
                            .openError(window.getShell(), "Error", message);
                }
            }
        });
        ;
    }

    /**
     * Display a warning message.
     * 
     * @param message
     *            String with text for displaying.
     */
    public static void showWarning(final String message)
    {
        PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable()
        {
            public void run()
            {
                IWorkbenchWindow window = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow();
                if (window != null)
                {
                    MessageDialog.openWarning(window.getShell(), "Warning",
                            message);
                }
            }
        });
        ;
    }

    /**
     * Display an info massage.
     * 
     * @param message
     *            String with text for displaying.
     */
    public static void showInfo(final String message)
    {
        PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable()
        {
            public void run()
            {
                IWorkbenchWindow window = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow();
                if (window != null)
                {
                    MessageDialog.openInformation(window.getShell(), "Info",
                            message);
                }
            }
        });
        ;
    }

}
