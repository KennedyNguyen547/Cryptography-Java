# Cryptography-Java
The SDES, TripleSDES, and RSA algorithms were written in Java using the Eclipse IDE.
These were done as class projects for a cryptography and information security class.

For those who are interested. SDES has an encryption class and decryption class.
The way I designed SDES to be used was that I would import the class and use it methods to encrypt and decrypt.

For my SDES class assignment, I had to decrypt a message that was encrypted in the SDES algorithm, using bits.
I did not include the "cracking" file in this folder for that assignment. But the gist is that I wrote a key generator using C++ <bitset> and <fstream> to generate all the possible bit keys used in encrypting the message to a text file. The codes for cracking (written in Java) simply read in the text file of keys, and then used the brute force approach to test all keys possible and write the possible messages for each key to a text file. I used the find function for text files, since it was not specified if my class had to write our own find function to search. Using an online dictationary for commonly used words, I found the message that was coherent in English.

TripleSDES is basically using the SDES methods, so you need to import SDES to use TripleSDES.
  
The RSA algorithm is much easier to grasp in my opinion, although also difficult to implement.
The Encrypt method will read in a file that contains a message and a file that contains keys. The RSA key generator should help with generating the key files.
Afterwards, the Encrypt method will generate a file with a message encrypted using RSA, the file will have a ".enc" extension.
The Decrypt method will read in the file with the RSA encrypted message and the key file. After decrypting the message, it will generate a ".dec" file with the decrypted message written in it.
