package com.tcs.internship.demotrack.decrypter;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.tcs.internship.demotrack.decryptor.Decrypter;

public class DecrypterTest {

	static Decrypter decrypter = new Decrypter();

	@BeforeClass
	public static void decryptSetKeys() {
		decrypter.setInitVector("encryptionIntVec");
		decrypter.setKey("aesEncryptionKey");
	}

	@Test
	public void decrypterTestPositive() {
		assertEquals("Pwd@2020", decrypter.decrypt("EHzocMRbwMEA54vyB9GZPg"));
	}

	@Test
	public void decrypterTestNegative() {
		assertNotEquals("Pwd@2020", decrypter.decrypt("YPRsdTDbxpE1jnUFauJLuA"));
	}

	@Test
	public void decrypterTestNotNull() {
		assertNull(decrypter.decrypt("$"));
	}

}