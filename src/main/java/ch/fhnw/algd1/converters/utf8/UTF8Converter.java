package ch.fhnw.algd1.converters.utf8;

/*
 * Created on 05.09.2014
 */

import java.util.Arrays;

/**
 * @author 
 */
public class UTF8Converter {
	public static byte[] codePointToUTF(int x) {
		byte[] b = null;
		// TODO allocate b in the right size (depending on x) and fill it with the
		// UTF-8 encoding of code point x. b[0] shall contain the first byte.
		String binS = toBinString(x);
		int count = 0;
		for (int i = 0; i < binS.length(); i++) {
			if (binS.charAt(i) != '0') {
				break;
			} else {
				count++;
			}
		}
		String bitBin = binS.substring(count);
		int len = bitBin.length();
		if (len < 8) {
			b = new byte[1];
			b[0] = Byte.parseByte("0" + bitBin);
		} else if (len < 12) {
			b = new byte[2];
			while (bitBin.length() < 11) {
				bitBin = "0" + bitBin;
			}
			b[0] = Byte.parseByte("110" + bitBin.substring(0, 4));
			b[1] = Byte.parseByte("10" + bitBin.substring(5, 10));
		} else if (len < 17) {
			b = new byte[3];
			while (bitBin.length() < 16) {
				bitBin = "0" + bitBin;
			}
			b[0] = Byte.parseByte("1110" + bitBin.substring(0, 3));
			b[1] = Byte.parseByte("10" + bitBin.substring(4, 9));
			b[2] = Byte.parseByte("10" + bitBin.substring(10, 15));
		} else if (len < 22) {
			b = new byte[4];
			while (bitBin.length() < 21) {
				bitBin = "0" + bitBin;
			}
			b[0] = Byte.parseByte("11110" + bitBin.substring(0, 2));
			b[1] = Byte.parseByte("10" + bitBin.substring(3, 8));
			b[2] = Byte.parseByte("10" + bitBin.substring(9, 14));
			b[3] = Byte.parseByte("10" + bitBin.substring(15, 20));
		}


		System.out.println(Arrays.toString(b));
		return b;
	}

	public static int UTFtoCodePoint(byte[] bytes) {
		if (isValidUTF8(bytes)) {
			// TODO replace return statement below by code to return the code point
			// UTF-8 encoded in array bytes. bytes[0] contains the first byte
			return 0;
		} else return 0;
	}

	private static boolean isValidUTF8(byte[] bytes) {
		if (bytes.length == 1) return (bytes[0] & 0b1000_0000) == 0;
		else if (bytes.length == 2) return ((bytes[0] & 0b1110_0000) == 0b1100_0000)
				&& isFollowup(bytes[1]);
		else if (bytes.length == 3) return ((bytes[0] & 0b1111_0000) == 0b1110_0000)
				&& isFollowup(bytes[1]) && isFollowup(bytes[2]);
		else if (bytes.length == 4) return ((bytes[0] & 0b1111_1000) == 0b1111_0000)
				&& isFollowup(bytes[1]) && isFollowup(bytes[2]) && isFollowup(bytes[3]);
		else return false;
	}

	private static boolean isFollowup(byte b) {
		return (b & 0b1100_0000) == 0b1000_0000;
	}

	public static String toBinString(int x) {
		// TODO expect x to be in range [-128, 127], return string with 8 binary
		// digits representing x in 2-complement
		if (x < 0) {
			x = 256 - x;
		}
		String bin = "";
		while (x > 0) {
			if ((x % 2) == 0) {
				bin = "0" + bin; // add (0 or 1) to the front of the string
				x /= 2;
			} else {
				bin = "1" + bin;  // Prepend the remainder (0 or 1)
				x /= 2;  // Divide the number by 2
			}
		}
		while (bin.length() < 8) {
			bin = "0" + bin;  // Prepend the remainder (0 or 1)
		}
		System.out.println(bin);
		return bin;

	}





}
