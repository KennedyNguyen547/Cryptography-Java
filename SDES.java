


/* this file is the implementation of SDES
 */


public class SDES 
{

	public SDES() 
	{
	
	}
	

	 public static int[][] S0_box = {	{1, 0, 3, 2},
			 							{3, 2, 1, 0},
			 							{0, 2, 1, 3},
			 							{3, 1, 3, 2}	};

	 public static int[][] S1_box = {	{0, 1, 2, 3},
			 							{2, 0, 1, 3},
			 							{3, 0, 1, 0},
			 							{2, 1, 0, 3}	};


	

	
	public static byte[] Encrypt( byte[] rawkey, byte[] plaintext)
	{
	
		byte[] rawkey_copy = new byte[rawkey.length];
		byte[] plaintext_copy = new byte[plaintext.length];
		
		System.arraycopy(rawkey, 0, rawkey_copy, 0, rawkey.length);
		System.arraycopy(plaintext, 0, plaintext_copy, 0, plaintext.length);
		
		//permute key with p10_key
		rawkey_copy = p10_key(rawkey_copy);
		
		
		// left circular shift first five bits
		left_bit_circ_shift(rawkey_copy, 0, 4);
		// left circular shift second five bits
		left_bit_circ_shift(rawkey_copy, 5, 9);
		
		// apply p8_key to get the first sub key
		byte[] key1 = p8_key(rawkey_copy);
		
		
		// left circular shift first five bits twice
		left_bit_circ_shift(rawkey_copy, 0, 4);
		left_bit_circ_shift(rawkey_copy, 0, 4);
		
		
		// left circular shift second five bits twice
		left_bit_circ_shift(rawkey_copy, 5, 9);
		left_bit_circ_shift(rawkey_copy, 5, 9);
		
		// apply p8 again to get second sub key
		byte[] key2 = p8_key(rawkey_copy);
		
		
		
		
		
		// permute the plaintext with ip permute
		plaintext_copy = ip_permute(plaintext_copy);
		
		// get left 4 bits
		byte[] left_4_bits = {plaintext_copy[0], plaintext_copy[1], plaintext_copy[2], plaintext_copy[3]};
		
		// get right 4 bits
		byte[] right_4_bits = {plaintext_copy[4], plaintext_copy[5], plaintext_copy[6], plaintext_copy[7]};
		
		
		// fk function with first sub key
		byte[] fk_result_bits_subkey1 = fk_function(left_4_bits , right_4_bits, key1);
		
		
		//swap left 4 bits with right 4 bits
		swap_function_8_bits(fk_result_bits_subkey1);
		
		
		// get left 4 bits for second fk function
		byte[] left_4_bits_round2 = { fk_result_bits_subkey1[0], fk_result_bits_subkey1[1], fk_result_bits_subkey1[2], fk_result_bits_subkey1[3] };
		
		// get right 4 bits for second fk function
		byte[] right_4_bits_round2 = { fk_result_bits_subkey1[4], fk_result_bits_subkey1[5], fk_result_bits_subkey1[6], fk_result_bits_subkey1[7] };
		
		// second fk function application
		byte[] fk_result_bits_subkey2 = fk_function(left_4_bits_round2 , right_4_bits_round2, key2);
		
		
		// apply the ip permutation inverse box
		byte[] result_ciphertext = ip_permute_inverse(fk_result_bits_subkey2);
		
		
		
		return result_ciphertext;
	}
	
	

	
	
	
	public static byte[] Decrypt(byte[] rawkey, byte[] ciphertext)
	{
	
		byte[] rawkey_copy = new byte[rawkey.length];
		byte[] ciphertext_copy = new byte[ciphertext.length];
		
		System.arraycopy(rawkey, 0, rawkey_copy, 0, rawkey.length);
		System.arraycopy(ciphertext, 0, ciphertext_copy, 0, ciphertext.length);
		
		
		//permute key with p10_key
		rawkey_copy = p10_key(rawkey_copy);
		
		
		// left circular shift first five bits
		left_bit_circ_shift(rawkey_copy, 0, 4);
		// left circular shift second five bits
		left_bit_circ_shift(rawkey_copy, 5, 9);
		
		// apply p8_key to get the first sub key
		byte[] key1 = p8_key(rawkey_copy);
		
		
		// left circular shift first five bits twice
		left_bit_circ_shift(rawkey_copy, 0, 4);
		left_bit_circ_shift(rawkey_copy, 0, 4);
		
		
		// left circular shift second five bits twice
		left_bit_circ_shift(rawkey_copy, 5, 9);
		left_bit_circ_shift(rawkey_copy, 5, 9);
		
		// apply p8 again to get second sub key
		byte[] key2 = p8_key(rawkey_copy);
		
		
		// permute the plaintext with ip permute
		ciphertext_copy = ip_permute(ciphertext_copy);
		
		
		// get left 4 bits
		byte[] left_4_bits = {ciphertext_copy[0], ciphertext_copy[1], ciphertext_copy[2], ciphertext_copy[3]};
		
		// get right 4 bits
		byte[] right_4_bits = {ciphertext_copy[4], ciphertext_copy[5], ciphertext_copy[6], ciphertext_copy[7]};
		
		// fk function with second sub key
		byte[] fk_result_bits_subkey2 = fk_function (left_4_bits, right_4_bits, key2 );
		
		//swap left 4 bits with right 4 bits
		swap_function_8_bits(fk_result_bits_subkey2);
		
		// get left 4 bits for second fk function
		byte[] left_4_bits_round2 = {fk_result_bits_subkey2[0], fk_result_bits_subkey2[1], fk_result_bits_subkey2[2], fk_result_bits_subkey2[3]};
		
		// get right 4 bits for second fk function
		byte[] right_4_bits_round2 = {fk_result_bits_subkey2[4], fk_result_bits_subkey2[5], fk_result_bits_subkey2[6], fk_result_bits_subkey2[7]};
		
		// second application of fk function
		byte[] fk_result_bits_subkey1 = fk_function (left_4_bits_round2, right_4_bits_round2, key1 );
		
		
		// apply the ip permutation inverse box
		byte[] result_plaintext = ip_permute_inverse(fk_result_bits_subkey1);
		
		return result_plaintext;
	}
	
	
	
	
	
	
	
	
	
	
	
