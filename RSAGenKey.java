



/* Kennedy Nguyen
 * 
 * Joan Flores
 * 
 *  RSA Key Generator
 *  
 *  CS 4780 - Cryptography
 *  
 *  This program generates a pri_key.txt and a pub_key.txt files
 *  based on the input provided
 */





import java.io.*;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;


public class RSAGenKey 
{

	// RSA Key Generator for an integer number of bits
	public static void RSAKeyGenerator(String k_value_for_bits) throws IOException
	{
		
		int k_number_of_bits = Integer.parseInt(k_value_for_bits);
		
		Random random = new Random();
		
		// generate random highly probable primes for p and q
		
		BigInteger p_value = BigInteger.probablePrime(k_number_of_bits, random);
		
		BigInteger q_value = BigInteger.probablePrime(k_number_of_bits, random);
		
		BigInteger n_value = p_value.multiply(q_value);
		

		// this computes  the phi (totient function) of p and q
		
		BigInteger p_minus_one_value = p_value.subtract(BigInteger.ONE);
		
		BigInteger q_minus_one_value = q_value.subtract(BigInteger.ONE);
		
		BigInteger phi_n_value = p_minus_one_value.multiply(q_minus_one_value);
		
		BigInteger e_value = BigInteger.valueOf(3);
		
		// this generates the e value, normally the lowest possible prime number
		// that is relatively prime to the phi (n) value
		
		while(!phi_n_value.gcd(e_value).equals(BigInteger.ONE))
		{
			e_value = e_value.add(BigInteger.ONE);
		}
		
		BigInteger d_value = e_value.modInverse(phi_n_value);
		
		
		// generate the pub_key.txt with the public key
		
		File make_public_key_file = new File ("pub_key.txt");
		
		make_public_key_file.createNewFile();
		
		BufferedWriter pub_key_out_writer = new BufferedWriter(new FileWriter ("pub_key.txt"));
		
		
		pub_key_out_writer.newLine();
		
		pub_key_out_writer.write("e = " + e_value);
		
		pub_key_out_writer.newLine();
		pub_key_out_writer.newLine();
		
		pub_key_out_writer.write("n = " + n_value);
		
		pub_key_out_writer.newLine();
		
		pub_key_out_writer.close();
		
		
		// generate the pri_key.txt with the private key
		
		File make_private_key_file = new File ("pri_key.txt");
		
		make_private_key_file.createNewFile();
		
		BufferedWriter pri_key_out_writer = new BufferedWriter(new FileWriter("pri_key.txt"));
		
		pri_key_out_writer.flush();
		
		pri_key_out_writer.newLine();
		
		pri_key_out_writer.write("d = " + d_value );
		
		pri_key_out_writer.newLine();
		pri_key_out_writer.newLine();
		
		pri_key_out_writer.write("n = " + n_value);
		
		pri_key_out_writer.newLine();
		pri_key_out_writer.close();
		
		
		
	}
	
	// RSA Key Generatoe for given p, q, and e.
	
	public static void RSAKeyGenerator(String p, String q, String e) throws IOException
	{
		
		BigInteger p_value = new BigInteger(p);
		
		BigInteger q_value = new BigInteger(q);
		
		BigInteger e_value = new BigInteger(e);
		
		
		BigInteger n_value = p_value.multiply(q_value);
		
		BigInteger p_minus_one_value = p_value.subtract(BigInteger.ONE);
		
		BigInteger q_minus_one_value = q_value.subtract(BigInteger.ONE);
		
		BigInteger phi_n_value = p_minus_one_value.multiply(q_minus_one_value);
		
		BigInteger d_value = e_value.modInverse(phi_n_value);
		
		File make_public_key_file = new File ("pub_key.txt");
		
		make_public_key_file.createNewFile();
		
		BufferedWriter pub_key_out_writer = new BufferedWriter(new FileWriter ("pub_key.txt"));
		
		
		pub_key_out_writer.newLine();
		
		pub_key_out_writer.write("e = " + e_value);
		
		pub_key_out_writer.newLine();
		pub_key_out_writer.newLine();
		
		pub_key_out_writer.write("n = " + n_value);
		
		pub_key_out_writer.newLine();
		
		pub_key_out_writer.close();
		
		
		
		File make_private_key_file = new File ("pri_key.txt");
		
		make_private_key_file.createNewFile();
		
		BufferedWriter pri_key_out_writer = new BufferedWriter(new FileWriter("pri_key.txt"));
		
		pri_key_out_writer.flush();
		
		pri_key_out_writer.newLine();
		
		pri_key_out_writer.write("d = " + d_value );
		
		pri_key_out_writer.newLine();
		pri_key_out_writer.newLine();
		
		pri_key_out_writer.write("n = " + n_value);
		
		pri_key_out_writer.newLine();
		pri_key_out_writer.close();
		
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException
	{
		
		Scanner input_reader = new Scanner (System.in);
		
		String input_line = input_reader.nextLine();
		
		input_line = input_line.trim();
		
		String[] input_numbers = input_line.split(" ");
		
		if(input_numbers.length == 1)
		{
			
			
			RSAKeyGenerator(input_numbers[0]);
		}
		
		if(input_numbers.length == 3)
		{
			RSAKeyGenerator(input_numbers[0], input_numbers[1], input_numbers[2]);
		}
		
	}
	
	
	
	
	
}
