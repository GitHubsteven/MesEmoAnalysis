package gui;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class SaveFileDialog extends Dialog {
	
	private DialogCallback call;

	protected Object result;
	protected Shell shell;
	private Text confirm;
	
	private Button btnOK;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SaveFileDialog(Shell parent, int style, DialogCallback call) {
		super(parent, style);
		this.call = call;
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(459, 150);
		shell.setText(getText());
		GridLayout gl_shell = new GridLayout(2, false);
		gl_shell.verticalSpacing = 20;
		gl_shell.marginRight = 10;
		gl_shell.marginLeft = 10;
		gl_shell.marginTop = 10;
		gl_shell.marginBottom = 10;
		shell.setLayout(gl_shell);
		
		confirm = new Text(shell, SWT.BORDER);
		confirm.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				btnOK.setEnabled(StringUtils.isNotEmpty(confirm.getText()));
			}
		});
		confirm.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button BtnChooseDir = new Button(shell, SWT.NONE);
		BtnChooseDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell);
				String directory = dialog.open();
				if (StringUtils.isNotBlank(directory)) {
					confirm.setText(directory);
				}
			}
		});
		GridData gd_BtnChooseDir = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_BtnChooseDir.widthHint = 76;
		BtnChooseDir.setLayoutData(gd_BtnChooseDir);
		BtnChooseDir.setText("浏览...");
		
		btnOK = new Button(shell, SWT.NONE);
		btnOK.setEnabled(false);
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				call.callback(confirm.getText());
				shell.dispose();
			}
		});
		GridData gd_btnOK = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnOK.widthHint = 74;
		btnOK.setLayoutData(gd_btnOK);
		btnOK.setText("确定");
		
		Button concel = new Button(shell, SWT.NONE);
		concel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		GridData gd_concel = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_concel.widthHint = 77;
		concel.setLayoutData(gd_concel);
		concel.setText("取消");

	}

}
