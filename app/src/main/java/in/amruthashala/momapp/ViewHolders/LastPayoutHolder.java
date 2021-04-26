package in.amruthashala.momapp.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.screens.NextPayout;
import in.amruthashala.momapp.screens.ViewPayout;

public class LastPayoutHolder extends RecyclerView.ViewHolder {

    public LastPayoutHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(Context context,LastPayoutHolder lastPayoutHolder){
        lastPayoutHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, NextPayout.class));
            }
        });
    }
}
