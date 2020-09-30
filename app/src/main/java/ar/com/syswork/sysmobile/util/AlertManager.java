package ar.com.syswork.sysmobile.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class AlertManager implements OnClickListener
{
	
	public static final int BUTTON_POSITIVE = AlertDialog.BUTTON_POSITIVE;
	public static final int BUTTON_NEGATIVE = AlertDialog.BUTTON_NEGATIVE;
	public static final int BUTTON_NEUTRAL = AlertDialog.BUTTON_NEUTRAL;
	
	private IAlertResult dialogResult;
	private Context context;
	
	private String title;
	private String message;
	
	private int idAlert;
	
    private AlertDialog.Builder alertBuilder;

	public AlertManager(Context context,IAlertResult dialogResult,int idAlert)
	{
		this.dialogResult = dialogResult;
		this.context = context;
		this.idAlert = idAlert;
		
		alertBuilder = new AlertDialog.Builder(this.context);
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public void setMessage(String message) 
	{
		this.message = message;
	}

	public void setPositiveButton(String title)
	{
	      alertBuilder.setPositiveButton(title,this); 
	}
	public void setNegativeButton(String title)
	{
	      alertBuilder.setNegativeButton(title,this); 
	}
	public void setNeutralButton(String title)
	{
	      alertBuilder.setNeutralButton(title,this); 
	}
	
	public void ShowAlert()
	{
	      alertBuilder.setTitle(title);
	      alertBuilder.setMessage(message);
	      alertBuilder.show();
	}
	
	@Override
	public void onClick(DialogInterface dialogInterface, int which) 
	{
		dialogResult.onAlertResult(idAlert, which);
	}
	
}
