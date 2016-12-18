
public class ArgChk {
	
	
	static boolean isValid(String usage, String[]args)
	{
		boolean result = true;
		if( args.length != 4 )
		{
			result = false;
		}
		return result;
	}
	
	static boolean switchFound(String opt, String[] args)
	{
		boolean result = false;
		for (int ind = 0; ind < args.length ; ind++)
		{
		   if(opt.equalsIgnoreCase(args[ind]))
			   result = true;
		}
		return result;
	}
	
	static String valAtSwitch( String opt, String[] args)
	{
		String result = "";
		return result;	
	}

}
