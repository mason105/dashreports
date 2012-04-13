package binky.dan.utils.encryption;

import junit.framework.TestCase;

public abstract class EncTestCase extends TestCase {
	protected String getTestString() {
		//get a test string of 1mb in size
		StringBuilder b = new StringBuilder();
		for (int i=0;i<1024*1024;i++) {
			int c =(int)Math.round(Math.random()*42)+48;
			b.append((char)c);
		}
		return b.toString();
	}
}
