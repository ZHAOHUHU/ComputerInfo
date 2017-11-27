package teamway.shenzhen.tms9000;

public final class OsCheck {
       /*
        * 系统类型
        */
	public enum OSType{
		Windows,MacOS,Linux,Other
	}
	public static OSType detectedOS;
	public static OSType getOperatingSystenType() {
		if(detectedOS==null) {
			String OS=System.getProperty("os.name", "generic").toLowerCase();
			if(OS.indexOf("win")>=0) {
				detectedOS=OSType.Windows;
			}else if((OS.indexOf("mac") >= 0)||(OS.indexOf("darwin") >= 0)){
				detectedOS = OSType.MacOS;
			} else if (OS.indexOf("nux") >= 0) {
				detectedOS = OSType.Linux;
			} else {
			detectedOS = OSType.Other;
			}
			}
			return detectedOS;
		}
	}
