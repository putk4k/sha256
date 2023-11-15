package ru.mirea.vikulov.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import ru.mirea.vikulov.firebaseauth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    // START declare_auth
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Initialization views
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
// [START initialize_auth] Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        binding.inB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(binding.editEmail.getText());
                String password = String.valueOf(binding.editPas.getText());
                //signIn(email, password);
                sha256 Sha256 = new sha256();
                String hash = Sha256.shisha(password);
                Toast.makeText(MainActivity.this,"Хеш " + hash, Toast.LENGTH_SHORT).show();
                binding.textUI.setVisibility(View.VISIBLE);
                binding.textUI.setText("Хеш: "+ hash);
            }
        });
        binding.createB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(binding.editEmail.getText());
                String password = String.valueOf(binding.editPas.getText());
                createAccount(email, password);
            }
        });
        binding.verifB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailVerification();
            }
        });
        binding.outB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
// [END initialize_auth]
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
// Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    // [END on_start_check_user]
    private void updateUI(FirebaseUser user) {
        if (user != null) {

            binding.textEmail.setText(getString(R.string.emailpassword_status_fmt, user.getEmail(), user.isEmailVerified()));
            binding.textUI.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            binding.editEmail.setVisibility(View.GONE);
            binding.editPas.setVisibility(View.GONE);
            binding.inB.setVisibility(View.GONE);
            binding.createB.setVisibility(View.GONE);
            binding.verifB.setVisibility(View.VISIBLE);
            binding.outB.setVisibility(View.VISIBLE);
            binding.verifB.setEnabled(!user.isEmailVerified());
        } else {
            binding.textEmail.setText(R.string.signed_out);
            binding.textUI.setText(null);
            binding.editEmail.setVisibility(View.VISIBLE);
            binding.editPas.setVisibility(View.VISIBLE);
            binding.inB.setVisibility(View.VISIBLE);
            binding.createB.setVisibility(View.VISIBLE);
            binding.verifB.setVisibility(View.GONE);
            binding.outB.setVisibility(View.GONE);
        }
        binding.textEmail.setText(R.string.signed_out);
        binding.textUI.setText(null);
        binding.editEmail.setVisibility(View.VISIBLE);
        binding.textEmail.setVisibility(View.GONE);
        binding.editPas.setVisibility(View.VISIBLE);
        binding.inB.setVisibility(View.VISIBLE);
        binding.createB.setVisibility(View.GONE);
        binding.verifB.setVisibility(View.GONE);
        binding.outB.setVisibility(View.GONE);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
//        if (!validateForm()) {
//            return;
//        }
// [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
// Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
// If sign in fails, display a message to the user.

                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
// [END create_user_with_email]
    }
    private void signIn(String email, String password) {


//        Log.d(TAG, "signIn:" + email);
//// [START sign_in_with_email]
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//// Sign in success, update UI with the signed-in user's information
//                    Log.d(TAG, "signInWithEmail:success");
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    updateUI(user);
//                } else {
//// If sign in fails, display a message to the user.
//                    Log.w(TAG, "signInWithEmail:failure", task.getException());
//                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                    updateUI(null);
//                }
//// [START_EXCLUDE]
//                if (!task.isSuccessful()) {
//                    binding.textEmail.setText(R.string.auth_failed);
//                }
//// [END_EXCLUDE]
//            }
//        });
//// [END sign_in_with_email]

    }
    public class sha256 {
        public String shisha(String inputString)  {

            String s0, s1, ch, temp1, temp2, maj;
            String h0 = String.format("%32s", Long.toBinaryString(0x6a09e667L)).replace(' ', '0');
            String h1 = String.format("%32s", Long.toBinaryString(0xbb67ae85L)).replace(' ', '0');
            String h2 = String.format("%32s", Long.toBinaryString(0x3c6ef372L)).replace(' ', '0');
            String h3 = String.format("%32s", Long.toBinaryString(0xa54ff53aL)).replace(' ', '0');
            String h4 = String.format("%32s", Long.toBinaryString(0x510e527fL)).replace(' ', '0');
            String h5 = String.format("%32s", Long.toBinaryString(0x9b05688cL)).replace(' ', '0');
            String h6 = String.format("%32s", Long.toBinaryString(0x1f83d9abL)).replace(' ', '0');
            String h7 = String.format("%32s", Long.toBinaryString(0x5be0cd19L)).replace(' ', '0');

            long[] k = {
                    0x428a2f98L, 0x71374491L, 0xb5c0fbcfL, 0xe9b5dba5L, 0x3956c25bL, 0x59f111f1L, 0x923f82a4L, 0xab1c5ed5L,
                    0xd807aa98L, 0x12835b01L, 0x243185beL, 0x550c7dc3L, 0x72be5d74L, 0x80deb1feL, 0x9bdc06a7L, 0xc19bf174L,
                    0xe49b69c1L, 0xefbe4786L, 0x0fc19dc6L, 0x240ca1ccL, 0x2de92c6fL, 0x4a7484aaL, 0x5cb0a9dcL, 0x76f988daL,
                    0x983e5152L, 0xa831c66dL, 0xb00327c8L, 0xbf597fc7L, 0xc6e00bf3L, 0xd5a79147L, 0x06ca6351L, 0x14292967L,
                    0x27b70a85L, 0x2e1b2138L, 0x4d2c6dfcL, 0x53380d13L, 0x650a7354L, 0x766a0abbL, 0x81c2c92eL, 0x92722c85L,
                    0xa2bfe8a1L, 0xa81a664bL, 0xc24b8b70L, 0xc76c51a3L, 0xd192e819L, 0xd6990624L, 0xf40e3585L, 0x106aa070L,
                    0x19a4c116L, 0x1e376c08L, 0x2748774cL, 0x34b0bcb5L, 0x391c0cb3L, 0x4ed8aa4aL, 0x5b9cca4fL, 0x682e6ff3L,
                    0x748f82eeL, 0x78a5636fL, 0x84c87814L, 0x8cc70208L, 0x90befffaL, 0xa4506cebL, 0xbef9a3f7L, 0xc67178f2L
            };

            //String inputString = "hello world";
            StringBuilder binaryStringBuilder = new StringBuilder();

            for (char character : inputString.toCharArray()) {
                binaryStringBuilder.append(
                        String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0')
                );
            }

            String binaryString = binaryStringBuilder.toString();

            binaryStringBuilder.append('1');
            int currentLength = binaryStringBuilder.length();
            int additionalZeros = 512 - (currentLength % 512) - 64;
            String bitRepresentation = String.format("%64s", Integer.toBinaryString(currentLength - 1)).replace(' ', '0');

            for (int i = 0; i < additionalZeros; i++) {
                binaryStringBuilder.append('0');
            }
            binaryStringBuilder.append(bitRepresentation);

            binaryString = binaryStringBuilder.toString();

            int tmpArrayLength = binaryString.length() / 32;

            String[] bitArray = new String[(binaryString.length() / 1025 + 1)*64];

            for (int i = 0; i < tmpArrayLength; i++) {
                bitArray[i] = binaryStringBuilder.substring(i * 32, (i + 1) * 32);
            }
            for (int i = tmpArrayLength; i < bitArray.length; i++) {
                bitArray[i] = "00000000000000000000000000000000";
            }

            for (int i = tmpArrayLength; i < bitArray.length; i++) {
                s0 = xorMultipleBitStrings(rightRotate(bitArray[i-15],  7), rightRotate(bitArray[i-15], 18), rightShift(bitArray[i-15],  3));
                s1 = xorMultipleBitStrings(rightRotate(bitArray[i- 2], 17), rightRotate(bitArray[i- 2], 19), rightShift(bitArray[i- 2], 10));
                bitArray[i] = addMod32(bitArray[i-16], s0, bitArray[i-7], s1);
            }


            String a = h0;
            String b = h1;
            String c = h2;
            String d = h3;
            String e = h4;
            String f = h5;
            String g = h6;
            String h = h7;


            for (int i = 0; i < bitArray.length; i++) {
                s1 = xorMultipleBitStrings(rightRotate(e, 6), rightRotate(e, 11), rightRotate(e, 25));
                ch = xorMultipleBitStrings(andBitStrings(e, f), andBitStrings(notBitString(e), g));
                temp1 = addMod32(h, s1, ch, String.format("%32s", Long.toBinaryString(k[i])).replace(' ', '0'), bitArray[i]);
                s0 = xorMultipleBitStrings(rightRotate(a, 2), rightRotate(a, 13), rightRotate(a, 22));
                maj = xorMultipleBitStrings(andBitStrings(a, b), andBitStrings(a, c), andBitStrings(b, c));
                temp2 = addMod32(s0, maj);

                h = g;
                g = f;
                f = e;
                e = addMod32(d, temp1);
                d = c;
                c = b;
                b = a;
                a = addMod32(temp1, temp2);

            }

            h0 = binaryToHex(addMod32(h0, a));
            h1 = binaryToHex(addMod32(h1, b));
            h2 = binaryToHex(addMod32(h2, c));
            h3 = binaryToHex(addMod32(h3, d));
            h4 = binaryToHex(addMod32(h4, e));
            h5 = binaryToHex(addMod32(h5, f));
            h6 = binaryToHex(addMod32(h6, g));
            h7 = binaryToHex(addMod32(h7, h));

            String hash = h0 + h1 + h2 + h3 + h4 + h5 + h6 + h7;
            //System.out.println("FINAL HASH:  " + hash);

            return hash;
        }

        private String rightRotate(String str, int amount) {
            if (str == null || str.length() == 0 || amount < 0) {
                return str;
            }

            amount %= str.length();
            String end = str.substring(str.length() - amount);
            String start = str.substring(0, str.length() - amount);

            return end + start;
        }

        private String rightShift(String str, int amount) {
            if (str == null || str.length() == 0 || amount < 0) {
                return str;
            }

            amount %= str.length();
            StringBuilder shifted = new StringBuilder();

            for (int i = 0; i < amount; i++) {
                shifted.append("0");
            }
            shifted.append(str, 0, str.length() - amount);

            return shifted.toString();
        }

        private String xorMultipleBitStrings(String... strings) {
            if (strings == null || strings.length == 0) {
                throw new IllegalArgumentException("Array must be non-null and not empty");
            }

            String result = strings[0];

            for (int i = 1; i < strings.length; i++) {
                result = xorBitStrings(result, strings[i]);
            }

            return result;
        }

        private String xorBitStrings(String str1, String str2) {
            if (str1 == null || str2 == null || str1.length() != str2.length()) {
                throw new IllegalArgumentException("Strings must be non-null and of equal length");
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < str1.length(); i++) {
                char bit1 = str1.charAt(i);
                char bit2 = str2.charAt(i);

                char xor = (bit1 == bit2) ? '0' : '1';
                result.append(xor);
            }

            return result.toString();
        }

        private String addMod32(String... strings) {
            long sum = 0;

            for (String str : strings) {
                if (str == null) {
                    throw new IllegalArgumentException("Строка должна быть не null");
                }
                sum += Long.parseLong(str, 2);
            }

            sum &= 0xFFFFFFFFL;

            return String.format("%32s", Long.toBinaryString(sum)).replace(' ', '0');
        }

        private String andBitStrings(String str1, String str2) {
            if (str1 == null || str2 == null || str1.length() != 32 || str2.length() != 32) {
                throw new IllegalArgumentException("Strings must be non-null and 32 bits in length");
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 32; i++) {
                char bit1 = str1.charAt(i);
                char bit2 = str2.charAt(i);

                char and = (bit1 == '1' && bit2 == '1') ? '1' : '0';
                result.append(and);
            }

            return result.toString();
        }

        private String notBitString(String str) {
            if (str == null || str.length() != 32) {
                throw new IllegalArgumentException("String must be non-null and 32 bits in length");
            }

            StringBuilder result = new StringBuilder();
            for (int i = 0; i < 32; i++) {
                char bit = str.charAt(i);

                char notBit = (bit == '1') ? '0' : '1';
                result.append(notBit);
            }

            return result.toString();
        }

        private String binaryToHex(String binaryString) {
            if (binaryString == null || binaryString.length() != 32) {
                throw new IllegalArgumentException("String must be non-null and 32 bits in length");
            }

            long number = Long.parseLong(binaryString, 2);
            return String.format("%08X", number); // Преобразование в 16-ричную строку с заполнением до 8 символов
        }

    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }
    private void sendEmailVerification() {
// Disable button
        binding.verifB.setEnabled(false);
// Send verification email
// [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        Objects.requireNonNull(user).sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
// [START_EXCLUDE]
// Re-enable button
                        binding.verifB.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,"Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(MainActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
// [END_EXCLUDE]
                    }
                });
// [END send_email_verification]
    }


}