




public class TripleSDES
{

	
	
	public TripleSDES()
	{
		
	}
	
	
	
	public static SDES public_sdes = new SDES();
	
	
	
	public static byte[] Encrypt( byte[] rawkey1, byte[] rawkey2, byte[] plaintext )
	{
		byte[] result_bits = {};
		
		result_bits = public_sdes.Encrypt(rawkey1, plaintext);
		
		result_bits = public_sdes.Decrypt(rawkey2, result_bits);
		
		result_bits = public_sdes.Encrypt(rawkey1, result_bits);
		
		return result_bits;
	}
	
	
	
	public static byte[] Decrypt( byte[] rawkey1, byte[] rawkey2, byte[] ciphertext )
	{
		
		byte[] result_bits = {};
		
		result_bits = public_sdes.Decrypt(rawkey1, ciphertext);
		
		result_bits = public_sdes.Encrypt(rawkey2, result_bits);
		
		result_bits = public_sdes.Decrypt(rawkey1, result_bits);
		
		
		return result_bits;
		
		
	}
	
	
	
	
	
	
	
	
	
}
