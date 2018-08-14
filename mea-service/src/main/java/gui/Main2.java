package gui;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import config.Properties;
import service.IAnalysisDataService;
import service.IAnalysisDataServiceImpl;
import utils.SettingUtils;

public class Main2 {
	private DataBindingContext m_bindingContext;

	protected Shell shell;
	private Text sampleContext;
	private Text testContext;
	private Text KText;
	private Text resultText;
	
	private Button btnRun;
	private Button outPut;
	private Text txtChooseModel;
	private Text txtTestData;
	private Text textTestResult;
	
	private Button btnChsSamCon;
	private Button btnRunTest;
	private Button btnExportResult;
	private Text text;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					Main2 window = new Main2();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(614, 664);
		shell.setText("短信联系人关系判断系统");
		

		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		
		TabItem tbtmBuildModel = new TabItem(tabFolder, SWT.NONE);
		tbtmBuildModel.setText("建立模型");
		
		Composite compositeBuild = new Composite(tabFolder, SWT.NONE);
		tbtmBuildModel.setControl(compositeBuild);
		
		TabItem tbtmApplyModel = new TabItem(tabFolder, SWT.NONE);
		tbtmApplyModel.setText("应用模型");
		
		Composite compositeApply = new Composite(tabFolder, SWT.NONE);
		compositeApply.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		tbtmApplyModel.setControl(compositeApply);
		GridLayout gl_compositeApply = new GridLayout(3, false);
		gl_compositeApply.verticalSpacing = 10;
		gl_compositeApply.marginWidth = 8;
		gl_compositeApply.marginTop = 10;
		gl_compositeApply.marginRight = 10;
		gl_compositeApply.marginLeft = 10;
		gl_compositeApply.marginHeight = 10;
		compositeApply.setLayout(gl_compositeApply);
		
