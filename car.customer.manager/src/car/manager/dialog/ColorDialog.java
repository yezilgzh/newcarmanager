package car.manager.dialog;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import car.manager.dto.control.DataCache;
import car.manager.lsn.IAfter;

public class ColorDialog extends Dialog
{
  private Text text_color;
  private IAfter<Object> after;

  public ColorDialog(Shell parentShell, IAfter<Object> after)
  {
    super(parentShell);
    this.after = after;
  }

  protected Control createDialogArea(Composite parent)
  {
    parent.getShell().setText("������ɫ");
    Composite main = (Composite)super.createDialogArea(parent);
    main.setLayout(new GridLayout(2, false));

    Label lblNewLabel = new Label(main, 0);
    lblNewLabel.setFont(SWTResourceManager.getFont("΢���ź�", 15, 0));
    lblNewLabel.setLayoutData(
      new GridData(16777216, 16777216, false, 
      false, 1, 1));
    lblNewLabel.setText("��ɫ");

    this.text_color = new Text(main, 2048);
    this.text_color.setLayoutData(
      new GridData(4, 16777216, true, 
      false, 1, 1));
    return main;
  }

  protected void okPressed()
  {
    String color = this.text_color.getText().trim();
    if ((color == null) || ("".equals(color))) {
      MessageDialog.openError(getParentShell(), "��ʾ��Ϣ", "������Ҫ�������ɫ");
      return;
    }
    try {
      DataCache.getInstance().getColorDao().insert(color);
    } catch (Exception e) {
      MessageDialog.openError(getParentShell(), "��ʾ��Ϣ", 
        "��ɫ�����쳣�������Ѵ���!");
      return;
    }

    this.after.afterOperate(null);
    super.okPressed();
  }
}