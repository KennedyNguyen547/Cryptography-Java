


/*
 * Kennedy Nguyen
 * 
 * Joan Flores
 * 
 * CS 4780 - Cryptography
 * 
 * This program encrypts a text file with the rsa algorithm
 * using a pub_key.txt or a text file with the specific format
 * for a public key.
 * Only English letters or spaces are encrypted.
 */


import java.io.*;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;




public class RSAEncrypt 
{

	// RSA Encyption function, it takes the file names of the file being encypted and
	// the file with the public key
	
	public static void RSAEncryption (String file_to_encrypt_name, String public_key_file_name) throws IOException
	{
		// reads the text file to be encrypted
		
		File file_to_encrypt = new File(file_to_encrypt_name);
		
		FileReader text_file_reader = new FileReader(file_to_encrypt);
		
		BufferedReader text_reader = new BufferedReader (text_file_reader ) ;
		
		String line;
		
		ArrayList<String[]> list_of_block_arrays = new ArrayList<String[]>();
		
		// while parsing the file, I ingoring characters that are not
		// english letters or spaces
		
		while( (line = text_reader.readLine()) != null  )
		{
			StringBuilder line2 = new StringBuilder();
			for(int i = 0; i < line.length(); i++)
			{
				if ( Character.isLetter(line.charAt(i)) || line.charAt(i) == ' ')
				{
					line2.append(line.charAt(i) );
				}
			}
			
			int size_of_block = 3;
			
			// pad spaces when needed
			int padding_spaces = line2.length() % size_of_block;
			
			if (padding_spaces == 1)
			{
				line2.append("  ");
			}
			else if (padding_spaces == 2)
			{
				line2.append(" ");
			}
			

			
			int number_of_blocks_size_3 = line2.length() / size_of_block;
			

			// create arrays each containing blocks of size 3
			
			String[]  array_of_char_blocks = new String[number_of_blocks_size_3];
			
			
			
			for (int i = 0, line2_index = 0 ; line2_index < line2.length(); i++, line2_index += size_of_block)
			{
				array_of_char_blocks[i] = line2.substring(line2_index, Math.min(line2_index+size_of_block, line2.length()) );
				
			}
			
			list_of_block_arrays.add(array_of_char_blocks);
			
		}
		
		
		// reads the public key file to be used
		
		File key_file = new File(public_key_file_name);
		
		
		FileReader key_file_reader = new FileReader(key_file);
		
		
		BufferedReader key_reader = new BufferedReader (key_file_reader ) ;
		
		String another_line;
		
		
		ArrayList<String[]> list_of_key_file_info = new ArrayList<String[]>();
		
		

		
		
		while( (another_line = key_reader.readLine()) != null  )
		{
			if(another_line.length() > 0)
			{
				if (another_line.charAt(0) == 'e' || another_line.charAt(0) == 'n')
				{
					String[] string_array = another_line.split(" ");
					
					list_of_key_file_info.add(string_array);
				}
			}
			
		}
		
		// given the expected format of the data in the files
		// their positions are known
		
		BigInteger e_value = new BigInteger(list_of_key_file_info.get(0)[2]);
		
		BigInteger n_value = new BigInteger( list_of_key_file_info.get(1)[2]);
		
		ArrayList<String[]> list_of_encrypted_blocks = new ArrayList<String[]>();
		
		// loops through the list of arrays
		
		for (int i = 0; i < list_of_block_arrays.size(); i++)
		{
			String[] current_block_array = list_of_block_arrays.get(i);
			
			String[] current_block_array_encoded = new String[current_block_array.length];
			
			// loops through the current array
			
			for (int j = 0; j < current_block_array.length; j++)
			{
				
				// loops through the current block in the array and
				// make a new array with the 2 digit representation of each
				// character as strings
				// each block of the new array is about 6 digits long
				String current_block = current_block_array[j];
				
				StringBuilder current_block_encoded = new StringBuilder();
				for (int k = 0; k < current_block.length(); k ++)
				{
					current_block_encoded.append( char_to_num_string(current_block.charAt(k)));
					
					
				}
				
				
				// after encoding the characters
				// encrypt the encoded blocks
				//current_block_encoded is current encoded block
				
				BigInteger encoded_block = new BigInteger (current_block_encoded.toString());
				
				//BigInteger encoded_block_raised =  encoded_block.pow(e_value);
				
				BigInteger encrypted_block = encoded_block.modPow(e_value, n_value);
				
				current_block_array_encoded[j] = encrypted_block.toString();
			}
			
			list_of_encrypted_blocks.add(current_block_array_encoded);
		}
		
		
		// write the encrypted message to
		// file named "test.enc"
		File make_file = new File("test.enc");
		
		make_file.createNewFile();
		
		BufferedWriter out_writer = new BufferedWriter (new FileWriter("test.enc"));
		
			out_writer.flush();
			
			for (int i = 0; i < list_of_encrypted_blocks.size(); i++ )
			{
				String[] current_encypted_block = list_of_encrypted_blocks.get(i);
				
				for(int j = 0; j < current_encypted_block.length; j++ )
				{
					out_writer.write(current_encypted_block[j]);
					if (j != current_encypted_block.length -1)
					{
						out_writer.write(" ");
					}
					
					
				}
				
				out_writer.newLine();
				
			}
			
			
			out_writer.close();
		
		
		
	}
	
	// function to convert characters to their double digit string 
	// representation
	public static String char_to_num_string (char character)
	{
		
		switch(character)
		{
		case 'a':

		case 'A':
			return "00";
			
			
		case 'b':

		case 'B':
			return "01";
			
		case 'c':

		case 'C':
			return "02";
			
		case 'd':

		case 'D':
			return "03";
			
		case 'e':

		case 'E':
			return "04";
			
			
		case 'f':

		case 'F':
			return "05";
			
		case 'g':

		case 'G':
			return "06";
			
		case 'h':

		case 'H':
			return "07";
			
			
		case 'i':

		case 'I':
			return "08";
			
		case 'j':

		case 'J':
			return "09";
			
		case 'k':

		case 'K':
			return "10";
			
		case 'l':

		case 'L':
			return "11";
			
		case 'm':

		case 'M':
			return "12";
			
		case 'n':

		case 'N':
			return "13";
			
		case 'o':

		case 'O':
			return "14";
			
		case 'p':

		case 'P':
			return "15";
			
		case 'q':

		case 'Q':
			return "16";
			
		case 'r':

		case 'R':
			return "17";
			
		case 's':

		case 'S':
			return "18";
			
		case 't':

		case 'T':
			return "19";
			
		case 'u':

		case 'U':
			return "20";
			
		case 'v':

		case 'V':
			return "21";
			
		case 'w':

		case 'W':
			return "22";
			
		case 'x':

		case 'X':
			return "23";
		
		case 'y':

		case 'Y':
			return "24";
			
		case 'z':

		case 'Z':
			return "25";
			
		case ' ':
			return "26";
			
			
		default:
			return "26";
		
		}
	
	
	}
	
	
	
	// file names are expected to contain no spaces
	
	public static void main(String[] args) throws IOException
	{
		
		Scanner input_reader = new Scanner (System.in);
		
		String input_line = input_reader.nextLine();
		
		input_line = input_line.trim();
		
		String[] input_file_names = input_line.split(" ");
		
		String file_to_encrypt_name = input_file_names[0];
		
		String public_key_file = input_file_names[1];
		
		
		
		RSAEncryption(file_to_encrypt_name,public_key_file  );

		
		
		
		
		
	}
	
	
	
	
}
