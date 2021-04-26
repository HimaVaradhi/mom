package in.amruthashala.momapp.ViewHolders;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.screens.ViewPayout;

public class NextPayoutHolder extends RecyclerView.ViewHolder {


    public NextPayoutHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(Context context, NextPayoutHolder nextPayoutHolder) {
        nextPayoutHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ViewPayout.class));
            }
        });
    }
}