	// permutation box for 10 bit key
	public static byte[] p10_key(byte[] key_bytes)
	{
		byte[] permuted_key = new byte [10];
		
		
		permuted_key[0] = key_bytes[2];
		
		permuted_key[1] = key_bytes[4];
		
		permuted_key[2] = key_bytes[1];
		
		permuted_key[3] = key_bytes[6];
		
		permuted_key[4] = key_bytes[3];
		
		permuted_key[5] = key_bytes[9];
		
		permuted_key[6] = key_bytes[0];
		
		permuted_key[7] = key_bytes[8];
		
		permuted_key[8] = key_bytes[7];
		
		permuted_key[9] = key_bytes[5];
		
		
		return permuted_key;
	}
	
	
	
	// function to perform left circular shift
	public static void left_bit_circ_shift(byte[] line_of_bytes, int start, int end)
	
	{
		
		byte temp = line_of_bytes[start];
		
		if (start >= end)
			return;
		
		for(int i = start; i < end; i++)
		{
			
			line_of_bytes[i] = line_of_bytes[i+1];
			
		}
		
		line_of_bytes[end] = temp;
		return;
	}
	
	
	// permutation box for 8 bit key
	public static byte[] p8_key(byte[] key_bytes)
	{
		
		byte[] permuted_key = new byte[8];
		
		
		permuted_key[0] = key_bytes[5];
		
		permuted_key[1] = key_bytes[2];
		
		permuted_key[2] = key_bytes[6];
		
		permuted_key[3] = key_bytes[3];
		
		permuted_key[4] = key_bytes[7];
		
		permuted_key[5] = key_bytes[4];
		
		permuted_key[6] = key_bytes[9];
		
		permuted_key[7] = key_bytes[8];
		
		
		
		return permuted_key;
	}
	
	
	
