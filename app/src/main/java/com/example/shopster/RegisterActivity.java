package com.example.shopster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopster.model.Cart;
import com.example.shopster.model.Product;
import com.example.shopster.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("user");
    DatabaseReference cartsRef = database.getReference("cart");
    DatabaseReference productsRef = database.getReference("products");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        EditText firstName = findViewById(R.id.etFirstName);
        EditText lastName = findViewById(R.id.etLastName);
        EditText email = findViewById(R.id.etEmailReg);
        EditText password = findViewById(R.id.etPasswordReg);

        findViewById(R.id.btnRegSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String e_mail = email.getText().toString();
                String pass = password.getText().toString();

                if(fName.isEmpty() || lName.isEmpty() || e_mail.isEmpty() || pass.isEmpty()){
                    return;
                }
                registerWithEMail(fName, lName, e_mail, pass);
            }
        });
    }

    private void registerWithEMail(String firstName, String lastName, String email, String password) {

        String fullName = firstName + " " + lastName;


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            //FirebaseUser user = mAuth.getCurrentUser();
                            String cartId = cartsRef.push().getKey();
                            DatabaseReference cartRef = cartsRef.child(cartId);
                            Cart cart = new Cart();
                            cart.setCreated(true);
                            cartRef.setValue(cart);
                            usersRef.push().setValue(new User(fullName, email, cartId));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

}