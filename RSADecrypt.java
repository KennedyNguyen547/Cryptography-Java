

/*
 * Kennedy Nguyen
 * 
 * Joan Flores
 * 
 * CS 4780 - Cryptography
 * 
 * This java file decrypts a text file with rsa encrypted message in a specific format
 * using a pri_key.txt or a text file containing the specific format of a private key file
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class RSADecrypt 
{

	// function to do RSA Decyption
	public static void RSADecryption(String file_to_decrypt_name, String private_key_file_name) throws IOException
	{
	
		// parse the private key file for the private key
		
		File key_file = new File (private_key_file_name);
		
		BufferedReader key_reader = new BufferedReader( new FileReader(key_file ));
		
		String current_string;
		
		
		ArrayList<String[]> list_of_key_file_info = new ArrayList<String[]>();
		
		
		while( (current_string = key_reader.readLine()) != null  )
		{
			if(current_string.length() > 0)
			{
				if (current_string.charAt(0) == 'd' || current_string.charAt(0) == 'n')
				{
					String[] string_array = current_string.split(" ");
					
					list_of_key_file_info.add(string_array);
				}
			}
			
		}
		
		BigInteger d_value = new BigInteger(list_of_key_file_info.get(0)[2]);
		
		BigInteger n_value = new BigInteger(list_of_key_file_info.get(1)[2]);
		
		
		
		// decrypt the data in the file
		
		File file_to_decrypt = new File(file_to_decrypt_name);
		
		BufferedReader text_reader = new BufferedReader( new FileReader( file_to_decrypt));
		
		String string_line;
		
		ArrayList<String[]> list_of_decrypted_block_arrays = new ArrayList<String[]>();
		
		// the blocks are expected to be separated by single spaces
		
		while ( (string_line = text_reader.readLine()) != null   )
		{
			String[] block_array = string_line.split(" ");
			
			String[] decrypted_block_array = new String [ block_array.length];
			
			for(int i = 0; i < block_array.length; i++)
			{
				
				// decrypt the block of 6 digit numbers
				BigInteger current_block = new BigInteger(block_array[i]);
				
				BigInteger decrypted_block = current_block.modPow(d_value, n_value);
				
				// pads the blocks with zeros in front as needed
				int  num_misisng_digits = decrypted_block.toString().length() % 6;
				
				
				StringBuilder current_string_block = new StringBuilder(decrypted_block.toString());
				
				if (num_misisng_digits> 0)
				{
					current_string_block.reverse();
					int padding_front_zeros = 6 - num_misisng_digits;
					
					for(int j = 0; j < padding_front_zeros; j++)
					{
						current_string_block.append("0");
					}
					current_string_block.reverse();
				}
				
				
				
				decrypted_block_array[i] = current_string_block.toString();
				
			}
			
			list_of_decrypted_block_arrays.add(decrypted_block_array);

		}
		
		
		// write out the decrypted message to
		// file named  "test.dec"
		
		File make_file = new File("test.dec");
		
		make_file.createNewFile();
		
		BufferedWriter out_writer = new BufferedWriter(new FileWriter(make_file));
		
		
		for(int i = 0; i < list_of_decrypted_block_arrays.size(); i++ )
		{
			String[] current_decrypted_block_array = list_of_decrypted_block_arrays.get(i);
			
			for(int j = 0; j  < current_decrypted_block_array.length; j++)
			{
				String curret_decrypted_block = current_decrypted_block_array[j];
				
				// as we decode each pair of digits to their respective english letters
				for(int k = 0; k < curret_decrypted_block.length(); k+=2 )
				{
					out_writer.write(num_to_char(curret_decrypted_block.substring(k, k+2)));
				}
				
				
			}
			
			
			out_writer.newLine();
			
		}
		
		out_writer.close();
		
	}
	
	
	// function needed to change number strings to their 
	// equivalent english letterss

	public static String num_to_char(String num_in_string)
	{
	
		Integer number = Integer.parseInt(num_in_string);
		
		
		switch(number)
		{
		case 0:
			return "a";
		case 1:
			return "b";
		case 2:
			return "c";
		case 3:
			return "d";
		case 4:
			return "e";
		case 5:
			return "f";
		case 6:
			return "g";
		case 7:
			return "h";
		case 8:
			return "i";
		case 9:
			return "j";
		case 10:
			return "k";
		case 11:
			return "l";
		case 12:
			return "m";
		case 13:
			return "n";
		case 14:
			return "o";
		case 15:
			return "p";
		case 16:
			return "q";
		case 17:
			return "r";
		case 18:
			return "s";
		case 19:
			return "t";
		case 20:
			return "u";
		case 21:
			return "v";
		case 22:
			return "w";
		case 23:
			return "x";
		case 24:
			return "y";
		case 25:
			return "z";
		case 26:
			return " ";
		
		default:
			return " ";
		
		}
		
		
	}
	
	
	
	public static void main(String[]args) throws IOException
	{
		Scanner input_reader = new Scanner (System.in);
		
		String input_line = input_reader.nextLine();
		
		input_line = input_line.trim();
		
		String[] input_file_names = input_line.split(" ");
		
		
		
		String file_to_decrypt_name = input_file_names[0] ;
		
		String private_key_file_name = input_file_names[1];
		
		RSADecryption(file_to_decrypt_name, private_key_file_name);
		
		
	}
	
	
	
	
	
}
