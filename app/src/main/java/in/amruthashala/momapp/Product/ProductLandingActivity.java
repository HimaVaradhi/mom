package in.amruthashala.momapp.Product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import in.amruthashala.momapp.R;

public class ProductLandingActivity extends AppCompatActivity {

    ImageView ivAddProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_landing);
        ivAddProduct = findViewById(R.id.ivAddProduct);

        ivAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ProductLandingActivity.this , AddProductActivity.class);
                startActivity(in);
            }
        });
    }
}