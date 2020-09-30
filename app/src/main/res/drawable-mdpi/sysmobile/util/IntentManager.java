package ar.com.syswork.sysmobile.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class IntentManager {

	public static void navegateToUrl(Activity activity, String url){
		 Intent i = new Intent(Intent.ACTION_VIEW);
		 i.setData(Uri.parse(url));
		 activity.startActivity(i);
	}
	
	public static void share(Activity activity,String linkShare, String titleShare){
		  Intent intent = new Intent(Intent.ACTION_SEND);

		  intent.setType("text/plain");
		  intent.putExtra(Intent.EXTRA_SUBJECT, titleShare);
		  intent.putExtra(Intent.EXTRA_TEXT, linkShare);

		  activity.startActivity(Intent.createChooser(intent, "Compartir"));
		}	

	public static void sendEmail(Activity activity, String Address){
		 Intent emailIntent = new Intent(Intent.ACTION_SEND);   
		 emailIntent.setType("plain/text");   
		 emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Address});
		 activity.startActivity(Intent.createChooser(emailIntent, "Enviar mail"));
		}

	public static void callPhoneNumber(Activity activity,String phone) {
		 Intent callIntent = new Intent(Intent.ACTION_CALL);
		 callIntent.setData(Uri.parse("tel:"+phone));
		 activity.startActivity(callIntent); 
		}
	
	public static void routeTo(Activity activity, String destiny){
	     Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+destiny)); 
	     activity.startActivity(i);
	}	
	
}