		Label label_3 = new Label(compositeApply, SWT.NONE);
		label_3.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.BOLD));
		label_3.setText("数据输入");
		new Label(compositeApply, SWT.NONE);
		new Label(compositeApply, SWT.NONE);
		
		Label label_6 = new Label(compositeApply, SWT.NONE);
		label_6.setText("选择分类模型：");
		
		txtChooseModel = new Text(compositeApply, SWT.BORDER);
		txtChooseModel.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				enableRunTestButtonListener();
			}
		});
		GridData gd_txtChooseModel = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_txtChooseModel.widthHint = 280;
		txtChooseModel.setLayoutData(gd_txtChooseModel);
		
		Button btnChooseModel = new Button(compositeApply, SWT.NONE);
		btnChooseModel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnChooseModel.setText("浏览....");
		
		Label label_8 = new Label(compositeApply, SWT.NONE);
		label_8.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_8.setText("选择待分类数据：");
		
		txtTestData = new Text(compositeApply, SWT.BORDER);
		txtTestData.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				enableRunTestButtonListener();
			}
		});
		txtTestData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnChooseTestData = new Button(compositeApply, SWT.NONE);
		btnChooseTestData.setText("浏览....");
		
		Label label_7 = new Label(compositeApply, SWT.NONE);
		label_7.setFont(SWTResourceManager.getFont("华文中宋", 12, SWT.BOLD));
		label_7.setText("分类结果");
		new Label(compositeApply, SWT.NONE);
		new Label(compositeApply, SWT.NONE);
		
		textTestResult = new Text(compositeApply, SWT.BORDER);
		textTestResult.setEditable(false);
		GridData gd_textTestResult = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_textTestResult.heightHint = 347;
		textTestResult.setLayoutData(gd_textTestResult);
		new Label(compositeApply, SWT.NONE);
		new Label(compositeApply, SWT.NONE);
		new Label(compositeApply, SWT.NONE);
		
		btnRunTest = new Button(compositeApply, SWT.NONE);
		btnRunTest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnRunTest.setEnabled(false);
		btnRunTest.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRunTest.setText("运行");
		
		btnExportResult = new Button(compositeApply, SWT.NONE);
		btnExportResult.setEnabled(false);
		GridData gd_btnExportResult = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnExportResult.widthHint = 101;
		btnExportResult.setLayoutData(gd_btnExportResult);
		btnExportResult.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnExportResult.setText("导出结果");
		new Label(compositeApply, SWT.NONE);
		
		GridLayout gl_compositeBuild = new GridLayout(3, false);
		gl_compositeBuild.marginWidth = 8;
		gl_compositeBuild.marginHeight = 8;
		gl_compositeBuild.marginTop = 10;
		gl_compositeBuild.marginBottom = 10;
		gl_compositeBuild.marginRight = 10;
		gl_compositeBuild.marginLeft = 10;
		compositeBuild.setLayout(gl_compositeBuild);
		
		Label label = new Label(compositeBuild, SWT.NONE);
		label.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.BOLD));
		label.setText("数据输入");
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		
		Label label_1 = new Label(compositeBuild, SWT.NONE);
		label_1.setText("选择训练样本：");
		
		sampleContext = new Text(compositeBuild, SWT.BORDER);
		sampleContext.setToolTipText("Hello");
		sampleContext.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				enableRunButtonListener();
			}
		});
		GridData gd_sampleContext = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_sampleContext.widthHint = 165;
		sampleContext.setLayoutData(gd_sampleContext);
		
		btnChsSamCon = new Button(compositeBuild, SWT.NONE);
		btnChsSamCon.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell);
				String samplePath = fd.open();
				if(StringUtils.isNoneBlank(samplePath)) {
					sampleContext.setText(samplePath);
				}
			}
		});
		GridData gd_btnChsSamCon = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnChsSamCon.widthHint = 68;
		btnChsSamCon.setLayoutData(gd_btnChsSamCon);
		btnChsSamCon.setText("浏览...");
		
		Label label_2 = new Label(compositeBuild, SWT.NONE);
		label_2.setText("训练样本类别：");
		
		testContext = new Text(compositeBuild, SWT.BORDER);
		testContext.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				enableRunButtonListener();
			}
		});
		testContext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button btnChsSamType = new Button(compositeBuild, SWT.NONE);
		btnChsSamType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(shell);
				String testContextPath = fd.open();
				if(StringUtils.isNoneBlank(testContextPath)){
					testContext.setText(testContextPath);
				}
			}
		});
		btnChsSamType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnChsSamType.setText("浏览...");
		
		Label label_4 = new Label(compositeBuild, SWT.NONE);
		label_4.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.BOLD));
		label_4.setText("分类选项");
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		
		Label lblKnnk = new Label(compositeBuild, SWT.NONE);
		lblKnnk.setText("KNN中的K：");
		
		KText = new Text(compositeBuild, SWT.BORDER);
		KText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				enableRunButtonListener();
			}
		});
		KText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeBuild, SWT.NONE);
		
		Label label_9 = new Label(compositeBuild, SWT.NONE);
		label_9.setText("聚类蔟数：");
		
		text = new Text(compositeBuild, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(compositeBuild, SWT.NONE);
		
		Label label_5 = new Label(compositeBuild, SWT.NONE);
		label_5.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.BOLD));
		label_5.setText("结果评价指标");
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		
		resultText = new Text(compositeBuild, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		resultText.setEditable(true);
		GridData gd_resultText = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 2);
		gd_resultText.heightHint = 216;
		resultText.setLayoutData(gd_resultText);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		new Label(compositeBuild, SWT.NONE);
		
		btnRun = new Button(compositeBuild, SWT.NONE);
		btnRun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Path sampleContextPath = Paths.get(sampleContext.getText());
				Path testContextPath = Paths.get(testContext.getText());
				String kStr = KText.getText();
				Integer k =Integer.valueOf(kStr);
				Integer tailOringType = 0;
				String result = "";
				try {
					//TODO  change the params
					result = getAnalysisResult(sampleContextPath,null,testContextPath,null,k,tailOringType);
				} catch (Exception e1) {
					result = e1.getMessage();
					if(StringUtils.isBlank(result)){
						result = "sorry, exception occurs!";
					}
					e1.printStackTrace();
				}
				resultText.setText(result);
			}
		});
		btnRun.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnRun.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRun.setText("运行");
		
		outPut = new Button(compositeBuild, SWT.NONE);
		outPut.setVisible(true);
		outPut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SaveFileDialog dialog = new SaveFileDialog(shell, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL, new DialogCallback() {
					@Override
					public void callback(String path) {
						exportResult(path);
					}});
				dialog.open();
			}
		});
		outPut.setText("保存模型");
		new Label(compositeBuild, SWT.NONE);

	}
	
	public void exportResult(String directory) {
		Path output = Paths.get(directory,Properties.DEFUALT_OUT_PUT_NAME);
		//params
		Path sampleContextPath = Paths.get(sampleContext.getText());
		Path testContextPath = Paths.get(testContext.getText());
		String kStr = KText.getText();
		Integer k =Integer.valueOf(kStr);
		//默认不进行剪裁
		Integer tailOringType = 0;
//		if(tailOringCheck.getSelection() && RMeans.getSelection()){
//			tailOringType = Properties.TAIL_ORING_K_MEANS;
//		}
//		if(tailOringCheck.getSelection() && RMedoids.getSelection()){
//			tailOringType = Properties.TAIL_ORING_K_MEDOIDS;
//		}
		//TODO change the interface and params
		String result = getAnalysisResult(sampleContextPath,null,testContextPath,null,k,tailOringType);
		Boolean outResult = SettingUtils.outPutTextToFile(result, output);
		if(outResult){
			resultText.setText("导出成功！");
		}else{
			resultText.setText("导出失败！");
		}
	}

	protected void enableRunButtonListener() {
		if (StringUtils.isEmpty(sampleContext.getText())
				|| StringUtils.isEmpty(testContext.getText())
				|| StringUtils.isEmpty(KText.getText())) {
			btnRun.setEnabled(false);
			outPut.setEnabled(false);
//			btnSaveModel.setEnabled(false);
		} else{
			btnRun.setEnabled(true);
			outPut.setEnabled(true);
//			btnSaveModel.setEnabled(true);
		}
	}
	
	
	protected void enableRunTestButtonListener() {
		if (StringUtils.isEmpty(sampleContext.getText())
				|| StringUtils.isEmpty(testContext.getText())
				|| StringUtils.isEmpty(KText.getText())) {
			btnRunTest.setEnabled(false);
			btnExportResult.setEnabled(false);
		} else{
			btnRunTest.setEnabled(true);
			btnExportResult.setEnabled(true);
		}
	}
	
	protected String getAnalysisResult(Path sampleContextPath,Path sampleTypePath,
			Path testContextPath,Path testTypePath,Integer k,Integer tailOringType ){
		IAnalysisDataService AnaService = new IAnalysisDataServiceImpl();
		if(StringUtils.isNoneBlank(testTypePath.toString())){
			return AnaService.AnalysisDataWithTestType(sampleContextPath, sampleTypePath, testContextPath,testTypePath, k, tailOringType);
		}else {
			return AnaService.AnalysisDataWithoutTestType(sampleContextPath, sampleTypePath, testContextPath, k, tailOringType);
		}
		
	}
	
	protected void exportModel(Path modelPath){
		IAnalysisDataService AnaService = new IAnalysisDataServiceImpl();
		
	}
}
