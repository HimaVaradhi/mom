package in.amruthashala.momapp.Adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.amruthashala.momapp.R;
import in.amruthashala.momapp.ViewHolders.PayoutReportHolder;

public class PayoutReportAdapater extends RecyclerView.Adapter<PayoutReportHolder> {
    Context context;

    public PayoutReportAdapater(Context context){
        this.context=context;
    }


    @NonNull
    @Override
    public PayoutReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PayoutReportHolder(LayoutInflater.from(context).inflate(R.layout.layout_report,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PayoutReportHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
