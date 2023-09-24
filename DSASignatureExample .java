import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DSASignatureExample {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // Tamanhos de chave a serem usados
        int[] keySizes = new int[]{512, 1024, 2048};

        for (int keySize : keySizes) {
            System.out.println("Usando tamanho de chave DSA: " + keySize);

            // Geração de chaves
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "BC");
            keyGen.initialize(keySize, new SecureRandom());
            KeyPair keyPair = keyGen.generateKeyPair();

            // Criação da assinatura
            Signature signature = Signature.getInstance("SHA256withDSA", "BC");
            signature.initSign(keyPair.getPrivate(), new SecureRandom());
            byte[] message = new byte[]{(byte)'a', (byte)'b', (byte)'c'};
            signature.update(message);
            byte[] sigBytes = signature.sign();

            // Verificação da assinatura
            signature.initVerify(keyPair.getPublic());
            signature.update(message);
            if (signature.verify(sigBytes)) {
                System.out.println("Assinatura validada - reconhecida");
            } else {
                System.out.println("Assinatura não reconhecida");
            }
        }
    }
}
