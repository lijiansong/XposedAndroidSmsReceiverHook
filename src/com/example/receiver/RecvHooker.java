package com.example.receiver;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * @author Li Jiansong
 * @date:2015-7-27  ����11:15:48
 * @version :
 *
 *Server�˶��Ž��ն˵����أ�������γ��ԣ�������Ч��������ķ���
 *����SmsMessage���ڲ���PduParser��getUserDataUCS2�������÷�����������ΪString
 *String getUserDataUCS2(int byteCount)
 *
 */
public class RecvHooker implements IXposedHookLoadPackage{
	
	private static final String TARGET_PACKAGE = "com.android.mms";

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		// TODO Auto-generated method stub
		//XposedBridge.log("--------loaded app:"+lpparam.packageName);
//		if(!lpparam.packageName.equals("com.android.mms"))
//			return;
		
		if (!TARGET_PACKAGE.equals(lpparam.packageName)) {
			// XposedBridge.log("SendRawSMSMod: ignoring package: " +
			// lpparam.packageName);
			return;
		}
		
//		/**
//		 * ����SmsMessage���ڲ���PduParser��getUserData������
//		 * byte[] getUserData(){}
//		 * �÷�����������
//		 */
//		final Class<?> recvClazz=XposedHelpers.findClass("com.android.internal.telephony.gsm"
//		+".SmsMessage$PduParser",lpparam.classLoader);
//		
//		XposedBridge.log("==========��ʼ��������----");
//		
//		XposedHelpers.findAndHookMethod(recvClazz, "getUserData",
//				new XC_MethodHook(){
//		
//			@Override
//			protected void afterHookedMethod(MethodHookParam param)
//					throws Throwable {
//				// TODO Auto-generated method stub
//				//super.beforeHookedMethod(param);
//		
//				XposedBridge.log("=========getUserData������");
//				byte[] recvByteSms=(byte[]) param.getResult();
//				String strRecvSms="";
//				strRecvSms+=new String(recvByteSms);
//			
//				//byte[] srtbyte = strRecvSms.getBytes();
//				//String lsx="6666666666666666666666666666666666";
//				param.setResult(strRecvSms.getBytes());
//				//SmsMessage msg=new SmsMessage();
//				
//				XposedBridge.log("========���յĶ�������Ϊ��"+strRecvSms);
//				return;
//			}
//			
//			
//		});
		
		
		
		
		
		//XposedBridge.log("-------��ʼ����");
//		findAndHookMethod("com.android.internal.telephony.gsm.SmsMessage",lpparam.classLoader,
//				"getSubmitPdu",String.class,
//				String.class, String.class, boolean.class, byte[].class,
//				int.class, int.class, int.class, new XC_MethodHook(){
//			
//			/**
//			 * ����SmsMessage��getSubmitPdu����������5������
//			 * String scAddress,
//			 * String destinationAddress, 
//			 * String message,
//			 * boolean statusReportRequested, 
//			 * byte[] header
//			 * 
//			 */
//			
//			/**
//		     * Get an SMS-SUBMIT PDU for a destination address and a message
//		     *
//		     * @param scAddress Service Centre address.  Null means use default.
//		     * @return a <code>SubmitPdu</code> containing the encoded SC
//		     *         address, if applicable, and the encoded message.
//		     *         Returns null on encode error.
//		     */
//			@Override
//			protected void beforeHookedMethod(MethodHookParam param)
//					throws Throwable {
//				// TODO Auto-generated method stub
//			//	super.beforeHookedMethod(param);
//				XposedBridge.log("getSubmitPdu������");
//				if(param.args[2]==null){
//					return;
//				}
//				String message=(String) param.args[2];
//				XposedBridge.log("======before��SMS message��"+message);
//				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				message+=df.format(new Date());
//				XposedBridge.log("========after   SMS message��"+message);
//				SubmitPdu rawPdu=new SubmitPdu();
//				//StringTokenizer stringTokenizer=new StringTokenizer(string, delimiters, returnDelimiters)
//				param.setResult(rawPdu);
//				XposedBridge.log("=============hook�滻�ɹ�");
//				
//				return;
//				
//			}
//		});
		
		
//		final Class<?> recvClazz=XposedHelpers.findClass("com.android.internal.telephony.gsm"
//				+".SmsMessage$PduParser",lpparam.classLoader);
		
		XposedBridge.log("=========��ʼ��������");
		XposedHelpers.findAndHookMethod("com.android.internal.telephony.gsm"+".SmsMessage$PduParser", 
				lpparam.classLoader,"getUserDataUCS2",int.class, 
				new XC_MethodHook(){
			/**
	         * Interprets the user data payload as UCS2 characters, and
	         * decodes them into a String.
	         *
	         * @param byteCount the number of bytes in the user data payload
	         * @return a String with the decoded characters
	         */
			/**
			 * ����SmsMessage���ڲ���PduParser��getUserDataUCS2�������÷�����������ΪString
			 * String getUserDataUCS2(int byteCount)
			 * 
			 */
			@Override
			protected void afterHookedMethod(MethodHookParam param)
					throws Throwable {
				// TODO Auto-generated method stub
			//	super.afterHookedMethod(param);
				try {
					String strMms=(String) param.getResult();
					XposedBridge.log("=========before:"+strMms);
					//String after="666666666666666";
					char[] recvArray=strMms.toCharArray();
					for(int i=0;i<recvArray.length;i++){
						recvArray[i]=(char) (recvArray[i]^20000);
					}
					String enCodeSms=new String(recvArray);
					param.setResult(enCodeSms);
					
					XposedBridge.log("=========after:"+param.getResult());
					
					//return;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					XposedBridge.log(e);
				}
				
			}
		});
		
		
	}
	
	

}