	// the ip permutation box for 8 bit text
	public static byte[] ip_permute(byte[] plaintext)
	{
		byte[] permuted_text = new byte[plaintext.length];
		
		
		permuted_text[0] = plaintext[1];
		
		permuted_text[1] = plaintext[5];
		
		permuted_text[2] = plaintext[2];
		
		permuted_text[3] = plaintext[0];
		
		permuted_text[4] = plaintext[3];
		
		permuted_text[5] = plaintext[7];
		
		permuted_text[6] = plaintext[4];
		
		permuted_text[7] = plaintext[6];
		
		return permuted_text;
	}
	
	
	// expansion box for f mapping function
	public static byte[] expand_permute(byte[] four_bits)
	{
		byte[] right_four_bits = {four_bits[0], four_bits[1], four_bits[2], four_bits[3]};
		
		byte[] expanded_permuted_8_bits = {right_four_bits[3], right_four_bits[0], right_four_bits[1], right_four_bits[2], right_four_bits[1] ,right_four_bits[2],
				right_four_bits[3], right_four_bits[0]};
		
		
		return expanded_permuted_8_bits;
	}
	
	
	
	
	// the xor function 
	public static byte[] xor(byte[] left_bits, byte[] right_bits)
	{
		byte[] result_bits = new byte[left_bits.length];
		
		
		for(int i = 0; i < left_bits.length; i++)
		{
			if ( left_bits[i] == right_bits[i])
			{
				result_bits[i] = 0;
			}
			else
			{
				result_bits[i] = 1;
			}
			
		}
		
		
		return result_bits;
	}
	
	// the f mapping function inside the fk function
	public static byte[] f_mapping(byte[] right_side_bits, byte[] sub_key)
	{
		
		
		byte[] exp_perm_bits = expand_permute(right_side_bits);
		
		byte[] xor_result_bits = xor(exp_perm_bits, sub_key);  
		
		byte[] result_bits = sboxesrules_f_mapping(xor_result_bits);
		
		result_bits = p4_f_mapping(result_bits);
		
		return result_bits;
	}
	
	
	// the substitution box rule function for the f mapping function
	public static byte[] sboxesrules_f_mapping(byte[] xored_8_bits )
	{
		
		int s0_row_pos = 0;
		int s0_col_pos = 0;
		
		int s1_row_pos = 0;
		int s1_col_pos = 0;
		
		int s0_box_value = 0;
		int s1_box_value = 0;
		
		byte[] result_bits = new byte[4];
		
		
		
		
		if(xored_8_bits[0] == 0 && xored_8_bits[3]== 0)
		{
			s0_row_pos = 0;
		}
		if(xored_8_bits[0] == 0 && xored_8_bits[3]== 1)
		{
			s0_row_pos = 1;
		}
		if(xored_8_bits[0] == 1 && xored_8_bits[3]== 0)
		{
			s0_row_pos = 2;
		}
		if(xored_8_bits[0] == 1 && xored_8_bits[3]== 1)
		{
			s0_row_pos = 3;
		}
		
		
		if(xored_8_bits[1] == 0 && xored_8_bits[2]== 0)
		{
			s0_col_pos = 0;
		}
		if(xored_8_bits[1] == 0 && xored_8_bits[2]== 1)
		{
			s0_col_pos = 1;
		}
		if(xored_8_bits[1] == 1 && xored_8_bits[2]== 0)
		{
			s0_col_pos = 2;
		}
		if(xored_8_bits[1] == 1 && xored_8_bits[2]== 1)
		{
			s0_col_pos = 3;
		}
		
		
		
		if(xored_8_bits[4] == 0 && xored_8_bits[7]== 0)
		{
			s1_row_pos = 0;
		}
		if(xored_8_bits[4] == 0 && xored_8_bits[7]== 1)
		{
			s1_row_pos = 1;
		}
		if(xored_8_bits[4] == 1 && xored_8_bits[7]== 0)
		{
			s1_row_pos = 2;
		}
		if(xored_8_bits[4] == 1 && xored_8_bits[7]== 1)
		{
			s1_row_pos = 3;
		}
		
		
		if(xored_8_bits[5] == 0 && xored_8_bits[6]== 0)
		{
			s1_col_pos = 0;
		}
		
		if(xored_8_bits[5] == 0 && xored_8_bits[6]== 1)
		{
			s1_col_pos = 1;
		}
		if(xored_8_bits[5] == 1 && xored_8_bits[6]== 0)
		{
			s1_col_pos = 2;
		}
		if(xored_8_bits[5] == 1 && xored_8_bits[6]== 1)
		{
			s1_col_pos = 3;
		}
		
		
		s0_box_value = S0_box[s0_row_pos][s0_col_pos];
		
		s1_box_value = S1_box[s1_row_pos][s1_col_pos];
		
		if (s0_box_value == 0)
		{
			result_bits[0] = 0;
			result_bits[1] = 0;
		}
		
		if (s0_box_value == 1)
		{
			result_bits[0] = 0;
			result_bits[1] = 1;
		}
		if (s0_box_value == 2)
		{
			result_bits[0] = 1;
			result_bits[1] = 0;
		}
		if (s0_box_value == 3)
		{
			result_bits[0] = 1;
			result_bits[1] = 1;
		}
		
		
		
		if (s1_box_value == 0)
		{
			result_bits[2] = 0;
			result_bits[3] = 0;
		}
		
		if (s1_box_value == 1)
		{
			result_bits[2] = 0;
			result_bits[3] = 1;
		}
		
		if (s1_box_value == 2)
		{
			result_bits[2] = 1;
			result_bits[3] = 0;
		}
		
		if (s1_box_value == 3)
		{
			result_bits[2] = 1;
			result_bits[3] = 1;
		}
	
		
		return result_bits;
		
	}
	
	
	// the 4 bit permutation box for the f mapping function
	public static byte[] p4_f_mapping(byte[] sbox_result_bits)
	{
		
		byte[] result_bits = {sbox_result_bits[1],sbox_result_bits[3], sbox_result_bits[2],sbox_result_bits[0] };
		
		
		return result_bits;
		
	}
	
	
	
	// the fk function for the left 4 and right 4 bits
	public static byte[] fk_function(byte[] left_4_bits, byte[] right_4_bits, byte[] sub_key)
	{
		
		byte[] f_mapping_result = f_mapping(right_4_bits, sub_key);
		
		byte[] left_side_bits = xor(left_4_bits, f_mapping_result);
		
		byte[] result_bits = {left_side_bits[0],left_side_bits[1],left_side_bits[2],left_side_bits[3], right_4_bits[0],right_4_bits[1], right_4_bits[2], right_4_bits[3] };
		
		
		return result_bits;
	}
	
	
	
	// the swapping function for the left 4 and right 4 bits
	public static void swap_function_8_bits(byte[] line_of_bits)
	{
		byte[] result_bits = {line_of_bits[4], line_of_bits[5], line_of_bits[6], line_of_bits[7], line_of_bits[0], line_of_bits[1], line_of_bits[2],line_of_bits[3]};
		
		
		for (int i = 0; i < line_of_bits.length; i++)
		{
			
			line_of_bits[i] = result_bits[i];
			
		}
		
		
	}
	
	
	// the ip permutation box inverse
	public static byte[] ip_permute_inverse( byte[] line_of_bits)
	{
		
		byte[] result_bits = { line_of_bits[3], line_of_bits[0], line_of_bits[2], line_of_bits[4], line_of_bits[6], line_of_bits[1], line_of_bits[7], line_of_bits[5] } ;
		
		return result_bits;
	}
	
	
	
	
	
	
	
}
